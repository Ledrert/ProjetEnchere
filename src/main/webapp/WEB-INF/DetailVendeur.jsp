<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Détail vendeur </title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">

</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>

<h1>Détail du vendeur</h1>

<div class = infoVendeur>

<p id="pseudo">Pseudo : ${utilisateur.pseudo}</p>
<p id="nom">Nom : ${utilisateur.nom}</p>
<p id="prénom">Prénom : ${utilisateur.prenom}</p>
<p id="email">Email : ${utilisateur.email}</p>
<p id="teléphone">Téléphone : ${utilisateur.telephone}</p>
<p id="rue">Rue : ${utilisateur.rue}</p>
<p id="codePostal">Code postal : ${utilisateur.codePostal}</p>
<p id="ville">Ville : ${utilisateur.ville}</p>

<form>
<a href="enchere">
<button type="button" >Retour vers l'accueil</button></a>
</form>


</div>
 
 
</body>
</html>