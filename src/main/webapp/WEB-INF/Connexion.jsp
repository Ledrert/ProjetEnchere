<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Connexion</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<h1>Connexion</h1>
<div id="pageCo">
<form class="formCo" action="/connexion" method="post">
    <div><div id="labelCo"><label for="identifiant">Identifiant :</label></div>
        <div><input type="text" id="identifiant" name="identifiant" maxlength="30"></div>
    </div><br>
    <div>
        <div id="labelCo"><label for="mot de passe">Mot de passe :</label></div>
        <div><input type="password" id="MDP" name="MDP" maxlength="30"></div>
    </div>
	
	<c:if test="${param.error == 1}">	
    <p style="color : red">Votre Email/Pseudo ou Mdp est incorrect, veuillez réessayer.</p>
	</c:if>
	
    <div id="btmCo">
    <button type="submit" name="connexion" formmethod="post" formaction="connexion">Connexion 
</button>
<input type="checkbox" id="saveMDP" name="saveMDP">  
  <label for="saveMDP">Se souvenir de moi</label>
  <br>
  <a href="A DEFINIR">Mot de passe oublié</a>
  </div>
</form>

   <form><button type="submit" name="inscription" formmethod="get" formaction="inscription">Créer un compte 
</button>
<a href="enchere">
		<button type="button" >Annuler</button></a>
		</form>
</div>
</body>
</html>
