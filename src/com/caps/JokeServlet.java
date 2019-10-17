package com.caps;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caps.random.jokes.RandomJokeGeneratorPrototype;

@WebServlet("/joke")
public class JokeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try {
			String joke = RandomJokeGeneratorPrototype.getJoke();
			out.print("<p>"+joke+"</p>");
		} catch (Exception e) {
			out.print(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
