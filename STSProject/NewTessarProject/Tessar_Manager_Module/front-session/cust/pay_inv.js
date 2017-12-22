$(document).ready(function () {
  	getuserinfo();
    $("#confirm").click(function(){
        //getData();
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
	        			getData();
	    				setTrendChart('day_payer');
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

function getData() {
    var id = getAppId($("#info").text());
	var path = getServerPath()+"app/user_active?app="+id+"&";
	var start = new Date($('#startDate').val());
    var offset = start.getTimezoneOffset();
	var startTime = start.getTime()/1000 + offset*60;
	var end = new Date($('#endDate').val());
	var endTime = end.getTime()/1000 + offset*60;

	$.ajax({
		type: "GET",
		url: path+"start="+startTime+"&end="+endTime,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {

			setData(data);
		}
	});
}

function getTitle(param) {
	var title = null;
    switch(param) {
    case 'day_payer':
    	title = "首日付费人数";
    	break
    case 'week_payer':
    	title = "首周付费人数";
    	break
    case 'month_payer':
    	title = "首月付费用户";
    	break
    }
    return title;
}

function setTrendChart(param) {
    $('#trend-chart').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: getTitle(param)
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
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
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
}
