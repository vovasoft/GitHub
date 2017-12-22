$(document).ready(function(){
	showModal();
	getuserinfo();
})

function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	if(sessionid ==null&&sessionid ==""){
		parent.location.href="login.html";
	}else
	console.log("sessionid:"+sessionid);
	var path = getServerPath()+"user/session?sessionid="+sessionid;
	console.log("path:"+path)
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#uid").val(data.user.uid);
        			$("#UserInfo").text(data.user.username);
        			$("#group").val(data.user.group);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				//addMenuByCookie();
        				$("#menu").css("display","block");
						if(data.user.group == "admin"){
							$("#user_admin").css("display","block");
						}
					}
        			$("body").css("display","block");
        			showch();
        			
        		}else{
	        		parent.location.href = "login.html";
        		}
        		break;
        		case 504:
	        		parent.location.href = "login.html";
        		break;
        	}
        }
        
    });
}

function getValue(value){
	if(!filter(value)){
		return false;
	}
	splitChannel(value);
}
function addch(){
	var channel = $("#channel").val();
	var parent = $("#parent").val();
	var child = $("#child").val();
	var seq = $("#seq").val();
	if($("#group").val() == "admin"){
		if(! (validate(channel,'channelname'))){
		return false;
		}
		if(! (validate(parent,'parent'))){
		return false;
		}
		if(! (validate(child,'child'))){
		return false;
		}
		if(! (validate(seq,'seq'))){
		return false;
		}
	}else{
		if(! (validate(channel,'channelname'))){
		return false;
		}
		getValue(channel);
	}
	var product = $("input[name=options]:checked").val();
	var status = $("input[name=soptions]:checked").val();
	var owner = $("#owner").val();
	var currency = $("#currency").val();
	var url = $("#url").val();
	var addtime = parseInt((new Date().getTime())/1000);
	var path = getServerPath()+"manage/add_ch?channel="+channel+"&parent="+parent+
	"&child="+child+"&seq="+seq+"&product="+product+"&owner="+owner+"&addtime="+addtime+"&changetime="+addtime+
	"&currency="+currency+"&status="+status+"&url="+url;
	console.log(path);
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	switch(data.flag) {
			   	case 702:
	  			$('#addchModal').modal('hide');
	  			showch();
			   	break
			   	case 703:
			   	$(".common").text("channelname has exist");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 705:
			   	$(".common").text("add channel failure, please try again later");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 707:
			   	$(".common").text("owner not exist");
				$(".common").css("display","block");
				return false;
			   	break
			   }
        }
      });	
}


function remData(channelName){
	remCss();
	$("#savedown").attr("onclick","addch()");
	$("#channel").val(channelName);
	$($("input[name=options]")[1]).prop("checked",true);
	$($("input[name=soptions]")[0]).prop("checked",true);
}

function selectAppShow(){
    showModal();
    showch();
    }

var ajax;
function showch(){
	var id = $("#selectCollection").val();
	var path = getServerPath()+"manage/ch_status?app="+id;
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
var tableStr = '';
function setData(data){
			if(data == ""){
				hideModal();				
	        	return ;
			}
	        var str = '';
	        var tstr = '';
			$("#FchData").html("<thead><tr><th style = 'text-align: center;' id='id_old_channel_name'></th>"+
			"<th style = 'text-align: center;' id='id_new_channel_name'></th>"+
			"<th style = 'text-align: center;' id='id_action'></th></tr></thead><tbody id='tbody'></tbody>");
			$.each(data.changed_chs, function(index,item) {
	        		str += "<tr><td style = 'text-align: center;' class='center'>"+item.oldname+"</td>";
	        		tstr += "<tr><td class='center'>"+item.oldname+"</td>";
	        		str += "<td style = 'text-align: center;' class='center'>"+item.newname+"</td>";
	        		tstr += "<td class='center'>"+item.newname+"</td>";
	        		tstr += "</tr>";
	        		str += "<td style = 'text-align: center;' class= 'center'><a class='btn btn-info btn-sm'"+
	        		"href='#myModal' onclick=editch('"+item.newname+"','"+item.oldname+"') data-toggle='modal' data-backdrop='static'>"+
	        		"<i class='glyphicon glyphicon-zoom-in icon-white'></i> Edit</a></td>";
	        		str += "</tr>";
	        	});
	        $("#tbody").html(str);
	        getFormattedChDatatabel();
	        var str1='';
	        $("#UnFchData").html("<thead><tr><th style = 'text-align: center;' id='id_channel_name'></th>"+
	        "<th></th><th style = 'text-align: center;' id='id_action_copy'></th></tr></thead><tbody id='untbody'></tbody>");
			$.each(data.org_chs, function(index,item) {
	        		str1 += "<tr><td style = 'text-align: center;'>"+item+"</td>";
	        		str1 += "<td></td>";
	        		str1 += "<td style = 'text-align: center;' class= 'center'><a class='btn btn-info btn-sm'"+
	        		"href='#addchModal' onclick=remData('"+item+"') data-toggle='modal' data-backdrop='static'>"+
	        		"<i class='glyphicon glyphicon-zoom-in icon-white'></i> Edit</a></td>";
	        		str1 += "</tr>";
	        	});
	        $("#untbody").html(str1);
	        getUnFormattedChDatatabel();
	        loadProperties();
			tableStr = '<table><tr><th>'+getExcelTitle('id_old_channel_name')+'</th>'+
			'<th>'+getExcelTitle("id_new_channel_name")+'</th></tr>';
			tableStr+=tstr;
	        hideModal();
}


var flag = 0;

function editch(newname,oldname){
	//console.log(newname+":"+oldname);
	$(".common").text("");
	$(".common").css("display","none");
	$("#savetop").attr("onclick","savech()");
	$("#newname").val(newname);
	$("#oldname").val(oldname);
}


function filter(channelname){
	var filter  = /^([a-zA-Z0-9[^\s]])+(\_)(([a-zA-Z0-9[^\s]])+)+\_(([a-zA-Z0-9[^\s]])+)+$/;
    if (!filter.test(channelname)){
    	$("#parent").val("");
    	$("#child").val("");
    	$("#seq").val("");
    	$(".common").text("channelname format is not correct (example:xxx_xxx_xxx)");
		$(".common").css("display","block");
          return false;
    }else{
    	return true;
    }
}

function savech(){
	$(".common").text("");
	$(".common").css("display","none");
	var oldname = $("#oldname").val();
	var newname = $("#newname").val();
	console.log(oldname+","+newname);
	if($("#group").val() == "admin"){
		if(! (validate(oldname,'oldname'))){
			return false;
		} 
		if(! (validate(newname,'newname'))){
			return false;
		}  
		
	}
	var path =  getServerPath()+"manage/change_ch?app=1001&oldname="+oldname+"&newname="+newname;
//	console.log("path:"+path)
	$.ajax({
	        type: "GET",
	        url: path,
	        dataType: "jsonp",
	        jsonpCallback: "apiStatus",
	        success: function(data) {
	        	switch(data.flag) {
			   	case 200:
			   		$('#myModal').modal('hide');
			   		showch();
			   	break
			   	case 201:
			   	$(".common").text("channel is not exit");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 203:
			   	$(".common").text("edit channel failed");
				$(".common").css("display","block");
				return false;
			   	break
			 }
	        }
	     });
	
}

function remCss(){
	$("#parent").val("");
	$("#child").val("");
	$("#seq").val("");
	$("#owner").val("");
	$("#currency").val("");
	$(".common").text("");
	$(".common").css("display","none");
}


function validate(param,str){
	if(param == '' || param == null || param == 'null'){
		$(".common").text(str + " should not be null");
		$(".common").css("display","block");
		return false;
	}else{
		return true;
	}
}
function hiddenValidate(){
	$(".common").css("display","none");
}
function getFormattedChDatatabel(){
	 var otable = $('#FchData').dataTable({
	 	"bDestroy": true,
		"bRetrieve": true
	 });
	 otable.fnDestroy();
	 otable = $('#FchData').dataTable({
	 	 "fnInitComplete":function(){
	 	 	this.fnAdjustColumnSizing(true);
	 	 },
		"bDestroy": true,
		"bRetrieve": true,
		"bAutoWidth" : false,
		"scrollable" : true,
        "sScrollY": "100%",
        "aoColumnDefs": [
      { "sWidth": "30%", "aTargets": [ 0 ] },
      { "sWidth": "30%", "aTargets": [ 1 ] },
      { "sWidth": "40%", "aTargets": [ 2 ] }
    ],
		"sPaginationType": "full_numbers"
	});
	$(window).resize(function () {
            otable.fnAdjustColumnSizing();
        });
}
function getUnFormattedChDatatabel(){
	 var otable = $('#UnFchData').dataTable({
	 	"bDestroy": true,
		"bRetrieve": true
	 });
	 otable.fnDestroy();
	 otable = $('#UnFchData').dataTable({
	 	 "fnInitComplete":function(){
	 	 	this.fnAdjustColumnSizing(true);
	 	 },
		"bDestroy": true,
		"bRetrieve": true,
		"bAutoWidth" : false,
		"scrollable" : true,
        "sScrollY": "100%",
        "aoColumnDefs": [
       { "sWidth": "30%", "aTargets": [ 0 ] },
       { "sWidth": "30%", "aTargets": [ 1 ] },
       { "sWidth": "40%", "aTargets": [ 2 ] }
    ],
		"sPaginationType": "full_numbers"
	});
	$(window).resize(function () {
            otable.fnAdjustColumnSizing();
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
        console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
                $('#id_formatted').html($.i18n.prop('id_formatted'));
                $('#id_unFormatted').html($.i18n.prop('id_unFormatted'));
                $('#id_old_channel_name').html($.i18n.prop('id_old_channel_name'));
                $('#id_new_channel_name').html($.i18n.prop('id_new_channel_name'));
                $('#id_channel_name').html($.i18n.prop('id_channel_name'));
                $('#id_action').html($.i18n.prop('id_action'));
                $('#id_action_copy').html($.i18n.prop('id_action_copy'));
                $('#id_add_channel').html($.i18n.prop('id_add_channel'));
                $('#id_channel_text').html($.i18n.prop('id_channel_text'));
                $('#id_parent').html($.i18n.prop('id_parent'));
                $('#id_child').html($.i18n.prop('id_child'));
                $('#id_sequence').html($.i18n.prop('id_sequence'));
                $('#id_owner').html($.i18n.prop('id_owner'));
                $('#id_currency').html($.i18n.prop('id_currency'));
		        $('#id_url').html($.i18n.prop('id_url'));
		        $('#id_select_product').html($.i18n.prop('id_select_product'));
		        $('#id_select_status').html($.i18n.prop('id_select_status'));
                $('#id_active').html($.i18n.prop('id_active'));
                $('#id_banned').html($.i18n.prop('id_banned'));
                $('#id_cancel').html($.i18n.prop('id_cancel'));
				$('#id_save').html($.i18n.prop('id_save'));
				$('#id_edit_channel').html($.i18n.prop('id_edit_channel'));
		        $('#id_old_channel_name_copy').html($.i18n.prop('id_old_channel_name_copy'));
                $('#id_new_channel_name_copy').html($.i18n.prop('id_new_channel_name_copy'));
                $('#id_cancel_copy').html($.i18n.prop('id_cancel_copy'));
				$('#id_save_copy').html($.i18n.prop('id_save_copy'));
            }
        });
}
