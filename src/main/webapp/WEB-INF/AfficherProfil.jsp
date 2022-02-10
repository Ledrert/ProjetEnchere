<%@page import="fr.eni.projetEnchere.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">    
<title>Informations profil</title>
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
<h1>Informations du profil :</h1>

<div class="infosPage">
<div id="details">
<p>Pseudo : ${requestScope.user.pseudo}</p>
<p>Nom : ${requestScope.user.nom }</p>
<p>Prénom : ${requestScope.user.prenom}</p>
<p>Email : ${requestScope.user.email}</p>
<p >Téléphone : ${requestScope.user.telephone}</p>
<p>Rue : ${requestScope.user.rue}</p>
<p>Code postal : ${requestScope.user.codePostal}</p>
<p>Ville : ${requestScope.user.ville}</p>
</div>

<form>
<button type="submit" name="modifier" formmethod="get" formaction="modifProfil">Modifier</button>
<button type="submit" name="retour" formmethod="get" formaction="enchere" >Retour vers l'accueil</button>
</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>