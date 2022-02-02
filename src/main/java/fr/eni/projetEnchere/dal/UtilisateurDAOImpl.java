package fr.eni.projetEnchere.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import fr.eni.projetEnchere.bo.Utilisateur;

public class UtilisateurDAOImpl {

	private final static String SQL_INSERT_UTILISATEUR = "{ call dbo.insertUtilisateur(?,?,?,?,?,?,?,?,?,?) };";
	private final static String SQL_UPDATE_UTILISATEUR = "UPDATE UTILISATEUR SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, "
			+ "code_postal=?, ville=?, mot_de_passe=? WHERE noUtilisateur=?);";
	private final static String SQL_DELETE_UTILISATEUR = "DELETE FROM UTILISATEUR WHERE noUtilisateur=?";
	private final static String SQL_SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEUR WHERE noUtilisateur=?;";
	private final static String SQL_SELECT_ALL = "SELECT * FROM UTILISATEUR;";
	
	
	public void AjouterUtilisateur(Utilisateur utilisateur) throws DalException {	
		Connection cnx = null;
		CallableStatement cstmt = null;
		try {
			cnx = ConnectionProvider.getConnection();
			cstmt = cnx.prepareCall(SQL_INSERT_UTILISATEUR);
			cstmt.setString(2, utilisateur.getPseudo());
			cstmt.setString(3, utilisateur.getNom());
			cstmt.setString(4, utilisateur.getPrenom());
			cstmt.setString(5, utilisateur.getEmail());
			cstmt.setString(6, utilisateur.getTelephone());
			cstmt.setString(7, utilisateur.getRue());
			cstmt.setString(8, utilisateur.getCodePostal());
			cstmt.setString(9, utilisateur.getVille());
			cstmt.setString(10, utilisateur.getPassword());
			cstmt.setInt(11, utilisateur.getCredit());
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.executeUpdate();				
			utilisateur.setNoUtilisateur(cstmt.getInt(1));
		
		} catch (SQLException e) {
			throw new DalException("Erreur SQL ajouterutilisateur()", e);
		} finally {
			ConnectionProvider.seDeconnecter(cstmt);
		}
	}
	
	public void ModifierUtilisateur(Utilisateur utilisateur) {
			try {
				Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE_UTILISATEUR);
				pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail() );
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getRue());
				pstmt.setString(7, utilisateur.getCodePostal()); 
				pstmt.setString(8, utilisateur.getVille());
				pstmt.setString(9, utilisateur.getPassword()); 
				pstmt.setInt(10, utilisateur.getNoUtilisateur());
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//deconnexion
			}
	}
		
	public void supprimerUtilisateur (Utilisateur utilisateur) {
			try {
				Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE_UTILISATEUR);	
				pstmt.setInt(1, utilisateur.getNoUtilisateur());
				pstmt.executeUpdate();
				} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//deconnexion
			}
		}
					
	public Utilisateur selectUtilisateurByiD (int index) {
		try {
			Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID);
			pstmt.setInt(1, index);
			pstmt.executeQuery();
			//rs ?
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void selectAllUtilisateur (Utilisateur utilisateur) {
		try {
			Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();
			stmt.executeQuery(SQL_SELECT_ALL);	
			//rs ?
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		}
	
	
	
	
		
}
	

