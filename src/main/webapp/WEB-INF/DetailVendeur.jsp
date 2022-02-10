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

<h1>${utilisateur.pseudo}</h1>

<div class="infosPage">
<div id="details">
<p>Nom : ${utilisateur.nom}</p>
<p>Prénom : ${utilisateur.prenom}</p>
<p>Email : ${utilisateur.email}</p>
<p >Téléphone : ${utilisateur.telephone}</p>
<p>Rue : ${utilisateur.rue}</p>
<p>Code postal : ${utilisateur.codePostal}</p>
<p>Ville : ${utilisateur.ville}</p>
</div>
<form>
<a href="enchere">
<button type="button" >Retour vers l'accueil</button></a>
</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %> 
</body>
</html>