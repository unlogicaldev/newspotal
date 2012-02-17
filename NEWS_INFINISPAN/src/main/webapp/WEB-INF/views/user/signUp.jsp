<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3 style="font-color:red;">${message}</h3>
<form name="nFm" method="post">
	<input type="hidden" name="_id" value="${user._id}" />
	userId : <input type="text" name="userId" value="${user.userId}" /><br/>
	userName : <input type="text" name="userName" value="${user.userName}" /><br/>
	password : <input type="text" name="password" value="${user.password}" /><br/>
	email : <input type="text" name="email" value="${user.email}" /><br/>
	year : <input type="text" name="year" value="${user.year}" /><br/>
	gender : <input type="text" name="gender" value="${user.gender}" /><br/>
	tags : <input type="text" name="tags" value="${user.tags}" /><br/>
	<input type="submit"/>
</form>
<script src="/resources/js/signup.js"></script>		