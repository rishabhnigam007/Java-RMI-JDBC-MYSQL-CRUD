package com.rmi.jdbc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author 55683
 * 
 */
public class JdbcServiceImpl extends UnicastRemoteObject implements JdbcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JdbcServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String insert(int id, String name, String gender) throws RemoteException {
		
		String sql="";
		
		try {
			System.out.println("Inside jdbcimpl class !!");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			System.out.println("id : " + id);
			System.out.println("name : " + name);
			System.out.println("gender : " + gender);
			sql = "insert into student (id, name, gender) values (" + id + ", " + name + ", " + gender + ")";
			System.out.println("query : " + sql);
			// This is working fine
//			String sql = "insert into student values (1,'rishabh','male')";
			st.executeUpdate(sql);
			System.out.println("Record inserted successfully !!");

			con.commit();
			// System.out.println("Record successfully saved in the database !!");
			con.close();
			return "Record Inserted Successfully !!";
		} catch (Exception e) {
			System.out.println("query : " + sql);
			return e.toString();
		}

	}

	@Override
	public String delete(int id) throws RemoteException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			String sql = "delete from student where id" + id;
			st.execute(sql);

			System.out.println("Record deleted successfully !!");

			return "Record deleted successfully";
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public String update(int id, String name, String gender) throws RemoteException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");
			Statement st = con.createStatement();

			String query = "update student set id=" + id + ", name=" + name + ", gender=" + gender;

			st.executeUpdate(query);
			System.out.println("Record Updated Successfully !!");
			con.close();
			return "Record Inserted Successfully !!";
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public ArrayList search(int id) throws RemoteException {

		ArrayList student = new ArrayList();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from student where id=" + id);

			System.out.println("All Records !!");

			while (rs.next()) {
				System.out.println(
						"ID : " + rs.getInt(1) + " || Name : " + rs.getString(2) + " || Gender : " + rs.getString(3));
				student.add("id" + rs.getInt("id"));
				student.add("name" + rs.getString("name"));
				student.add("gender" + rs.getString("gender"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

}
