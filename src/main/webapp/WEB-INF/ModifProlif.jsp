<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/style.css">
<title>Je modifie mon profil</title>
</head>
<body>

	<%@ include file="/WEB-INF/jsp/header.jsp" %>

<main>

	<h1>Modification de mon profil</h1>
	
	<%@ include file="/WEB-INF/jsp/form.jsp" %>

	<div>
     	<button class="button" type="submit" >Valider</button>	
        					
		<a href="#">
		<button type="button" >Annuler</button></a>
	</div>
	
</main>

<%-- 	<%@ include file="/WEB-INF/jsp/footer.jsp" %> --%> </body>

</html>