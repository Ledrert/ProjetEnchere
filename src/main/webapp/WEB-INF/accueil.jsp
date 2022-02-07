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
	</script>
</head>
<body>
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
<input type="radio" id="RdAchat" name="radioVente" onclick="filtre('achat')" checked><label for="RdAchat">Achats</label><ul id="listeSearch">
<li><input type="checkbox" id="EncDebut" name="EncDebut" value="achat"><label for="EncDebut">Enchères ouvertes</label></li>
<li><input type="checkbox" id="MyEncEC" name="MyEncEC" value="achat"><label for="MyEncEC">Mes enchères en cours</label></li>
<li><input type="checkbox" id="MyEncWin" name="MyEncWin" value="achat"><label for="MyEncWin">Mes enchères remportées</label></li>
</ul></div>
<div id="mesVentesSearch">
<input type="radio" id="RdVente" name="radioVente" onclick="filtre('vente')"><label for="RdVente">Mes ventes</label><ul id="listeSearch">
<li><input type="checkbox" id="MyVenteEC" name="MyVenteEC" value="vente" disabled="disabled"><label for="MyVenteEC">Mes ventes en cours</label></li>
<li><input type="checkbox" id="MyVenteNC" name="MyVenteNC" value="vente" disabled="disabled"><label for="MyVenteNC">ventes non débutées</label></li>
<li><input type="checkbox" id="MyVenteFin" name="MyVenteFin" value="vente" disabled="disabled"><label for="MyVenteFin">ventes terminées</label></li>
</ul></div>
</div>
</div>
<div>
<button class="btnSearchAdv" type="button" formaction="enchere" formmethod="post" >Rechercher</button>
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
<button class="btnSearch" type="button" formaction="enchere" formmethod="post" >Rechercher</button>
</div>
</c:otherwise>
</c:choose>
</form>
<div class="listEnchere">
<c:forEach var="article" items="${listeArt}"> <!-- Pour chaque enchère : une case -->
<div id="cardEnchere">
<img alt="img" src="" id="imgEnchere">
<a id="nomArticle" href="#">${article.getNomArticle() }</a>
<p id="detailArticle">Prix : ${article.getPrixInitial() }</p>
<p>Fin de l'enchère : ${article.getDateFinEncheres() }</p>
<p>Vendeur : <a href="#">${article.getUserVendeur().getPseudo() }</a></p>
</div>
</c:forEach>
</div>
</body>
</html>