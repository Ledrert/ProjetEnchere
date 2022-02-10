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
<div class="infosPage">
<form>
		<div id="ajoutVente">
		<div id="inputVente">
			<div id="labelVente"><label for="article">Article :</label> </div>
			<div><input type="text" id="article" name="article"  maxlength="30" value="${article.nomArticle}">
		</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
			<label for="description">Description :</label></div>
				<div><input type="text" id="description" name="description" maxlength="300" value="${article.description}">
		</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
			<label for="categorie">Catégorie </label> </div>
				<div><select id="categorie" name="categorie">
				<option>Informatique</option>
				<option>Ameublement</option>
				<option>Vêtement</option>
				<option>Sport & Loisirs</option>
			</select>
		</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
			<label for="prix">Mise à prix : </label></div>
				<div><input type="number" id="prix" name="prix" min="1" max="100000"  value="${article.prixVente}"> 
		</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
			<label for="dateDebutEnchere">Début de l'enchère : </label></div>
				<div><input type="date" id="debut" name="debut" min="${today}"value="${article.dateDebutEncheres}"> 
		</div>
			</div>
			<div id="inputVente">
			<div id="labelVente">
			<label for="dateFinEnchere">Fin de l'enchère : </label></div>
				<div><input type="date" id="fin" name="fin" min="${today}" value="${article.dateFinEncheres}">
		</div>
	</div>
	</div>


			<h2>Retrait :</h2>
		<div id="ajoutVente">
			<div id="inputVente">
			<div id="labelVente">
			<label for="rue">Rue : </label></div>
				<div><input type="text" id="rue" name="rue" maxlength="50" value="${article.getRetrait().rue}"> 
		</div></div>
				<div id="inputVente">
			<div id="labelVente"> 
			<label for="codePostal">Code postal :</label> </div>
				<div><input type="text" id="codePostal" name="codePostal" maxlength="10" value="${article.getRetrait().codePostal}">
		</div>
				</div>
				 <div id="inputVente">
			<div id="labelVente">
			<label for="ville">Ville : </label></div>
				<div><input type="text" id="ville" name="ville" maxlength="30" value="${article.getRetrait().ville}">
		</div>
				</div>
		</div>

		<a href="enchere"> 
      		<button class="button" type="submit" formmethod="post" formaction="vendre">Enregistrer</button></a>
			<button type="submit" name="annuler" formmethod="get" formaction="enchere">Annuler</button>

</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %> 
</body>
</html>