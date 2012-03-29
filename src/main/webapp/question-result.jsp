<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="../../../../ARenaWA.css">
</head>
<body>
	<c:if test="${correct != null}">
		<div class="questionResult">
			<h1>${correct}</h1>
		</div>
		<form action="return" method="post">
			<input type="submit" value="close" />
		</form>
	</c:if>
	<c:if test="${correct == null}">
		<div class="questionResult">
			<h1>${incorrect}</h1>
		</div>
		<form action="return" method="post">
			<input type="submit" value="close" />
		</form>
	</c:if>
</body>
</html>