$(document).ready(function () {
	showModal();
	getuserinfo();
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
function getData(){
	var id = getAppId($("#info").text());
	var path = getServerPath()+"app/pay_sort?app="+id;

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
	var dataPool = data.detail;
	dataPool = dataPool.sort(function(a,b){
		
		return b.pay_total - a.pay_total;
		
	});
	var str='';
	$.each(dataPool, function(index,item) {
		str += '<tr><td style="text-align: center;">'+(index+1)+'</td>'+'<td style="text-align: center;">'+item.id+'</td>';
		str += '<td style="text-align: center;">'+parseFloat(item.pay_total).toFixed(2)+'</td>'+'<td style="text-align: center;">'+item.channel+'</td></tr>';
	});
	$("#tbody").html(str);
	getDatatabel();
	loadProperties();
    tableStr = '<table><tr><th>'+getExcelTitle("id_topn")+'</th>'+
    '<th>'+getExcelTitle("id_user")+'</th><th>'+getExcelTitle("id_payed_money")+'</th>'+
    '<th>'+getExcelTitle("id_reg_channel")+'</th></tr>';
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
            	$('#id_user').html($.i18n.prop('id_user'));
            	$('#id_payed_money').html($.i18n.prop('id_payed_money'));
            	$('#id_reg_channel').html($.i18n.prop('id_reg_channel'));
            }
        });
}   	