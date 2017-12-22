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
        	//console.log("log.data.flag:"+data.flag);
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
	console.log("id==="+id);
	var uid = $('#uid').val();
	var oid = $('#oid').val();
	if(uid==""&&oid==""){
	$("#showData").html("<thead><tr><th style='text-align: center;' id='id_order_id_en'></th>"+
	"<th style='text-align: center;' id='id_uid'></th><th style='text-align: center;' id='id_gameid'></th>"+
	"<th style='text-align: center;' id='id_role'></th><th style='text-align: center;' id='id_channel_text'></th>"+
	"<th style='text-align: center;' id='id_new_channel_text'></th><th style='text-align: center;' id='id_server'></th>"+
	"<th style='text-align: center;' id='id_ip'></th><th style='text-align: center;' id='id_game_money'></th>"+
	"<th style='text-align: center;' id='id_amount'></th><th style='text-align: center;' id='id_currency'></th>"+
	"<th style='text-align: center;' id='id_paytype'></th><th style='text-align: center;' id='id_status'></th>"+
	"<th style='text-align: center;' id='id_add_time'></th></tr></thead><tbody id='tbody'></tbody>");
	getDatatabel();
	loadProperties();
    hideModal();
	return;
	}
	var path = getServerPath()+"query/user_order?app="+id+"&uid="+uid+"&orderid="+oid;
	//console.log("path:"+path);
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
	$("#showData").html("<thead><tr><th style='text-align: center;' id='id_order_id_en'></th>"+
	"<th style='text-align: center;' id='id_uid'></th><th style='text-align: center;' id='id_gameid'></th>"+
	"<th style='text-align: center;' id='id_role'></th><th style='text-align: center;' id='id_channel_text'></th>"+
	"<th style='text-align: center;' id='id_new_channel_text'></th><th style='text-align: center;' id='id_server'></th>"+
	"<th style='text-align: center;' id='id_ip'></th><th style='text-align: center;' id='id_game_money'></th>"+
	"<th style='text-align: center;' id='id_amount'></th><th style='text-align: center;' id='id_currency'></th>"+
	"<th style='text-align: center;' id='id_paytype'></th><th style='text-align: center;' id='id_status'></th>"+
	"<th style='text-align: center;' id='id_add_time'></th></tr></thead><tbody id='tbody'></tbody>");
	var str='';
	$.each(data.detail, function(index,item) {
	var addTimeDate = item.addTime; 
	if(addTimeDate==null){
		addTimeDate = '';
	}else{
	 addTimeDate = (new Date(item.addTime*1000)).Format("yyyy-MM-dd hh:mm:ss");
	}
	var orid = item.orid;
	if(orid==null){
		orid = '';
	}
	var uid = item.uid;
	if(uid==null){
		uid = '';
	}
	var gid = item.gid;
	if(gid==null){
		gid='';
	}
	var role = item.role;
	if(role==null){
		role = '';
	}
	var ch = item.channel;
	if(ch==null||ch==""||ch==undefined){
		ch = "unknown";
	}
	var newChannel = item.newChannel;
	if(newChannel==null){
		newChannel='';
	}
	var server = item.server;
	if(server==null){
		server = '';
	}
	var ip = item.ip;
	if(ip==null){
		ip = '';
	}
	var gMony = item.gMony;
	if(gMony==null){
		gMony='';
	}
	var amount = item.amount;
	if(amount==null){
		amount='';
	}
	var currency = item.currency;
	if(currency==null){
		currency = '';
	}
	var payType = item.payType;
	if(payType==null){
		payType = '';
	}
	var status = item.status;
	if(status==null){
		status = '';
	}
		str += '<tr>';
		str += '<td style = "overflow:hidden;text-align: center;" title="'+orid+'"><a href="order_track.html?orid='+orid+'" title="click here">'+orid+'</a></td><td style="text-align: center;">'+uid+'</td><td style="text-align: center;">'+gid+'</td>';
		str += '<td style="text-align: center;">'+role+'</td><td style="overflow:hidden;text-align: center;" title="'+ch+'">'+ch+'</td><td style = "overflow:hidden;text-align: center;" title="'+newChannel+'">'+newChannel+'</td><td style="overflow:hidden;text-align: center;">'+server+'</td>';
		str += '<td style="overflow:hidden;text-align: center;" title="'+ip+'">'+ip+'</td><td style="text-align: center;">'+gMony+'</td><td style="text-align: center;">'+amount+'</td><td style="text-align: center;">'+currency+'</td>';
		str += '<td style="text-align: center;">'+getPayTypeById(payType)+'</td><td style="text-align: center;">'+status+'</td><td style="text-align: center;">'+addTimeDate+'</td>';
		str += '</tr>';
	});
	$("#tbody").html(str);
    getDatatabel();
    loadProperties();
	tableStr='<table><tr><th>'+getExcelTitle("id_order_id_en")+'</th><th>'+getExcelTitle("id_uid")+'</th>'+
	'<th>'+getExcelTitle("id_gameid")+'</th><th>'+getExcelTitle("id_role")+'</th><th>'+getExcelTitle("id_channel_text")+'</th>'+
	'<th>'+getExcelTitle("id_new_channel_text")+'</th><th>'+getExcelTitle("id_server")+'</th>'+
	'<th>'+getExcelTitle("id_ip")+'</th><th>'+getExcelTitle("id_game_money")+'</th><th>'+getExcelTitle("id_amount")+'</th>'+
	'<th>'+getExcelTitle("id_currency")+'</th><th>'+getExcelTitle("id_paytype")+'</th>'+
	'<th>'+getExcelTitle("id_status")+'</th><th>'+getExcelTitle("id_add_time")+'</th></tr>';
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
            	$('#id_order_query_copy').html($.i18n.prop('id_order_query_copy'));
            	$('#id_order_id_en').html($.i18n.prop('id_order_id_en'));
            	$('#id_uid').html($.i18n.prop('id_uid'));
            	$('#id_gameid').html($.i18n.prop('id_gameid'));
            	$('#id_role').html($.i18n.prop('id_role'));
            	$('#id_channel_text').html($.i18n.prop('id_channel_text'));
            	$('#id_new_channel_text').html($.i18n.prop('id_new_channel_text'));
            	$('#id_server').html($.i18n.prop('id_server'));
            	$('#id_ip').html($.i18n.prop('id_ip'));
            	$('#id_game_money').html($.i18n.prop('id_game_money'));
            	$('#id_amount').html($.i18n.prop('id_amount'));
            	$('#id_currency').html($.i18n.prop('id_currency'));
            	$('#id_paytype').html($.i18n.prop('id_paytype'));
            	$('#id_status').html($.i18n.prop('id_status'));
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


