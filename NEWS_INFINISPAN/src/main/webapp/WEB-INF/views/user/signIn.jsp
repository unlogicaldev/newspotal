<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form name="nFm" method="post">
	userId : <input type="text" name="userId" value="${user.userId}" /><br/>
	password : <input type="text" name="password" value="${user.password}" /><br/>
	<input type="submit"/>
</form>
<script src="/resources/js/signin.js"></script>	