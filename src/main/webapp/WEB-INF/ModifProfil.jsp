<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@page import="fr.eni.projetEnchere.bo.Utilisateur"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<title>Je modifie mon profil</title>
</head>
<body>

	<%@ include file="/WEB-INF/jsp/header.jsp" %>

<main>

	<h1>Modification de mon profil</h1>
	
<form action="modifProfil" method="post"> 	
<div class="formProfil">
	<div id="labelCo">
		<label for="pseudo">Pseudo :</label>
	</div>
	<div>
		<input type="text" name="pseudo" id="pseudo" pattern="([A-z0-9]){4,}" value="${user.pseudo}" maxlength="30" autofocus required>
	</div>
	<div id="labelCo">
		<label for="prenom">Prénom :</label>
	</div>
	<div>
		<input type="text" name="prenom" id="prenom" maxlength="30" value="${user.nom}" required>
	</div>
	<div id="labelCo">
		<label for="nom">Nom :</label>
	</div>
	<div>
		<input type="text" name="nom" id="nom" maxlength="30" value="${user.prenom}" required> 
	</div>
	<div id="labelCo">
		<label for="email">Email :</label>
	</div>
	<div>
		<input type="email" name="email" id="email" maxlength="50" value="${user.email}" required>
	</div>
	<div id="labelCo">
		<label for="phone">Téléphone :</label>
	</div>
	<div>
		<input type="tel" id="phone" name="phone" value="${user.telephone}" required>
	</div>
	<div id="labelCo">
		<label for="adresse">Rue :</label>
	</div>
	<div>
		<input type="text" name="adresse" id="adresse" maxlength="50" value="${user.rue}" required>
	</div>
	<div id="labelCo">
		<label for="codePostal">Code Postal :</label>
	</div>
	<div>
		<input type="text" name="codePostal" id="codePostal" maxlength="8" pattern="([0-9]+)" value="${user.codePostal}" required>
	</div>
	<div id="labelCo">
		<label for="ville">Ville :</label>
	</div>
	<div>
		<input type="text" name="ville" id="ville" maxlength="30" value="${user.ville}" required>
	</div>
	<div id="labelCo">
		<label for="password">Mot de passe :</label>
	</div>
	<div>
	
		<input type="password" name="password" id="password" maxlength="30"  required>
	</div>
	<div id="labelCo">
		<label for="verif">Confirmez le mot de passe :</label>
	</div>
	<div>
		<input type="password" name="verif" id="verif"	maxlength="30" required>
	</div>
</div>

	<c:if test="${param.error == 1}">	
    <p style="color : red">Vos mdp ne correspondent pas. Veuillez reessayer</p>
	</c:if>

    <div class="buttonForm">
      	<button type="submit" name="modification" formmethod="post" formaction="modifProfil">Modifier</button>					
		<button type="submit" name="suppression" formmethod="get" formaction="SuppressionProfil">Supprimer mon compte</button>
		</div>
</form>	
<form><div class="buttonForm">
<button type="submit" name="annuler" formmethod="get" formaction="enchere">Annuler</button>					
</div></form>
</main>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>