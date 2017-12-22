	$(document).ready(function(){
		showModal();
		getuserinfo();

    $("#id_confirm").click(function(){
    	showModal();
        getData();
    });

    $("#id_new_user_copy").click(function() {
    	showModal();
        setChartWithExistData('new_user', jsonData);
    });
    $("#id_active_user_copy").click(function() {
    	showModal();
        setChartWithExistData('dau', jsonData);
    });
    $("#id_active_compose").click(function() {
    	showModal();
        setChartWithExistData('active_component', jsonData);
    });

    $("#id_payer_copy").click(function() {
    	showModal();
        setChartWithExistData('payer', jsonData);
    });
    $("#id_payed_money").click(function() {
    	showModal();
        setChartWithExistData('income', jsonData);
    });
    $("#id_new_payer").click(function() {
    	showModal();
        setChartWithExistData('new_payer', jsonData);
    });
    $("#pay_rate").click(function() {
    	showModal();
        setChartWithExistData('pay_rate', jsonData);
    });  
});
var anchorTime = 0;
var dateCount = 0;
var daySeconds = 24*3600;
var Count = 0;
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
        				getData();
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

function getTitle(param) {
	var title = null;
    switch(param) {
    case 'new_user':
        title = $('#id_new_user_copy').val();
        break
    case 'dau':
        title = $('#id_active_user_copy').val();
        break
    case 'active_component':
        title = $('#id_active_compose').val();
        break
    case 'payer':
    	title = $('#id_payer_copy').val();
    	break
    case 'income':
        title = $('#id_payed_money').val();
        break
    case 'new_payer':
        title = $('#id_new_payer').val();
        break

    }

    return title;
}

function setChartWithExistData(param, data) {
	var dataPool = null;
	var chartObject = null;
	var isColumnChart = (param == 'active_component' ? true : false);
	//if(data != null) {
		//try{
            switch(param) {
            case 'new_user':
            case 'dau':
            case 'active_component':
                chartObject = $("#gneric-chart");
            	dataPool = data.trend;
            	break;
            case 'payer':
            case 'income':
            case 'new_payer':
            case 'pay_rate':
                chartObject = $("#detail-chart");
                dataPool = data.detail;
            }
			Count = dataPool.length;
			var xArr = [];//x轴
			for(var i = 0; i < Count; i++) {
        xArr[i] = new Date((anchorTime + i*daySeconds)*1000).Format("yyyy-MM-dd");
    }
			var dataArr = [];
			var oldArr = [];
			var newArr = [];
			var datamap = {};
			dataPool = dataPool.sort(function(a,b){
				return a.date - b.date;
			});
			newArr = getInitialArray(Count);
			oldArr = getInitialArray(Count);
			$.each(dataPool, function(index, item) {
				
				//var dateTime = new Date(item.date*1000).Format("yyyy-MM-dd");
				
				//xArr[index] = dateTime;
			    var idx = parseInt((item.date - anchorTime)/daySeconds);
			    dataArr = datamap[xArr];
				if(dataArr == undefined || dataArr == null || dataArr == '') {
              dataArr = getInitialArray(Count);
              
            }
				//console.log(xArr+"**"+idx+"$$"+dataArr);
			    
				if(isColumnChart){
					newArr[idx] = item.new_user;
					oldArr[idx] = item.dau-item.new_user;
				}else{
                    var value = item[param];
                    if(param == 'income') {
                        value = parseFloat(value.toFixed(2));
                    } 
					dataArr[idx] = value;
					datamap[xArr] = dataArr;
				}
				
			});
			
			if(isColumnChart) {
				var jsonStr = [{
					name: $('#id_new_user_copy').val(),
					data: newArr
					},
					{
						name:$('#id_old_user').val(),
						data:oldArr
					}
					];
	            setStackColumnChart(chartObject, getTitle(param), xArr, jsonStr);
			} else {
				var value = '';
				if(param == 'income'){
					value = '';
				}
				var jsonStr = [{
					name: getTitle(param),
					data: dataArr
				}];
	            setListChart(chartObject,value, getTitle(param), xArr, jsonStr);
			}

	
	 var i = 0;
    $.each(datamap, function(key, value) {
        //console.log("!!!!!!!!!key = "+key+", value = "+value);
        var num = 0;
        //console.log("map[key]"+datamap[key]);
        for(var idx = 0; idx < datamap[key].length; idx++ ) {
            num  = num + datamap[key][idx];
        }
        if(num == 0) {
            //console.log(key+", num = "+num);
        } else {
            jsonStr[i] = {
                name: key,
                data:datamap[key]
            }
            i++;
            //console.log("*****i"+i)
        }

    });
	
   hideModal();
   Count = 0;
}

function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) {
        arr[i] = 0;
    }
    return arr;
}

function setData(data) {
	//set generic data
	var total = data.generic.total;
	$("#total_user").text(total);
	$("#user_wau").text(data.generic.wau);
	$("#user_mau").text(data.generic.mau);
	var payer = data.generic.total_payer;
	
	$("#total_payer").text(data.generic.total_payer);
	$("#total_income").text(parseFloat(data.generic.income).toFixed(2));
	$("#pay_rate").text(parseFloat(payer/total*100).toFixed(2)+"%");
	$("#pay_count").text(data.generic.pay_count);
	$("#arpu").text(parseFloat(data.generic.arpu).toFixed(2));
	$("#arppu").text(parseFloat(data.generic.arppu).toFixed(2));

	//set trend data
	setChartWithExistData('new_user', data);
	//set detail data
	setChartWithExistData('payer', data);
	loadProperties();
	hideModal();
}
var ajax;
function getData() {
	var tzo = getTZO();
    var id = getAppId($("#info").text());
    var offset = getTimeOffsetById(id);
	var start = new Date($('#startDate').val().replace(/-/g,"/"));
	var startTime = start.getTime()/1000 +(offset+tzo)*3600;
	var end = new Date($('#endDate').val().replace(/-/g,"/"));
	var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    var id = getAppId($("#info").text());
    anchorTime = startTime;
    dateCount = (endTime - startTime)/daySeconds + 1;
	var path = getServerPath()+"app/app_trend?app="+id+"&"+"start="+startTime+"&end="+endTime;
	//console.log(path);
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

function setListChart(object, value,title, xArr, yArr) {
	object.highcharts({
        chart: {
            type: 'line',
            zoomType: 'x'
        },
        title: {
            text: title
        },
        xAxis: {
            categories: xArr
        },
        yAxis: {
            title: {
                text: value
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
        series: yArr
    });
}

function setStackColumnChart(object, title, xArr, yArr) {
	
	object.highcharts({
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: title
        },
        xAxis: {
            categories: xArr
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    'Total: ' + this.point.stackTotal;
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        series: yArr
    });
    
}

function setColumnChart(object, title, xArr, yArr) {
	object.highcharts({
        chart: {
            type: 'column',
            zoomType: 'x'
        },
        title: {
            text: title
        },
        xAxis: {
            categories: xArr
        },
        credits: {
            enabled: false
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
                $('#id_total_user').html($.i18n.prop('id_total_user'));
                $('#id_week_active').html($.i18n.prop('id_week_active'));
                $('#id_month_active').html($.i18n.prop('id_month_active'));
                $('#id_total_payer').html($.i18n.prop('id_total_payer'));
                $('#id_total_income').html($.i18n.prop('id_total_income'));
                $('#id_payed_rate').html($.i18n.prop('id_payed_rate'));
                $('#id_arpu').html($.i18n.prop('id_arpu'));
                $('#id_arppu').html($.i18n.prop('id_arppu'));
                $('#id_new_user_copy').val($.i18n.prop('id_new_user_copy'));
                $('#id_active_user_copy').val($.i18n.prop('id_active_user_copy'));
                $('#id_active_compose').val($.i18n.prop('id_active_compose'));
                $('#id_payer_copy').val($.i18n.prop('id_payer_copy'));
                $('#id_payed_money').val($.i18n.prop('id_payed_money'));
                $('#id_new_payer').val($.i18n.prop('id_new_payer'));
                $('#id_old_user').val($.i18n.prop('id_old_user'));
            }
        });
}