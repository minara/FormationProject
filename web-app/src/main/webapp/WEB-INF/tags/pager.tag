<%@ tag body-content="empty" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="lastPage" required="true" %>
<%@ attribute name="perPage" required="true" %>
<%@ attribute name="search" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>
		<a class="btn small" href="./dashboard?page=1"><spring:message code="first" text="default text" /></a>
		<a class="btn small" href="./dashboard?page=${pageNumber-1<1? 1 : pageNumber-1 }"><spring:message code="prec" text="default text" /></a>
		<span>${pageNumber}/${lastPage}</span>
		<a class="btn small" href="./dashboard?page=${pageNumber+1>lastPage? lastPage : pageNumber+1}"><spring:message code="next" text="default text" /></a>
		<a class="btn small" href="./dashboard?page=${lastPage}"><spring:message code="last" text="default text" /></a>
		<form action="./dashboard/limit" method="get" >
			<select name="limitation">
				<option value="10" ${perPage==10 ? 'selected' : ''}>10 <spring:message code="per" text="default text" /></option>
				<option value="20" ${perPage==20 ? 'selected' : ''} >20 <spring:message code="per" text="default text" /></option>
				<option value="50" ${perPage==50 ? 'selected' : ''} >50 <spring:message code="per" text="default text" /></option>
			</select>
			<input type="submit" id="perpagesubmit" value="<spring:message code="change" text="default text" />" class="btn small">
		</form>
	</div>