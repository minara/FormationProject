<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>

<section id="main">
	<h1 id="homeTitle">${wrapper.count} Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search" value="${wrapper.name}"
				placeholder="Search name" data-validation="alphanumeric" data-validation-allowing=" -_/." data-validation-optional="true" > 
			<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">
			<select name="searchDomain">
				<option value="0" ${wrapper.searchDomain==0 ? 'selected' : ''}>Computer</option>
				<option value="1" ${wrapper.searchDomain==1 ? 'selected' : ''}>Company</option>
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
					<a href="DashboardServlet?order=name&search=${wrapper.name}">
							<img alt="^" src="<c:out value='${source[0]}' default='image/downnoir.jpg'></c:out>" style="height:15px; width:15px"/>
						</a>
				</th>
				<th>Introduced Date
					<a href="DashboardServlet?order=introduced&search=${wrapper.name}">
							<img alt="^" src="<c:out value="${source[1]}"> </c:out>" style="height:15px; width:15px"/>
						</a>
				</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date
					<a href="DashboardServlet?order=discontinued&search=${wrapper.name}">
							<img alt="^" src="<c:out value="${source[2]}"> </c:out>" style="height:15px; width:15px"/>
						</a>
				</th>
				<!-- Table header for Company -->
				<th>Company
					<a href="DashboardServlet?order=company&search=${wrapper.name}">
							<img alt="^" src="<c:out value="${source[3]}"> </c:out>" style="height:15px; width:15px"/>
						</a>
				</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${wrapper.objects}">
				<tr>
					<td class="col1">
						<a href="ModifyServlet?computerId=${computer.id}&search=${wrapper.name}">
							<img alt="Edit" src="image/boutonplume.jpg" style="height:25px; width:25px">
						</a>
					</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td>
						<a href="DashboardServlet?computerId=${computer.id}&delete=true&search=${wrapper.name}">
							<img alt="Delete" src="image/boutondelete.jpg" style="height:25px; width:25px">
						</a>
					</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	
	<p:pager pageNumber="${wrapper.page}" lastPage="${wrapper.pageMax}" perPage="${wrapper.limit}" search="${wrapper.name}"/>
	
	<script type="text/javascript">if('${error}'==='true'){alert('${errorMsg}');}</script> 
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
	$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />
