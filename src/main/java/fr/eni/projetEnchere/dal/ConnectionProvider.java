package fr.eni.projetEnchere.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
	
	private static DataSource dataSource;

	static {	
		Context context;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cnx_pool");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	} 
	
	public static void seDeconnecter(Statement st) throws DalException {
		
		try {
			if(st != null) {
			st.close();
			}
		} catch (SQLException e) {
			throw new DalException("Problème fermeture Statement");
		}
		
	}
	
	public static void seDeconnecter(PreparedStatement pst) throws DalException {
		
		try {
			if(pst != null) {
			pst.close();
			}
		} catch (SQLException e) {
			throw new DalException("Problème fermeture PreparedStatement");
		}
		
	}
	
	public static void seDeconnecter(CallableStatement cst) throws DalException {
		
		try {
			if(cst != null) {
			cst.close();
			}
		} catch (SQLException e) {
			throw new DalException("Problème fermeture CallableStatement");
		}
	}
	
	public static void seDeconnecter(Connection cnx) {
		try {
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
