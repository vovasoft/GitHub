$(document).ready(function () {
	showModal();
   getuserinfo();
    $("#id_confirm").click(function(){
    	showModal();
        getData();
    });

});

function getuserinfo(){
	var path = getServerPath()+"user/session";
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
	        			getData();
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

function setDefaultRange() {
	var today = new Date();
	var start = today.getTime() - 6*24*60*60*1000;
	$('#startDate').datepicker('update', (new Date(start)).Format("yyyy-MM-dd"));
	$('#endDate').datepicker('update', today.Format("yyyy-MM-dd"));
}
var ajax;
function getData() {
	var tzo = getTZO();
	var id = getAppId($("#info").text());
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 +(offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    //console.log("start = "+start.getTime()/1000+", end = "+end.getTime()/1000);
    anchorTime = startTime;
    //dateCount = (endTime - startTime)/daySeconds + 1;
	var path = getServerPath()+"app/ch_track?app="+id+"&start="+startTime+"&end="+endTime;
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
	$("#showData").html("<thead><tr><th style='text-align: center;' id='id_channel_name'></th><th style='text-align: center;' id='id_new_user_copy'></th>"+
	"<th style='text-align: center;' id='id_payer_copy'></th><th style='text-align: center;' id='id_total_income'></th>"+
	"<th style='text-align: center;' id='id_arpu'></th></tr></thead><tbody id='tbody'></tbody>");
//	var date = (new Date(data.detail.date*1000)).Format("yyyy-MM-dd hh:mm:ss");
//	$("#h2").text('(更新时间：'+date+')');
//	var str = '<tr><td>'+date+'</td>';
	var str='';
	$.each(data.channels, function(index,item) {
		str += '<tr>';
		str += '<td style = "overflow:hidden;text-align:center" title="'+item.channel+'">'+item.channel+'</td><td style="text-align: center;">'+item.new_user+'</td><td style="text-align: center;">'+item.payer+'</td>';
		str += '<td style="text-align: center;">'+isZero(parseFloat(item.income).toFixed(2))+'</td><td style="text-align: center;">'+isZero(parseFloat(item.arppu).toFixed(2))+'</td>';
		str += '</tr>';
	});
	$("#tbody").html(str);
    getDatatabel();
    loadProperties();
	tableStr='<table><tr><th>'+getExcelTitle("id_channel_name")+'</th><th>'+getExcelTitle("id_new_user_copy")+'</th>'+
	'<th>'+getExcelTitle("id_payer_copy")+'</th><th>'+getExcelTitle("id_total_income")+'</th><th>'+getExcelTitle("id_arpu")+'</th></tr>';
	tableStr+=str;
	hideModal();
}

// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) { 
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
            	$('#id_channel_name').html($.i18n.prop('id_channel_name'));
            	$('#id_new_user_copy').html($.i18n.prop('id_new_user_copy'));
            	$('#id_payer_copy').html($.i18n.prop('id_payer_copy'));
            	$('#id_total_income').html($.i18n.prop('id_total_income'));
            	$('#id_arpu').html($.i18n.prop('id_arpu'));
            }
        });
}   	