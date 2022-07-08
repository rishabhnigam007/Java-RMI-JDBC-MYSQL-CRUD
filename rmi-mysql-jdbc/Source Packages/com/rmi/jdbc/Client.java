package com.rmi.jdbc;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws RemoteException, NotBoundException, Exception {

		JdbcService jdbcService = (JdbcService) Naming.lookup("rmi://localhost:4000/db");

		Scanner sc = new Scanner(System.in);

		int choice;

		System.out.println("Please enter your choice");
		System.out.println("1. Insert record");
		System.out.println("2. Delete record");
		System.out.println("3. Update record");
		System.out.println("4. Search record");

		choice = sc.nextInt();
		switch (choice) {
		case 1:
			try {
				int id;
				String name, gender;
				jdbcService = (JdbcService) Naming.lookup("rmi://localhost:4000/db");

				System.out.println("Enter ID : ");
				id = sc.nextInt();
				System.out.println("Enter Name : ");
				name = sc.next();
				System.out.println("Enter Gender : ");
				gender = sc.next();

				String result = jdbcService.insert(id, name, gender);

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				int id;
				jdbcService = (JdbcService) Naming.lookup("rmi://localhost:4000/db");

				System.out.println("Enter ID : ");
				id = sc.nextInt();

				String result = jdbcService.delete(id);

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				int id;
				String name, gender;
				jdbcService = (JdbcService) Naming.lookup("rmi://localhost:4000/db");

				System.out.println("Enter ID : ");
				id = sc.nextInt();
				System.out.println("Enter Name : ");
				name = sc.next();
				System.out.println("Enter Gender : ");
				gender = sc.next();

				String result = jdbcService.update(id, name, gender);

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:
			try {
				int id;
				jdbcService = (JdbcService) Naming.lookup("rmi://localhost:4000/db");

				System.out.println("Enter ID : ");
				id = sc.nextInt();

				ArrayList result = jdbcService.search(id);

				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Wrong choice");
			System.exit(0);
		}

		sc.close();
	}

}
