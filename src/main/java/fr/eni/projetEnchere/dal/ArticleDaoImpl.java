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

import fr.eni.projetEnchere.bo.Article;
import fr.eni.projetEnchere.bo.Categorie;

public class ArticleDaoImpl implements ArticleDAO {

	private final static String SELECT_ALL = "SELECT * FROM article ;";
	private final static String SELECT_CATEGORIE = "SELECT * FROM categorie;";
	private final static String CAT_BY_NOARTICLE = "SELECT nom_categorie, libelle FROM categorie WHERE nom_categorie = ?;";
	private final static String CAT_BY_LIBEL = "SELECT nom_categorie, libelle FROM categorie WHERE libelle = ?;";
	private final static String UPDATE_ARTICLE = "UPDATE article SET (nom_article, description, date_debut, date_fin, prix_initial, nom_categorie VALUES (?,?,?,?,?,?) WHERE no_article = ?;";
	private final static String INSERT_ARTICLE = "{call dbo.insertArticle (?,?,?,?,?,?,?,?)}";
	private final static String DELETE_ARTICLE = "DELETE FROM article WHERE no_article = ?;";
	
	@Override
	public List<Article> listerArticle() throws DalException {
		Connection cnx = null;
		Statement st = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl user = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			st = cnx.createStatement();
			rs = st.executeQuery(SELECT_ALL);
			
			while(rs.next()) {
				art = new Article();
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(user.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(user.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				listeArticle.add(art);
			}
		} catch (SQLException e) {
			throw new DalException("Erreur SQL listerArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(st);
		}
		return listeArticle;
	}
	
	@Override
	public List<Categorie> listerCategorie() throws DalException{
		Connection cnx = null;
		Statement st = null;
		ResultSet rs = null;
		List<Categorie> listeCat = new ArrayList<>();
		Categorie cat;
		
		try {
			cnx = ConnectionProvider.getConnection();
			st = cnx.createStatement();
			rs = st.executeQuery(SELECT_CATEGORIE);
			while(rs.next()) {
				cat = new Categorie(rs.getString("libelle"));
						cat.setNomCategorie(rs.getString("nom_categorie"));;
				listeCat.add(cat);
			}
		} catch (SQLException e) {
			throw new DalException("Erreur SQL listerCategorie()", e);
		} finally {
			ConnectionProvider.seDeconnecter(st);
		}
		return listeCat;
	}
	
	@Override
	public Categorie rechercherCategorieParNom(String nom) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Categorie cat = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(CAT_BY_NOARTICLE);
			pst.setString(1, nom);
			rs = pst.executeQuery();
			if(rs.next()) {
				cat = new Categorie();
				cat.setNomCategorie(rs.getString("nom_categorie"));
				cat.setLibelle(rs.getString("libelle"));
			}
		} catch (SQLException e) {
			throw new DalException("Erreur SQL rechercherCategorie()", e);
		}
//		} finally {
//			ConnectionProvider.seDeconnecter(pst);
//		}
		return cat;
	}
	
	@Override
	public Categorie rechercherCategorieParLibelle(String libelle) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Categorie cat = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(CAT_BY_LIBEL);
			pst.setString(1, libelle);
			rs = pst.executeQuery();
			if(rs.next()) {
				cat = new Categorie();
				cat.setNomCategorie(rs.getString("nom_categorie"));
				cat.setLibelle(rs.getString("libelle"));
			}
		} catch (SQLException e) {
			throw new DalException("Erreur SQL rechercherCategorie()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
		}
		return cat;
	}
	
	@Override
	public void modifierArticle(Article art) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(UPDATE_ARTICLE);
			pst.setString(1, art.getNomArticle());
			pst.setString(2, art.getDescription());
			pst.setDate(3, art.getDateDebutEncheres());
			pst.setDate(4, art.getDateFinEncheres());
			pst.setInt(5, art.getPrixInitial());
			pst.setString(6, art.getCategorie().getNomCategorie());
		} catch (SQLException e) {
			throw new DalException("Erreur SQL modifierArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
		}
	}
	
	@Override
	public void ajouterArticle(Article art) throws DalException {
		Connection cnx = null;
		CallableStatement cst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			cst = cnx.prepareCall(INSERT_ARTICLE);
			cst.setString(2, art.getNomArticle());
			cst.setString(3, art.getDescription());
			cst.setDate(4, art.getDateDebutEncheres());
			cst.setDate(5, art.getDateFinEncheres());
			cst.setInt(6, art.getPrixInitial());
			cst.setInt(7, art.getUserVendeur().getNoUtilisateur());
			cst.setString(8, art.getCategorie().getNomCategorie());
			//récupération ID générée par la BDD
			cst.registerOutParameter(1, Types.INTEGER);
			cst.executeUpdate();
			art.setNoArticle(cst.getInt(1));
		} catch (SQLException e) {
			throw new DalException("Erreur SQL ajouterArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(cst);
		}
		
	}
	
	@Override
	public void supprimerArticle(int index) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(DELETE_ARTICLE);
			pst.setInt(1, index);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DalException("Erreur SQL supprimerArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
		}
		
	}
}
