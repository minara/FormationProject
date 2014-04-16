<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">
	
		<h1>
			<spring:message code="${topMessage}" text="default text" />
			<span id="language"> <a href="?language=en">English</a>|<a href="?language=fr">Français</a></span>
		</h1>
	
	<h1 id="homeTitle">${wrapper.count} <spring:message code="found" text="default text" /></h1>
	<div id="actions">
		<form action="./dashboard/search" method="GET">
			<input type="search" id="searchbox" name="search"
				value="${wrapper.name}" placeholder="<spring:message code="search" text="default text" />"
				data-validation="alphanumeric" data-validation-allowing=" -_/."
				data-validation-optional="true"> <input type="submit"
				id="searchsubmit" value="<spring:message code="filter" text="default text" />" class="btn primary">
			<select name="searchDomain">
				<option value="0" ${wrapper.searchDomain==0 ? 'selected' : ''}><spring:message code="computer" text="default text" /></option>
				<option value="1" ${wrapper.searchDomain==1 ? 'selected' : ''}><spring:message code="company" text="default text" /></option>
			</select>
		</form>
		
		<c:set var="spmsg">
			<spring:message code="add" text="default text" />
		</c:set>
		<p:link call="./Computer" title="${spmsg}" class="btn success" id="add" />

	</div>


	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th></th>
				<!-- Table header for Computer Name -->
				<th><spring:message code="name" text="default text" /> <p:orderBy order="${wrapper.order}"
						type="NAME" asc="${wrapper.asc}" call="./dashboard/order"
						title="^" style="height:15px; width:15px" />

				</th>
				<th><spring:message code="introduced" text="default text" /> <p:orderBy order="${wrapper.order}"
						type="INTRODUCED" asc="${wrapper.asc}" call="./dashboard/order"
						title="^" style="height:15px; width:15px" />

				</th>
				<!-- Table header for Discontinued Date -->
				<th><spring:message code="discontinued" text="default text" /> <p:orderBy order="${wrapper.order}"
						type="DISCONTINUED" asc="${wrapper.asc}" call="./dashboard/order"
						title="^" style="height:15px; width:15px" />
				</th>
				<!-- Table header for Company -->
				<th><spring:message code="company" text="default text" /> <p:orderBy order="${wrapper.order}" type="COMPANY"
						asc="${wrapper.asc}" call="./dashboard/order" title="^"
						style="height:15px; width:15px" />
				</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${wrapper.objects}">
				<tr>
					<td class="col1"><p:link call="./Computer/editForm"
							computerId="${computer.id}" title="Edit"
							img="/computer-database/image/boutonplume.jpg" style="height:25px; width:25px" /></td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td><p:link call="./Computer/delete"
							computerId="${computer.id}" title="Delete"
							img="/computer-database/image/boutondelete.jpg" style="height:25px; width:25px" /></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<p:pager pageNumber="${wrapper.page}" lastPage="${wrapper.pageMax}"
		perPage="${wrapper.limit}" search="${wrapper.name}" />

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
		$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />
