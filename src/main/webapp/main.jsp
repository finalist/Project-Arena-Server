<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->
<link type="text/css" rel="stylesheet" href="ARenaWA.css">

<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<title>ARena</title>
<script
	src="https://maps.googleapis.com/maps/api/js?sensor=true&key=AIzaSyAznPQJZawn-phM6UTge2tMeUEVQ3B63wI"
	type="text/javascript"></script>
<script type="text/javascript">
      var map;
      function initialize() {
        var myOptions = {
          zoom: 8,
          center: new google.maps.LatLng(-34.397, 150.644),
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById('map_canvas'),
            myOptions);
      }

      google.maps.event.addDomListener(window, 'load', initialize);
    </script>

<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript"
	src="arenaserver/arenaserver.nocache.js"></script>

</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
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
