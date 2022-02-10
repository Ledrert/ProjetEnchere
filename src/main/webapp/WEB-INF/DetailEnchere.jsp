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
<p id="Retrait">Retrait : ${article.getRetrait().rue} ${article.getRetrait().codePostal} ${article.getRetrait().ville}</p>
</div>
<c:if test="${sessionScope.user != null && article.userVendeur.noUtilisateur != user.noUtilisateur}">
	<form><div id="invisible"><input type="text" id="oldEnc" name="oldEnc" value="${enchere.montantEnchere}">
<input type="text" id="oldEncU" name="oldEncU" value="${enchere.getNoEncherisseur().noUtilisateur}">
<input type="text" id="noArt" name="noArt" value="${article.noArticle }"><input type="text" id="noArt" name="noArt" value="${article.noArticle }">
	<input type="number" id="mtnArt" name="mtnArt" value="${enchere.montantEnchere }"></div>
	<label for="prix">Ma proposition : </label>
	<input type="number" id="prix" name="prix" min="1" max="100000">
	<button type="submit" id ="proposition" name="proposition" formmethod="post" formaction="detailEnchere">Enchérir</button></form>
</c:if>
	
<c:if test="${param.error == 1}">	
    <p style="color : red">Veuillez saisir une enchère plus élevée que l'enchère actuelle. </p>
</c:if>


<br>
<c:if test="${article.userVendeur.noUtilisateur == user.noUtilisateur && article.dateDebutEncheres gt today}">
	<a id="detailEnchere" href="modifArticle?id=${article.getNoArticle()}">Modifier mon article</a>
</c:if>
<br>
<form><button type="submit" name="retour" formmethod="get" formaction="enchere">Retour vers l'accueil</button></form> 
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>