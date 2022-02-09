<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>   
 <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Détail enchère</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>${article.nomArticle}</h1>
<h3>Vendu par ${article.userVendeur.pseudo}</h3>
<div class="infosPage">
<div id="details">
<p id="description">Description : ${article.description}</p>
<p id="categorie">Catégorie : ${article.categorie.libelle}</p>
<p id="miseAprix">Mise à prix : ${article.prixInitial}</p>
<c:choose>
<c:when test="${enchere.montantEnchere == article.prixInitial && enchere.getNoEncherisseur().pseudo == article.userVendeur.pseudo }">
<p id="prixVente">Meilleure offre actuelle : -</p>
</c:when>
<c:otherwise>
<p id="prixVente">Meilleure offre actuelle : ${enchere.montantEnchere} par ${enchere.getNoEncherisseur().pseudo}</p>
</c:otherwise>
</c:choose>
<p id="finEnchere">Fin de l'enchère : ${article.dateFinEncheres}</p>
<p id="Retrait">Retrait : ${article.userVendeur.rue} ${article.userVendeur.codePostal} ${article.userVendeur.ville}</p>
</div>
<c:if test="${sessionScope.user != null && article.userVendeur.noUtilisateur != user.noUtilisateur}">
	<form><div id="invisible"><input type="text" id="noArt" name="noArt" value="${article.noArticle }"></div>
	<label for="prix">Ma proposition : </label>
	<input type="number" id="prix" name="prix" min="1" max="100000">
	<button type="submit" id ="proposition" name="proposition" formmethod="post" formaction="detailEnchere">Enchérir</button></form>
</c:if>
<br>
<c:if test="${article.userVendeur.noUtilisateur == user.noUtilisateur && article.dateDebutEncheres gt today}">
	<a id="detailEnchere" href="modifArticle?id=${article.getNoArticle()}">Modifier mon article</a>
</c:if>
<br>
<form><button type="submit" name="retour" formmethod="get" formaction="enchere">Retour vers l'accueil</button></form> 
</div>
</body>
</html>