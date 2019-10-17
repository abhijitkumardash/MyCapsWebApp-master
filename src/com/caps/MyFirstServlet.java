package com.caps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/date")
public class MyFirstServlet  extends HttpServlet{
	
	public MyFirstServlet() {
		System.out.println("Instantiation Phase.");
	}
	 
	@Override
	public void init() throws ServletException {
		System.out.println("Init phase");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Service Phase");
		PrintWriter out = resp.getWriter();
		out.println(new Date());
		ServletContext ctx = getServletContext();
		String email = ctx.getInitParameter("email");
		out.println(email);
	}
	
	@Override
	public void destroy() {
		System.out.println("Bye bye world.. :( ");
	}
}
