package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.Customer;
import dto.TaskDB;
import helper.AES;

public class MyService {
	
	MyDao dao = new MyDao();
public void saveUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	// TODO Auto-generated method stub
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String passwd = req.getParameter("pass");
			long mobNumb = Long.parseLong(req.getParameter("mobile"));
			LocalDate DateofB = LocalDate.parse(req.getParameter("dob"));
			
			int age = LocalDate.now().getYear() - DateofB.getYear();
			
			
			if(age<18) {
				resp.getWriter().print("<h1>You are not eligible for creating account here</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			}
			else {				
				
				Customer customer2 = dao.findByEmail(email);
				
				if(customer2 == null) {
					Customer customer = new Customer();
					customer.setName(name);
					customer.setAge(age);
					customer.setDob(DateofB);
					customer.setEmail(email);
					customer.setMobNum(mobNumb);
					customer.setPassword(AES.encrypt(passwd));
					
					dao.saveCustomer(customer);
					resp.getWriter().print("<h1 align='center' style='color:green'>Account Created succesfully</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
					
				}
				else
				{
					resp.getWriter().print("<h1 align='center' style='color: red;'>Account with this email already exists..</h1>");
					req.getRequestDispatcher("SignUp.html").include(req, resp);
				}
				
			}
			 

}

public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
	String email = req.getParameter("email");
	String password = req.getParameter("pwd");
	
	
	Customer customer = dao.findByEmail(email);
	
	
	if(customer == null) {
		resp.getWriter().print("<h1 align='center' style='color: red;'> Invalid email</h1>");
	}
	else {
		if(password.equals(AES.decrypt(customer.getPassword()))) {
			// Creating a session.
			req.getSession().setAttribute("customer", customer);
			
			// Set max time interval.
			req.getSession().setMaxInactiveInterval(5000);
			
			// Succesful login.
			resp.getWriter().print("<h1 align='center' style='color: green;'> Login succesful </h1>");
			
			// Fetch all tasks of logged in user.
			List<TaskDB> tasks = dao.fetchTask(customer.getId());
			req.setAttribute("task", tasks);
			
			req.getRequestDispatcher("HomePage.jsp").include(req, resp);
		}
		else {
			resp.getWriter().print("<h1 align='center' style='color: red;'>Invalid password</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		}
	}
}

public void addTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
	String taskName = req.getParameter("task");
	String taskDesc = req.getParameter("taskDesc");
	
	TaskDB task = new TaskDB();
	task.setTaskName(taskName);
	task.setTaskDescription(taskDesc);
	task.setTimeCreated(LocalDateTime.now());
//	task.setStatus(false);
	Customer customer = (Customer)req.getSession().getAttribute("customer");
	task.setCustomer(customer);
	
	
	dao.saveTask(task);
	
	resp.getWriter().print("<h1 style='color: green;'> Task has been added succesfully </h1>");
	
	List<TaskDB> tasks = dao.fetchTask(customer.getId());
	req.setAttribute("task", tasks);
	
	
	req.getRequestDispatcher("HomePage.jsp").include(req, resp);
}

public void completeTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	int id=Integer.parseInt(req.getParameter("id"));
	TaskDB tasks=dao.findById(id);
	tasks.setStatus(true);
	dao.updateTask(tasks);
	
	resp.getWriter().print("<h1 align='center' style='color:green'>Status Change Success</h1>");
	
	Customer customer=(Customer) req.getSession().getAttribute("customer");
	List<TaskDB> task=dao.fetchTask(customer.getId());
	req.setAttribute("task",task);
	
	req.getRequestDispatcher("HomePage.jsp").include(req, resp);
}

public void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	int id=Integer.parseInt(req.getParameter("id"));
	TaskDB tasks=dao.findById(id);
	
	dao.deleteTask(tasks);
	
	resp.getWriter().print("<h1 align='center' style='color:green'>Task Deleted Success</h1>");
	Customer customer=(Customer) req.getSession().getAttribute("customer");
	List<TaskDB> task=dao.fetchTask(customer.getId());
	req.setAttribute("task",task);
	
	req.getRequestDispatcher("HomePage.jsp").include(req, resp);
}

public void editTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	// TODO Auto-generated method stub
	int id = Integer.parseInt(req.getParameter("id"));
	String taskUpdatedName = req.getParameter("tname");
	String taskUpdatedDesc = req.getParameter("tdesc");
	
//	TaskDB tasks=dao.findById(id);
	
	dao.editTask(id, taskUpdatedName, taskUpdatedDesc);
	
	System.out.println(id);
	System.out.println(taskUpdatedDesc);
	System.out.println(taskUpdatedName);
	
	resp.getWriter().print("<h1 align='center' style='color:green'>Task edited Success</h1>");
	Customer customer=(Customer) req.getSession().getAttribute("customer");
	List<TaskDB> task=dao.fetchTask(customer.getId());
	req.setAttribute("task",task);
	
	req.getRequestDispatcher("HomePage.jsp").include(req, resp);
	
	
}

}
