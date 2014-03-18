<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>

<section id="main">
	<h1 id="homeTitle">${count} Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search" value="${name}"
				placeholder="Search name" data-validation="alphanumeric" data-validation-allowing=" -_/." data-validation-optional="true" > 
			<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
			<select name="searchDomain">
				<option value="0" ${domain==0 ? 'selected' : ''}>Computer</option>
				<option value="1" ${domain==1 ? 'selected' : ''}>Company</option>
			</select>
		</form>

		<a class="btn success" id="add" href="AddComputerServlet">Add
			Computer</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th></th>
				<!-- Table header for Computer Name -->
				<th>Computer Name
					<a href="DashboardServlet?order=name&search=${name}">
							<img alt="^" src="${nameSrc}" style="height:15px; width:15px">
						</a>
				</th>
				<th>Introduced Date
					<a href="DashboardServlet?order=introduced&search=${name}">
							<img alt="^" src="${introSrc}" style="height:15px; width:15px">
						</a>
				</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date
					<a href="DashboardServlet?order=discontinued&search=${name}">
							<img alt="^" src="${discoSrc}" style="height:15px; width:15px">
						</a>
				</th>
				<!-- Table header for Company -->
				<th>Company
					<a href="DashboardServlet?order=company&search=${name}">
							<img alt="^" src="${compSrc}" style="height:15px; width:15px">
						</a>
				</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
				<tr>
					<td class="col1">
						<a href="ModifyServlet?computerId=${computer.id}&search=${name}">
							<img alt="Edit" src="image/boutonplume.jpg" style="height:25px; width:25px">
						</a>
					</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td>
						<a href="DashboardServlet?computerId=${computer.id}&delete=true&search=${name}">
							<img alt="Delete" src="image/boutondelete.jpg" style="height:25px; width:25px">
						</a>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	
	<p:pager pageNumber="${pageNumber}" lastPage="${lastPage}" perPage="${perPage}" search="${name}"/>
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
	$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />
