package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MyService;

@WebServlet("/editTask")
public class EditTask extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MyService service = new MyService();
		service.editTask(req, resp);
		
		// Seeing url contents.
	//	System.out.println("Request URL: " + req.getRequestURL() + "?" + req.getQueryString());

		// Seeing url object.
//		System.out.println("Request Object: " + req.toString());
	}
}
