<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<title>Je m'inscris !</title>
</head>
<body>

	<%@ include file="/WEB-INF/jsp/header.jsp" %>

<main>

	<h1>Creation de mon profil</h1>
	
	<%@ include file="/WEB-INF/jsp/form.jsp" %>

    <div class="buttonForm">
    	<a href="inscription"> 
      	<button class="button" type="submit" >Enregistrer</button></a>
        					
		<a href="enchere">
		<button type="button" >Annuler</button></a>
	</div>

</main>

<%-- 	<%@ include file="/WEB-INF/jsp/footer.jsp" %> --%> </body>

</html>