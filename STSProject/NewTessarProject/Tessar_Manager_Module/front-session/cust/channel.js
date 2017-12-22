 $(document).ready(function () {
 	//showModal();
 	loadProperties();
	getuserinfo();
	$("#id_confirm").click(function(){
    	showModal();
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
					    //getData('new_user');
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
function getData(param) {
	var tzo = getTZO();
    var id = parseInt($('#selectCollection').val());
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 + (offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    anchorTime = startTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
    var path = getServerPath()+"main/channel?start="+startTime+"&end="+endTime;
//  console.log("path = "+path+", dateCount = "+dateCount);

    ajax = $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
           //console.log("****************");
           setData(param,data);

        }
    });
}
var tableStr='';
var newparam;
function setData(param,data) {
	newparam = param;
    var jsonStr = [];
	var xArr = [];
    //console.log("length = "+data.channels.length);
	var title;
            switch(param) {
			    case 'new_user':
			        title = $('#id_new_user').val();
			        break
			    case 'payed_order':
			    	title = $('#id_payed_order').val();
			    	break
			    case 'income':
			    	title = $('#id_total_income').val();
			    	break
    		}
            $("#h2").text(title);
//  for(var i = 0; i < dateCount; i++) {
//      xArr[i] = new Date((anchorTime + i*daySeconds)*1000).Format("yyyy-MM-dd");
//  }
    var map = {};
	var dataArr = [];
    var str="";
    $.each(data.apps, function(index,app) {
    var aps = getAppName(app['app']);
    dataArr = map[aps];
    	if(dataArr == undefined || dataArr == null || dataArr == '') {
              dataArr = [];
            }
		if(selectApp()==app['app']){
    	$.each(app.channels, function(index,item) {
    	xArr[index] = item['parent'];
		//console.log(xArr[index]+"***"+index);
		dataArr[index] = item[param];
		//xArr[index] = new Date(item.date*1000).Format("yy-MM-dd");
		//console.log("time:"+new Date(item.time*1000).Format("hh:mm"))
       $("#showData").html("<thead id='thead'><tr><th style='text-align: center;' id='id_main_channel'></th><th style='text-align: center;' id='id_new_user_copy'></th>"+
       "<th style='text-align: center;'id='id_payed_order_copy'></th><th style='text-align: center;' id='id_total_income_copy'></th></tr></thead><tbody id='tbody'></tbody>");
		str += '<tr>';
		str += '<td style = "overflow:hidden;text-align: center;" title="'+item.parent+'">' + item.parent+ '</td>';
		str += '<td style="text-align: center;">' + item.new_user + '</td>';
		str += '<td style="text-align: center;">' + item.payed_order + '</td>';
		str += '<td style="text-align: center;">' + isZero((parseFloat(item.income).toFixed(2)))+ '</td>';
		str += '</tr>';
    	});
		$("#tbody").html(str);
	    getDatatabel();
      }
	  map[aps] = dataArr;
    	//console.log(getAppName(app.app)+"+_+_+_:"+dataArr+"))(____"+xArr);
    });
		
    	//console.log(getAppName(app.app)+"+_+_+_:"+dataArr+"))(____"+xArr);
    var i = 0;
    $.each(map, function(key, value) {
        //console.log("!!!!!!!!!key = "+key+", value = "+value);
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
            i++;
        }

    });	
    setChart(xArr,title,jsonStr);
    loadProperties();
    tableStr='<table><tr><th>'+getExcelTitle("id_main_channel")+'</th><th>'+getExcelTitle("id_new_user_copy")+'</th>'+
    '<th>'+getExcelTitle("id_payed_order_copy")+'</th><th>'+getExcelTitle("id_total_income_copy")+'</th></tr>';
    tableStr+=str;
    hideModal();
}

function selectAppShow(){
	 showModal();
	 setData(newparam,jsonData);
    }
function selectApp(){
		 var selectApp = $("#selectCollection").val();
		 return selectApp;
    }


function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) {
        arr[i] = 0;
    }
    return arr;
    }

function setChart(xArr,param,jsonStr) {
    $('#trend-chart').highcharts({
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: ''
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
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: jsonStr
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
                $('#id_all_app').html($.i18n.prop('id_all_app'));
                $('#id_new_user').val($.i18n.prop('id_new_user'));
                $('#id_new_user_copy').html($.i18n.prop('id_new_user_copy'));
                $('#id_payed_order').val($.i18n.prop('id_payed_order'));
                $('#id_payed_order_copy').html($.i18n.prop('id_payed_order_copy'));
                $('#id_total_income').val($.i18n.prop('id_total_income'));
                $('#id_total_income_copy').html($.i18n.prop('id_total_income_copy'));
                $('#id_data_details').html($.i18n.prop('id_data_details'));
                $('#id_main_channel').html($.i18n.prop('id_main_channel'));
            }
        });
}
