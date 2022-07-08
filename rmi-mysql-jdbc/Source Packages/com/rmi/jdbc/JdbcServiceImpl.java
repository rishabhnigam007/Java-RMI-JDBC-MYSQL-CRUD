package com.rmi.jdbc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author Rishabh
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

		String sql = "";

		try {
			System.out.println("Inside jdbcimpl class !!");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			System.out.println(
					"Query should be like : " + "insert into student (id, name, gender) values (2, 'simba', 'male')");
			sql = "insert into student (id, name, gender) values (" + id + ", '" + name + "', '" + gender + "')";
			System.out.println("query : " + sql);
//			This is working fine
//			String sql = "insert into student values (1,'rishabh','male')";
			st.executeUpdate(sql);
			System.out.println("Record inserted successfully !!");

			st.close();
			con.commit();
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
			String sql = "delete from student where id=?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
			ps.close();
			con.close();
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

			String query = "update student set name='" + name + "', gender='" + gender + "' where id=" + id;

			st.executeUpdate(query);
			System.out.println("Record Updated Successfully !!");
			st.close();
			con.close();
			return "Record Updated Successfully !!";
		} catch (Exception e) {
			return e.toString();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList search(int id) throws RemoteException {

		ArrayList student = new ArrayList();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "1234");

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from student where id=" + id);

			while (rs.next()) {
				System.out.println(
						"ID : " + rs.getInt(1) + " || Name : " + rs.getString(2) + " || Gender : " + rs.getString(3));
				student.add(rs.getInt("id"));
				student.add(rs.getString("name"));
				student.add(rs.getString("gender"));
			}
			rs.close();
			st.close();
			con.close();
			System.out.println("Data fetch successfully !!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

}
