$(document).ready(function () {
	showModal();
	getuserinfo();
	 $("#id_confirm").click(function(){
    	tableStr = '<table>';
    	showModal();
        getData();
    });
});
function setDefaultRange() {
	var today = new Date();
	var end = today.getTime() - 24*60*60*1000;
	var start = end - 24*60*60*1000;
	$("#startDate").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        todayHighlight: true,
        pickerPosition: "bottom",
        endDate: today
    });
	$("#endDate").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        todayHighlight: true,
        pickerPosition: "bottom",
        endDate:today
    });
	$('#startDate').datepicker('update', (new Date(start)).Format("yyyy-MM-dd"));
	$('#endDate').datepicker('update', (new Date(end)).Format("yyyy-MM-dd"));
}
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

function getuserinfo(){
	var path = getServerPath()+"user/session";
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
        	console.log("error")
        }
        
    });
}
var ajax;
var startTime;
var endTime;
function getData(){
	var tzo = getTZO();
    var id = getAppId($("#info").text());
	var start = new Date(Date.parse($('#startDate').val().replace(/-/g,"/")));
	startTime = start.getTime()/1000 + tzo*3600;
	var end = new Date(Date.parse($('#endDate').val().replace(/-/g,"/")));
	endTime = end.getTime()/1000 + tzo*3600;
	var path = getServerPath()+"app/pay_sort_ch?app="+id+ "&start=" + startTime + "&end=" + endTime;
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
	$("#showData").html("<thead id='thead'><tr><th style='text-align: center;' id='id_topn'></th><th style='text-align: center;' id='id_reg_channel'></th>"+
	"<th style='text-align: center;' id='id_payed_money'></th>"+
	"</tr></thead><tbody id='tbody'></tbody>");
	
	
	var dataPool = data.detail;
	dataPool = dataPool.sort(function(a,b){
		return b.pay_total - a.pay_total;
		
	});
	var str='';
	$.each(dataPool, function(index,item) {
		str += '<tr><td style="text-align: center;">'+(index+1)+'</td>'+'<td style="text-align: center;"><a href="detail_ch_order.html?channel='+item.channel+'&start='+startTime+'&end='+endTime+'" title="click here">'+item.channel+'</a></td>';
		str += '<td style="text-align: center;">'+parseFloat(item.pay_total).toFixed(2)+'</td></tr>';
	});
	$("#tbody").html(str);
	getDatatabel();
	loadProperties();
    tableStr = '<table><tr><th>'+getExcelTitle("id_topn")+'</th>'+
    '<th>'+getExcelTitle("id_reg_channel")+'</th><th>'+getExcelTitle("id_payed_money")+'</th></tr>';
	tableStr +=str;
	hideModal();
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
            	$('#id_topn').html($.i18n.prop('id_topn'));
            	$('#id_payed_money').html($.i18n.prop('id_payed_money'));
            	$('#id_reg_channel').html($.i18n.prop('id_reg_channel'));
            }
        });
}   	