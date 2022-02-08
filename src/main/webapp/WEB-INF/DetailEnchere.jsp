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
<p id="nomArticle">${article.nomArticle}</p>
<p id="description">Description : ${article.description}</p>
<p id="categorie">Catégorie : ${article.categorie.libelle}</p>
<p id="prixVente">Meilleure offre : ${article.prixVente}</p>
<p id="miseAprix">Mise à prix : ${article.prixInitial}</p>
<p id="finEnchere">Fin de l'enchère : ${article.dateFinEncheres}</p>
<p id="Retrait">Retrait : ${article.userVendeur.rue} ${article.userVendeur.codePostal} ${article.userVendeur.ville}</p>
<p id="vendeur">Vendeur : ${article.userVendeur.pseudo}</p>
<label for="prix">Ma proposition : </label>
<input type="number" id="prix" name="prix" min="1" max="100000">
       

</div>



       
<a id="detailEnchere" href="modifArticle?id=${article.getNoArticle()}">>Modifier mon article</button></a>
<form><button type="submit" name="retour" formmethod="get" formaction="enchere">Retour vers l'accueil</button></form>



</body>
</html>