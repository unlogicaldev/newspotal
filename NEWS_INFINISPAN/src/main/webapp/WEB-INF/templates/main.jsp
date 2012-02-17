<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html xmlns:fb="http://www.facebook.com/2008/fbml"> 
<head>
	<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>INFINISPAN_NEWS</title>
	<link rel="stylesheet" href="/resources/css/common.css">
	<link rel="stylesheet" href="/resources/css/jquery-ui-1.8.17.custom.css">
	<script src="http://www.google.com/jsapi" type="text/javascript"></script>
	<script src="/resources/js/common.js"></script>	
</head>
<body>
<div id="warp">
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="contents">
		<tiles:insertAttribute name="body" />
	</div>
	<form name="fm" method="post"></form>
</div>
</body>
</html>