	$(document).ready(function(){
		getuserinfo();
		loadProperties();
		var end = new Date();
	var start = end.getFullYear()+"-"+ parseInt(end.getMonth())+"-"+parseInt(end.getDate());  
	$(".input-daterange").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        weekStart: 1,
        todayHighlight: true,
        pickerPosition: "bottom",
        startDate: start,
        endDate: end
    });
   
    setDefaultRange();
	/*active*/
    $("#id_download").click(function(){
        getActiveCsv();
    });
    /*payment*/
    $("#id_download_copy").click(function(){
    	getPaymentCsv();
    });
    
    
});

function setDefaultRange() {
	var today = new Date();
	var start = today.getTime() - 24*60*60*1000;
	$('#startDate').datepicker('update', (new Date(start)).Format("yyyy-MM-dd"));
	$('#endDate').datepicker('update', today.Format("yyyy-MM-dd"));
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
function getActiveCsv() {
	var tzo = getTZO();
    var id = getAppId($("#info").text());
    var offset = getTimeOffsetById(id);
	var start = new Date($('#startDate').val().replace(/-/g,"/"));
	var startTime = start.getTime()/1000 + (offset+tzo)*3600;
	var end = new Date($('#endDate').val().replace(/-/g,"/"));
	var endTime = end.getTime()/1000 + (offset+tzo)*3600;
	var path = "http://stats.xcloudgame.com:8080/stats/csv/active_download?app="+id+"&start="+startTime+"&end="+endTime;
	console.log("path:"+path);
	$("#id_download").attr('href',path);
}
function getPaymentCsv() {

    var id = getAppId($("#info").text());
	var path = "http://stats.xcloudgame.com:8080/stats/csv/payment_download?app="+id;
	console.log("path:"+path);
	$("#id_download_copy").attr('href',path);
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
            	$('#id_userinfo').html($.i18n.prop('id_userinfo'));
            	$('#id_cust_active_user_text').html($.i18n.prop('id_cust_active_user_text'));
            	$('#id_payer_info').html($.i18n.prop('id_payer_info'));
            	$('#id_cust_payer_text').html($.i18n.prop('id_cust_payer_text'));
            	$('#id_download').html($.i18n.prop('id_download'));
            	$('#id_download_copy').html($.i18n.prop('id_download_copy'));
            }
        });
}   	 	
