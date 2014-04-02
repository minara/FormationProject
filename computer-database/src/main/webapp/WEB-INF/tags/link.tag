<%@ tag body-content="empty" dynamic-attributes="dynattrs"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="call" required="true"%>
<%@ attribute name="search"%>
<%@ attribute name="searchDomain"%>
<%@ attribute name="page"%>
<%@ attribute name="limit"%>
<%@ attribute name="order"%>
<%@ attribute name="asc"%>
<%@ attribute name="computerId"%>
<%@ attribute name="title"%>
<%@ attribute name="img"%>
<%@ attribute name="style"%>

<a href="${call}?search=${search}&searchDomain=${searchDomain}&page=${page}&limit=${limit}&order=${order}&asc=${asc}&computerId=${computerId}"
	<c:forEach items="${dynattrs}" var="dyn">
		${dyn.key}="${dyn.value}"
	</c:forEach>
>
	<c:choose >
		<c:when test="${img!=null}">
			<img alt="${title}" src="${img}" style="${style}">
		</c:when>
		<c:otherwise>
			${title}
		</c:otherwise>
	</c:choose>
</a>


