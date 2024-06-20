package com.twd.flutter.both.connection;

import java.sql.Connection;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
	  private static  String sugarFactoryId;
	public DBConnection(String factoryId){
		this.sugarFactoryId=factoryId;
	}

	static Logger log = Logger.getLogger(DBConnection.class.getName());

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds;
			System.out.println("sugarFactoryId code is in db: "+sugarFactoryId);
			if(Integer.parseInt(sugarFactoryId) == 1) {
				ds = (DataSource) envCtx.lookup("jdbc/flutterJSMPL/unit1");
				return ds.getConnection();
			}
			if(Integer.parseInt(sugarFactoryId) ==2) {
				ds = (DataSource) envCtx.lookup("jdbc/flutterJSMPL/unit2");
				return ds.getConnection();	
			}
			if(Integer.parseInt(sugarFactoryId) ==3) {
				ds = (DataSource) envCtx.lookup("jdbc/flutterJSMPL/unit3");
				return ds.getConnection();
			}
			if(Integer.parseInt(sugarFactoryId) ==4) {
				ds = (DataSource) envCtx.lookup("jdbc/flutterJSMPL/unit4");
				return ds.getConnection();	
			}
			if(Integer.parseInt(sugarFactoryId) ==5) {
				ds = (DataSource) envCtx.lookup("jdbc/flutterJSMPL/unit5");
				return ds.getConnection();	
			}
			
		} catch (Exception se) {
			se.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
