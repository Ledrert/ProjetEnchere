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
<jsp:include page="jsp/header.jsp"></jsp:include>
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
    <p style="color : red">Identifiants incorrectes, veuillez r�essayer.</p>
	</c:if>
	
    <div id="btmCo">
    <button type="submit" name="connexion" formmethod="post" formaction="connexion">Connexion 
</button>
<input type="checkbox" id="saveMDP" name="saveMDP">  
  <label for="saveMDP">Se souvenir de moi</label>
  <br>
  <a href="A DEFINIR">Mot de passe oubli�</a>
  </div>
</form>

   <form><button type="submit" name="inscription" formmethod="get" formaction="inscription">Cr�er un compte 
</button>
<a href="enchere">
		<button type="button" >Annuler</button></a>
		</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body> 
</html>