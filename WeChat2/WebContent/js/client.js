/**
 * 用来与服务器端进行连接
 */

var ws=null;
function connect(){
	var target="ws://localhost:8080/WeChat2/chat";
	if('WebSocket' in window){
		ws=new WebSocket(target);
		
		alert("连接成功");
	}else if('MozWebSocket' in window){
			ws=new MozWebSocket(target);
	}else{
		alert("websocket is not supported by this browser");
	}
	ws.onclose=function(event){
		alert("用户已下线");
	};
	
	ws.onopen=function(event){
		alert("open");
	};
	ws.onerror=function(event){
		alert("erorr");
	};
	ws.onmessage=function(event){
		console.log(event.data);
		var text=event.data;
		var obj=JSON.parse(text);
		var names=obj.names;
		$("#left").html("");
		for(var i in names){
			console.log(names[i]);
			$("#left").append(names[i]+"</br>");
		}
		var currentdate = getNowFormatDate();
		$("#right").html("");
		$("#right").append("<b>"+obj.sender+"</b>"+": "+obj.alert+obj.message+"		"+currentdate);
	};
	
}
function unconnect(){
	alert("close");
	if(ws.readyState===WebSocket.OPEN){
		ws.close();
		alert("have close");
	}
}

function sendMsg(){
	//获取输入框里面的值使用value
	var msg=$("#mytext").val();
	console.log(msg);
	ws.send(msg);
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
} 

