<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<header>ENI-Ench�re
<nav id="menu"><c:forEach var="menu" items="${listMenu }">
<a href="#">${menu}</a></c:forEach>
</nav>
</header>