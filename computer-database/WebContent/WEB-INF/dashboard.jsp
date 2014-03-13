<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section id="main">
	<h1 id="homeTitle">${nbComputer} Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search" value="${name}"
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" class="btn primary">
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
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date</th>
				<!-- Table header for Company -->
				<th>Company</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
				<tr>
					<td class="col1">
						<form action="ModifyServlet" method="GET">
							<input type="hidden" name="modify" value="${computer.id}"> 
							<input type="image" src="image/boutonplume.jpg" style="height:25px; width:25px" >
						</form>
					</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
