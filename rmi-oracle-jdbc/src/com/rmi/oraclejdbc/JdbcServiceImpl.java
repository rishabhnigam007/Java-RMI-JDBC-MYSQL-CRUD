package com.rmi.oraclejdbc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	public String insert(int id, String name, String gender)
			throws RemoteException {

		System.out.println("Inside jdbcimpl class !!");

		String sql = "";

		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			// Connection con = DriverManager.getConnection(
			// "jdbc:oracle:thin:@localhost:1521:XE", "jdbc_db", "1234");
			// Statement st = con.createStatement();
			// con.setAutoCommit(false);

			Statement st = OracleConnection.getConnection().createStatement();
			OracleConnection.getConnection().setAutoCommit(false);
			System.out
					.println("Query should be like : "
							+ "insert into student (id, name, gender) values (2, 'simba', 'male')");
			sql = "insert into student (id, name, gender) values (" + id
					+ ", '" + name + "', '" + gender + "')";
			System.out.println("query : " + sql);
			// This is working fine
			// String sql = "insert into student values (1,'rishabh','male')";
			st.executeUpdate(sql);
			System.out.println("Record inserted successfully !!");

			st.close();
			// con.commit();
			// con.close();

			OracleConnection.getConnection().commit();
			OracleConnection.closeConnection();
			return "Record Inserted Successfully !!";
		} catch (Exception e) {
			System.out.println("query : " + sql);
			return e.toString();
		}

	}

	@Override
	public String insertUsingProcedure(int collegeId, String collegeName,
			String university) throws RemoteException {

		System.out.println("Inside jdbcimpl class !!");

		try {

			CallableStatement cs = OracleConnection.getConnection()
					.prepareCall("{call insertcollege(?,?,?)}");

			cs.setInt(1, collegeId);
			cs.setString(2, collegeName);
			cs.setString(3, university);

			boolean b = cs.execute();
			if (!b) {
				System.out.println("Procedure Called and store value!!");
			} else {
				System.out.println("Procedure Not Called !!");
			}
			cs.close();
			OracleConnection.getConnection().commit();
			OracleConnection.closeConnection();
			return "Record Inserted Successfully using procedure!!";
		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public String delete(int id) throws RemoteException {

		System.out.println("Inside jdbcimpl class !!");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XE", "jdbc_db", "1234");
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
	public String update(int id, String name, String gender)
			throws RemoteException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XE", "jdbc_db", "1234");
			Statement st = con.createStatement();

			String query = "update student set name='" + name + "', gender='"
					+ gender + "' where id=" + id;

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

		System.out.println("Inside jdbcimpl class !!");

		ArrayList student = new ArrayList();

		try {
			Statement st = OracleConnection.getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from student where id="
					+ id);

			while (rs.next()) {
				System.out.println("ID : " + rs.getInt(1) + " || Name : "
						+ rs.getString(2) + " || Gender : " + rs.getString(3));
				student.add(rs.getInt("id"));
				student.add(rs.getString("name"));
				student.add(rs.getString("gender"));
			}
			rs.close();
			st.close();
			OracleConnection.closeConnection();
			System.out.println("Data fetch successfully !!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList findAllStudent() throws RemoteException {
		
		System.out.println("Inside jdbcimpl class !!");

		ArrayList studentlist = new ArrayList();

		ArrayList student = new ArrayList();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XE", "jdbc_db", "1234");

			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("select * from student");

			while (rs.next()) {
				System.out.println("ID : " + rs.getInt(1) + " || Name : "
						+ rs.getString(2) + " || Gender : " + rs.getString(3));
				 Student students = new Student();
				 students.setId(rs.getInt("id"));
				 students.setName(rs.getString("name"));
				 students.setGender(rs.getString("gender"));
				 studentlist.add(students);
				student.add(rs.getInt(1));
				student.add(rs.getString(2));
				student.add(rs.getString(3));
				studentlist.addAll(student);
			}
			System.out.println("Student List : " + studentlist.toString());
			rs.close();
			st.close();
			con.close();
			System.out.println("All Data fetch successfully !!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentlist;
	}

}