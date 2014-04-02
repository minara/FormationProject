<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section id="main">

	<h1>Edit Computer</h1>
	
	<form:form modelAttribute="computerDTO" action="/computer-database/Computer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<form:input path="name" type="text" name="name" value="${computer.name}" data-validation="alphanumeric" 
					data-validation-allowing=" -_/." data-validation-error-msg="${errorResponse[0]}"/>
					<span class="help-inline">Required</span>
					<div class="help-block" style="color:red"><form:errors path="name"></form:errors> </div>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<form:input path="introduced" id="introduced" type="date" name="introducedDate" value="${computer.introduced}" data-validation="pastdate" 
					data-validation-optional="true" data-validation-error-msg="${errorResponse[1]}"/>
					<span class="help-inline">YYYY-MM-DD</span>
					<div class="help-block" style="color:red"><form:errors path="introduced"></form:errors> </div>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<form:input path="discontinued" type="date" name="discontinuedDate" value="${computer.discontinued}" data-validation="limitdate"  data-validation-limit="#introduced"
					data-validation-optional="true" data-validation-error-msg="${errorResponse[2]}"/>
					<span class="help-inline">YYYY-MM-DD</span>
					<div class="help-block" style="color:red"><form:errors path="discontinued"></form:errors> </div>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<form:select path="companyId" name="company">
						<form:option value="0" label="--"/>
						<form:options items="${companies}" itemValue="id" itemLabel="name"/>
					</form:select>
				</div>
			</div>
			<form:hidden path="id"/>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Edit" class="btn primary">
			or <p:link call="/computer-database/dashboard" title="Cancel" class="btn" />
		</div>
	</form:form>
	<script type="text/javascript">if('${error}'==='true'){alert('${errorMsg}');}</script> 
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
		errorMessage:"Incorrect date; the date must be in the past",
		errorMessageKey:""});

	$.formUtils.addValidator({
		name:"limitdate",
		validatorFunction:function(val,$el,conf){
			var dateFormat="yyyy-mm-dd";
			if($el.valAttr("format")){dateFormat=$el.valAttr("format");}
			else if(typeof conf.dateFormat!="undefined"){dateFormat=conf.dateFormat;}
			var introduced=false;
			if($el.valAttr("limit")){
				var limit=$el.valAttr("limit");
				var date=$(limit).val();
				if(date!=""){
					introduced=$.formUtils.parseDate(date,dateFormat);
				}
			}
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
					if(day>currentDay){return false;}
				}else if (month>currentMonth){ return false;}
			}else if(year>currentYear){ return false;}
			if (!introduced) { return true;} 
			else {
				var limitYear = introduced[0];
				var limitMonth = introduced[1];
				var limitDay = introduced[2];
				if (year === limitYear) {
					if (month === limitMonth) {
						return day > limitDay;
					} else {
							return month > limitMonth;
					}
				} else {
					return year>limitYear;
				}
			}

		},
		errorMessage : "",
		errorMessageKey : ""
		});
	
		$.validate();
	</script>
</section>

<jsp:include page="include/footer.jsp" />