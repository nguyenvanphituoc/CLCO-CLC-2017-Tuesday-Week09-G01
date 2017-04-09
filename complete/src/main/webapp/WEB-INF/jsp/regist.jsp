<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>User Registration Form</title>
</head>

<body>
<div class="well lead">User Registration Form</div>
<form:form method="POST" modelAttribute="webInfo" class="form-horizontal">
<label class="col-md-3 control-lable" for="description">description</label>
<form:input type="text" path="description" id="description" class="form-control input-sm"/>

<label class="col-md-3 control-lable" for="content">Content</label>
<form:input type="text" path="content" id="content" class="form-control input-sm"/>

<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/' />">Cancel</a>
</form:form>


</body>