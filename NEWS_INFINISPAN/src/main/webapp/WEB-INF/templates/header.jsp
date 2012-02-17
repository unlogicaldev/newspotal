<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empty USER_INFO}">
	<form name="nFm" method="post" action="/user/signin">
		id <input type="text" name="userId" size=15/>
		password <input type="password" name="password" size=15/>
		<input type="submit" value="SignIn"/>
		<a href="/user/signup"><input type="button" value="SignUp"/></a><font color="red">${message}</font> 
		</form>
	</c:when>
	<c:otherwise>
	<form action="/user/signout">
		${USER_INFO.userId}(${USER_INFO.userName})
		<input type="submit" value="SignOut"/>
		</form>
	</c:otherwise>
</c:choose>