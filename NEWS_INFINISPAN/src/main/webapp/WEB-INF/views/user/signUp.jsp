<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form name="nFm" method="post">
	<input type="hidden" name="_id" value="${user._id}" />
	<input type="text" name="userId" value="${user.userId}" />
	<input type="text" name="password" value="${user.password}" />
	<input type="text" name="email" value="${user.email}" />
	<input type="text" name="year" value="${user.year}" />
	<input type="text" name="gender" value="${user.gender}" />
	<input type="text" name="tags" value="${user.tags}" />
	<input type="submit"/>
</form>
<script src="/resources/js/signup.js"></script>		