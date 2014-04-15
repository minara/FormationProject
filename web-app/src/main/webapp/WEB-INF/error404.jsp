<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<section id="main">
	<h1>
		<spring:message code="lost" text="default text" />
		<span id="language"> <a href="?language=en">English</a>|<a
			href="?language=fr">Français</a></span>
	</h1>

	<c:set var="spmsg">
		<spring:message code="home" text="default text" />
	</c:set>
	<div style="width: 50%; margin: auto; text-align: center;">
		<img alt="^^" src="/computer-database/image/somnambule.jpg" style="vertical-align: middle"> 
		
			<p:link call="/computer-database/dashboard" title="${spmsg}"
				class="btn success"  style="vertical-align: middle"/>
	</div>
</section>

<jsp:include page="include/footer.jsp" />