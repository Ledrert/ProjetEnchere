<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="java.util.Set" %>
<header>ENI-Enchère
<nav id="menu"><c:forEach var="lien" items="${listMenu.keySet() }">
<a href="${lien }">${listMenu.get(lien)}</a></c:forEach>
</nav>
</header>