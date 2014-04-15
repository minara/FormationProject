<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<section id="main">

	<h1>
		<spring:message code="500" text="default text" />
	</h1>

	<c:set var="spmsg">
		<spring:message code="home" text="default text" />
	</c:set>
	<p:link call="/computer-database/dashboard" title="${spmsg}"
		class="btn success" id="add" />
	<h2>${name}: </h2>
	<h3>${message}</h3>
	<p>${stackTrace}</p>

</section>

<jsp:include page="include/footer.jsp" />