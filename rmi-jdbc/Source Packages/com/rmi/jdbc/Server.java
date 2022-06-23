package com.rmi.jdbc;

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
		Registry registry = LocateRegistry.createRegistry(4000);
		JdbcServiceImpl jdbcServiceImpl = new JdbcServiceImpl();
		registry.rebind("db", jdbcServiceImpl);
		System.out.println("Server is running....");
	}

}
