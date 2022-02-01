<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Je m'inscris !</title>
</head>
<body>

<%@ include file="/WEB-INF/jsp/header.jsp" %>

<main>

<h1>Création d'un nouveau profil</h1>

<div>
	<form method="post">
		<div> 
			<label for="pseudo">Pseudo :</label>
			<input type="text" name="pseudo" id="pseudo" required>
		</div>
		<div> 
			<label for="prenom">Prénom :</label>
			<input type="text" name="prenom" id="prenom" required>
		</div>
		<div> 
			<label for="nom">Nom :</label>
			<input type="text" name="nom" id="nom" required>
		</div>
		<div> 
			<label for="email">Email :</label>
			<input type="email" name="email" id="email" required>
		</div>
		<div> 
			<label for="phone">Téléphone :</label>
			<input type="tel" id="phone" name="phone" pattern="[0-9]{10}" required>
		</div>
		<div> 
			<label for="adresse">Rue :</label>
			<input type="text" name="adresse" id="adresse" required>
		</div>
		<div> 
			<label for="codePostal">Code Postal :</label>
			<input type="number" name="codePostal" id="codePostal" required>
		</div>
		<div> 
			<label for="ville">Ville :</label>
			<input type="text" name="ville" id="ville" required>
		</div>		
		<div>
    		<label for="password">Mot de passe :</label>
			<input type="password" name="password" id="password">
		</div>	
		<div> 
			<label for="confirmPassword">Confirmez le mot de passe :</label>
			<input type="password" name="confirmPassword" id="confirmPassword">
		</div>
        <div>
        	<button class="button" type="submit" >Valider</button>	
        					
			<a href="#">
			<button type="button" >Annuler</button></a>
		</div>
	</form>
</div>

</main>
	<%-- <%@ include file="/WEB-INF/jsp/footer.jsp" %> --%>
</body>
</html>