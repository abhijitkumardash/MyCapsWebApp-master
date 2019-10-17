package com.caps;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caps.beans.Cat;
import com.caps.beans.Dog;

@WebServlet(urlPatterns="/serv1", 
		initParams=@WebInitParam(name="pwd",value="root"))
public class Serv1 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Serv1");
		Dog d = new Dog();
		d.setName("Bingo");
		d.setColor("White");
		d.setBreed("Pomerian");
		
		req.setAttribute("dog", d);
		
		Cat c = new Cat();
		c.setName("Genie");
		c.setColor("White");
		c.setGender("Unknown");
		
		ServletContext ctx = getServletContext();
		ctx.setAttribute("cat", c);
		
		
		
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/serv2");
		dispatcher.forward(req, resp);
	}
}
