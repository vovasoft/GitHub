$(document).ready(function () {
//	showModal();
	getuserinfo();
    setDefaultRange();
    $("#id_confirm").click(function(){
    	showModal();
        getData();
    });

});
function setDefaultRange() {
	var today = new Date();
	var end = today.getTime();
	var start = end - 24*60*60*1000;
	$("#startDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayHighlight: true,
        pickerPosition: "bottom",
        endDate: today
    });
	$("#endDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayHighlight: true,
        pickerPosition: "bottom",
        endDate:today
    });
	$('#startDate').datetimepicker('update', (new Date(start)).Format("yyyy-MM-dd hh:mm"));
	$('#endDate').datetimepicker('update', (new Date(end)).Format("yyyy-MM-dd hh:mm"));
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
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
	        			$("body").css("display","block");
//					    getData();
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
        	console.log("error")
        }
        
    });
}

var jsonData = null;
function apiStatus(data) {
	jsonData = data;
}

var anchorTime = 0;
//var dateCount = 0;
var daySeconds = 24*3600;
var endDate = 0;
var ajax;
function getData() {
	var tzo = getTZO();
//  var id = getAppId($("#info").text());
	var id = $("#selectCollection").val();
	console.log(id+"+++id");
	var start = new Date(Date.parse($('#startDate').val().replace(/-/g,"/")));
	var startTime = start.getTime()/1000 + tzo*3600;
	var end = new Date(Date.parse($('#endDate').val().replace(/-/g,"/")));
	var endTime = end.getTime()/1000 + tzo*3600;
    var path = getServerPath() + "query/pay_order?app=" + id + "&start=" + startTime + "&end=" + endTime;
//  console.log(path);
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
	"<th style='text-align: center;' id='id_uid'></th><th style='text-align: center;' id='id_server'></th>"+
	"<th style='text-align: center;' id='id_good'></th><th style='text-align: center;' id='id_amount'></th>"+
	"<th style='text-align: center;' id='id_paytype'></th><th style='text-align: center;' id='id_currency'></th>"+
	"<th style='text-align: center;' id='id_channel_text'></th><th style='text-align: center;' id='id_new_channel_text'></th>"+
	"<th style='text-align: center;' id='id_status'></th><th style='text-align: center;' id='id_input_time'></th>"+
	"<th style='text-align: center;' id='id_checkout_time'></th></tr></thead><tbody id='tbody'></tbody>");
	var str='';
	var totalOrder = 0;
	var payedOrder = 0;
	$.each(data.detail, function(index,item) {
	    totalOrder = data.detail.length;
		var orderStatus = $('#status').val();
		var orderArr = orderStatus.split(":");
		if(orderArr.length==1&&orderArr[0]==0){
			if(item.status==0){
			var inputTime = (new Date(item.input_time*1000)).Format("yyyy-MM-dd hh:mm:ss");
			var checkTime = (new Date(item.checkout_time*1000)).Format("yyyy-MM-dd hh:mm:ss");
				str += '<tr>';
				str += '<td style = "overflow:hidden;text-align: center;" title="'+item.orid+'">'+item.orid+'</td><td style="text-align: center;">'+item.uid+'</td>';
				str += '<td style="text-align: center">'+item.server+'</td><td style="text-align: center;">'+item.good+'</td><td style="text-align: center;">'+item.amount+'</td><td style="text-align: center;">'+ getPayTypeById(item.payType)+'</td>';
				str += '<td style="text-align: center;">'+item.currency+'</td><td style = "overflow:hidden;text-align: center;" title="'+item.channel+'">'+item.channel+'</td><td style = "overflow:hidden;text-align: center;" title="'+item.newChannel+'">'+item.newChannel+'</td>';
				str += '<td style = "text-align: center;">'+item.status+'</td>'
				str += '<td style="text-align: center;">'+inputTime+'</td>';
				str += '<td style="text-align: center;">'+checkTime+'</td>';
				str += '</tr>';
			}
		}else{
			if(item.status!=0){
			var inputTime = (new Date(item.input_time*1000)).Format("yyyy-MM-dd hh:mm:ss");
			var checkTime = (new Date(item.checkout_time*1000)).Format("yyyy-MM-dd hh:mm:ss");
				str += '<tr>';
				str += '<td style = "overflow:hidden;text-align: center;" title="'+item.orid+'">'+item.orid+'</td><td style="text-align: center;">'+item.uid+'</td>';
				str += '<td style="text-align: center">'+item.server+'</td><td style="text-align: center;">'+item.good+'</td><td style="text-align: center;">'+item.amount+'</td><td style="text-align: center;">'+ getPayTypeById(item.payType)+'</td>';
				str += '<td style="text-align: center;">'+item.currency+'</td><td style = "overflow:hidden;text-align: center;" title="'+item.channel+'">'+item.channel+'</td><td style = "overflow:hidden;text-align: center;" title="'+item.newChannel+'">'+item.newChannel+'</td>';
				str += '<td style = "text-align: center;">'+item.status+'</td>'
				str += '<td style="text-align: center;">'+inputTime+'</td>';
				str += '<td style="text-align: center;">'+checkTime+'</td>';
				str += '</tr>';
			}
		}
		if(item.status!=0){
			payedOrder++;
		}
	});
		$('#total_order').text(totalOrder);
		$('#payed_order').text(payedOrder);
	$("#tbody").html(str);
    getDatatabel();
    loadProperties();
	tableStr='<table><tr><th>'+getExcelTitle("id_order_id_en")+'</th><th>'+getExcelTitle("id_uid")+'</th>'+
	'<th>'+getExcelTitle("id_server")+'</th><th>'+getExcelTitle("id_good")+'</th><th>'+getExcelTitle("id_amount")+'</th>'+
	'<th>'+getExcelTitle("id_paytype")+'</th><th>'+getExcelTitle("id_currency")+'</th>'+
	'<th>'+getExcelTitle("id_channel_text")+'</th><th>'+getExcelTitle("id_new_channel_text")+'</th><th>'+getExcelTitle("id_status")+'</th>'+
	'<th>'+getExcelTitle("id_input_time")+'</th><th>'+getExcelTitle("id_checkout_time")+'</th></tr>';
	tableStr+=str;
    hideModal();
}
function selectAppShow(){
	 showModal();
	 getData();
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
            	$('#id_total_order').html($.i18n.prop('id_total_order'));
            	$('#id_succeed_order').html($.i18n.prop('id_succeed_order'));
            	$('#id_succeed_order_copy').html($.i18n.prop('id_succeed_order_copy'));
            	$('#id_unpayed_order').html($.i18n.prop('id_unpayed_order'));
            	$('#id_payer_info').html($.i18n.prop('id_payer_info'));
            	$('#id_order_id_en').html($.i18n.prop('id_order_id_en'));
            	$('#id_uid').html($.i18n.prop('id_uid'));
            	$('#id_server').html($.i18n.prop('id_server'));
            	$('#id_good').html($.i18n.prop('id_good'));
            	$('#id_amount').html($.i18n.prop('id_amount'));
            	$('#id_paytype').html($.i18n.prop('id_paytype'));
            	$('#id_currency').html($.i18n.prop('id_currency'));
            	$('#id_channel_text').html($.i18n.prop('id_channel_text'))
            	$('#id_new_channel_text').html($.i18n.prop('id_new_channel_text'));
            	$('#id_status').html($.i18n.prop('id_status'));
            	$('#id_input_time').html($.i18n.prop('id_input_time'));
            	$('#id_checkout_time').html($.i18n.prop('id_checkout_time'));
            }
        });
}   	 	
    	
    	
    	
    	
       
    


