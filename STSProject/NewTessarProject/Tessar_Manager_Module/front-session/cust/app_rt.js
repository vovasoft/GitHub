$(document).ready(function() {
	//	showModal();
	loadProperties();
	getuserinfo();
	$("#endDate").datepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		weekStart: 1,
		todayHighlight: true,
		pickerPosition: "bottom"
	});
	setDefaultDate1();
	$("#id_confirm").click(function() {
		showModal();
		var name = parent.window.document.getElementById("hide").value;
		console.log(name + "+name");
		getData(name);
	});
});
var ajax;

function getuserinfo() {
	var sessionid = $.cookie("sessionid");
	if(sessionid ==null&&sessionid ==""){
		parent.location.href="login.html";
	}else
	var path = getServerPath() + "user/session?sessionid=" + sessionid;
	ajax = $.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		async: false,
		jsonpCallback: "apiStatus",
		success: function(data) {
			//console.log("log.data.flag:"+data.flag);
			switch (data.flag) {
				case 502:
					if (data.user != null) {
						$("#UserInfo").text(data.user.username);
						if (data.user.group == "admin" || data.user.group == "xcloud") {
							addMenuByCookie();
							//getData('new_user');

							$("body").css("display", "block");
						} else {
							parent.location.href = "login.html";
						}
					} else {
						parent.location.href = "login.html";
					}
					break;
				case 504:
					parent.location.href = "login.html";
					break;
			}
		},
		error: function(data) {
			//console.log("error")
		}

	});
}
var tableStr = '';
var timeMinute;
var hours;
var mins;

function setWithData(param, data) {
	var xArr = [];
	var dataArr = [];
	$("#showData").html("<thead id='thead'><tr><th style='text-align: center;' id='id_time'></th><th style='text-align: center;' id='id_new_user_copy1'></th>" +
		"<th style='text-align: center;' id='id_active_user_copy1'></th><th style='text-align: center;' id='id_payer_copy1'></th>" +
		"<th style='text-align: center;' id='id_new_payer_copy'></th><th style='text-align: center;' id='id_payed_money_copy'></th>" +
		"<th style='text-align: center;' id='id_today_order'></th>" +
		"<th style='text-align: center;' id='id_succeed_order'></th><th style='text-align: center;' id='id_payed_rate'></th></tr></thead><tbody id='tbody'></tbody>");
	var detail = data.detail.sort(function(a, b) {
		return a.time - b.time;
	});
	for (var index = 0; index < detail.length; index++) {
		if (index == 0) {
			continue;
		}
		timeMinute = new Date(detail[index].time * 1000);
		hours = timeMinute.getUTCHours() - offset;
		mins = timeMinute.getUTCMinutes();
		if (appId == "1001" || appId == "2001" ||appId == "3001"||appId == "4001"||appId == "5001") {
			if (hours < 0) {
				hours = 24 + hours;
			}
		} 
//		else if (appId == "2001") {
//			if (hours >= 24) {
//				hours = hours - 24;
//			}
//		}
		if (hours == 0 && index == detail.length - 1 && detail.length > 4) {
			hours = "24";
		}
		if (mins == 0) {
			mins = mins + "0";
		}
		xArr[index - 1] = hours + ":" + mins;
		//		console.log("xArr[index]:"+xArr[index]);
		//		xArr[index] = item.time;
		if (param == 'income') {
			dataArr[index - 1] = parseFloat(detail[index][param].toFixed(2));
		} else {
			dataArr[index - 1] = detail[index][param];
		}
	}

	var jsonStr = [{
		name: getAppName(data.app),
		data: dataArr
	}];
	setChart(param, xArr, jsonStr);

	var str = '';
	for (var index = 0; index < detail.length; index++) {
		if (index == 0) {
			continue;
		}
		timeMinute = new Date(detail[index].time * 1000);
		hours = timeMinute.getUTCHours() - offset;
		mins = timeMinute.getUTCMinutes();
		if (appId == "1001" || appId == "2001" ||appId == "3001"||appId == "4001"||appId == "5001") {
			if (hours < 0) {
				hours = 24 + hours;
			}
		} 
//		else if (appId == "2001") {
//			if (hours >= 24) {
//				hours = hours - 24;
//			}
//		}
		if (hours == 0 && index == detail.length - 1) {
			hours = "24";
		}
		if (mins == 0) {
			mins = mins + "0";
		}
		str += '<tr>';
		str += '<td style="text-align: center;">' + xArr[index - 1] + '</td>';
		str += '<td style="text-align: center;">' + detail[index].new_user + '</td>';
		str += '<td style="text-align: center;">' + detail[index].active_user + '</td>';
		str += '<td style="text-align: center;">' + detail[index].payer + '</td>';
		str += '<td style="text-align: center;">' + detail[index].new_payer + '</td>';
		//str += '<td class="center">' + item.first_payer + '</td>';
		str += '<td style="text-align: center;">' + isZero((detail[index].income).toFixed(2)) + '</td>';
		str += '<td style="text-align: center;">' + detail[index].payed_order + '</td>';
		str += '<td style="text-align: center;">' + detail[index].all_order + '</td>';
		str += '<td style="text-align: center;">' + isZero((parseFloat(detail[index].payed_order / detail[index].all_order).toFixed(2))) * 100 + '%</td>';
		str += '</tr>';
	};
	$("#tbody").html(str);
	getDatatabel();
	loadProperties();
	tableStr = '<table><tr><th>' + getExcelTitle("id_time") + '</th><th>' + getExcelTitle("id_new_user_copy1") + '</th>' +
		'<th>' + getExcelTitle("id_active_user_copy1") + '</th><th>' + getExcelTitle("id_payer_copy1") + '</th>' +
		'<th>' + getExcelTitle("id_new_payer_copy") + '</th><th>' + getExcelTitle("id_payed_money_copy") + '</th>' +
		'<th>' + getExcelTitle("id_today_order") + '</th><th>' + getExcelTitle("id_succeed_order") + '</th>' +
		'<th>' + getExcelTitle("id_payed_rate") + '</th> </tr>';
	tableStr += str;
	hideModal();
}


var jsonData = null; //global
function apiStatus(data) {
	jsonData = data;
}


var offset = 0;
var appId;

function getData(param) {
	var tzo = getTZO();
	//	var id = getAppId($("#info").text());
	var id = parseInt($("#selectCollection").val());
	console.log("info++id++" + id);
	appId = id;
	offset = getTimeOffsetById(id);
	console.log(offset);
	var date = new Date($('#endDate').val().replace(/-/g, "/"));
	var time = date.getTime() / 1000 + (offset + tzo) * 3600;
	var path = getServerPath() + "app/app_rt?app=" + id + "&date=" + time;
	console.log("path = " + path);

	$.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonp: "callback",
		jsonpCallback: "apiStatus",
		success: function(data) {
			var name = parent.window.document.getElementById("hide").value;
			setWithData(name, data);
		}
	})
}

function workWithExistData(param, data) {
	if (data == null) {
		getData(param);
		setWithData('new_user', data);
	} else {
		setWithData(param, data);
	}
}

function getTitle(strName) {
	var strTitle = "";
	switch (strName) {
		case 'new_user':
			strTitle = $('#id_new_user_copy').val();
			break
		case 'active_user':
			strTitle = $('#id_active_user_copy').val();
			break
		case 'payer':
			strTitle = $('#id_payer_copy').val();
			break
		case 'new_payer':
			strTitle = $('#id_new_payer').val();
			break
			// case 'first_payer':
			//     strTitle = "首次充值用户";
			//     break
		case 'income':
			strTitle = $('#id_payed_money').val();
			break
		case 'all_order':
			strTitle = $('#id_order_number').val();
			break
		default:
			strTitle = "";

	}
	return strTitle;
}

function setChart(param, xArr, jsonStr) {
	for (var i = 0; i < xArr.length; i++) {
		//console.log("xarr["+i+"]:"+xArr[i]);
	}
	//get json data from server, then load to chart
	//console.log("title:"+param+"xArr"+xArr+"yArr"+jsonStr);
	$('#trend-chart').highcharts({
		chart: {
			type: 'line',
			zoomType: 'x'
		},
		title: {
			text: getTitle(param)
		},
		xAxis: {
			categories: xArr
		},
		yAxis: {
			title: {
				text: ''
			}
		},
		plotOptions: {
			line: {
				dataLabels: {
					enabled: true
				},
				enableMouseTracking: false
			}
		},
		series: jsonStr
	});
}

function setDefaultDate1() {
	var today = new Date();
	var date = today.getTime();
	$('#endDate').datepicker('update', (new Date(date)).Format("yyyy-MM-dd"));
}



// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function refuseData() {
	ajax.abort();
}

function loadProperties() {
	var lan = jQuery.i18n.browserLang();
	//console.log(jQuery.i18n.browserLang());
	jQuery.i18n.properties({
		name: 'strings',
		path: './i18n/',
		mode: 'map',
		language: lan,
		encoding: "UTF-8",
		callback: function() {
			$('#id_time').html($.i18n.prop('id_time'));
			$('#id_new_user_copy').val($.i18n.prop('id_new_user_copy'));
			$('#id_new_user_copy1').html($.i18n.prop('id_new_user_copy1'));
			$('#id_active_user_copy').val($.i18n.prop('id_active_user_copy'));
			$('#id_active_user_copy1').html($.i18n.prop('id_active_user_copy1'));
			$('#id_payer_copy').val($.i18n.prop('id_payer_copy'));
			$('#id_payer_copy1').html($.i18n.prop('id_payer_copy1'));
			$('#id_new_payer').val($.i18n.prop('id_new_payer'));
			$('#id_new_payer_copy').html($.i18n.prop('id_new_payer_copy'));
			$('#id_payed_money').val($.i18n.prop('id_payed_money'));
			$('#id_payed_money_copy').html($.i18n.prop('id_payed_money_copy'));
			$('#id_order_number').val($.i18n.prop('id_order_number'));
			$('#id_today_order').html($.i18n.prop('id_today_order'));
			$('#id_succeed_order').html($.i18n.prop('id_succeed_order'));
			$('#id_payed_rate').html($.i18n.prop('id_payed_rate'));
			$('#id_data_details').html($.i18n.prop('id_data_details'));
		}
	});
}