<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script
	src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var myUrl = "service/test/login";

	function login() {
		var userName = dojo.byId("nameTxt").value;
		var password = dojo.byId("cardTxt").value;
		dojo.xhrGet({
			url : myUrl,
			content : {
				"username" : userName,
				"password" : password
			}
		}).addCallback(function(msg) {
			alert(msg);
		});
	}
</script>
<title>Insert title here</title>
</head>
<body>
<p>userName:<input type="text" id="nameTxt" /> card:<input
	type="text" id="cardTxt" />
<button onclick="login()">login</button>
</p>
</body>
</html>