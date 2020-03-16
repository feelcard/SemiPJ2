<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<html>
<head>
<meta charset="EUC-KR">
<title>fcm</title>
<script>
function display(data){
   var result='';
   $(data).each(function(idx,item){
      result += '<h3>';
      result += item.id+ '<br/><br/>';
      result += '<h3>';
      
   });
   $('#uu').html(result);
};
function getData(){
   $.ajax({
      url:'fcmweb2',
       dataType:"json",
      success:function(data){
    	  //alert(data);
         display(data);
 
      }
   })
};
   $(document).ready(function(){
      setInterval(getData,1000);
   })
</script>
</head>
<body>
<h1>fcm web</h1>
<form action="fcm" method="post">
IP<input type="text" name="ip"><br>
SPEED<input type="text" name="speed"><br>
<input type="submit" value="send">
</form>
<form action="fcmweb2" method="get">
IP<input type="text" name="ip"><br>
<input type="submit" value="send">
</form>
<h1>aaaaaaaa</h1>
<div id = "uu">
</div>
</body>
</html>
