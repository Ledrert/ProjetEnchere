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
import fr.eni.projetEnchere.bo.Enchere;
import fr.eni.projetEnchere.bo.Retrait;
import fr.eni.projetEnchere.bo.Utilisateur;

public class ArticleDaoImpl implements ArticleDAO {

	private final static String SELECT_ALL = "SELECT * FROM article ;";
	private final static String SELECT_BY_ID = "SELECT * FROM article WHERE no_article =?;";
	private final static String SELECT_CATEGORIE = "SELECT * FROM categorie;";
	private final static String CAT_BY_NOARTICLE = "SELECT nom_categorie, libelle FROM categorie WHERE nom_categorie = ?;";
	private final static String CAT_BY_LIBEL = "SELECT nom_categorie, libelle FROM categorie WHERE libelle = ?;";
	private final static String UPDATE_ARTICLE = "UPDATE article SET (nom_article, description, date_debut, date_fin, prix_initial, nom_categorie VALUES (?,?,?,?,?,?) WHERE no_article = ?;";
	private final static String INSERT_ARTICLE = "{call dbo.insertArticle (?,?,?,?,?,?,?,?)}";
	private final static String DELETE_ARTICLE = "DELETE FROM article WHERE no_article = ?;";
	
	private final static String SEARCH_ACHATS = "select * from article where no_utilisateur != ?;";
	private final static String ENCH_EC = "select * from article where date_debut_encheres < getdate() and date_fin_encheres > getdate() and no_utilisateur != ? \r\n"
			+ "and no_article not in (select no_article from enchere where no_utilisateur = ?);";
	private final static String MY_ENCH_EC = "select a.* from enchere e JOIN article a on e.no_article = a.no_article WHERE a.date_fin_encheres > getDate() and e.no_utilisateur = ? and a.no_utilisateur != ?;";
	private final static String MY_ENCH_WIN = "select * from article WHERE no_acheteur = ?;";
	
	private final static String SEARCH_VENTES = "select * from article where no_utilisateur = ?;";
	private final static String VENTE_EC = "select * from article where date_debut_encheres < getdate() and date_fin_encheres > getdate() and no_utilisateur = ?;";
	private final static String MY_VENTE_PREP = "select * from article WHERE date_debut_encheres > getdate() and no_utilisateur = ?;";
	private final static String MY_VENTE_END = "select * from article WHERE date_fin_encheres < getdate()and no_utilisateur = ?;";

	private final static String AJOUT_ENCHERE = "INSERT INTO enchere VALUES (?,?,?,?);";
	private final static String SELECT_ACHETEUR = "SELECT no_utilisateur, montant_enchere FROM enchere e INNER JOIN \r\n"
			+ "(select no_article, MAX(date_enchere) as lastEnchere from enchere where no_article = ? group by no_article) m \r\n"
			+ "ON e.no_article = m.no_article and e.date_enchere = m.lastEnchere;";
	private final static String UPDATE_FIN_ENCHERE = "UPDATE article SET no_acheteur = ?, prix_vente = ? WHERE no_article = ?;";
	private final static String SELECT_ENCHERES_ART = "SELECT * FROM enchere WHERE no_article = ?;";
	
	private final static String AJOUT_RETRAIT = "INSERT INTO retrait VALUES (?,?,?,?);";
	private final static String SELECT_RETRAIT = "SELECT * FROM retrait WHERE no_article=?;";
	
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
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(user.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(user.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException e) {
			throw new DalException("Erreur SQL listerArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(st);
			ConnectionProvider.seDeconnecter(cnx);
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
			ConnectionProvider.seDeconnecter(cnx);
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
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
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
			ConnectionProvider.seDeconnecter(cnx);
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
			ConnectionProvider.seDeconnecter(cnx);
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
			//r?cup?ration ID g?n?r?e par la BDD
			cst.registerOutParameter(1, Types.INTEGER);
			cst.executeUpdate();
			art.setNoArticle(cst.getInt(1));
			ajoutRetrait(art.getRetrait());
		} catch (SQLException e) {
			throw new DalException("Erreur SQL ajouterArticle()", e);
		} finally {
			ConnectionProvider.seDeconnecter(cst);
			ConnectionProvider.seDeconnecter(cnx);
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
			ConnectionProvider.seDeconnecter(cnx);
		}
		
	}

	@Override
	public List<Article> listerAchats(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(SEARCH_ACHATS);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerAchats()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}

	@Override
	public List<Article> listerEnchereOuvertes(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(ENCH_EC);
			pst.setInt(1, user.getNoUtilisateur());
			pst.setInt(2, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerEnchereEnCours()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> listerEnchereEnCours(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(MY_ENCH_EC);
			pst.setInt(1, user.getNoUtilisateur());
			pst.setInt(2, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerEnchereEnCours()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> chercherEnchereRemportee(Utilisateur user) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(MY_ENCH_WIN);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL chercherEnchereRemportee()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> listerVentes(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(SEARCH_VENTES);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerVentes()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> listerVentesEnCours(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(VENTE_EC);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerAchats()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}

	@Override
	public List<Article> listerVentesNonDebut(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(MY_VENTE_PREP);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerVentesNonDebut()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	@Override
	public List<Article> listerVentesTerminees(Utilisateur user) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Article> listeArticle = new ArrayList<Article>();
		Article art;
		UtilisateurDAOImpl userDao = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(MY_VENTE_END);
			pst.setInt(1, user.getNoUtilisateur());
			rs = pst.executeQuery();
			while(rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDao.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDao.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
				listeArticle.add(art);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur SQL listerVentesTerminees()", e);
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return listeArticle;
	}
	
	public Article selectByID(int no_article) throws DalException {
		Connection cnx = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		Article art = null;
		try {
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, no_article);
			rs = pstmt.executeQuery();
			
			UtilisateurDAOImpl userDAO = new UtilisateurDAOImpl();
			
			if (rs.next()) {
				art = new Article();
				art.setNoArticle(rs.getInt("no_article"));
				art.setNomArticle(rs.getString("nom_article"));
				art.setDescription(rs.getString("description"));
				art.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				art.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				art.setPrixInitial(rs.getInt("prix_initial"));
				art.setPrixVente(rs.getInt("prix_vente"));
				art.setUtilisateurVendeur(userDAO.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				art.setUtilisateurAcheteur(userDAO.selectUtilisateurByiD(rs.getInt("no_acheteur")));
				art.setCategorie(rechercherCategorieParNom(rs.getString("nom_categorie")));
				art.setRetrait(recupererRetrait(art));
				art.setListeEnchere(recupererEnchereArticle(art));
			}
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode select art by id", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pstmt);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return art;
	}
	
	@Override
	public Enchere dernierEncherisseur(Article art) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;	
		ResultSet rs = null;
		Enchere vente = null;
		UtilisateurDAOImpl userDAO = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(SELECT_ACHETEUR);
			pst.setInt(1, art.getNoArticle());
			rs = pst.executeQuery();
			if(rs.next()) {
				Utilisateur user = userDAO.selectUtilisateurByiD(rs.getInt("no_utilisateur"));
				int montant = rs.getInt("montant_enchere");
				vente = new Enchere();
				vente.setNoEncherisseur(user);
				vente.setMontantEnchere(montant);
			}
		} catch (SQLException | DalException e) {
			throw new DalException("Erreur sur la m?thode dernierEncherisseur()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return vente;
	}
	
	@Override
	public void updateFinEnchere(Article art, Enchere vente) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(UPDATE_FIN_ENCHERE);
			pst.setInt(1, vente.getNoEncherisseur().getNoUtilisateur());
			pst.setInt(2, vente.getMontantEnchere());
			pst.setInt(3, art.getNoArticle());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode updateFinEnchere()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
	}

	@Override
	public void ajoutRetrait(Retrait ret) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(AJOUT_RETRAIT);
			pst.setInt(1, ret.getArticle().getNoArticle());
			pst.setString(2, ret.getRue());
			pst.setString(3, ret.getCodePostal());
			pst.setString(4, ret.getVille());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode ajoutRetrait()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
	}
	
	@Override
	public void ajoutEnchere(Enchere enc) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(AJOUT_ENCHERE);
			pst.setTimestamp(1, enc.getDateEnchere());
			pst.setInt(2, enc.getNoEncherisseur().getNoUtilisateur());
			pst.setInt(3, enc.getArticleVendu().getNoArticle());
			pst.setInt(4, enc.getMontantEnchere());
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode ajoutEnchere()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
	}
	
	@Override
	public Retrait recupererRetrait(Article art) throws DalException {
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Retrait ret = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(SELECT_RETRAIT);
			pst.setInt(1, art.getNoArticle());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				ret = new Retrait();
				ret.setArticle(art);
				ret.setRue(rs.getString("rue"));
				ret.setCodePostal(rs.getString("code_postal"));
				ret.setVille(rs.getString("ville"));
			}
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode recupererRetrait()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return ret;
	}
	
	public List<Enchere> recupererEnchereArticle(Article art) throws DalException{
		Connection cnx = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Enchere enc = null;
		List<Enchere> liste = new ArrayList<Enchere>();
		UtilisateurDAOImpl user = new UtilisateurDAOImpl();
		
		try {
			cnx = ConnectionProvider.getConnection();
			pst = cnx.prepareStatement(SELECT_ENCHERES_ART);
			pst.setInt(1, art.getNoArticle());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				enc = new Enchere();
				enc.setArticleVendu(art);
				enc.setDateEnchere(rs.getTimestamp("date_enchere"));
				enc.setMontantEnchere(rs.getInt("montant_enchere"));
				enc.setNoEncherisseur(user.selectUtilisateurByiD(rs.getInt("no_utilisateur")));
				liste.add(enc);
			}
		} catch (SQLException e) {
			throw new DalException("Erreur sur la m?thode recupererEnchereArticle()", e); 
		} finally {
			ConnectionProvider.seDeconnecter(pst);
			ConnectionProvider.seDeconnecter(cnx);
		}
		return liste;
	}
}
