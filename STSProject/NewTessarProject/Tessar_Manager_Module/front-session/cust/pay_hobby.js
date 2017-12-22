 $(document).ready(function () {
// 	showModal();
	getuserinfo();
//  setTrendChart();
$("#id_confirm").click(function(){
    	showModal();
        //getData(getParam());
		/* getData("new_user",jsonData); */
		var name = parent.window.document.getElementById("hide").value;
		console.log(name+".............");
		getData(name);
    });
});
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
        	//console.log("log.data.flag:"+data.flag);
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
//					    getData('payer');
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
var dateCount = 0;
var daySeconds = 24*3600;
var ajax;
var appName;
function getData(param) {
	var tzo = getTZO();
//  var id = getAppId($("#info").text());
	var id = $("#selectCollection").val();
    var appName = getAppName('1001');
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 + (offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    anchorTime = startTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
    var path = getServerPath()+"app/pay_hobby?app="+id+"&start="+startTime+"&end="+endTime;
    ajax = $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
           setData(param,data);

        }
    });
}
var tableStr='<table>';
var newparam;
function setData(param,data) {
	$("#all_payer").text(data.generic.all_payer);
	$("#all_order").text(data.generic.all_order);
	$("#income").text(parseFloat(data.generic.income.toFixed(2)));
	newparam = param;
    var jsonStr = [];
    var jsonChartStr = [];
	var xArr = [];
    //console.log("length = "+data.channels.length);
	var title;
            switch(param) {
			    case 'payer':
			        title = "支付用户";
			        break
			    case 'order':
			    	title = "支付订单";
			    	break
			    case 'income':
			    	title = "累计收入";
			    	break
    		}
            $("#h2").text(title);
            $("#param").val(param);
//  for(var i = 0; i < dateCount; i++) {
//      xArr[i] = new Date((anchorTime + i*daySeconds)*1000).Format("yyyy-MM-dd");
//  }
    var map = {};
    var dataMap;
	var payTypeArr = {};
	count = 0;
	$.each(data.detail, function(index,items) {
		$.each(items.types, function(index,item) {
			xArr[count] = item.paytype;
		    count++;
		});
	});
		xArr = xArr.unique();
		xArr = xArr.sort(function(a,b){
			return a-b;
		});
		for(var n=0;n<xArr.length;n++){
			var val = xArr[n];
			payTypeArr[val] = 0;
			}
	$.each(data.detail, function(index,items) {
		var chs = items['channel'];
		var dataMap = map[chs];
		if(dataMap == undefined || dataMap == null || dataMap == '') {
			dataMap = {};
			for(var n=0;n<xArr.length;n++){
			var val = xArr[n];
			dataMap[val] = 0;
			}
          }
		$.each(items.types, function(index,item) {
			if(param=="income"){
				dataMap[item.paytype] = parseFloat(item[param].toFixed(2));
				payTypeArr[item.paytype] += item[param];
			}else{
			dataMap[item.paytype] = item[param];
			payTypeArr[item.paytype] += item[param];
			}
		});
		map[chs] = dataMap;
	});
    var i = 0;
    $.each(map, function(key, value) {
        var num = 0;
        for(var idx = 0; idx < map[key].length; idx++ ) {
            num  = num + map[key][idx];
        }
        if(num != 0) {
        } else {
            jsonStr[i] = {
                name: key,
                data:map[key]
            }
            i++;
          }

    });	  
    var j = 0;
    var dataStr
    $.each(payTypeArr , function(key, value) {
            jsonChartStr[j] = value;
            j++;
    });	
      dataStr = [{
            name: "PayType",
            data: jsonChartStr
    }];
    var chartxArr = [];
    for(var i=0;i<xArr.length;i++){
    	chartxArr[i] =getPayTypeById(xArr[i]);
    }
    
    
    setChart(chartxArr,dataStr,param);
    setList(xArr, jsonStr);
    hideModal();
}
function setList(xArr, jsonStr) {
	$("#showData").html("<thead id='thead'></thead><tbody id='tbody'></tbody>");
	var str = '<tr><th style="text-align: center;" id="id_channel_name"></th>';
	for(var i=0;i<xArr.length;i++){
		str += '<th style="text-align: center;">'+getPayTypeById(xArr[i])+'</th>';
	}
	str += '</tr>';
	$("#thead").html(str);
	var tbody = '';
	$.each(jsonStr,function(index,item){
		tbody += '<tr><td width="3px" style = "overflow:hidden;text-align: center;" title="'+item.name+'">'+item.name+'</td>';
		$.each(item.data,function(index,item){
			tbody += '<td style="text-align: center;">'+item+'</td>';
		})
		tbody += '</tr>';
	});
	$("#tbody").html(tbody);
    getDatatabel();
    loadProperties();
    tableStr+='<tr><th>'+getExcelTitle("id_channel_name")+'</th>'
    var tstr='';
    for(var i=0;i<xArr.length;i++){
		tstr += '<th style="text-align: center;">'+getPayTypeById(xArr[i])+'</th>';
	}
    tstr+='</tr>'
	tableStr += tstr;
	tableStr += tbody;
    hideModal();
}

function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) {
        arr[i] = 0;
    }
    return arr;
    }
function setChart(xArr,dataStr,param) {
    $('#trend-chart').highcharts({
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: param
        },
        xAxis: {
            categories: xArr,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<table>',
            pointFormat: '<tr><td>'+param+':{point.y:1f}' +
                '</td></tr></table>',
            shared: false,
            useHTML: false
        },
        plotOptions: {
        	 series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true
//                          format: '{point.y:1f}'
                        }
                   },
//          column: {
//              pointPadding: 0.2,
//              borderWidth: 0
//          }
        },
        series: dataStr
    });

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
            	$('#id_data_details').html($.i18n.prop('id_data_details'));
            	$('#id_payer_copy').val($.i18n.prop('id_payer_copy'));
            	$('#id_payer_copy1').html($.i18n.prop('id_payer_copy1'));
            	$('#id_payed_order_copy').val($.i18n.prop('id_payed_order_copy'));
            	$('#id_payed_order_copy1').html($.i18n.prop('id_payed_order_copy1'));
            	$('#id_total_income').val($.i18n.prop('id_total_income'));
            	$('#id_total_income_copy').html($.i18n.prop('id_total_income_copy'));
            	$('#id_channel_name').html($.i18n.prop('id_channel_name'));
            }
        });
}   	
function getTimeOffsetById(id) {
	var offset = 0;
    switch(id) {
    	case "1001":
    	offset = 3;
    	break
    	case "2001":
    	offset = -2;
    	break
    }
    return offset;
}