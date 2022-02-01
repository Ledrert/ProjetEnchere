<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Les objets sont nos amis</title>
<link rel="stylesheet" href="css/style.css" type="text/css">
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
</form>
</body>
</html>