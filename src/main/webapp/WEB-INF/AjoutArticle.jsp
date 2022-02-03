<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ajouter un article</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<imgNewVente>

</imgNewVente>
		<h1>Nouvelle vente</h1>
		
		<div class="centerNewArticle">
	
	<div>
		<label for="article">Article :</label> <input type="text" id="article"
			name="article" maxlength="30">
	</div>
	<div>
		<label for="description">Description :</label> <input type="text"
			id="description" name="description" maxlength="30">
	</div>

	<label for="catégorie">Catégorie </label>
	<select id="catégorie">
		<option value="valeur1">Informatique</option>
		<option value="valeur2">Ameublement</option>
		<option value="valeur3">Vêtement</option>
		<option value="valeur3">Sport & Loisirs</option>
	</select>

	<form method="post" action="page.php" enctype="multipart/form-data">
		<label for="photo">Photo de l'article </label> <input type="file"
			id="photo" name="photo" accept="image/png, image/jpeg">
	</form>
	
	<label for="prix">Mise à prix : </label>

<input type="number" id="prix" name="prix"
       min="1" max="100000">
       
       
       <label for="prix">Début de l'enchère : </label>
       <input type="date" id="début" name="début">
       
       <label for="prix">Fin de l'enchère : </label>
       <input type="date" id="fin" name="fin">
       
       </div>
	
	
	<div class="retrait">
	<h2>Retrait : </h2>	
		<label for="rue">Rue : </label> <input type="text" id="rue"
			name="rue" maxlength="50">	
		<label for="codePostal">Code postal : </label> <input type="text" id="codePostal"
			name="codePostal" maxlength="10">	
		<label for="ville">Ville : </label> <input type="text" id="ville"
			name="ville" maxlength="30">	
	</div>
	
	
	<form><button type="submit" name="enregistrer" formmethod="get" formaction="enchere">Enregistrer 
</button></form><br>

<form><button type="submit" name="annuler" formmethod="get" formaction="enchere">Annuler 
</button></form>

	
	

</body>
</html>