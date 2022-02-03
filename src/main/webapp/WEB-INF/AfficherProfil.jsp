<%@page import="fr.eni.projetEnchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Informations profil</title>
</head>
<body>
<h1>Informations du profil :</h1>



<h3>
Pseudo : ${requestScope.user.pseudo} <br>
Nom : ${requestScope.user.nom } <br>
Prénom : ${requestScope.user.prenom} <br>
Email : ${requestScope.user.email}  <br>
Téléphone : ${requestScope.user.telephone}  <br>
Rue : ${requestScope.user.rue}  <br>
Code postal : ${requestScope.user.codePostal}  <br>
Ville : ${requestScope.user.ville}  <br>
</h3>

<form><button type="submit" name="modifier" formmethod="get" formaction="modifProfil">Modifier 
</button></form>
</body>
</html>