<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.jros.Daylog" %>
<%@ page import="com.jros.PMF" %>
<%@ page import="java.util.Date" %>
<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>
  <h3>Your Reminders</h3>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<%--show reminders here --%>

<%=request.getAttribute("html") %>

<a href="daylog.jsp">Create another reminder</a>

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