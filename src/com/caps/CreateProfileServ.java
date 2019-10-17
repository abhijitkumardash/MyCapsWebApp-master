package com.caps;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createProfile")
public class CreateProfileServ extends HttpServlet {
	@Override
	protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("firstname");
		String lname = req.getParameter("lastname");
		String gender = req.getParameter("gender").toUpperCase();
		PrintWriter out = resp.getWriter();
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbUrl="jdbc:mysql://localhost:3306/capsv4_db";
			con = DriverManager.getConnection(dbUrl,"root","root"); //1st version of getConnection
			String sql = "insert into students_info "
					+ " (firstname,lastname,gender,type,password) "
					+ " values(?,?,?,'N','root')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,fname);
			pstmt.setString(2,lname);
			pstmt.setString(3,gender);
			int count = 0;
			synchronized(this) {
				count = pstmt.executeUpdate();
			}
			if(count > 0) {
				out.println("<h1>Profile Created</h1>");
				Cookie c = new Cookie("name", fname);
				resp.addCookie(c);
			}else {
				out.println("<h1>Failed</h1>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<h1>Failed</h1>");
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
