package fr.eni.projetEnchere.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetEnchere.bo.Utilisateur;

public class UtilisateurDAOImpl extends DAO implements UtilisateurDAO {

	private final static String SQL_INSERT_UTILISATEUR = "{ call dbo.insertUtilisateur(?,?,?,?,?,?,?,?,?,?,?,?) };";
	private final static String SQL_UPDATE_UTILISATEUR = "UPDATE UTILISATEUR SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, "
			+ "code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?;";
	private final static String SQL_DELETE_UTILISATEUR = "{call dbo.suppressionUtilisateur (?) };";
	private final static String SQL_SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEUR WHERE no_utilisateur=?;";
	private final static String SQL_SELECT_ALL = "SELECT * FROM UTILISATEUR;";
	private final static String SQL_SEARCH_PSEUDO = "SELECT pseudo FROM UTILISATEUR WHERE email=?;";
	private final static String SQL_VERIF_ID = "SELECT * FROM UTILISATEUR WHERE pseudo=? AND mot_de_passe=?;";
	
	@Override
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
			cstmt.setString(12, "n");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.executeUpdate();				
			utilisateur.setNoUtilisateur(cstmt.getInt(1));
		
		} catch (SQLException e) {
			throw new DalException("Erreur SQL ajouterutilisateur()", e);
		} finally {
			ConnectionProvider.seDeconnecter(cstmt);
			seDeconnecter(cnx);		
		}
	}
	
	@Override
	public void ModifierUtilisateur(Utilisateur utilisateur) throws DalException {
		Connection cnx = null;
		PreparedStatement pstmt = null;	
		try {
				cnx = ConnectionProvider.getConnection();
				pstmt = cnx.prepareStatement(SQL_UPDATE_UTILISATEUR);
				pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getRue());
				pstmt.setString(7, utilisateur.getCodePostal()); 
				pstmt.setString(8, utilisateur.getVille());
				pstmt.setString(9, utilisateur.getPassword()); 
				pstmt.setInt(10, utilisateur.getNoUtilisateur());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new DalException("Erreur sur la méthode Modifier()", e);
			} finally {
				ConnectionProvider.seDeconnecter(pstmt);
				seDeconnecter(cnx);		
			}
	}
		
	@Override
	public void supprimerUtilisateur (Utilisateur utilisateur) throws DalException {
		Connection cnx = null;
		CallableStatement cstmt = null;		
		System.out.println("test supprimer");
			try {
				cnx = ConnectionProvider.getConnection();
				cstmt = cnx.prepareCall(SQL_DELETE_UTILISATEUR);
				System.out.println(utilisateur.getNoUtilisateur());
				cstmt.setInt(1, utilisateur.getNoUtilisateur());
				cstmt.executeUpdate();
				} catch (SQLException e) {
					throw new DalException("Erreur sur la méthode Supprimer()", e);
				} finally {
				ConnectionProvider.seDeconnecter(cstmt);
			}
		}
				
	@Override
	public Utilisateur selectUtilisateurByiD (int index) throws DalException {
		Connection cnx = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		Utilisateur utilisateur = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SQL_SELECT_UTILISATEUR_BY_ID);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				utilisateur  = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setPassword(rs.getString("mot_de_passe"));
				
				String admin = rs.getString("administrateur");
				
				if (admin.equals("y")) {
					utilisateur.setAdmin(true);
				} else { 
					utilisateur.setAdmin(false);
				}
			} 
			
		} catch (SQLException e) {
			throw new DalException("Erreur sur la méthode SelectById()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pstmt);
			seDeconnecter(cnx);		
		}
		return utilisateur;
	}

	@Override
	public List<Utilisateur> selectAllUtilisateur() throws DalException {
		List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
		Connection cnx = null;
		Statement stmt = null;	
		ResultSet rs = null;
		Utilisateur utilisateur = null;
		try {
			cnx = ConnectionProvider.getConnection();
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_ALL);
			while (rs.next()) {
				utilisateur  = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setPassword(rs.getString("mot_de_passe"));
				String admin = rs.getString("administrateur");
				if (admin.equals("y")) {
					utilisateur.setAdmin(true);
				} else { 
					utilisateur.setAdmin(false);
				}
				listeUtilisateur.add(utilisateur);
			} 				
					
		} catch (SQLException e) {
			throw new DalException("Erreur sur la méthode SelectAll()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(stmt);
		}
		return listeUtilisateur;
	}
	

	@Override
	public String chercherPseudo(String email) throws DalException {
		Connection cnx = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String pseudo = "";
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SQL_SEARCH_PSEUDO);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
				if (rs.next()) {
					pseudo = rs.getString("pseudo");
				}
		} catch (SQLException e) {
		throw new DalException("Erreur sur la méthode chercherPseudo()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pstmt);
			seDeconnecter(cnx);		
		} return pseudo;
}
	
	@Override
	public Utilisateur verifIdentifiants(String pseudo, String password) throws DalException {
		Connection cnx = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		Utilisateur utilisateur = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SQL_VERIF_ID);
			pstmt.setString(1, pseudo);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
				if (rs.next()) {
					utilisateur = new Utilisateur();
					utilisateur.setPseudo(rs.getString("pseudo"));
					utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
					utilisateur.setPrenom(rs.getString("prenom"));
					utilisateur.setNom(rs.getString("nom"));
					utilisateur.setEmail(rs.getString("email"));
					utilisateur.setTelephone(rs.getString("telephone"));
					utilisateur.setRue(rs.getString("rue"));
					utilisateur.setCodePostal(rs.getString("code_postal"));
					utilisateur.setVille(rs.getString("ville"));
					utilisateur.setPassword(rs.getString("mot_de_passe"));
					utilisateur.setCredit(rs.getInt("credit"));
				}
		} catch (SQLException e) {
		throw new DalException("Erreur sur la méthode verifIdentifiants()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pstmt);
			seDeconnecter(cnx);		
		} return utilisateur;
}
}

