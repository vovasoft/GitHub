$(document).ready(function () {
//	showModal();
	getuserinfo();
	$("#endDate").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        weekStart: 1,
        todayHighlight: true,
        pickerPosition: "bottom"
    });
	setDefaultDate2();
    $("#id_confirm").click(function(){
    	showModal();
    	tableStr = '<table>';
        getData();
    });

});
function setDefaultDate2() {
	var today = new Date();
	var date = today.getTime();
	$('#endDate').datepicker('update', (new Date(date)).Format("yyyy-MM-dd"));
}
function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	var path = getServerPath()+"user/session?sessionid="+sessionid;
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        async:false,
        jsonpCallback: "apiStatus",
        success: function(data) {
        	////console.log("log.data.flag:"+data.flag);
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
//	        			getData();
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

var jsonData = null;
function apiStatus(data) {
	jsonData = data;
}
var appid;
var offset;
var ajax;
function getData() {
	var tzo = getTZO();
	var date = new Date($('#endDate').val().replace(/-/g,"/"));
//  var id = getAppId($("#info").text());
	var id = $("#selectCollection").val();
    offset = getTimeOffsetById(id);
	var time = date.getTime()/1000 + (offset+tzo)*3600;
    appid = id;
    var path = getServerPath()+"app/ch_detail?app="+id+"&date="+time;
//  console.log("path = "+path);

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

function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) {
        arr[i] = 0;
    }
    return arr;
}


function setData(data) {
	var map = {};
    if(data.channels == null) {
        return;
    }
    var length = data.channels.length;
    var xArr = [];//x轴
    //console.log("length = "+length);

    var jsonStr = [];
    var chArr = [];
    for(var i=0;i<data.channels.length;i++){
        ch = data.channels[i].channel;
        if(ch == "all"){
        	continue;
        }
        var dataArr = map[ch];
        if(dataArr == undefined || dataArr == null || dataArr == '') {
                    dataArr = [];
            }
	     var detail = data.channels[i].details.sort(function(a,b){
			return a.time - b.time;
	    });
	    for(var index=0 ; index < detail.length;index++ ){
	    	if(index==0){
	    		continue ; 
	    	}
	        var timeMinute = new Date(detail[index].time*1000);
		    var hours = timeMinute.getUTCHours()-offset;
			var mins = timeMinute.getUTCMinutes();
			if(appid == "1001"){
				if(hours < 0){
					hours = 24+hours;
				}
			}else if(appid == "2001"){
				if(hours >= 24){
					hours = hours-24;
				}
			}
			if(hours == 0 && index == detail.length-1 && detail.length > 4){
				hours = "24";
			}
			if(mins == 0){
				mins = mins+"0";
			}
			xArr[index-1] = hours+ ":" +mins;
            dataArr[index-1] = detail[index]['new_user'];
	       //console.log("xArr:"+xArr[Index])
	    }
         map[ch] = dataArr;
        
    };
    var i = 0;
    //console.log("x轴 = "+xArr);
    $.each(map, function(key, value) {

        //console.log("key = "+key+", value = "+value);
//      var num = 0;
//      for(var idx = 0; idx < map[key].length; idx++ ) {
//          num  = num + map[key][idx];
//      }
//      
//      if(num == 0) {
//
//      } else {

            jsonStr[i] = {
                name: key,
                data: map[key]
            }
            //console.log(i +", key = "+key+"value = "+map[key]);
            i++;
//       }

    });

    setChart(xArr, jsonStr);
    hideModal();
    setList(xArr, jsonStr);
}

function setChart(xArr, seriesData) {
	for(var i=0;i<xArr.length;i++){
		//console.log("xarr["+i+"]:"+xArr[i]);
	}
    $('#trend-chart').highcharts({
        chart: {
            type: 'line',
            zoomType: 'x'
        },
        title: {
            text: 'new user'
        },
        xAxis: {
            categories: xArr
        },
        yAxis: {
            title: {
                text: ''
            }
        },
         tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: 'new_user ：{point.y}'
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        series: seriesData
    });
}

var tableStr = '<table>';
function setList(xArr, seriesData) {
    var str = '<tr><th></th>';
    for(var i = 0; i < xArr.length; i++){
        str += '<th>' + xArr[i] + '</th>';
    }
    str += '</tr>';
    $("#thead").html(str);
    var tbody='';
    $.each(seriesData, function(index, item){
    	//console.log("seriesData*&*"+seriesData.name);
        tbody += '<tr><td>' + item.name + '</td>';
        //console.log("name = " + item.name);
        $.each(item.data, function(index, item){
            tbody += '<td>' + item + '</td>';
            //console.log("item = "+item);
        })
        tbody += '</tr>';
    });
    $("#tbody").html(tbody);
    loadProperties();
    tableStr+='<tr><th>'+$('#id_channel_name').val()+'</th>'
    var tstr='';
    for(var i=0;i<xArr.length;i++){
		tstr += '<th style="text-align: center;">'+xArr[i]+'</th>';
	}
    tstr+='</tr>'
	tableStr += tstr;
	tableStr += tbody;
   // getDatatabel();
}

// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) { 
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
            	$('#id_channel_name').val($.i18n.prop('id_channel_name'));
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