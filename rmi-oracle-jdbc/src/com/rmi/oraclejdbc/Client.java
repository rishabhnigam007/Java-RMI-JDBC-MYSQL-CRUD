package com.rmi.oraclejdbc;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws RemoteException,
			NotBoundException, Exception {

		Logger logger = LoggerFactory.getLogger(Client.class);

		JdbcService jdbcService = (JdbcService) Naming
				.lookup("rmi://localhost:4000/db");

		Scanner sc = new Scanner(System.in);

		int flag = 1;
		while(flag != 0) {

			int choice;

			System.out.println("Please enter your choice");
			System.out.println("1. Insert record");
			System.out.println("2. Delete record");
			System.out.println("3. Update record");
			System.out.println("4. Search record");
			System.out.println("5. Print all record");
			System.out.println("6. Insert record using Procedure");

			choice = sc.nextInt();
			switch (choice) {
			case 1:
				try {
					logger.info("inside insert method..");
					int id;
					String name, gender;
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					System.out.println("Enter ID : ");
					id = sc.nextInt();
					System.out.println("Enter Name : ");
					name = sc.next();
					System.out.println("Enter Gender : ");
					gender = sc.next();

					String result = jdbcService.insert(id, name, gender);

					System.out.println(result);
					logger.info("exiting insert method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					logger.info("inside delete method..");
					int id;
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					System.out.println("Enter ID : ");
					id = sc.nextInt();

					String result = jdbcService.delete(id);

					System.out.println(result);
					logger.info("exiting delete method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					logger.info("inside update method..");
					int id;
					String name, gender;
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					System.out.println("Enter ID : ");
					id = sc.nextInt();
					System.out.println("Enter Name : ");
					name = sc.next();
					System.out.println("Enter Gender : ");
					gender = sc.next();

					String result = jdbcService.update(id, name, gender);

					System.out.println(result);
					logger.info("exiting update method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					logger.info("inside find method..");
					int id;
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					System.out.println("Enter ID : ");
					id = sc.nextInt();

					ArrayList result = jdbcService.search(id);

					System.out.println(result);
					logger.info("exiting find method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					logger.info("inside print all method..");
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					ArrayList result = jdbcService.findAllStudent();

					/*
					 * for (Student s : result) System.out.println(s);
					 */
					System.out.println(result.toString());
					logger.info("exiting print all method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					logger.info("inside insert method using Procedure..");
					int collegeId;
					String collegeName, university;
					jdbcService = (JdbcService) Naming
							.lookup("rmi://localhost:4000/db");

					System.out.println("Enter College ID : ");
					collegeId = sc.nextInt();
					System.out.println("Enter College Name : ");
					collegeName = sc.next();
					System.out.println("Enter University : ");
					university = sc.next();

					String result = jdbcService.insertUsingProcedure(collegeId,
							collegeName, university);

					System.out.println(result);
					logger.info("exiting insert method..");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Wrong choice");
				System.exit(0);
			}
			System.out.println("Do you want to continue : ");
			System.out.println("1. for continue");
			System.out.println("0. for exit");
			flag = sc.nextInt();
		}
	}
}