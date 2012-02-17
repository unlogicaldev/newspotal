<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empty SESSION}">
		id <input type="text" name="userId" size=15/>
		password <input type="password" name="password" size=15/>
		<input type="button" value="SignIn"/>
		<input type="button" value="SignUp"/>
	</c:when>
	<c:otherwise>
		${SESSION.userId}(${SESSION.userName})
		<input type="button" value="SignOut"/>
	</c:otherwise>
</c:choose>