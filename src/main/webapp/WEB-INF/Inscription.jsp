<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/style.css">
<title>Je m'inscris !</title>
</head>
<body>

	<%@ include file="/WEB-INF/jsp/header.jsp" %>

<main>

	<h1>Creation de mon profil</h1>
	
	<%@ include file="/WEB-INF/jsp/form.jsp" %>

    <div>
      	<button class="button" type="submit" >Enregistrer</button>	
        					
		<a href="#">
		<button type="button" >Supprimer mon compte</button></a>
	</div>

</main>

<%-- 	<%@ include file="/WEB-INF/jsp/footer.jsp" %> --%> </body>

</html>