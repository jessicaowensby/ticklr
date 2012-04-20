<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.jros.Daylog" %>
<%@ page import="com.jros.PMF" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<a href="listReminders.jsp">Back</a><br />

<%
String ex = "";
try{
	PersistenceManager pm = PMF.get().getPersistenceManager();
	String id = request.getParameter("id");
    String query = "select from " + Daylog.class.getName() + " where id == idParam ";
    List<Daylog> daylog = (List<Daylog>) pm.newQuery(query).execute(id);   
    for (Daylog d : daylog) {
	if (d.getAuthor().toString().equals(user.getEmail()) && d.getId().toString().equals(id)){
		//TODO: refactor into a filter
%>
<form action="/sign" method="post" style="background: none repeat scroll 0 0 #EBF2DD; width: 500px; padding: 4px;">
   <div>Send this reminder in: 
   <select name="reminderTime">
	<option value="day">1 hour</option>
	<option value="day">1 day</option>
	<option value="week">1 week</option>
	<option value="month">1 month</option>
   </select>
   </div>
   <br />
   Reminder text <i>(enter text or a link)</i>:
   <div><textarea name="content" rows="7" cols="60"><%= d.getContent()%></textarea></div>
   <br />
   <input name="action" value="delete" type="hidden" />
   <div><input type="submit" value="delete" /></div>
<%
	}
        }
    pm.close();
}
catch(Exception e){
	ex = e.getLocalizedMessage();
}
%>
<%=ex %>

</form>

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>

<%
    }
%>


  </body>
</html>
