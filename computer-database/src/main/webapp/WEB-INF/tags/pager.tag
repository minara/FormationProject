<%@ tag body-content="empty" %>
<%@ attribute name="pageNumber" required="true" %>
<%@ attribute name="lastPage" required="true" %>
<%@ attribute name="perPage" required="true" %>
<%@ attribute name="search" %>

<div>
		<a class="btn small" href="./dashboard?page=1">First</a>
		<a class="btn small" href="./dashboard?page=${pageNumber-1}">Prec.</a>
		<span>${pageNumber}/${lastPage}</span>
		<a class="btn small" href="./dashboard?page=${pageNumber+1}">Next</a>
		<a class="btn small" href="./dashboard?page=${lastPage}">Last</a>
		<form action="./dashboard/limit" method="get" >
			<select name="limitation">
				<option value="10" ${perPage==10 ? 'selected' : ''}>10 per page</option>
				<option value="20" ${perPage==20 ? 'selected' : ''} >20 per page</option>
				<option value="50" ${perPage==50 ? 'selected' : ''} >50 per page</option>
			</select>
			<input type="submit" id="perpagesubmit" value="Change" class="btn small">
		</form>
	</div>