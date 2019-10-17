package com.caps.random.jokes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RandomJokeGeneratorPrototype {
	
	public static String getJoke() throws Exception {
//		inserIntoDB(getNameFromFile("G:\\Temp Workspace\\Caps V3\\Names.txt"));
		String[] name = null;
		String n = null;
		while(true) {
			n = getRandomName();
			if(n != null) {
				break;
			}
		}
		name = n.split(" ");
		String fname = name[0];
		String lname= name[1];
		String jokeJson = TheRandomJokeGenerator.getRandomJoke(fname, lname);
		String joke = jokeJson.split(":")[4];//.split("\"")[1]
		return joke;
	}
	
	
	public static String getRandomName() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String name = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String dbUrl="jdbc:mysql://localhost:3306/capsv4_db";
			
			//3rd Way to get a DB Connection
//			String filePath = "F:/Files/db.properties";
//			FileReader reader = new FileReader(filePath);
//			Properties prop = new Properties();
//			prop.load(reader);
//			
			con = DriverManager.getConnection(dbUrl,"root","root");
			
			String getCountSQL = "select count(*) from students_info";
			pstmt = con.prepareStatement(getCountSQL);
			rs = pstmt.executeQuery();
			int count = -1;
			if(rs.next()) {
				count = rs.getInt(1);
//				System.out.println("Count is: "+count);
			}
			int randomId = (int) (Math.random() * count);
			
//			System.out.println("Random num is: "+randomId);
			
			String getRandomNameSQL = "select firstname,lastname from students_info where "
					+ " sid=? and gender='M'";
			
			pstmt2 = con.prepareStatement(getRandomNameSQL);
			pstmt2.setInt(1, randomId);

			rs.close();
			
			rs = pstmt2.executeQuery();
			
			if(rs.next()) {
				name = rs.getString(1) + " " + rs.getString(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			/*
			 * 5. Close all the JDBC Objects
			 */

			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return name;
	}
	public static ArrayList<String> getNameFromFile(String path) throws IOException{
		File file = new File("G:\\Temp Workspace\\Caps V3\\Names.txt");
		FileReader in = new FileReader(file);
		BufferedReader reader = new BufferedReader(in);
		String name = "";
		ArrayList<String> names = new ArrayList<String>();
		while((name = reader.readLine()) != null) {
			names.add(name);
		}
		reader.close();
		return names;
	}
	public static void inserIntoDB(ArrayList<String> names)throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String dbUrl="jdbc:mysql://localhost:3306/capsV3_db";
			
			//3rd Way to get a DB Connection
			String filePath = "F:/Files/db.properties";
			FileReader reader = new FileReader(filePath);
			Properties prop = new Properties();
			prop.load(reader);
			
			con = DriverManager.getConnection(dbUrl, prop);
			
			
			String sql = "insert into person_info values(?,?)";
			pstmt = con.prepareStatement(sql);
			int id = 1;
			for(String name: names) {
				pstmt.setInt(1, id);
				pstmt.setString(2, name);
				id++;
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("Data Inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			/*
			 * 5. Close all the JDBC Objects
			 */

			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
