package com.rmi.oraclejdbc;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class Server {

	public Server() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws ExportException,
			RemoteException {

		// Registry registry = LocateRegistry
		// .createRegistry(Registry.REGISTRY_PORT);
		// By default Registry port is 1099
		Registry registry = LocateRegistry.createRegistry(4000);
		JdbcServiceImpl jdbcServiceImpl = new JdbcServiceImpl();
		registry.rebind("db", jdbcServiceImpl);
		System.out.println("Server is running....");
	}

}