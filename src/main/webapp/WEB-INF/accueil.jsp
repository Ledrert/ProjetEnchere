<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Les objets sont nos amis</title>
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>liste des enchères</h1>
<form>
<div>
<label for="filtre">Filtres :</label>
<input type="text">
<label for="categorie">Catégories :</label>
<select><option>Informatique</option>
<option>Ameublement</option>
<option>Vêtement</option>
<option>Sport & loisirs</option></select>
</div>
</form>
</body>
</html>