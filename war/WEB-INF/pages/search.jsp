<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <script src="<c:url value="/resources/js/jquery-1.11.3.min.js"/>"></script>
    <script src="<c:url value="/resources/js/fetchinfo.js"/>"></script>
    <title>Discography Search</title>
    <style>
        tr {
            text-align:center
        }
    </style>
  </head>
  <body>
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '453044741542416',
      xfbml      : true,
      version    : 'v2.4'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
	<center>
		<font size='5'>
		<h3><b>Discography Search</b></h3>
		<form id = "queryform">
			Search: <input type="text" name="keyword" id="keyword"><br>
			Type: <select id="type">
			<c:forEach var="type" items="${types}">
      				<option value=${type} >${type}
      		</c:forEach>
      			</select><br>
				<input type="button" value="Search" id="submitData"></font>
		</form>
		<div id="content_table"></div>
</center>
  </body>
</html>
