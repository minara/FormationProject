<%@ tag body-content="empty" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="lastPage" required="true" %>
<%@ attribute name="perPage" required="true" %>
<%@ attribute name="search" %>

<div>
		<a class="btn small" href="DashboardServlet?page=1&search=${search}">First</a>
		<a class="btn small" href="DashboardServlet?page=${pageNumber-1}&search=${search}">Prec.</a>
		<span>${pageNumber}/${lastPage}</span>
		<a class="btn small" href="DashboardServlet?page=${pageNumber+1}&search=${search}">Next</a>
		<a class="btn small" href="DashboardServlet?page=${lastPage}&search=${search}">Last</a>
		<form action="" method="get" >
			<input type="hidden" name="search" value="${search}">
			<select name="limitation">
				<option value="10" ${perPage==10 ? 'selected' : ''}>10 per page</option>
				<option value="20" ${perPage==20 ? 'selected' : ''} >20 per page</option>
				<option value="50" ${perPage==50 ? 'selected' : ''} >50 per page</option>
			</select>
			<input type="submit" id="perpagesubmit" value="Change" class="btn small">
		</form>
	</div>