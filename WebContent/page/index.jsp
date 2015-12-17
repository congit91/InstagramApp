<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/my-style.css" /> 
	
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Photos from Instagram social network</h1>
	<form action="PhotoServlet">
		<input id="access-token" type="hidden" value="${accessToken}" name="accessToken" />
		<label>Enter user name: </label>
		<input id="user-name" type="text" name="username" value="${username }" />
		<input id="user-id" type="hidden" value="${userId }" />
		<input type="submit" value="Load photos" /> <br/>
		<label>Number of photos: </label><label id="numberOfPhotos"></label>
		<input type=button id="btn-load" onclick="loadPhotos()" value="Load more" /> <br/>
		<div id="photos"></div>
		<div id="viewPhoto" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		        <div class="modal-body">
		            <img id="img-src" class="img-responsive">
		        </div>
		    </div>
		  </div>
		</div>
	</form>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>