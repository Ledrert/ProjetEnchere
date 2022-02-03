<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
</button></form>
</div>
</body>
</html>