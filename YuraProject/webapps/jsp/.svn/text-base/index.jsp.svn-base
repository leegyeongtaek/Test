<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="../js/lib/jquery-1.4.2.js"></script>
<script type="text/javascript">
var $J = jQuery.noConflict();
//http://127.0.0.1/CertificationProject/cert/select/retrieveAnnouncementInfo.tmall?memId='crewmate'
function test1(){
	try{
		var url = "/product/select/test1.tmall";
		$J.post(url, {"memId":"crewmate"}, function(data){
			try{
				alert(data);
				data = eval('('+data+')');
				alert(data);
			}catch(e){}
		});
	}catch(e){}
}


function test2(){
	try{
		var frm = document.frmMain;
		frm.action = "/product/select/test2.tmall?memId=crewmate";
		frm.submit();
	}catch(e){}
}
</script>
</head>
<body>
<form name="frmMain" id="frmMain" method="post" enctype="multipart/form-data">
	hi~~~~!!!!
	<input type="button" value="AJAX Type" onclick="test1()" />
	<input type="button" value="Redirect Type" onclick="test2()" />
</form>	
</body>
</html>