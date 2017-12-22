$(document).ready(function(){
	showModal();
	loadProperties();
	getuserinfo();
})


function getuserinfo(){
	var path = getServerPath()+"user/session";
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
							$('#channelCheck').html("<a href='ch_status.html' role='button' class='btn btn-success' id='id_check_channels'>"+
								"<i class='glyphicon icon-white'></i></a>");
						}
					}
        			showch();
        			$("body").css("display","block");
        			
        		}else{
	        		window.location.href = "login.html";
        		}
        		break;
        		case 504:
	        		window.location.href = "login.html";
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

function remData(){
	$("#channel").removeAttr("disabled");
	$("#currency").removeAttr("disabled");
		var options = $("input[name=options]");
		for(var i=0;i<options.length;i++){
			$(options[i]).removeAttr("disabled")
		}
		var soptions = $("input[name=soptions]");
		for(var i=0;i<soptions.length;i++){
		$(soptions[i]).removeAttr("disabled")
		}
	$("#span").text("Add Channel");
	$("#saveuser").attr("onclick","addch()");
	$("#saveuser").removeAttr("disabled","disabled");
	$("#saveuser").addClass("btn btn-primary");
	$("#channel").val("");
	$("#parent").val("");
	$("#child").val("");
	$("#seq").val("");
	$("#currency").val("");
	if($("#group").val() != "admin" && $("#group").val() != "xcloud"){
		$("#currency").val("");
		$("#currency").attr("disabled","disabled");
	}
	$($("input[name=options]")[1]).prop("checked",true);
	$($("input[name=soptions]")[0]).prop("checked",true);
	$("#owner").val("");
	$("#url").val("");
	remCss();
	if($("#group").val() != "admin"){
		$("#owner").val($("#UserInfo").text());
	}else{
		$("#owner").removeAttr("disabled");
		$("#channel").removeAttr("placeholder");
		$("#channel").removeAttr("onblur");
		$("#parent").removeAttr("disabled");
		$("#child").removeAttr("disabled");
		$("#seq").removeAttr("disabled");
	}
		
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
		//if(!adminfilter(channel)){
		//	return false;
		//}
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
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	switch(data.flag) {
			   	case 702:
	  			$('#myModal').modal('hide');
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
var ajax;
function showch(){
	var uid = $("#uid").val();
	var path = getServerPath()+"manage/manage_ch?app=7001&uid="+uid+"&page=1";
//	console.log("path:"+path);
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
	        	if(data.detail.length == 0 || data.detail == null){
	        		hideModal();
					$("#tbody").html("");
	        		return ;
	        	}
	        var str = '';
	        var detailArr = data.detail.sort(function(a,b){
				return a.addtime - b.addtime;
			});
			var totalNumber=0;
			var totalPayer=0;
			var totalIncome=0;
			$("#showData").html("<thead><tr><th style='text-align: center;' id='id_channel_text'></th>"+
			"<th style='text-align: center;' id='id_product'></th><th style='text-align: center;' id='id_add_date'></th>"+
			"<th style='text-align: center;' id='id_owner'></th><th style='text-align: center;' id='id_total_number_copy'></th>"+
			"<th style='text-align: center;' id='id_payed_number_copy'></th><th style='text-align: center;' id='id_income_copy'></th>"+
			"<th style='text-align: center;' id='id_status'></th><th style='text-align: center;' id='id_actions'></th></tr></thead><tbody id='tbody'></tbody>");
			$.each(detailArr, function(index,item) {
				    totalNumber += item.total;
					totalPayer += item.payed;
					totalIncome += item.income;
					var appid = item.product;
					if(appid=="2"){
						appid=1001
					}else{
						appid=2001
					}
	        		str += "<tr><td class='center' style = 'overflow:hidden;text-align:center;' title='"+item.channel+"'><a href='channel_info.html?channel="+item.channel+"&appid="+appid+"'>"+item.channel+"</a></td>";
	        		switch(item.product) {
					   	case '2':
					   	str += "<td style='text-align: center;'>BLOOD STRIKE</td>";
					   	break
					   	case '5':
					   	str += "<td style='text-align: center;'>WON</td>";
						 break
					}
	        		str += "<td style='text-align: center;'>"+new Date(item.addtime*1000).Format("yyyy-MM-dd hh:mm:ss")+"</td>";
	        		str += "<td style='text-align: center;'>"+item.owner+"</td>";
	        		str += "<td style='text-align: center;'>"+item.total+"</td>";
	        		str += "<td style='text-align: center;'>"+item.payed+"</td>";
	        		str += "<td style='text-align: center;'>"+isZero(parseFloat(item.income).toFixed(2))+"</td>";
	        		switch(item.status) {
					   	case 0:
					   	str += "<td style='text-align: center;'><span class='label-success label label-danger'>Banned</span></td>";
					   	break
					   	case 1:
					   	str += "<td style='text-align: center;'><span class='label-success label label-default'>Active</span></td>";
					 break
					}
	        		var channel = encodeURIComponent(item.channel);
	        		str += "<td style='text-align: center;'><a class='btn btn-success btn-sm' href='#' onclick='view("+item.chid+")'>"+
	        		"<i class='glyphicon glyphicon-zoom-in icon-white'></i> View</a> "+
	        		"<a id="+item.chid+" class='btn btn-info btn-sm' href='#myModal' data-toggle='modal' data-backdrop='static'"+
	        		" onclick=editch('"+item.chid+"','"+channel+"','"+item.parent+"','"+item.child+"','"+item.seq+
	        		"',"+item.addtime+",'"+item.owner+"',"+item.total+","+item.payed+",'"+item.product+"','"+
	        		item.currency+"','"+item.url+"',"+item.income+","+item.status+")>"+
	        		"<i class='glyphicon glyphicon-edit icon-white'></i> Edit</a> "+
	        		"<a class='btn btn-danger btn-sm' onclick=delch("+item.chid+",'"+channel+"','"+item.product+"')>"+
	        		"<i class='glyphicon glyphicon-zoom-in icon-white'></i>Delete</a></td>";
	        		str += "</tr>";
	        	});
				var testincome = 0;
	        $("#Total_number").text(totalNumber);
	        $("#payed_number").text(totalPayer);
	        $("#Tincome").text(testincome);
//	        	var row =  $('#example').dataTable().closest("tr").get(0);
	        $("#tbody").html(str);
	        getDatatabel();
	        loadProperties();
			tableStr = '<table><tr><th>'+getExcelTitle("id_channel_text")+'</th>'+
			'<th>'+getExcelTitle("id_product")+'</th><th>'+getExcelTitle("id_add_date")+'</th>'+
			'<th>'+getExcelTitle("id_owner")+'</th><th>'+getExcelTitle("id_total_number_copy")+'</th>'+
			'<th>'+getExcelTitle("id_payed_number_copy")+'</th><th>'+getExcelTitle("id_income_copy")+'</th>'+
			'<th>'+getExcelTitle("id_status")+'</th><th>'+getExcelTitle("id_actions")+'</th></tr>';
			tableStr+=str;
	        hideModal();
        
}


var flag = 0;
function view(chid){
	$("#span").text("View");
	$("#payed").attr("disabled","disabled");
	$("#channel").attr("disabled","disabled");
	$("#parent").attr("disabled","disabled");
	$("#child").attr("disabled","disabled");
	$("#seq").attr("disabled","disabled");
	$("#income").attr("disabled","disabled");
	$("#currency").attr("disabled","disabled");
	$("#owner").attr("disabled","disabled");
	var options = $("input[name=options]");
	for(var i=0;i<options.length;i++){
		$(options[i]).attr("disabled","disabled");
	}
	var soptions = $("input[name=soptions]");
	for(var i=0;i<soptions.length;i++){
		$(soptions[i]).attr("disabled","disabled");
	}
	$("#saveuser").attr("disabled","disabled");
	$("#saveuser").removeClass("btn btn-primary");
	$("#saveuser").addClass("btn btn-default");
	flag = 1;
	$("#"+chid).click();	
}

function editch(chid,channel,parent, child, seq, addtime,owner,total,payed,product,currency,url,income,status){
    channel = unescape(channel);
	$("#channel").prop("disabled","disabled");
	remCss();
	$("#saveuser").attr("onclick","savech()");
	var str = "<span class='input-group-addon' id='id_chid'></span>";
	str += "<input type='text' class='form-control' id='chid' value="+chid+" disabled='true'>";
	$("#dis").html(str);
	$("#channel").val(channel);
	$("#parent").val(parent);
	$("#child").val(child);
	$("#seq").val(seq);
	$("#owner").val(owner);
	var time = new Date(addtime*1000).Format("yyyy-MM-dd hh:mm:ss");
	$("#time").before("<div id='br' class='clearfix'><br></div>");
	$("#time").append("<span class='input-group-addon' id='id_add_date_copy'></span>");
	$("#time").append("<input type='text' class='form-control' id='addtime' value="+time+" disabled='true'>");
	$("#ctotal").before("<div id='tbr' class='clearfix'><br></div>");
	$("#ctotal").append("<span class='input-group-addon' id='id_total_number_copy1'></span>");
	$("#ctotal").append("<input type='text' class='form-control' id='total' value="+total+" disabled='true'>");
	
	$("#cpayed").before("<div id='pbr' class='clearfix'><br></div>");
	$("#cpayed").append("<span class='input-group-addon' id='id_payed_number_copy1'></span>");
	$("#cpayed").append("<input type='text' class='form-control' id='payed' value="+payed+" disabled='true'>");
	
	$("#cincome").before("<div id='inbr' class='clearfix'><br></div>");
	$("#cincome").append("<span class='input-group-addon' id='id_income_copy1'></span>");
	$("#cincome").append("<input type='text' class='form-control' id='income' value="+income+" disabled='true'>");
	if(flag == 0){
		$("#span").text("Edit Channel");
		if($("#group").val() == "admin"){
		$("#owner").removeAttr("disabled");
		/*$("#channel").removeAttr("disabled");
		$("#channel").removeAttr("onblur");*/
		$("#parent").removeAttr("disabled");
		$("#child").removeAttr("disabled");
		$("#seq").removeAttr("disabled");
		}
		if($("#group").val() == "admin" || $("#group").val() == "xcloud"){
			$("#currency").removeAttr("disabled");
			$("#payed").removeAttr("disabled");
			$("#income").removeAttr("disabled");
		}
		$("#saveuser").removeAttr("disabled");
		$("#saveuser").removeClass("btn btn-default");
		$("#saveuser").addClass("btn btn-primary");
		var options = $("input[name=options]");
		for(var i=0;i<options.length;i++){
			$(options[i]).removeAttr("disabled")
		}
		var soptions = $("input[name=soptions]");
		for(var i=0;i<soptions.length;i++){
		$(soptions[i]).removeAttr("disabled")
		}
	}
	flag = 0;
	$("#currency").val(currency);
	$("#url").val(url);
	if($("#group").val() != "admin" && $("#group").val() != "xcloud"){
			$("#currency").attr("disabled","disabled");
		}
	switch(product) {
			   	case '2':
				$($("input[name=options]")[1]).prop("checked",true);
			   	break
			   	case '5':
				$($("input[name=options]")[0]).prop("checked",true);
			   	break
	}
	switch(status) {
			   	case 0:
			   	$($("input[name=soptions]")[1]).prop("checked",true);
			   	break
			   	case 1:
			   	$($("input[name=soptions]")[0]).prop("checked",true);
			   	break
	}
	loadProperties();
}

function delch(chid,channel,product){
	if(confirm("Delete channel "+channel+"  ?")){
	var path = getServerPath()+"manage/delete_ch?chid="+chid+"&channel="+channel+"&product="+product;
	
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	switch(data.flag) {
			   	case 802:
//			   	window.location.reload();
				showch();
			   	break
			   	case 805:
			   	alert("delete channel failed");
			   	return false;
				break
				case 806:
			   	alert("channel is not exist");
			   	return false;
				break
			}
        }
      })
	}
}
function adminfilter(channelname){
	var filter  = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	/*var filter  = /^([a-zA-Z0-9[^\s]])+(\_)(([a-zA-Z0-9[^\s]])+)+\_(([a-zA-Z0-9[^\s]])+)+$/;*/
    if (!filter.test(channelname)){
    	$("#parent").val("");
    	$("#child").val("");
    	$("#seq").val("");
    	$(".common").text("channelname cannot contain Spaces");
		$(".common").css("display","block");
          return false;
    }else{
    	return true;
    }
}


function filter(channelname){
	var filter  = /^([a-zA-Z0-9])+(\_)(([a-zA-Z0-9])+)+\_(([a-zA-Z0-9])+)+$/;
	/*var filter  = /^([a-zA-Z0-9[^\s]])+(\_)(([a-zA-Z0-9[^\s]])+)+\_(([a-zA-Z0-9[^\s]])+)+$/;*/
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
		if(!filter(channel)){
			return false;
		}
		if(! (validate(channel,'channelname'))){
			return false;
		}
		if(!filter(channel)){
			return false;
		}
	}
	var product = $("input[name=options]:checked").val();
	var status = $("input[name=soptions]:checked").val();
	var owner = $("#owner").val();
	var total = $("#total").val();
	var payed = $("#payed").val();
	//console.log("payed:"+payed);
	var income = $("#income").val();
	console.log("income:"+income);
	var currency = $("#currency").val();
	var url = $("#url").val();
	var changetime = parseInt(new Date().getTime()/1000);
	var path =  getServerPath()+"manage/edit_ch?"+
	"channel="+channel+"&parent="+parent+"&child="+child+"&seq="+seq+"&product="+product+"&owner="+owner+"&payednum="+payed+"&income="+income+
	"&changetime="+changetime+"&currency="+currency+"&status="+status+"&url="+url;
	//console.log("path:"+path)
	$.ajax({
	        type: "GET",
	        url: path,
	        dataType: "jsonp",
	        jsonpCallback: "apiStatus",
	        success: function(data) {
	        	switch(data.flag) {
			   	case 602:
			   		$('#myModal').modal('hide');
			   		showch();
			   	break
			   	case 603:
			   	$(".common").text("channelname has exist, please input again");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 605:
			   	$(".common").text("edit channel failed");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 607:
			   	$(".common").text("owner not exist");
				$(".common").css("display","block");
				return false;
			   	break
			 }
	        }
	     });
	
}

function remCss(){
	$("#dis").text("");
	$("#time").text("");
	$("#ctotal").text("");
	$("#cpayed").text("");
	$("#cincome").text("");
	$("#br").remove();
	$("#tbr").remove();
	$("#pbr").remove();
	$("#inbr").remove();
	$(".common").text("");
	$(".common").css("display","none");
}

function splitChannel(channelname){
	var arr = channelname.split("_");
	$("#parent").val(arr[0]);
	$("#child").val(arr[1]);
	$("#seq").val(arr[2]);
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
                $('#id_total_number').html($.i18n.prop('id_total_number'));
                $('#id_payed_number').html($.i18n.prop('id_payed_number'));
                $('#id_income').html($.i18n.prop('id_income'));
                $('#id_check_channels').html($.i18n.prop('id_check_channels'));
                $('#id_channel_info').html($.i18n.prop('id_channel_info'));
                $('#id_channel_text').html($.i18n.prop('id_channel_text'));
                $('#id_product').html($.i18n.prop('id_product'));
                $('#id_add_date').html($.i18n.prop('id_add_date'));
                $('#id_owner').html($.i18n.prop('id_owner'));
                $('#id_total_number_copy').html($.i18n.prop('id_total_number_copy'));
                $('#id_payed_number_copy').html($.i18n.prop('id_payed_number_copy'));
                $('#id_income_copy').html($.i18n.prop('id_income_copy'));
                $('#id_status').html($.i18n.prop('id_status'));
                $('#id_actions').html($.i18n.prop('id_actions'));
                $('#id_channel_text_copy').html($.i18n.prop('id_channel_text_copy'));
                $('#id_parent').html($.i18n.prop('id_parent'));
                $('#id_child').html($.i18n.prop('id_child'));
                $('#id_sequence').html($.i18n.prop('id_sequence'));
                $('#id_owner_copy').html($.i18n.prop('id_owner_copy'));
                $('#id_currency').html($.i18n.prop('id_currency'));
		        $('#id_url').html($.i18n.prop('id_url'));
		        $('#id_select_product').html($.i18n.prop('id_select_product'));
		        $('#id_select_status').html($.i18n.prop('id_select_status'));
                $('#id_active').html($.i18n.prop('id_active'));
                $('#id_banned').html($.i18n.prop('id_banned'));
                $('#id_cancel').html($.i18n.prop('id_cancel'));
				$('#id_save').html($.i18n.prop('id_save'));
		        $('#id_chid').html($.i18n.prop('id_chid'));
		        $('#id_add_date_copy').html($.i18n.prop('id_add_date_copy'));
		        $('#id_total_number_copy1').html($.i18n.prop('id_total_number_copy1'));
		        $('#id_payed_number_copy1').html($.i18n.prop('id_payed_number_copy1'));
		        $("#id_income_copy1").html($.i18n.prop('id_income_copy1'));
		
            }
        });
}
