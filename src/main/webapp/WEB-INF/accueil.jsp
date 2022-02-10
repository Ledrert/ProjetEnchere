<%@page import="fr.eni.projetEnchere.bll.ArticleManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Les objets sont nos amis</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<script type="text/javascript">
function filtre(id) {
	if(id == 'achat') {
		document.getElementById("EncDebut").disabled=false;
		document.getElementById("MyEncEC").disabled=false;
		document.getElementById("MyEncWin").disabled=false;
		document.getElementById("MyVenteEC").disabled=true;
		document.getElementById("MyVenteNC").disabled=true;
		document.getElementById("MyVenteFin").disabled=true;
	} else {
		if(id == 'vente'){
			document.getElementById("EncDebut").disabled=true;
			document.getElementById("MyEncEC").disabled=true;
			document.getElementById("MyEncWin").disabled=true;
			document.getElementById("MyVenteEC").disabled=false;
			document.getElementById("MyVenteNC").disabled=false;
			document.getElementById("MyVenteFin").disabled=false;
		}
	}
};

function loadFiltre(){
	if('${ckAchat}' == ' checked'){filtre('achat');}
	else {filtre('vente');}
};
	</script>
</head>
<body onload="loadFiltre()">
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>Enchères</h1>
<form class="formSearch">
<c:choose>
<c:when test="${!empty sessionScope.user }">
<div id="divSearch">
<div id="filtreSearch">
<label for="filtre">Filtres :</label>
<input type="text" name="keyword">
<label for="categorie">Catégories :</label>
<select name="catSelect"><option>Toutes</option>
<c:forEach var="libCat" items="${listeCat }">
<option>${libCat }</option></c:forEach></select></div>
<div id="advSearch">
<div id="achatsSearch">
<input type="radio" id="RdAchat" name="radioFiltre" value="achat" onclick="filtre('achat')" ${ckAchat}><label for="RdAchat">Achats</label><ul id="listeSearch">
<li><input type="checkbox" id="EncDebut" name="EncDebut"${ckEncDeb}><label for="EncDebut">Enchères ouvertes</label></li>
<li><input type="checkbox" id="MyEncEC" name="MyEncEC"${ckEncEC}><label for="MyEncEC">Mes enchères en cours</label></li>
<li><input type="checkbox" id="MyEncWin" name="MyEncWin"${ckEncWin}><label for="MyEncWin">Mes enchères remportées</label></li>
</ul></div>
<div id="achatsSearch">
<input type="radio" id="RdVente" name="radioFiltre" value="vente" onclick="filtre('vente')" ${ckVente}><label for="RdVente">Mes ventes</label><ul id="listeSearch">
<li><input type="checkbox" id="MyVenteEC" name="MyVenteEC" disabled="disabled"${ckVEC}><label for="MyVenteEC">Mes ventes en cours</label></li>
<li><input type="checkbox" id="MyVenteNC" name="MyVenteNC" disabled="disabled"${ckVNC}><label for="MyVenteNC">ventes non débutées</label></li>
<li><input type="checkbox" id="MyVenteFin" name="MyVenteFin" disabled="disabled"${ckVEnd}><label for="MyVenteFin">ventes terminées</label></li>
</ul></div>
</div>
</div>
<div>
<button class="btnSearchAdv" type="submit" formaction="enchere" formmethod="post" >Rechercher</button>
<button class="btnSearchAdv" type="submit" formmethod="get" formaction="enchere">Réinitialiser les filtres</button>
</div>
</c:when>
<c:otherwise>
<div id="divSearch">
<div id="filtreSearch">
<label for="filtre">Filtres :</label>
<input type="text" name="keyword">
<label for="categorie">Catégories :</label>
<select name="catSelect"><option>Toutes</option>
<c:forEach var="libCat" items="${listeCat }">
<option>${libCat }</option></c:forEach></select></div>
</div>
<div>
<button class="btnSearch" type="submit" formaction="enchere" formmethod="post" >Rechercher</button>
<button class="btnSearch" type="submit" formmethod="get" formaction="enchere">Réinitialiser les filtres</button>
</div>
</c:otherwise>
</c:choose>
</form>
<div class="listEnchere">
<c:forEach var="article" items="${listeArt}"> <!-- Pour chaque enchère : une case  -->
<div id="cardEnchere">
<img alt="img" src="img/image.png" id="imgEnchere">
<a id="nomArticle" href="detailEnchere?id=${article.getNoArticle()}">${article.getNomArticle() }</a>
<p id="detailArticle">Prix : ${article.recupererDernierEnchere() }</p>
<p>Fin de l'enchère : ${article.getDateFinEncheres() }</p>
<p>Vendeur : <a href="detailVendeur?id=${article.getUserVendeur().getNoUtilisateur()}">${article.getUserVendeur().getPseudo() }</a></p>
</div>
</c:forEach>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body> 
</html>