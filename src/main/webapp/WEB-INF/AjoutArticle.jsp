<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ajouter un article</title>
</head>
<body>
	<center>
		<h1>Nouvelle vente</h1>
	</center>
	<div>
		<label for="article">Article :</label> <input type="text" id="article"
			name="article" maxlength="30">
	</div>
	<br>
	<div>
		<label for="description">Description :</label> <input type="text"
			id="description" name="description" maxlength="30">
	</div>
	<br>
	<div>
		<label for="article">Article :</label> <input type="text" id="article"
			name="article" maxlength="30">
	</div>
	<br>

	<label for="cat�gorie">Cat�gorie </label>
	<select id="cat�gorie">
		<option value="valeur1">Informatique</option>
		<option value="valeur2">Ameublement</option>
		<option value="valeur3">V�tement</option>
		<option value="valeur3">Sport & Loisirs</option>
	</select>
	<br>
	<br>


	<form method="post" action="page.php" enctype="multipart/form-data">
		<label for="photo">Photo de l'article </label> <input type="file"
			id="photo" name="photo" accept="image/png, image/jpeg">
	</form><br>
	
	<label for="prix">Mise � prix : </label>

<input type="number" id="prix" name="prix"
       min="1" max="100000"><br><br>
       
       
       <label for="prix">D�but de l'ench�re : </label>
       <input type="date" id="d�but" name="d�but"><br><br>
       
       <label for="prix">Fin de l'ench�re : </label>
       <input type="date" id="fin" name="fin"><br>
	
	<h2>Retrait : </h2><br>
	<div>
		<label for="rue">Rue : </label> <input type="text" id="rue"
			name="rue" maxlength="50"><br><br>   
	</div>
	
	<div>
		<label for="codePostal">Code postal : </label> <input type="text" id="codePostal"
			name=""codePostal"" maxlength="10"><br><br>   
	</div>
	
	<div>
		<label for="ville">Ville : </label> <input type="text" id="ville"
			name="ville" maxlength="30"><br><br>   
	</div><br><br>
	
	<form><button type="submit" name="enregistrer" formmethod="get" formaction="enchere">Enregistrer 
</button></form><br>

<form><button type="submit" name="annuler" formmethod="get" formaction="enchere">Annuler 
</button></form>
	
	

</body>
</html>