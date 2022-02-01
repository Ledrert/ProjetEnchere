<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Connexion</title>
</head>
<body>
<center>
<h1>Connexion</h1>
</center>
<form action="/ma-page-de-traitement" method="post">
    <div>
        <label for="identifiant">Identifiant :</label>
        <input type="text" id="identifiant" name="identifiant" maxlength="30">
    </div><br>
       <div>
        <label for="mot de passe">Mot de passe :</label>
        <input type="password" id="mot de passe" name="mot de passe" maxlength="30">
    </div>
    <br>
    
    <form><button type="submit" name="connexion" formmethod="get" formaction="enchere">Modifier 
</button></form>

<div>
  <input type="checkbox" id="saveMDP" name="saveMDP">
         
  <label for="saveMDP">Se souvenir de moi</label>
  <br>
  <ul>
  <a href="A DEFINIR">Mot de passe oublié</a>
</ul>
</div>

   <form><button type="submit" name="inscription" formmethod="get" formaction="inscription">Créer un compte 
</button></form>

</body>
</html>