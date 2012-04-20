package com.jros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class EnterDaylogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(EnterDaylogServlet.class.getName());
	
	private static long DEFAULT_REMINDER_WAIT = 86400000;
	private static final String BLOCKQUOTE_BEGIN = "<blockquote>";
	private static final String BLOCKQUOTE_END = "</blockquote>";

	
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String content = req.getParameter("content");
        String reminderTime = req.getParameter("reminderTime");
        StringBuilder sb = new StringBuilder(BLOCKQUOTE_BEGIN);
        int count = 1;
        PersistenceManager pm = null;
        
        try {
        pm = PMF.get().getPersistenceManager();
        System.out.println("reminderTime: " + reminderTime);
        if (reminderTime != null)
        {
        	if (reminderTime.equals("week")){
        		DEFAULT_REMINDER_WAIT = 604800000;
        	}
        	if (reminderTime.equals("month")){
        		DEFAULT_REMINDER_WAIT = 604800000 * 4;
        	}
        	if (reminderTime.equals("hour")){
        		DEFAULT_REMINDER_WAIT = 3600000;
        	}        	
	        
	        Date date = new Date();
	        ArrayList<String> al = new ArrayList<String>();
	        Daylog daylog = new Daylog(user, content, date, al, DEFAULT_REMINDER_WAIT);
	        pm.makePersistent(daylog);
	
	        
	        TaskOptions taskOptions =
	        	TaskOptions.Builder.url("/emailDaylog")
	        	.param("address", user.getEmail())
	        	.param("content", daylog.getContent());
	        com.google.appengine.api.labs.taskqueue.Queue queue = QueueFactory.getDefaultQueue();
	        queue.add(taskOptions.countdownMillis(DEFAULT_REMINDER_WAIT));
        }

		String query = "select from " + Daylog.class.getName();
		List<Daylog> daylogs = (List<Daylog>) pm.newQuery(query).execute();
		
		for (Daylog d : daylogs) {
			if (d.getAuthor().toString().equals(user.getEmail())) {
				// TODO: refactor into a filter
				Date now = new Date(System.currentTimeMillis());
				Date taskTime = new Date(d.getReminderTime()
						+ d.getDate().getTime());
				if (taskTime.after(now) || taskTime == now) {
					sb.append(count++);
					sb.append(" ");
					sb.append(d.getContent());
					sb.append("&nbsp;");
					sb.append(getReadableTime(d.getReminderTime()));
					sb.append(" <br />");
				}
			}
		}

    
		sb.append(BLOCKQUOTE_END);
		
		req.setAttribute("html", sb.toString());
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/listReminders.jsp");
		try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        } finally {
        	pm.close();
        }
    }

	public static Logger getLog() {
		return log;
	}
	
	private String getReadableTime(long time){
    	String readableTime = null;
		
		if (time == 604800000){
			readableTime = "week";
    	}
    	if (time == 604800000 * 4){
    		readableTime = "month";
    	}
    	if (time == 3600000){
    		readableTime = "hour";
    	} 
    	
    	return readableTime;
	}
	
}