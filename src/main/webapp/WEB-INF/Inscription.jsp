<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<title>Je m'inscris !</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp" %>
	
<h1>Creation de mon profil</h1>

<div class="main">
	
<form action="inscription" method="post"> 

<div class="formProfil">
	<div id="labelCo">
		<label for="pseudo">Pseudo :</label>
	</div>
	<div>
		<input type="text" name="pseudo" id="pseudo" pattern="([A-z0-9]){4,}" maxlength="30" autofocus required>
	</div>
	<div id="labelCo">
		<label for="prenom">Prénom :</label>
	</div>
	<div>
		<input type="text" name="prenom" id="prenom" maxlength="30" required>
	</div>
	<div id="labelCo">
		<label for="nom">Nom :</label>
	</div>
	<div>
		<input type="text" name="nom" id="nom" maxlength="30" required>
	</div>
	<div id="labelCo">
		<label for="email">Email :</label>
	</div>
	<div>
		<input type="email" name="email" id="email" maxlength="50"
			placeholder="exemple@exemple.fr" required>
	</div>
	<div id="labelCo">
		<label for="phone">Téléphone :</label>
	</div>
	<div>
		<input type="tel" id="phone" name="phone" placeholder="XXXXXXXXXX"
			required>
	</div>
	<div id="labelCo">
		<label for="adresse">Rue :</label>
	</div>
	<div>
		<input type="text" name="adresse" id="adresse" maxlength="50" required>
	</div>
	<div id="labelCo">
		<label for="codePostal">Code Postal :</label>
	</div>
	<div>
		<input type="text" name="codePostal" id="codePostal" maxlength="8" pattern="([0-9]+)" required>
	</div>
	<div id="labelCo">
		<label for="ville">Ville :</label>
	</div>
	<div>
		<input type="text" name="ville" id="ville" maxlength="30" required>
	</div>
	<div id="labelCo">
		<label for="password">Mot de passe :</label>
	</div>
	<div>
		<input type="password" name="password" id="password" maxlength="30" required>
	</div>
	<div id="labelCo">
		<label for="confirmPassword">Confirmez le mot de passe :</label>
	</div>
	<div>
		<input type="password" name="confirmPassword" id="confirmPassword"
			maxlength="30" required>
	</div>
</div>
    <div class="buttonForm">
    	<a href="inscription"> 
      	<button class="button" type="submit" >Enregistrer</button></a>
        					
		<a href="enchere">
		<button type="button" >Retour vers l'accueil</button></a>
	</div>

</form>	
</div>


<%-- 	<%@ include file="/WEB-INF/jsp/footer.jsp" %> --%> </body>

</html>