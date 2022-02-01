<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Les objets sont nos amis</title>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css"> --%>
<link rel="stylesheet" href="/ProjetEnchere/css/style.css">
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>liste des enchères</h1>
<form>
<div id="divSearch">
<label for="filtre">Filtres :</label>
<input type="text">
<label for="categorie">Catégories :</label>
<select><c:forEach var="libCat" items="${listeCat }">
<option>${libCat }</option></c:forEach></select>
<button class="btnSearch" type="button" onclick="" >Rechercher</button>
</div>
<div class="listEnchere">
<%-- <c:forEach></c:forEach> --%> <!-- Pour chaque enchère : une case -->
<div id="cardEnchere">
<img alt="img" src="" id="imgEnchere">
<p id="nomArticle">Nom de l'article</p>
<p id="detailArticle">Prix : 100 credits</p>
<p>Fin de l'enchère : date</p>
<p>Vendeur : <a href="#">nom Vendeur</a></p>
</div>
</div>
</form>
</body>
</html>