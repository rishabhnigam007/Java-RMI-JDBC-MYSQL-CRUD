package com.rmi.oraclejdbc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface JdbcService extends Remote {

	public String insert(int id, String name, String gender)
			throws RemoteException;

	public String delete(int id) throws RemoteException;

	public String update(int id, String name, String gender)
			throws RemoteException;

	@SuppressWarnings("rawtypes")
	public ArrayList search(int id) throws RemoteException;

	@SuppressWarnings("rawtypes")
	public ArrayList findAllStudent() throws RemoteException;

	public String insertUsingProcedure(int collegeId, String collegeName,
			String university) throws RemoteException;

}