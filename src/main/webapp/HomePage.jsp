<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List" %>
<%@page import="dto.TaskDB" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<style>
*{
text-align='center';
}
</style>
</head>
<body>
<% List<TaskDB> task = (List<TaskDB>)request.getAttribute("task"); %>

<div>
<h1> Welcome to Home page.</h1>
<h2> Viewing your to do list here.</h2>
<table style='border:1px solid black;' border="1px" cellpadding="10px" cellspacing="1px">
</thead>
<tr>
<th>Task name</th>
<th>Task description</th>
<th>Date created</th>
<th>Status</th>
<th>Delete</th>
<th>Edit</th>
</tr>
</thead>


<% 
	if(task!=null){
		for(TaskDB tasks:task){ 
%>
			<tr>
				<td><%=tasks.getTaskName()%></td>
				<td><%=tasks.getTaskDescription()%></td>
				<td><%=tasks.getTimeCreated()%></td>
				<td><% if(tasks.isStatus()){%>
				Completed
				<%}else{ %>
				<a href="complete?id=<%=tasks.getTaskId()%>"><button style='color: red;'>Pending</button><%}%></a></td>
				<td><a href="delete?id=<%=tasks.getTaskId()%>"><button>Delete</button></a></td>
				<td><a href="EditTask.jsp?id=<%=tasks.getTaskId()%>"><button>Edit</button></a></td>
			</tr>
<% 		}
	}
%>
</table>
<br>
<a href="task.html"><button>Add Task</button></a>
<a href="logout"><button>Logout</button></a>
</div>

</body>
</html>