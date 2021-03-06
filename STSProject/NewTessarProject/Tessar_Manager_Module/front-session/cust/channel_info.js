$(document).ready(function () {
	showModal();
	getuserinfo();
    $("#id_confirm").click(function(){
    	tableStr="<table>";
    	showModal();
        getData();
    });
});

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
        			var urlParam = window.location.search;
					if(urlParam==null || urlParam==undefined || urlParam==''){
						hideModal();
						parent.location.href = "login.html";
						return;
					}
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				//addMenuByCookie();
        				$("#menu").css("display","block");
						if(data.user.group == "admin"){
							$("#user_admin").css("display","block");
						}
					}
        			$("body").css("display","block");
        			getData();
        			
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

var jsonData = null;
function apiStatus(data) {
    jsonData = data;
}

var anchorTime = 0;
var lastTime = 0;
var dateCount = 0;
var daySeconds = 24*3600;
var Count = 0;
var ajax;
function getData() {
	var tzo = getTZO();
	var urlParam = window.location.search;
	var channel = urlParam.split("=")[1].split("&")[0];
	var appid = urlParam.split("=")[2];
    var offset = getTimeOffsetById(appid);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 + (offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    anchorTime = startTime;
    lastTime = endTime;
    dateCount = (endTime - startTime)/daySeconds+1;
    var path = getServerPath() +"manage/ch_query?app="+appid+"&channel="+channel+"&start="+startTime+"&end="+endTime;
    console.log(path);
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

function setData(data) {
    var xArr = [];//x轴
    var jsonStr = [];
    var chArr = [];
    var map = {};
    var dArr=[];
    var tnum = 0;
    //console.log("length = "+data.channels.length);
    $.each(data.channels, function(index,channel) {
    	     if(channel.detail.length>Count){
    	     	Count = channel.detail.length;
    	     }
    	      $.each(channel.detail, function(index,item) {
    	     	dArr[tnum]=item.date;
    	     	tnum++;
    	     });
    });
    dArr = dArr.unique();
    dArr = dArr.sort(function(a,b){
    	return a-b;
    });
    //看最后一天的数据出来没
    if(dateCount!=Count){
    	if(dArr[Count-1]!=lastTime){
    		dateCount = dateCount-1;
    	}
    	Count=dateCount;
    }
    for(var i = 0; i < Count; i++) {
        xArr[i] = new Date((anchorTime + i*daySeconds)*1000).Format("MM-dd");
    }
    
    $.each(data.channels, function(index,channel) {
    	 var ch = channel['channel'];
    	 if(ch=='all'){
    	 	//console.log("NO All");
    	 }else{
            var dataArr = map[ch];
            if(dataArr == undefined || dataArr == null || dataArr == '') {
                dataArr = getInitialArray(Count);
            }
    	
    	$.each(channel.detail, function(index,item) {
    		var idx = parseInt((item.date - anchorTime)/daySeconds);
    		dataArr[idx] = item['new_user'];
    		
            map[ch] = dataArr;
    	});
    	}
    });
    var i = 0;
    //console.log("x轴 = "+xArr);
    $.each(map, function(key, value) {

        //console.log("key = "+key+", value = "+value);
        var num = 0;
        for(var idx = 0; idx < map[key].length; idx++ ) {
            num  = num + map[key][idx];
        }
        if(num == 0) {
            //console.log(key+", num = "+num);
        } else {
            jsonStr[i] = {
                name: key,
                data:map[key]
            }
            //console.log(i +", key = "+key+"value = "+map[key]);
            i++;
        }

    });

    setChart(xArr, jsonStr);
    setList(xArr, jsonStr);
    Count = 0;
}

var tableStr = '<table>';
var total = 0;
function setList(xArr, jsonStr) {
	$("#showData").html("<thead id='thead'></thead><tbody id='tbody'></tbody>");
	var str = '<tr><th style="text-align: center;" id="id_channel_name"></th><th style="text-align: center;" id="id_total"></th>';
	for(var i=0;i<xArr.length;i++){
		str += '<th style="text-align: center;">'+xArr[i]+'</th>';
	}
	str += '</tr>';
	$("#thead").html(str);
	var tbody = '';
	$.each(jsonStr,function(index,item){
		tbody += '<tr><td width="3px" style = "overflow:hidden;text-align:center;" title="'+item.name+'">'+item.name+'</td>';
		$.each(item.data,function(index,item){
			total +=item;
		})
		tbody += '<td style="text-align: center;">'+total+'</td>';
		$.each(item.data,function(index,item){
			tbody += '<td style="text-align: center;">'+item+'</td>';
		})
		tbody += '</tr>';
		total=0;
	});
	$("#tbody").html(tbody);
    getDatatabel();
    loadProperties();
     tableStr+='<tr><th>'+getExcelTitle("id_channel_name")+'</th><th>'+getExcelTitle("id_total")+'</th>'
    var tstr='';
    for(var i=0;i<xArr.length;i++){
		tstr += '<th style="text-align: center;">'+new Date(new Date().getFullYear()+"-"+ xArr[i]).Format("MM/dd/yyyy")+'</th>';
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

function setChart(xArr, yArr) {
    $('#user-chart').highcharts({
        chart: {
            type: 'line',
            zoomType: 'x'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: xArr
        },
        yAxis: {
            title: {
                text: 'new user'
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
        series: yArr
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
                $('#id_new_user_copy').html($.i18n.prop('id_new_user_copy'));
                $('#id_channel_name').html($.i18n.prop('id_channel_name'));
                $('#id_channel_name_copy').html($.i18n.prop('id_channel_name_copy'));
                $('#id_total').html($.i18n.prop('id_total'));
                $('#id_total_copy').html($.i18n.prop('id_total_copy'));
                
            }
        });
}