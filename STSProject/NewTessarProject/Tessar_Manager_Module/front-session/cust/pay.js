 $(document).ready(function () {
// 	showModal();
	getuserinfo();
	
	$("#id_confirm").click(function(){
    	showModal();
        //getData(getParam());
		/* getData("new_user",jsonData); */
		var name = parent.window.document.getElementById("hide").value;
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
					   // getData('payer');
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
var Count = 0;
var ajax;
function getData(param) {
	var tzo = getTZO();
	var id = parseInt($('#selectCollection').val());
    //var id = getAppId($("#info").text());
    console.log("idid+++"+id);
    var offset = getTimeOffsetById(id);
    var start = new Date($('#startDate').val().replace(/-/g,"/"));
    var startTime = start.getTime()/1000 +(offset+tzo)*3600;
    var end = new Date($('#endDate').val().replace(/-/g,"/"));
    var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    anchorTime = startTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
    var path = getServerPath()+"main/pay?app=" + id + "&start="+startTime+"&end="+endTime;
    console.log("path = "+path+", dateCount = "+dateCount);

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
var newparam;
var tableStr = '';
function setData(param,data) {
	newparam = param;
    var jsonStr = [];
	var xArr = [];
    //console.log("length = "+data.channels.length);
	var title;
            switch(param) {
			    case 'payer':
			        title = $("#id_payer").val();
			        break
			    case 'new_payer':
			        title = $("#id_new_payer").val();
			        break
			    case 'income':
			    	title = $("#id_total_income").val();
			    	break
    		}
            $("#h2").text(title);
            $.each(data.apps, function(index,channel) {
    	     if(channel.details.length>Count){
    	     	Count = channel.details.length;
    	     }
            });
    for(var i = 0; i < Count; i++) {
        xArr[i] = new Date((anchorTime + i*daySeconds)*1000).Format("yyyy-MM-dd");
    }
    var map = {};
    var str="";
    $.each(data.apps, function(index,app) {
	var dataArr = [];
    var aps = getAppName(app['app']);
    dataArr = map[aps];
    	if(dataArr == undefined || dataArr == null || dataArr == '') {
              dataArr = getInitialArray(Count);
            }
    	
    	//下拉框判断表格显示
    	$.each(app.details, function(index,item) {
    	var idx = parseInt((item.date - anchorTime)/daySeconds);
    	if(param=='income'){
		dataArr[idx] =parseFloat(item[param].toFixed(2));    		
    	}else{
		dataArr[idx] = item[param];
    	}
		map[aps] = dataArr;
        $("#showData").html("<thead id='thead'><tr><th style='text-align: center;' id='id_calendar'></th><th style='text-align: center;' id='id_payer_copy'></th>"+
        "<th style='text-align: center;' id='id_new_payer_copy'></th><th style='text-align: center;' id='id_total_income_copy'></th></tr></thead><tbody id='tbody'></tbody>");
		str += '<tr>';
		str += '<td style="text-align: center;">' + new Date(item.date*1000).Format("yy-MM-dd") + '</td>';
		//console.log(new Date(item.date*1000).Format("yy-MM-dd"));
		str += '<td style="text-align: center;">' + item.payer + '</td>';
		str += '<td style="text-align: center;">' + item.new_payer + '</td>';
		str += '<td style="text-align: center;">' + isZero((parseFloat(item.income).toFixed(2)))+ '</td>';
		str += '</tr>';
    	});
		$("#tbody").html(str);
	    getDatatabel();	
    	//console.log(getAppName(app.app)+"+_+_+_:"+dataArr+"))(____"+xArr);
    });
   var i = 0;
    $.each(map, function(key, value) {
        var num = 0;
//      for(var idx = 0; idx < map[key].length; idx++ ) {
//          num  = num + map[key][idx];
//      }
//      if(num == 0) {
//          //console.log(key+", num = "+num);
//      } else {
            jsonStr[i] = {
                name: key,
                data:map[key]
          }
            i++;
            //console.log("*****i"+i)
//        }
    });
    setChart(title,xArr,jsonStr);
    loadProperties();
	tableStr = '<table><tr><th>'+getExcelTitle("id_calendar")+'</th><th>'+getExcelTitle("id_payer_copy")+'</th>'+
	'<th>'+getExcelTitle("id_new_payer_copy")+'</th><th>'+getExcelTitle("id_total_income_copy")+'</th></tr>';
    tableStr += str;
	hideModal();
	Count = 0;
}

function selectAppShow(){
    showModal();
    //console.log("test:"+selectApp);
    getData(newparam);
    }

function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) {
        arr[i] = 0;
    }
    return arr;
    }

function setChart(param,xArr,jsonStr) {
	//console.log("title:"+param+"xArr"+xArr+"yArr"+jsonStr);
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
                text: param
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
                $('#id_payer').val($.i18n.prop('id_payer'));
                $('#id_payer_copy').html($.i18n.prop('id_payer_copy'));
                $('#id_new_payer').val($.i18n.prop('id_new_payer'));
                $('#id_new_payer_copy').html($.i18n.prop('id_new_payer_copy'));
                $('#id_total_income').val($.i18n.prop('id_total_income'));
                $('#id_total_income_copy').html($.i18n.prop('id_total_income_copy'));
                $('#id_data_details').html($.i18n.prop('id_data_details'));
                $('#id_calendar').html($.i18n.prop('id_calendar'));
            }
        });
}

