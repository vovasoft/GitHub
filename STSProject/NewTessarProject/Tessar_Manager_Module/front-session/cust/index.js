$(document).ready(function() {
	showModal();
	getuserinfo();
	loadProperties();
});


function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	var path = getServerPath()+"user/session?sessionid="+sessionid;
//	var path = getServerPath()+"user/session";
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
        			var d= new Date(); 
					d.setTime(d.getTime() + 30*60*1000);
					document.cookie = "param=7001; expires=" + d.toGMTString();
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
	        			$("body").css("display","block");
					}else{
		        		parent.location.href = "login.html";
					}
				    getData();
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
var ajax;
var tableStr = '';
function getData() {
	var timestamp = Date.parse(new Date)/1000;
	var path = getServerPath()+"main/generic";
	//console.log(path);
    ajax = $.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonp: "callback",
		jsonpCallback: "apiStatus",
		success: function(data) {
			var str;
			$("#user_total").text(data.generic.total_user);
			$("#payer").text(data.generic.payer);
			$("#mau").text(data.generic.mau);
			$("#wau").text(data.generic.wau);
			tableStr = '<table><tr><th>'+getExcelTitle("id_app")+'</th>'+
			'<th>'+getExcelTitle("id_today_newer")+'</th><th>'+getExcelTitle("id_yesterday_newer")+'</th>'+
			'<th>'+getExcelTitle("id_dau")+'</th><th>'+getExcelTitle("id_yesterday_dau")+'</th>'+
			'<th>'+getExcelTitle("id_total_income")+'</th></tr>';
			var tbStr = '';
			$.each(data.details, function(index, item) {
				tbStr += '<tr>';
				tbStr += '<td style="text-align: center;">' + getAppName(item.app) + '</td>';
				tbStr += '<td style="text-align: center;">' + item.new_tdy + '</td>';
				tbStr += '<td style="text-align: center;">' + item.new_ytd + '</td>';
				tbStr += '<td style="text-align: center;">' + item.active_tdy + '</td>';
				tbStr += '<td style="text-align: center;">' + item.active_ytd + '</td>';
				tbStr += '<td style="text-align: center;">' + parseFloat(item.income).toFixed(2) + '</td>';
				tbStr += '</tr>';
			});
			tableStr+=tbStr;
			$("#tb2").html(tbStr);
			hideModal();
			//console.log(data);
		}
	});
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
                $('#id_all_app').html($.i18n.prop('id_all_app'));
                $('#id_total_user').html($.i18n.prop('id_total_user'));
                $('#id_total_payer').html($.i18n.prop('id_total_payer'));
                $('#id_month_active').html($.i18n.prop('id_month_active'));
                $('#id_week_active').html($.i18n.prop('id_week_active'));
                $('#id_app_list').html($.i18n.prop('id_app_list'));
                $('#id_app').html($.i18n.prop('id_app'));
                $('#id_today_newer').html($.i18n.prop('id_today_newer'));
                $('#id_yesterday_newer').html($.i18n.prop('id_yesterday_newer'));
                $('#id_dau').html($.i18n.prop('id_dau'));
                $('#id_yesterday_dau').html($.i18n.prop('id_yesterday_dau'));
                $('#id_total_income').html($.i18n.prop('id_total_income'));
            }
        });
}



