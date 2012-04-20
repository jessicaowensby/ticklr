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
<p>Hello, <%= user.getNickname() %>! 
<br />
<form action="/preferences" method="post">
   <div>Default reminder time: 
   <select name="reminderTime">
	<option value="day">1 day</option>
	<option value="week">1 week</option>
	<option value="month">1 month</option>
   </select></div>
   <div><input type="submit" value="Save" /></div>
</form>
<%
    } else {
%>
<p>Hi!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to send yourself a reminder.</p>
<%
    }
%>

  </body>
</html>