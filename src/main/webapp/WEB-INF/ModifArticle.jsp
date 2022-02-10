<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<head>
<meta charset="ISO-8859-1">
<title>Modifier mon article</title>
</head>
<body>

<h1>Modifier mon article</h1>
<form>
	<div class="centerNewArticle">
	
		<div>
			<label for="article">Article :</label> 
			<input type="text" id="article" name="article"  maxlength="30" value="${article.nomArticle}">
		</div>
		
		<div>
			<label for="description">Description :</label> 
			<input type="text" id="description" name="description" maxlength="300" value="${article.description}">
		</div>

		<div>
			<label for="categorie">Catégorie </label> 
			<select id="categorie" name="categorie">
				<option>Informatique</option>
				<option>Ameublement</option>
				<option>Vêtement</option>
				<option>Sport & Loisirs</option>
			</select>
		</div>

		<div>
			<label for="prix">Mise à prix : </label> <input type="number" id="prix" name="prix" min="1" max="100000"  value="${article.prixVente}"> 
		</div>
		
		<div>
			<label for="dateDebutEnchere">Début de l'enchère : </label>
			<input type="date" id="debut" name="debut" min="${today}"value="${article.dateDebutEncheres}"> 
		</div>
		
		<div>
			<label for="dateFinEnchere">Fin de l'enchère : </label> 
			<input type="date" id="fin" name="fin" min="${today}" value="${article.dateFinEncheres}">
		</div>
	</div>


	<div class="retrait">
			<h2>Retrait :</h2>
		<div>
			<label for="rue">Rue : </label> <input type="text" id="rue" name="rue" maxlength="50" value="${article.getRetrait().rue}"> 
		</div>
		<div>
			<label for="codePostal">Code postal :</label> 
			<input type="text" id="codePostal" name="codePostal" maxlength="10" value="${article.getRetrait().codePostal}">
		</div>
		<div>
			<label for="ville">Ville : </label> <input type="text" id="ville" name="ville" maxlength="30" value="${article.getRetrait().ville}">
		</div>
	</div>


	<div class="boutton">
		<a href="enchere"> 
      		<button class="button" type="submit" formmethod="post" formaction="vendre">Enregistrer</button></a>
			<button type="submit" name="annuler" formmethod="get" formaction="enchere">Annuler</button>
	</div>
</form>
<%@ include file="/WEB-INF/jsp/footer.jsp" %> 
</body>
</html>