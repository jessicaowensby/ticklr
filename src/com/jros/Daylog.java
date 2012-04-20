package com.jros;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Daylog {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User author;

    @Persistent
    private String content;

    @Persistent
    private Date date;
    
	@Persistent
	private ArrayList<String> log;

	@Persistent
	private long reminderTime;
	
    public Daylog(User author, String content, Date date, ArrayList<String> log, long reminderTime) {
        this.author = author;
        this.content = content;
        this.date = date;
		this.log = log;
		this.reminderTime = reminderTime;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

	public ArrayList<String> getLog(){
		return log;
	}

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	public void setLog(ArrayList<String> log){
		this.log = log;
	}

	public void setReminderTime(long reminderTime) {
		this.reminderTime = reminderTime;
	}

	public long getReminderTime() {
		return reminderTime;
	}
}
