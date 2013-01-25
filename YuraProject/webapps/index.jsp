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

/**

*

*  Base64 encode / decode

*  http://www.webtoolkit.info/

*

**/

 

var Base64 = {

      // private property
      _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

      // public method for encoding
      encode : function (input) {

            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;

            input = Base64._utf8_encode(input);

            while (i < input.length) {

                  chr1 = input.charCodeAt(i++);
                  chr2 = input.charCodeAt(i++);
                  chr3 = input.charCodeAt(i++);

                  enc1 = chr1 >> 2;
                  enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                  enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                  enc4 = chr3 & 63;

                  if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                  } else if (isNaN(chr3)) {
                        enc4 = 64;
                  }

                  output = output +
                  this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
                  this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

            }

            return output;

      },

      // public method for decoding
      decode : function (input) {

            var output = "";
            var chr1, chr2, chr3;
            var enc1, enc2, enc3, enc4;
            var i = 0;

            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            while (i < input.length) {

                  enc1 = this._keyStr.indexOf(input.charAt(i++));
                  enc2 = this._keyStr.indexOf(input.charAt(i++));
                  enc3 = this._keyStr.indexOf(input.charAt(i++));
                  enc4 = this._keyStr.indexOf(input.charAt(i++));

                  chr1 = (enc1 << 2) | (enc2 >> 4);
                  chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                  chr3 = ((enc3 & 3) << 6) | enc4;

                  output = output + String.fromCharCode(chr1);

                  if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                  }

                  if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                  }

            }

            output = Base64._utf8_decode(output);

            return output;

      },

      // private method for UTF-8 encoding

      _utf8_encode : function (string) {

            string = string.replace(/\r\n/g,"\n");
            var utftext = "";

            for (var n = 0; n < string.length; n++) {

                  var c = string.charCodeAt(n);

                  if (c < 128) {
                        utftext += String.fromCharCode(c);
                  }

                  else if((c > 127) && (c < 2048)) {
                        utftext += String.fromCharCode((c >> 6) | 192);
                        utftext += String.fromCharCode((c & 63) | 128);
                  }

                  else {
                        utftext += String.fromCharCode((c >> 12) | 224);
                        utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                        utftext += String.fromCharCode((c & 63) | 128);
                  }

            }

            return utftext;

      },

      // private method for UTF-8 decoding
      _utf8_decode : function (utftext) {

            var string = "";
            var i = 0;
            var c = c1 = c2 = 0;

            while ( i < utftext.length ) {
            	
                  c = utftext.charCodeAt(i);

                  if (c < 128) {
                        string += String.fromCharCode(c);
                        i++;
                  }

                  else if((c > 191) && (c < 224)) {
                        c2 = utftext.charCodeAt(i+1);
                        string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                        i += 2;
                  }

                  else {
                        c2 = utftext.charCodeAt(i+1);
                        c3 = utftext.charCodeAt(i+2);
                        string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                        i += 3;
                  }

            }

            return string;
      }
}


function test1(){
	try{
		var _url = "/cert/select/certNumConfirm.tmall";
		
		var str = Base64.encode('crewmate:11st');
		
		var option = {
				type : "POST",
				url : _url,
				data : {"memId":"crewmate", "passwd":"11st"},
				async : false,
				beforeSend : function(xhr){
					xhr.setRequestHeader("Authorization", "Basic c2lueTIwNjo0ODE5YzY1MjdmMDIzNGVhNmEzM2E1NTU0OGUyYjI2Mw==");
				},
				success :  function(data){
					try{
						alert(data);
						data = eval('('+data+')');
						alert(data);
					}catch(e){}
				}
		}
		
		$J.ajax(option);
		
		/*
		$J.post(url, {"memId":"crewmate"}, function(data){
			try{
				alert(data);
				data = eval('('+data+')');
				alert(data);
			}catch(e){}
		});
		*/
	}catch(e){}
}


function test2(){
	try{
		var frm = document.frmMain;
		frm.action = "/product/select/test2.tmall?memId=crewmate";
		frm.submit();
	}catch(e){}
}

function testtest() {
	try{
		var url = "/product/select/test3.tmall";
		$J.post(url, {"memId":"crewmate", "prdNo":93285543}, function(data){
			try{
				alert(data);
				data = eval('('+data+')');
			}catch(e){}
		});
	}catch(e){}
}

function test3(){
	var xhr = null;
	
	xhr = getXMLHttpRequest();
	
	var url = "http://towncert.11st.co.kr/cert/update/certNumConfirm.tmall?certSpotNo=22220001&certNo=123456789012&userIp=122345&userNo=12345";
	
	xhr.onreadystatechange = function(){
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				var str = xhr.responseText;
				alert("status1="+xhr.status)
				alert(str);
			}else{
				alert("status2="+xhr.status)
			}
		}
	}
	param = "certSpotNo=22220001&certNo=123456789012&userIp=122345&userNo=12345";
	
	xhr.open("POST", url, true);
	//xhr.setRequestHeader("Authorization", "Basic c2lueTIwNjo0ODE5YzY1MjdmMDIzNGVhNmEzM2E1NTU0OGUyYjI2Mw==");
	//xhr.send("certSpotNo=22220001&certNo=123456789012&userIp=122345&userNo=12345");
	xhr.send(null);
	
}

function getXMLHttpRequest(){
	if (window.ActiveXObject) {
		try {
			return new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e1){}
		
		try {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e2){}
		
		return null;
	}else{
		return new XMLHttpRequest();
	}
}

function test33(){
	alert(jQuery);
	try{
		var url = "http://127.0.0.1/cert/update/certNumConfirm.tmall";
		var option = {
				type : "POST",
				url : url,
				param : {"certSpotNo":22220001, "certNo":123456789012, "userIp":'127.0.0.1', "userNo":'12345'},
				async : true,
				beforeSend : function(xhr){
					xhr.setRequestHeader("Authorization", "Basic c2lueTIwNjo0ODE5YzY1MjdmMDIzNGVhNmEzM2E1NTU0OGUyYjI2Mw==");
					xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				},
				success :  function(data){
					try{
						alert(data);
						data = eval('('+data+')');
						alert(data);
					}catch(e){
						alert(data);
					}
				}
		}
		
		$J.ajax(option);
	}catch(e){}
}

function test4(){
	try{
		var str = Base64.encode('crewmate:11st');
		var url = "/cert/select/nowCertSearch.tmall";
		var option = {
				type : "POST",
				url : url,
				data : {"certSpotNo":22220001},
				async : true,
				beforeSend : function(xhr){
					xhr.setRequestHeader("Authorization", "Basic " + str);
				},
				success :  function(data){
					try{
						alert(data);
						data = eval('('+data+')');
						alert(data);
					}catch(e){}
				}
		}
		
		$J.ajax(option);
	}catch(e){}
}

function test5(type){
	try{
		var str = Base64.encode('crewmate:11st');
		var url = "/cert/select/certNumConfirm.tmall";
		var option = {
				type : "POST",
				url : url,
				data : {"userNo":22220001, "certSpotNo":22220001, "certNo":123456789012},
				async : true,
				beforeSend : function(xhr){
					xhr.setRequestHeader("Authorization", "Basic " + str);
				},
				success :  function(data){
					try{
						alert(data);
						data = eval('('+data+')');
						alert(data);
					}catch(e){}
				}
		}
		
		$J.ajax(option);
	}catch(e){}
}


</script>
</head>
<body>
<form name="frmMain" id="frmMain" method="post" enctype="multipart/form-data">
	index jsp hi~~~~!!!!
	<input type="button" value="AJAX Type" onclick="test1()" />
	<input type="button" value="Redirect Type" onclick="test2()" />
	<input type="button" value="AJAX Type2 (ProductBO)" onclick="test3()" />
	<input type="button" value="Redirect Type2 (ProductBO)" onclick="test4()" />
	<input type="button" value="processGroupREG" onclick="test5('REG')" />
	<input type="button" value="processGroup"UPDATE onclick="test5('UPDATE')" />
</form>	
</body>
</html>