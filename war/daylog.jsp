<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

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
<p>Hi, <%= user.getNickname() %>! 

<form method="post" action="/sign" name="list">
	<a href="javascript:document.forms['list'].submit()">View your reminders</a>
</form> 


<form action="/sign" method="post" style="background: none repeat scroll 0 0 #EBF2DD; width: 500px; padding: 4px;">
   <div>Send this reminder in: 
   <select name="reminderTime">
	<option value="hour">1 hour</option>
	<option value="day">1 day</option>
	<option value="week">1 week</option>
	<option value="month">1 month</option>
   </select>
   </div>
   <br />
   Reminder text <i>(enter text or a link)</i>:
   <div><textarea name="content" rows="7" cols="60"></textarea></div>
   <br />
   <div><input type="submit" value="Remind me later" /></div>
</form>

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></p>
<%
    } else {
%>
<h3>A Ticklr File</h3>
<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Create</a> your ticklr file today.</p>
<p><a href="http://wiki.43folders.com/index.php/Tickler_file" style="font-size:14px; text-decoration:none;"><i>What's a Tickler File?</i></a>
<span style="font-size:12px;">"A tickler file is a collection of date-labeled file folder organized in a way that allows time-sensitive documents to be filed according to the future date on which each document needs action."</span>
</p>
<p>Ideas for your ticklr file:

<ul>
<li>send yourself motivational quotes and photos</li>
<li>remember birthdays, meetings, appointments</li>
<li>actually send yourself an article at a time when you are likely to read it</li>
<li>review your cliff notes the day before the big meeting</li>
<li>divide course material up into consumable, accessible chunks</li>
</ul>
</p>
<%
    }
%>

  </body>
</html>
