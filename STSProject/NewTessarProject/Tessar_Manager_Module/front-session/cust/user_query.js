$(document).ready(function () {
   getuserinfo();
    $("#id_confirm").click(function(){
    	showModal();
        getData();
    });

});

function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	if(sessionid ==null&&sessionid ==""){
		parent.location.href="login.html";
	}else
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
        				$("body").css("display","block");
//	        			getData();
					}else{
		        		parent.location.href = "login.html";
					}
        		}else{
	        		parent.location.href = "login.html";
        		}
        		break;
        		case 504:
	        		parent.location.href = "login.html";
        		break;
        	}
        },
        error:function(data){
        	//console.log("error")
        }
        
    });
}

var ajax;
function getData() {
//	var id = getAppId($("#info").text());
	var id = $("#selectCollection").val();
	console.log(id+"---00id");
	var msg = $('#msg').val();
	if(msg==''||msg==null||msg==undefined){
		$("#showData").html("<thead><tr><th style='text-align:center;' id='id_name'></th><th style='text-align:center;' id='id_uid'></th>"+
		"<th style='text-align:center;' id='id_gameid'></th><th style='text-align:center;' id='id_server'></th>"+
		"<th style='text-align:center;' id='id_channel_text'></th><th style='text-align:center;' id='id_new_channel_text'></th><th style='text-align:center;' id='id_ip'></th><th style='text-align:center;' id='id_location'></th>"+
		"<th style='text-align:center;' id='id_locale'></th><th style='text-align:center;' id='id_add_time'></th></tr>"
		+"</thead><tbody id='tbody'></tbody>");
		getDatatabel();
		loadProperties();
		hideModal();
		return;
	}
	//console.log("msg:"+msg);
	var path = getServerPath()+"query/user_query?app="+id+"&msg="+msg;
	console.log("path:"+path);
	ajax = $.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {
			setData(data);
		}
	});
}
var tableStr='';
function setData(data){
	$("#showData").html("<thead><tr><th style='text-align:center;' id='id_name'></th><th style='text-align:center;' id='id_uid'></th>"+
		"<th style='text-align:center;' id='id_gameid'></th><th style='text-align:center;' id='id_server'></th>"+
		"<th style='text-align:center;' id='id_channel_text'></th><th style='text-align:center;' id='id_new_channel_text'></th><th style='text-align:center;' id='id_ip'></th><th style='text-align:center;' id='id_location'></th>"+
		"<th style='text-align:center;' id='id_locale'></th><th style='text-align:center;' id='id_add_time'></th></tr>"
		+"</thead><tbody id='tbody'></tbody>");
	var addTimeDate = data.addTime; 
	if(addTimeDate==null){
		addTimeDate = '';
	}else{
	 addTimeDate = (new Date(data.addTime*1000)).Format("yyyy-MM-dd hh:mm:ss");
	}
	var userName = data.userName;
	if(userName==null){
		userName = '';
	}
	var uid = data.uid;
	if(uid==null){
		uid = '';
	}
	var gid = data.gid
	if(gid==null){
		gid = '';
	}
	var ch = data.channel;
	if(ch==null||ch==""||ch==undefined){
		ch = "unknown";
	}
	var server = data.server;
	if(server==null){
		server = '';
	}
	var newChannel = data.newChannel;
	if(newChannel==null){
		newChannel='';
	}
	var ip = data.ip;
	if(ip==null){
		ip = '';
	}
	var local = data.local;
	if(local==null){
		local = '';
	}
	var location = data.location;
	if(location==null){
		location = '';
	}
	var str='';
		str += '<tr>';
		str += '<td style = "overflow:hidden;text-align: center;" title="'+userName+'">'+userName+'</td><td style = "overflow:hidden;text-align: center;" title="'+uid+'">'+uid+'</td><td style="text-align: center;">'+gid+'</td>';
		str += '<td style = "overflow:hidden;" title="'+server+'">'+server+'</td><td style = "overflow:hidden;text-align:center;" title="'+ch+'">'+ch+'</td><td style = "overflow:hidden;text-align: center;" title="'+newChannel+'">'+newChannel+'</td><td style = "overflow:hidden;text-align: center;" title="'+ip+'">'+ip+'</td>';
		str += '<td style="text-align: center;">'+location+'</td><td style="text-align: center;">'+local+'</td><td style="text-align: center;">'+addTimeDate+'</td>';
		str += '</tr>';
	$("#tbody").html(str);
    getDatatabel();
    loadProperties();
	tableStr='<table><tr><th>'+getExcelTitle("id_name")+'</th><th>'+getExcelTitle("id_uid")+'</th>'+
	'<th>'+getExcelTitle("id_gameid")+'</th><th>'+getExcelTitle("id_server")+'</th><th>'+getExcelTitle("id_channel_text")+'</th>'+
	'<th>'+getExcelTitle("id_new_channel_text")+'</th><th>'+getExcelTitle("id_ip")+'</th><th>'+getExcelTitle("id_location")+'</th>'+
	'<th>'+getExcelTitle("id_locale")+'</th><th>'+getExcelTitle("id_add_time")+'</th></tr>';
	tableStr+=str;
    hideModal();
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
            	$('#id_info_display').html($.i18n.prop('id_info_display'));
            	$('#id_name').html($.i18n.prop('id_name'));
            	$('#id_uid').html($.i18n.prop('id_uid'));
            	$('#id_gameid').html($.i18n.prop('id_gameid'));
            	$('#id_server').html($.i18n.prop('id_server'));
            	$('#id_channel_text').html($.i18n.prop('id_channel_text'))
            	$('#id_new_channel_text').html($.i18n.prop('id_new_channel_text'));
            	$('#id_ip').html($.i18n.prop('id_ip'));
            	$('#id_location').html($.i18n.prop('id_location'));
            	$('#id_locale').html($.i18n.prop('id_locale'));
            	$('#id_add_time').html($.i18n.prop('id_add_time'));
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

