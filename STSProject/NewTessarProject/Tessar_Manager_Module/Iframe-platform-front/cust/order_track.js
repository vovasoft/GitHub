$(document).ready(function () {
   getuserinfo();
   getOrderID();
    $("#id_confirm").click(function(){
    	showModal();
        getData();
    });

});

function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	var path = getServerPath()+"user/session?sessionid="+sessionid;
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        async:false,
        jsonpCallback: "apiStatus",
        success: function(data) {
        	////console.log("log.data.flag:"+data.flag);
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
//	        			getData();
	        			$("body").css("display","block");
					}else{
		        		window.location.href = "login.html";
					}
        		}else{
	        		window.location.href = "login.html";
        		}
        		break;
        		case 504:
	        		window.location.href = "login.html";
        		break;
        	}
        },
        error:function(data){
        	//console.log("error")
        }
        
    });
}
function getOrderID(){
	var urlParam = window.location.search;
	var orderID = urlParam.split("=")[1];
	var oid = $('#oid').val(orderID);
	console.log("orderID:"+orderID);
//	$("#ul").css("display","block");
}
var ajax;
function getData() {
//	var id = getAppId($("#info").text());
	var id = $("#selectCollection").val();
console.log("id++"+id);
	var oid = $('#oid').val();
	if(oid==null||oid==''||oid==undefined){
		loadProperties();
		return;
	}
	showModal();
	var path = getServerPath()+"query/order_track?app="+id+"&orderid="+oid;
	console.log("path:"+path);
	ajax = $.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {
			console.log("oid:"+oid);
			$('#orderVal').text(oid);
			var datamap ={} ;
			var orderContent='{';
			for(var key in data.detail){
				//console.log(data.detail[key]);
				datamap = data.detail[key];
			}
			$.each(datamap, function(key,value) {
				 orderContent += key+":"+value+",";
			});
			     orderContent +='}';
			   $('#orderContent').text(orderContent);
			   loadProperties();
			   hideModal();
		}
	});
}
// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) 
{ 
	var o = { 
		"M+" : this.getMonth()+1,                 //月份 
		"d+" : this.getDate(),                    //日 
		"h+" : this.getHours(),                   //小时 
		"m+" : this.getMinutes(),                 //分 
		"s+" : this.getSeconds(),                 //秒 
		"q+" : Math.floor((this.getMonth()+3)/3), //季度 
		"S"  : this.getMilliseconds()             //毫秒 
	}; 
	if(/(y+)/.test(fmt)) 
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	for(var k in o) 
		if(new RegExp("("+ k +")").test(fmt)) 
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	return fmt; 
}
function refuseData(){
	ajax.abort();	
}  
function loadProperties() {
        var lan = jQuery.i18n.browserLang();
        //console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
            	$('#id_order_track_copy').html($.i18n.prop('id_order_track_copy'));
            	$('#id_order_id').html($.i18n.prop('id_order_id'));
            	$('#id_content').html($.i18n.prop('id_content'));
            }
        });
}   
function getTimeOffsetById(id) {
	var offset = 0;
    switch(id) {
    	case "1001":
    	offset = 3;
    	break
    	case "2001":
    	offset = -2;
    	break
    }
    return offset;
}

