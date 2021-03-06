$(document).ready(function () {
	 showModal();
     getuserinfo();
     loadProperties();
    $("#id_confirm").click(function(){
    	tableStr = '<table>';
    	showModal();
        getData('new_user');
    });
    $("#id_new_user_copy").click(function(){
    	tableStr = '<table>';
    	showModal();
        setData('new_user',jsonData);
    });
    $("#id_active_user_copy").click(function(){
    	tableStr = '<table>';
    	showModal();
        setData('dau',jsonData);
    });
    $("#id_payer_copy").click(function(){
    	tableStr = '<table>';
    	showModal();
        setData('payer',jsonData);
    });
    $("#id_payed_money").click(function(){
    	tableStr = '<table>';
    	showModal();
        setData('income',jsonData);
    });
    $("#arpu").click(function(){
    	tableStr = '<table>';
    	showModal();
       setData('arpu',jsonData);
    });
    $("#arppu").click(function(){
    	tableStr = '<table>';
    	showModal();
        setData('arppu',jsonData);
    });

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
	        			getData('new_user');
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
        	//console.log("error")
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
    var id = getAppId($("#info").text());
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 + (offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    anchorTime = startTime;
    lastTime = endTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
    var path = getServerPath()+"app/ch_list?app="+id+"&"+"start="+startTime+"&end="+endTime;
//  console.log("path = "+path+", dateCount = "+dateCount);
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

function setData(param,data) {
    var xArr = [];//x轴
    var jsonStr = [];
    var chArr = [];
    var map = {};
    var dArr=[];
    var tnum = 0;
    //console.log("length = "+data.channels.length);
	var title;
            switch(param) {
			    case 'new_user':
			        title = $('#id_new_user_copy').val();
			        break
			    case 'dau':
			        title = $('#id_active_user_copy').val();
			        break
			    case 'payer':
			    	title = $('#id_payer_copy').val();
			    	break
			    case 'income':
			    	title = $('#id_payed_money').val();
			    	break
    		}
            $("#h2").text(title);
            $("#param").val(param);
            $.each(data.channels, function(index,channel) {
    	     if(channel.details.length>Count){
    	     	Count = channel.details.length;
    	     }
    	      $.each(channel.details, function(index,item) {
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
    
    $.each(data.channels,function(index,channel){
    	var dataArr=[];
    	var ch = channel['channel'];
    	if(ch=='all'){
    		//console.log("NO show");
    	}else{
    	var dataArr = map[ch];
    	if(dataArr == undefined || dataArr == null || dataArr == '') {
	              dataArr = getInitialArray(Count);
	          }
    	$.each(channel.details, function(index,item) {
    		var idx = parseInt((item.date - anchorTime)/daySeconds);
    		if(param=='income'){
    		dataArr[idx] = parseFloat(item[param].toFixed(2));    			
    		}else{
    		if(item[param]==null||item[param]=='null'){
    			item[param]=0;
    		}
    		dataArr[idx] = item[param];
    		}
    		map[ch] = dataArr;
    	});
    	}
    });
    var i = 0;
    $.each(map, function(key, value) {

        //console.log("key = "+key+", value = "+value);
//      var num = 0;
//      for(var idx = 0; idx < map[key].length; idx++ ) {
//          num  = num + map[key][idx];
//      }
//   
//      if(num == 0) {
//          console.log(key+", num = "+num);
//      } else {

            jsonStr[i] = {
                name: key,
                data:map[key]
            }
            //console.log(i +", key = "+key+"value = "+map[key]);
            i++;
//       }

    });

    setChart(param,title,xArr, jsonStr);
    setList(xArr, jsonStr);
    Count=0;

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
		tbody += '<tr><td style = "overflow:hidden;text-align:center;" title="'+item.name+'">'+item.name+'</td>';
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
        //console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
            	$('#id_channel_name').html($.i18n.prop('id_channel_name'));
            	$('#id_new_user_copy').val($.i18n.prop('id_new_user_copy'));
            	$('#id_active_user_copy').val($.i18n.prop('id_active_user_copy'));
            	$('#id_payer_copy').val($.i18n.prop('id_payer_copy'));
            	$('#id_payed_money').val($.i18n.prop('id_payed_money'));
            	$('#id_total').html($.i18n.prop('id_total'));
            }
        });
}   	

