<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ajouter un article</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<jsp:include page="jsp/header.jsp"></jsp:include>
	<imgNewVente> </imgNewVente>
	<h1>Nouvelle vente</h1>
<form>
	<div class="centerNewArticle">
	
		<div>
			<label for="article">Article :</label> <input type="text"
				id="article" name="article" maxlength="30">
		</div>
		<div>
			<label for="description">Description :</label> <input type="text"
				id="description" name="description" maxlength="300">
		</div>

		<label for="categorie">Cat�gorie </label> <select id="categorie" name="categorie">
			<option>Informatique</option>
			<option>Ameublement</option>
			<option>V�tement</option>
			<option>Sport & Loisirs</option>
		</select>

		<!--  <form method="post" action="page.php" enctype="multipart/form-data">
			<label for="photo">Photo de l'article </label> <input type="file"
				id="photo" name="photo" accept="image/png, image/jpeg">
		</form> -->

		<label for="prix">Mise � prix : </label> <input type="number"
			id="prix" name="prix" min="1" max="100000"> 
		<label for="dateDebutEnchere">D�but de l'ench�re : </label>
		 <input type="date" id="debut" name="debut" min="${today}"value="${today}"> 
		<label for="dateFinEnchere">Fin de l'ench�re : </label> 
		<input type="date" id="fin" name="fin" min="${today}">

	</div>


	<div class="retrait">
		<h2>Retrait :</h2>
		<label for="rue">Rue : </label> <input type="text" id="rue" name="rue"
			maxlength="50" value="${user.rue}"> <label for="codePostal">Code postal :
		</label> <input type="text" id="codePostal" name="codePostal" maxlength="10" value="${user.codePostal}">
		<label for="ville">Ville : </label> <input type="text" id="ville"
			name="ville" maxlength="30" value="${user.ville}">
	</div>


	<div class="boutton">
	<a href="enchere"> 
      	<button class="button" type="submit" formmethod="post" formaction="vendre">Enregistrer</button></a>
	<br>

		<button type="submit" name="annuler" formmethod="get"
			formaction="enchere">Retour vers l'accueil</button>
			
	</div>
</form>


</body>
</html>