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
	<h1>Nouvelle vente</h1>
	<div class="infosPage">
	<form>
		<div id="ajoutVente">
		<div id="inputVente">
			<div id="labelVente">
				<label for="article">Article :</label></div>
				<div><input type="text" id="articleArt" name="article" maxlength="30">
			</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
				<label for="description">Description :</label></div>
				<div><textarea id="descriptionArt" name="description" maxlength="300"></textarea>
			</div></div>
			<div id="inputVente">
			<div id="labelVente">
				<label for="categorie">Catégorie </label></div>
				<div><select id="categorie" name="categorie">
					<option>Informatique</option>
					<option>Ameublement</option>
					<option>Vêtement</option>
					<option>Sport & Loisirs</option>
				</select>
			</div></div>
			<!--  <form method="post" action="page.php" enctype="multipart/form-data">
			<label for="photo">Photo de l'article </label> <input type="file"
				id="photo" name="photo" accept="image/png, image/jpeg">
		</form> -->
		<div id="inputVente">
			<div id="labelVente">
				<label for="prix">Mise à prix : </label> </div>
				<div><input type="number" id="prix" name="prix" min="1" max="100000">
			</div></div>
			<div id="inputVente">
			<div id="labelVente">
				<label for="dateDebutEnchere">Début de l'enchère : </label></div>
				 <div><input type="date" id="debut" name="debut" min="${today}" value="${today}">
			</div></div>
			<div id="inputVente">
			<div id="labelVente">
				<label for="dateFinEnchere">Fin de l'enchère : </label></div>
				<div><input type="date" id="fin" name="fin" min="${today}">
			</div>
		</div></div>

<h2>Retrait</h2>
		<div id="ajoutVente">
			<div id="inputVente">
			<div id="labelVente">
			<label for="rue">Rue : </label></div><div> <input type="text" id="rue"
				name="rue" maxlength="50" value="${user.rue}"></div>
				</div>
				<div id="inputVente">
			<div id="labelVente"> <label
				for="codePostal">Code postal : </label></div><div> <input type="text"
				id="codePostal" name="codePostal" maxlength="10"
				value="${user.codePostal}"></div>
				</div>
				 <div id="inputVente">
			<div id="labelVente"><label for="ville">Ville
				: </label></div><div> <input type="text" id="ville" name="ville" maxlength="30"
				value="${user.ville}"></div>
				</div>
		</div>


				<button class="button" type="submit" formmethod="post"
					formaction="vendre">Enregistrer</button>

			<button type="submit" name="annuler" formmethod="get"
				formaction="enchere">Retour vers l'accueil</button>
	</form>
</div>

</body>
</html>