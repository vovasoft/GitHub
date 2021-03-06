$(document).ready(function () {

	getuserinfo();
	loadProperties();
	$("#id_confirm").click(function(){
    	showModal();
		var name = parent.window.document.getElementById("hide").value;
		console.log(name+".............");
		getData(name);
    });
});

function getuserinfo(){
	var sessionid= $.cookie("sessionid");
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
var anchorTime = 0;
var lastTime = 0;
var dateCount = 0;
var daySeconds = 24*3600;
var Count = 0;
var ajax;
function getData(param) {
	var tzo = getTZO();

    var id = $("#selectCollection").val();
    console.log("user_pay-id+"+id);
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 +(offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 +(offset+tzo)*3600;
    anchorTime = startTime;
    lastTime = endTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
    var path = getServerPath()+"app/user_pay?app="+id+"&"+"start="+startTime+"&end="+endTime;
    console.log(path);
    $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
            setData(param,data);
        }
    });


}

function setData(param,data) {
    var xArr = [];//x轴
    var jsonStr = [];
    var chArr = [];
    var map = {};
    var dArr=[];
    var tnum = 0;

	var title;
            switch(param) {
			    case 'new_payer':
			        title = $('#id_new_payer').val();
			        break
			    case 'payer':
			        title = $('#id_payer_copy').val();
			        break
    		}
            $("#h2").text(title);
            $('#param').val(param);
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
    relCount = dArr.length;
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
    	 	//console.log("NO show");
    	 }else{
            var dataArr = map[ch];
            if(dataArr == undefined || dataArr == null || dataArr == '') {
                dataArr = getInitialArray(Count);
            }

    	$.each(channel.detail, function(index,item) {
    		var idx = parseInt((item.date - anchorTime)/daySeconds);
    		if(item[param]==null||item[param]=='null'){
    			item[param]=0;
    		}
    		dataArr[idx] = item[param];
            map[ch] = dataArr;
    	});
    	}
    });
    var i = 0;
    $.each(map, function(key, value) {


            jsonStr[i] = {
                name: key,
                data:map[key]
            }

            i++;


    });

    setChart(param,title,xArr, jsonStr);
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
		tbody += '<tr><td style = "overflow:hidden;text-align:center" title="'+item.name+'">'+item.name+'</td>';
		$.each(item.data,function(index,item){
			total += item;
		});
		tbody += '<td style="text-align: center;">'+total+'</td>';
		$.each(item.data,function(index,item){
			tbody += '<td style="text-align: center;">'+item+'</td>';
		});
		tbody += '</tr>';
		total = 0;
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

function setChart(param,title,xArr, yArr) {
    $('#trend-chart').highcharts({
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
                text: title
            }
        },
         tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: param+'：{point.y}'
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

        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
                $('#id_payer_copy').val($.i18n.prop('id_payer_copy'));
                $('#id_new_payer').val($.i18n.prop('id_new_payer'));
                $('#id_channel_name').html($.i18n.prop('id_channel_name'));
                $('#id_total').html($.i18n.prop('id_total'));
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
