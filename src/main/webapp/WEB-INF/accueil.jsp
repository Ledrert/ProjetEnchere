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
	if(id="achat")
		$("input[type=checkbox][value=vente]").attr("disabled", "disabled"); 
		$("input[type=checkbox][value=achat]").removeAttr("disabled"); 
	
	} else {
		if(id="vente"){
		$("input[type=checkbox][value=achat]").attr("disabled", "disabled"); 
		$("input[type=checkbox][value=vente]").removeAttr("disabled"); }
	}
});
	  
	</script>
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>liste des enchères</h1>
<form>
<div id="divSearch">
<div>
<label for="filtre">Filtres :</label>
<input type="text">
<label for="categorie">Catégories :</label>
<select><c:forEach var="libCat" items="${listeCat }">
<option>${libCat }</option></c:forEach></select></div>
<div id="advSearch">
<div id="achatsSearch">
<input type="radio" id="RdAchat" name="radioVente" onclick="filtre(achat)" checked><label for="RdAchat">Achats</label><ul id="listeSearch">
<li><input type="checkbox" id="EncDebut" name="EncDebut" value="achat"><label for="EncDebut">Enchères ouvertes</label></li>
<li><input type="checkbox" id="MyEncEC" name="MyEncEC" value="achat"><label for="MyEncEC">Mes enchères en cours</label></li>
<li><input type="checkbox" id="MyEncWin" name="MyEncWin" value="achat"><label for="MyEncWin">Mes enchères remportées</label></li>
</ul></div>
<div id="mesVentesSearch">
<input type="radio" id="RdVente" name="radioVente" onclick="filtre(vente)"><label for="RdVente">Mes ventes</label><ul id="listeSearch">
<li><input type="checkbox" id="MyVenteEC" name="MyVenteEC" value="vente"><label for="MyVenteEC">Mes ventes en cours</label></li>
<li><input type="checkbox" id="MyVenteNC" name="MyVenteNC" value="vente"><label for="MyVenteNC">ventes non débutées</label></li>
<li><input type="checkbox" id="MyVenteFin" name="MyVenteFin" value="vente"><label for="MyVenteFin">ventes terminées</label></li>
</ul></div>
</div>
</div>
<div>
<button class="btnSearch" type="button" onclick="" >Rechercher</button>
</div>
<div class="listEnchere">
<%-- <c:forEach></c:forEach> --%> <!-- Pour chaque enchère : une case -->
<div id="cardEnchere">
<img alt="img" src="" id="imgEnchere">
<a id="nomArticle" href="#">Nom de l'article</a>
<p id="detailArticle">Prix : 100 credits</p>
<p>Fin de l'enchère : date</p>
<p>Vendeur : <a href="#">nom Vendeur</a></p>
</div>
</div>
</form>
</body>
</html>