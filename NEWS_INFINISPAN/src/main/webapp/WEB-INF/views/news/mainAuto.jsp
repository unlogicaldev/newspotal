<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="tabs">
	<ul>
		<c:forEach items="${cList}" var="x">
			<li><a href="#${x}">${x}</a></li>
		</c:forEach>
	</ul>
	<c:forEach items="${cList}" var="x">
		<div id="${x}"></div>
	</c:forEach>
</div>
<script src="/resources/js/newsAuto.js"></script>	