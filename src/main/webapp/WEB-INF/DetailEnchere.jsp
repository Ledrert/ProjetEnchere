<%@page import="fr.eni.projetEnchere.bo.Enchere"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Détail enchère</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>

<h1>Détail vente</h1>

<div class= infoEnchere>
<p id="detailEnchere">${article.nomArticle}</p>
<p id="detailEnchere">Description : ${article.description}</p>
<p id="detailEnchere">Catégorie : ${article.categorie.libelle}</p>
<p id="detailEnchere">Meilleure offre : ${article.prixVente}</p>
<p id="detailEnchere">Mise à prix : ${article.prixInitial}</p>
<p id="detailEnchere">Fin de l'enchère : ${article.dateFinEncheres}</p>
<p id="detailEnchere">Retrait : ${article.userVendeur.rue} ${article.userVendeur.codePostal} ${article.userVendeur.ville}</p>
<p id="detailEnchere">Vendeur : ${article.userVendeur.pseudo}</p>
<label for="prix">Ma proposition : </label>
<input type="number" id="prix" name="prix" min="1" max="100000">
       

</div>



       
     <form><button type="submit" name="retour" formmethod="get" formaction="enchere">Retour vers l'accueil
</button></form>


</body>
</html>