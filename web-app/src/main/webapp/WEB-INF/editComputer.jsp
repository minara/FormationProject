<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">

	<h1><spring:message code="edit" text="default text" />
	<span id="language"> <a href="/computer-database/Computer/editForm?language=en&computerId=${computerDTO.id}">English</a>|<a href="/computer-database/Computer/editForm?language=fr&computerId=${computerDTO.id}">Français</a></span>
	</h1>
	
	<c:set var="nameError">
		<spring:message code="name.error" text="default text" />
	</c:set>
	<c:set var="introError">
		<spring:message code="intro.error" text="default text" />
	</c:set>
	<c:set var="discoError">
		<spring:message code="disco.error" text="default text" />
	</c:set>
	
	<form:form modelAttribute="computerDTO" action="/computer-database/Computer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="name" text="default text" />:</label>
				<div class="input">
					<form:input path="name" type="text" name="name" value="${computer.name}" data-validation="alphanumeric" 
					data-validation-allowing=" -_/." data-validation-error-msg="${nameError}"/>
					<span class="help-inline"><spring:message code="required" text="default text" /></span>
					<div class="help-block" style="color:red"><form:errors path="name"></form:errors> </div>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced"><spring:message code="introduced" text="default text" />:</label>
				<div class="input">
					<form:input path="introduced" id="introduced" type="date" name="introducedDate" value="${computer.introduced}" data-validation="pastdate" 
					data-validation-optional="true" data-validation-error-msg="${introError}"/>
					<span class="help-inline">YYYY-MM-DD</span>
					<div class="help-block" style="color:red"><form:errors path="introduced"></form:errors> </div>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="discontinued" text="default text" />:</label>
				<div class="input">
					<form:input path="discontinued" type="date" name="discontinuedDate" value="${computer.discontinued}" data-validation="limitdate"  data-validation-limit="#introduced"
					data-validation-optional="true" data-validation-error-msg="${discoError}"/>
					<span class="help-inline">YYYY-MM-DD</span>
					<div class="help-block" style="color:red"><form:errors path="discontinued"></form:errors> </div>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="companyName" text="default text" />:</label>
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
			<input type="submit" value="<spring:message code="edit2" text="default text" />" class="btn primary">
			or 
			<c:set var="spmsg"><spring:message code="cancel" text="default text" /></c:set>
			<p:link call="/computer-database/dashboard" title="${spmsg}" class="btn" />
		</div>
	</form:form>
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