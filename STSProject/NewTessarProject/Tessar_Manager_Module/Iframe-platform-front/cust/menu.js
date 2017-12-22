/*$(document).ready(function () {
	$(".mychild").click(function() {
		console.log($(this).attr("name"));
		var name = $(this).attr("name");
		$("#hide").val(name);
	});
});*/
//应用趋势二级菜单
$(document).ready(function () {
	$(".mychildren").click(function() {
		/* console.log($(this).attr("id")); */
		var name = $(this).attr("name");
		var childMenuId = $(this).attr("id");
		$("#hide_child_menu_id").val(childMenuId);
		$("#hide").val(name);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
		$("li[name='menuid']").attr("class","");
	});
	
	//支付二级菜单
	$(".pay").click(function(){
		var name = $(this).attr("name");
		var pay_Id = $(this).attr("id");
		$("#hide_child_menu_id").val(pay_Id);
		$("#hide").val(name);
		$("hide_pay_id").val(hide_pay_id);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
	});
	
	//渠道二级菜单
	$(".channel").click(function(){
		var name = $(this).attr("name");
		var channel_Id = $(this).attr("id");
		$("#hide_child_menu_id").val(channel_Id);
		$("#hide").val(name);
		$("hide_channel_id").val(hide_channel_id);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
	});
	//概况
	$(".RT").click(function(){
		var name = $(this).attr("name");
		var channel_Id = $(this).attr("id");
		$("#hide_child_menu_id").val(channel_Id);
		$("#hide").val(name);
		$("hide_RT_id").val(hide_channel_id);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
	});

	/*//渠道列表三级菜单
	$(".ch_list").click(function(){
		var name = $(this).attr("name");
		var ch_list_Id = $(this).attr("id");
		$("#hide_child_menu_id").val(ch_list_Id);
		$("#hide").val(name);
		$("hide_ch_list_id").val(ch_list_Id);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
	});*/
	
	/*//支付趋势三级菜单
	$(".pay_trend").click(function(){
		var name = $(this).attr("name");
		var ch_pay_trend_Id = $(this).attr("id");
		$("#hide").val(name);
		$("#hide_child_menu_id").val(ch_pay_trend_Id);
		console.log($("#hide").val(name)+"++++12121212121");
		$("hide_pay_trend_id").val(ch_pay_trend_Id);
		$("ul.nav").children("li").removeClass("child_menu_gaol");
		$(this).parent("li").addClass("child_menu_gaol");
	});*/
});
function getAppId(name) {
	var id = 7001;
   switch(name) {
   	case '全部应用':
    id = 7001;
   	break
   	case 'BLOOD STRIKE':
   	id = 1001;
   	break
   	case 'WON':
   	id = 2001;
   	break
   	default:
   	id = 7001;
   }
   return id;
}

function getAppName(id) {
	var name = '';
	switch(id) {
   	case '7001':
    name = '全部应用';
   	break
   	case '1001':
   	name = 'BLOOD STRIKE';
   	break
   	case '2001':
   	name = 'WON';
   	break
   	default:
   	name = '全部应用';
   }
   return name;
}

function getServerPath() {
//	return "http://stats.xcloudgame.com:8080/stats/";
	return "http://localhost:8080/stats/"; 
}

function getTimeOffsetById(id) {
	var offset = 0;
    switch(id) {
    	case 1001:
    	offset = 3;
    	break
    	case 2001:
    	offset = -2;
    	break
    }
    return offset;
}

function addMenuByCookie(){
	var cookie = document.cookie;
	console.log("++++++cookie:"+cookie);
    var param;
    if(cookie != "undefined" && cookie != null && cookie != ""){
//      param = (cookie.split(";")[0]).split("=")[1];
		var cookies = cookie.split(";");
		for (var i = 0; i < cookies.length; i++) {
			var cook = $.trim(cookies[i]);
			if(cook.substring(0, "param".length + 1) == "param="){
				param = cook.split("=")[1];
			}
		}
    }
    console.log("param:"+param);
    switch(param) {
		case '7001':
		MenuForAll();
		break
		case '1001':
		MenuForBS();
		break
		case '2001':
		MenuForWon();
		break
		default:
		//MenuForAll();
		getPageInfo();
	}
    loadPropertiesForMemu();
}


function getPageInfo(){
	var location = window.location.href;
		var page = location.substring(location.lastIndexOf("/")+1);
		if(page == "index.html" || page == "trend.html" || page == "pay.html" || page == "channel.html"){
			MenuForAll();
		}else{
			window.location.href = "allmenu.html";
		}
}




function visit(){
	var d= new Date(); 
	d.setHours(d.getHours() + 0);
	document.cookie = "param= ; expires=" + d.toGMTString();
	window.location.href = "allmenu.html";
}

function logout(){
	var d= new Date(); 
	d.setHours(d.getHours() + 0);
	document.cookie = "param= ; expires=" + d.toGMTString();
	var path = getServerPath()+"user/logout";
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	window.location.href = "login.html";	
        }
      })
}
function getDatatabel(){
	 var otable = $('#showData').dataTable({
	 	"bDestroy": true,
		"bRetrieve": true
	 });
	 otable.fnDestroy();
	 otable = $('#showData').dataTable({
	 	 "fnInitComplete":function(){
	 	 	this.fnAdjustColumnSizing(true);
	 	 },
		"bDestroy": true,
		"bRetrieve": true,
		"bAutoWidth" : false,
		"scrollable" : true,
        "sScrollY": "100%",
        "aoColumnDefs": [
      { "sWidth": "150px", "aTargets": [ 0 ] }
    ],
		"sPaginationType": "full_numbers"
	});
	$(window).resize(function () {
            otable.fnAdjustColumnSizing();
        });
}
function MenuForBS(){
	$("#info").text("BLOOD STRIKE");
	var str = '<li class="dropdown"><a href="#" data-toggle="dropdown">'+
		'<i class="glyphicon glyphicon-star"></i>BLOOD STRIKE<span class="caret"></span></a><ul class="dropdown-menu" role="menu">';
		str += '<li><a href="#" onclick="getchByApp(7001)" id="id_all_app"></a></li>';
		str += '<li><a href="#" onclick="getchByApp(2001)">WON</a></li>';
		$("#memu").html(str);
}

function MenuForAll(){
		$("#info").html('<font id="id_all_app_copy"></font>');
		var str = '<li class="dropdown"><a href="#" data-toggle="dropdown">'+
		'<i class="glyphicon glyphicon-star"></i><font id="id_all_app"></font><span class="caret"></span></a><ul class="dropdown-menu" role="menu">';
		str += '<li><a href="#" onclick="getchByApp(1001)">BLOOD STRIKE</a></li>';
		str += '<li><a href="#" onclick="getchByApp(2001)">WON</a></li>';
		$("#memu").html(str);
}
var num;
function isZero(num){
	if(num==0.00||num==0.0||num=='NaN'){
		return 0;
	}else{
		return num;
	}
}

function MenuForWon(){
	$("#info").text("WON");
	var str = '<li class="dropdown"><a href="#" data-toggle="dropdown">'+
		'<i class="glyphicon glyphicon-star"></i>WON<span class="caret"></span></a><ul class="dropdown-menu" role="menu">';
		str += '<li><a href="#" onclick="getchByApp(7001)" id="id_all_app"></a></li>';
		str += '<li><a href="#" onclick="getchByApp(1001)">BLOOD STRIKE</a></li>';
		$("#memu").html(str);
}

function getchByApp(param){
	var d= new Date(); 
	d.setTime(d.getTime() + 30*60*1000);
	document.cookie = "param="+param+"; expires=" + d.toGMTString();
	switch(param) {
		case 7001:
		window.location.href = "allmenu.html";
		break
		case 1001:
		window.location.href = "app_rt.html";
		case 2001:
		window.location.href = "app_rt.html";
		default:
	}
}
function getExcel(fileName){
    var param = $("#param").val();
    //console.log("parm"+param);
    if(param==null||param==''||param==undefined){
    	
	$('#dataTable').tableExport({type: 'excel', escape: 'false', tableName : fileName});
    }else{
    	$('#dataTable').tableExport({type: 'excel', escape: 'false', tableName : fileName+"_"+param});
    }
}
function showModal(){
//	$('#delprogressModal').html("Loading...<img src = 'img/ajax-loaders/ajax-loader-7.gif'>");
	$('#delprogressModal').html("<img src = 'img/ajax-loaders/0504315.gif'></br>Loading...");
	$('#progressModal').modal('show');
	window.setTimeout(hidModalToError,15*1000);
}
function hideModal(){
	$('#progressModal').modal('hide');
}
function hidModalToError(){
	/*$('#delprogressModal').html("<button type='button' onclick='refuseData()' class='close' data-dismiss='modal'>"
	+"×</button><br/>Loading...<img src = 'img/ajax-loaders/ajax-loader-7.gif'>");*/
	$('#delprogressModal').html("<button type='button' onclick='refuseData()' class='close' data-dismiss='modal'>"
	+"×</button><br/><img src = 'img/ajax-loaders/13221818.gif'></br>Loading....");
}
function getPayTypeById(id) {
	var payTypeMap = {};
	payTypeMap[25] = "Paypal";
	payTypeMap[26] = "Rixty";
	payTypeMap[4] = "ebanx-creditcard";
	payTypeMap[12] = "BoaCompra Pagseguro";
	payTypeMap[5] = "ebanx-tef";
	payTypeMap[7] = "ebanx-boleto";
	payTypeMap[9] = "BoaCompra";
	payTypeMap[11] = "BoaCompra-paypal";
	payTypeMap[10] = "Pagamento via Celular";
	payTypeMap[13] = "TRANSFERÊNCIA BANCÁRIA";
	payTypeMap[14] = "Brasil Credit Card";
	payTypeMap[17] = "INTERNATIONAL CREDIT CARD";
	payTypeMap[24] = "BoaCompra Gold";
	payTypeMap[27] = "FACEBOOK";
	payTypeMap[28] = "Boleto";
	payTypeMap[29] = "Boleto";
	payTypeMap[30] = "BoaCompra VISA";
	payTypeMap[31] = "BoaCompra MasterCard";
	payTypeMap[32] = "BoaCompra American Express";
	payTypeMap[33] = "BoaCompra Visa Electron";
	payTypeMap[34] = "BoaCompra Diners";
	payTypeMap[35] = "BoaCompra Hipercard";
	payTypeMap[36] = "BoaCompra Aura";
	payTypeMap[37] = "BoaCompra Elo Card";
	payTypeMap[38] = "BoaCompra Personal Card";
	payTypeMap[39] = "BoaCompra Pleno Card";
	payTypeMap[40] = "BoaCompra Banco do Brasil";
	payTypeMap[41] = "BoaCompra Bradesco";
	payTypeMap[42] =  "BoaCompra Caixa Econômica Federal";
	payTypeMap[43] = "BoaCompra Bradesco HSBC";
	payTypeMap[44] = "BoaCompra Itaú";
	payTypeMap[45] = "BoaCompra Banrisul Online";
	payTypeMap[46] = "Cartão de Crédito";
	payTypeMap[47] = "Depósito ou Transferência";
	payTypeMap[48] = "BoaCompra online wallet Group";
	payTypeMap[53] = "Boacompra Paypal";
	payTypeMap[49] = "Int.Credit Card";
	payTypeMap[50] = "FACEBOOK MOBILE";
	payTypeMap[51] = "Skrill";
	payTypeMap[52] = "E-prepag";
	payTypeMap[54] = "Pagamento via Celular";
	payTypeMap[55] = "Pagamento via Celular";
	return payTypeMap[id];
}
function loadPropertiesForMemu() {
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
                $('#id_all_app_copy').html($.i18n.prop('id_all_app_copy'));
            }
        });
}
function getExcelTitle(id){
	return $("#"+id).html();
}
//本地时间与UTC时间的偏量
function getTZO(){
	var tzo=(new Date().getTimezoneOffset()/60)*(-1);
	return tzo;
}
Array.prototype.unique = function(){
 var res = [];
 var json = {};
 for(var i = 0; i < this.length; i++){
  if(!json[this[i]]){
   res.push(this[i]);
   json[this[i]] = 1;
  }
 }
 return res;
}
function isActive(ob){
	$("li[name='menuid']").attr("class","");
	$(ob).attr("class","active");
}


 