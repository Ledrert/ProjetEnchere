package fr.eni.projetEnchere.dal;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO {

	protected void seDeconnecter(Connection cnx) {
		try {
			cnx.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
