<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section id="main">

	<h1>Edit Computer</h1>
	
	<form action="" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" value="${computer.name}" data-validation="alphanumeric" data-validation-allowing=" -_/."/>
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" value="${computer.introduced}" data-validation="pastdate" data-validation-optional="true"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" value="${computer.discontinued}" data-validation="pastdate" data-validation-optional="true"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="0">--</option>
						<c:forEach var="comp" items="${companies}">
							<option value="${comp.id}" ${computer.company==comp ? 'selected' : ''}>${comp.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn primary">
			or <a href="DashboardServlet?search=${search}" class="btn">Cancel</a>
		</div>
	</form>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<script>
	$.formUtils.addValidator({
		name:"pastdate",
		validatorFunction:function(val,$el,conf){
			var dateFormat="yyyy-mm-dd";
			if($el.valAttr("format")){dateFormat=$el.valAttr("format");}
			else if(typeof conf.dateFormat!="undefined"){dateFormat=conf.dateFormat;}
			var inputDate=$.formUtils.parseDate(val,dateFormat);
			if(!inputDate){return false;}
			var d=new Date;
			var currentYear=d.getFullYear();
			var year=inputDate[0];
			var month=inputDate[1];
			var day=inputDate[2];
			if(year===currentYear){
				var currentMonth=d.getMonth()+1;
				if(month===currentMonth){
					var currentDay=d.getDate();
					return day<=currentDay;
				}else{
					return month<currentMonth;
			}}else{
				return year<currentYear;
		}},
		errorMessage:"incorrect date; the date must be in the past",
		errorMessageKey:""});
	
		$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />