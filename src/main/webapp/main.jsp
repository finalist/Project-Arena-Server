<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	<link type="text/css" rel="stylesheet" href="ARenaWA.css">
	
	<title>ARena</title>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.11&sensor=true" type="text/javascript"></script>
	
	<script type="text/javascript" language="javascript" src="arenaserver/arenaserver.nocache.js"></script>

</head>

<body>

<!-- For printing support -->
 <iframe id="__printingFrame" style="width:0;height:0;border:0"></iframe>

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
	style="position: absolute; width: 0; height: 0; border: 0"></iframe>

<div id="content" style="heigth: 100%; width: 100%"></div>
<script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
	var pageTracker = _gat._getTracker("<c:out value='${googleAnalyticskey}'/>");
    pageTracker._initData();
    pageTracker._trackPageview("/arena/Designer");
</script>
</body>
</html>
