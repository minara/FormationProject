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

		<p:link call="AddComputerServlet" search="${wrapper.name}" title="Add Computer" class="btn success" id="add" />
		
	</div>
	

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th></th>
				<!-- Table header for Computer Name -->
				<th>Computer Name
				 <p:orderBy order="${wrapper.order}" type="NAME" asc="${wrapper.asc}" call="DashboardServlet" search="${wrapper.name}"
				 title="^" style="height:15px; width:15px"/>

				</th>
				<th>Introduced Date
				 <p:orderBy order="${wrapper.order}" type="INTRODUCED" asc="${wrapper.asc}" call="DashboardServlet" search="${wrapper.name}"
				 title="^" style="height:15px; width:15px"/>
					
				</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date
					 <p:orderBy order="${wrapper.order}" type="DISCONTINUED" asc="${wrapper.asc}" call="DashboardServlet" search="${wrapper.name}"
				 title="^" style="height:15px; width:15px"/>
				</th>
				<!-- Table header for Company -->
				<th>Company
				 <p:orderBy order="${wrapper.order}" type="COMPANY" asc="${wrapper.asc}" call="DashboardServlet" search="${wrapper.name}"
				 title="^" style="height:15px; width:15px"/>
				</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${wrapper.objects}">
				<tr>
					<td class="col1">
						<p:link call="ModifyServlet" computerId="${computer.id}" delete="false" search="${wrapper.name}"
						title="Edit" img="image/boutonplume.jpg" style="height:25px; width:25px"/>
					</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td>
						<p:link call="DashboardServlet" computerId="${computer.id}" delete="true" search="${wrapper.name}"
						title="Delete" img="image/boutondelete.jpg" style="height:25px; width:25px"/>
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
