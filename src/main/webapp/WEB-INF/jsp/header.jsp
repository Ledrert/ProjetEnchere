<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<header>ENI-Enchère
<nav id="menu"><c:forEach var="lien" items="${liensMenu }">
<a href="${pageContext.request.contextPath }${lien}">${listMenu.get(lien)}</a></c:forEach>
</nav>
</header>