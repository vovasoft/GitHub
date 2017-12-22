$(document).ready(function () {
//	showModal();
	getuserinfo();
	var end = new Date();
	var preDate = new Date(end.getTime() - 24*60*60*1000);  //前一天
	console.log(end+"end"+"==="+preDate);
	var start = end.getFullYear() + "-" + parseInt(end.getMonth()) + "-" + parseInt(end.getDate());
	
	console.log(start+":start");
	$("#endDate").datepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		weekStart: 1,
		todayHighlight: true,
		pickerPosition: "bottom",
		startDate: start,
		endDate:preDate
	});
	loadProperties();
    setDefaultRange();
    
    $("#id_confirm").click(function(){
    	showModal();
        tableStr = "<table>";
        getData();
    });

});

function setDefaultRange() {
	var today = new Date();
	var date = today.getTime()-24*60*60*1000;
	$('#endDate').datepicker('update', (new Date(date)).Format("yyyy-MM-dd"));
}
function changeStartDate(){
	var startDate = new Date($('#startDate').val());
	var start = new Date(startDate.getTime()+5*60*1000).Format("yyyy-MM-dd")
	var endDate = new Date(startDate.getTime()+24*60*60*1000).Format("yyyy-MM-dd");
	var end = new Date(endDate).getTime()-1000;
	var relend = new Date(end);
	//console.log("endDate:"+relend);
//	$('#endDate').datetimepicker('setStartDate', start);
//	$('#endDate').datetimepicker('setEndDate', relend);
//	$('#endDate').datetimepicker('update', (new Date(start)).Format("yyyy-MM-dd"));
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
//					    getData();
	        			$("body").css("display","block");
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
	var id = parseInt($("#selectCollection").val());
	console.log("id:"+id);
	var offset = getTimeOffsetById(id);
	var start = new Date(Date.parse($('#endDate').val().replace(/-/g,"/")));
	var startTime = start.getTime()/1000 + (offset+tzo)*3600;
	console.log("offset:"+offset+"---"+"startTime:"+startTime);
//	var end = new Date(Date.parse($('#endDate').val().replace(/-/g,"/")));
//	var endTime = end.getTime()/1000 + tzo*3600;

    var path = getServerPath() + "app/roi_query?app=" + id + "&date=" + startTime;
//	var path="http://localhost:8080/stats/app/roi_query?app=1001&date=1476846000";
    //console.log(path);
	anchorTime = startTime;
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
var count=0;
function setData(data) {
	var map = {};
	var jsonStr = [];
	$.each(data.channels, function(index,item) {
		var ch = item['channel'];
		var newUser = item['new_user']; 
		var dataArr = map[ch];
		if(dataArr == undefined || dataArr == null || dataArr == ''){
			dataArr = {};
		}
		
		$.each(item.roi, function(key,value) {
			//console.log("item.ret****"+item.ret[key]);
			$.each(item.roi[key], function(index,value) {	
//				 if(value==0){
//                  dataArr[index] = value;	
//	            }else{
//	            	if(newUser!=0){
	            	dataArr[index] = value+":"+newUser;
//	            	}else{
//	            		dataArr[index] = 0;
//	            	}
//	            }
			});
			 count = item.roi.length;
		});
		map[ch] = dataArr;
	});
	
	var i = 0;
    //console.log("x轴 = "+xArr);
    $.each(map, function(key, value) {

//      var num = 0;
//      for(var idx = 0; idx < map[key].length; idx++) {
//          num  = num + map[key][idx];
//      }
//      if(num != 0) {
//      } else {
            jsonStr[i] = {
                name: key,
                data:map[key]
            }
            i++;
//       }

    });
		setList(count,jsonStr);
	
}
function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) { 
        arr[i] = 0;
    }
    return arr;
}
var tableStr = '<table>';
function setList(count,jsonStr) {
	$("#showData").html("<thead id='thead'></thead><tbody id='tbody'></tbody>");
	var str = '<tr><th style="text-align: center;" id="id_channel_name"></th>';
	for(var i=1;i<=count;i++){
		if(i==1){
			str += '<th style="text-align: center;" id="id_register"></th><th style="text-align: center;">当<font id="id_day_payer"></font></th><th style="text-align: center;">当<font id="id_day_pay_money"></font></th>';
		}else{
		str += '<th style="text-align: center;">'+i+$('#id_day_payer_copy').val()+'</th><th style="text-align: center;">'+i+$('#id_day_pay_money_copy').val()+'</th>';
		}
	}
	str += '</tr>';
	$("#thead").html(str);
    var tbody = '';
    var nUser;
	$.each(jsonStr,function(index,item){
    tbody += '<tr><td style = "overflow:hidden;text-align: center;" title="'+item.name+'">'+item.name+'</td>';
    $.each(item.data,function(index,val){
		    nUser = val.split(":")[1];
			if(nUser==null||nUser==undefined||nUser==""){
					nUser = parseInt(nUser);
					nUser = 0;
				}
		});
			tbody += '<td style="text-align: center;">'+nUser +'</td>';
		$.each(item.data,function(index,val){
			var log = val.split(":")[0];
			if(log==null||log==undefined||log==""){
					log = parseInt(log);
					log = 0;
				}
//			var nUser = val.split(":")[1];
//			if(nUser==null||nUser==undefined||nUser==""){
//					nUser = parseInt(nUser);
//					nUser = 0;
//				}
//			if(nUser==0){
//					var roi = 0;
//				}else{
//				   roi = log/nUser;					
//				}
				    tbody += '<td style="text-align: center;">'+log.split("_")[0]+'</td>';
					tbody += '<td style="text-align: center;">'+log.split("_")[1] +'</td>';	
			});
			tbody += '</tr>';
		});
	$("#tbody").html(tbody);
	getDatatabel();
	loadProperties();
	tableStr+='<tr><th>'+getExcelTitle("id_channel_name")+'</th>'
    var tstr='';
	for(var i=1;i<=count;i++){
		if(i==1){
			tstr += '<th style="text-align: center;">'+getExcelTitle("id_register")+'</th><th style="text-align: center;">'+i+$('#id_day_payer_copy').val()+'</th><th style="text-align: center;">'+i+$('#id_day_pay_money_copy').val()+'</th>';
		}else{
		tstr += '<th style="text-align: center;">'+i+$('#id_day_payer_copy').val()+'</th><th style="text-align: center;">'+i+$('#id_day_pay_money_copy').val()+'</th>';
		}
	}
	tstr += '</tr>';
	tableStr += tstr;
	tableStr += tbody;
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
            	$('#id_channel_name').html($.i18n.prop('id_channel_name'));
            	$('#id_register').html($.i18n.prop('id_register'));
            	$('#id_secday_login').html($.i18n.prop('id_secday_login'));
            	$('#id_secday_retention').html($.i18n.prop('id_secday_retention'));
            	$('#id_day_login').html($.i18n.prop('id_day_login'));
            	$('#id_day_retention').html($.i18n.prop('id_day_retention'));
            	$('#id_day_login_copy').val($.i18n.prop('id_day_login_copy'));
            	$('#id_day_retention_copy').val($.i18n.prop('id_day_retention_copy'));
            	$('#id_day_payer').html($.i18n.prop('id_day_payer'));
            	$('#id_day_pay_money').html($.i18n.prop('id_day_pay_money'));
            	$('#id_day_payer_copy').val($.i18n.prop('id_day_payer_copy'));
            	$('#id_day_pay_money_copy').val($.i18n.prop('id_day_pay_money_copy'));
            }
        });
}   	


    	
    	
    	
       
    


