$(document).ready(function () {
//	showModal();
	getuserinfo();
    $("#id_confirm").click(function(){
    	showModal();
        getData();
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
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
        			$("#UserInfo").text(data.user.username);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				addMenuByCookie();
//					    getData();
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
//var dateCount = 0;
var daySeconds = 24*3600;
var endDate = 0;
var ajax;
function getData() {
	var tzo = getTZO();
	var id = parseInt($("#selectCollection").val());
	console.log("id---"+id);
    var offset = getTimeOffsetById(id);
	var start = new Date($('#startDate').val().replace(/-/g,"/"));
	var startTime = start.getTime()/1000 + (offset+tzo)*3600;
	var end = new Date($('#endDate').val().replace(/-/g,"/"));
	var endTime = end.getTime()/1000 + (offset+tzo)*3600;
    var path = getServerPath() + "app/user_retention?app=" + id + "&start=" + startTime + "&end=" + endTime;
      console.log(path);
    anchorTime = startTime;
    endDate = endTime;
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
var cMap = {};
var tableStr = '';
function setData(data) {
    var channelMap = {};
    var chartTime = 0;//只记录最大的date
    var computetDate;
	$.each(data.channels, function(Index,item) {
		var computetDate = item.date;
		console.log("computetdate"+computetDate);
		var newUser = item.new_user;
		if(chartTime<item.date){
			chartTime = item.date;
		}
	    var ch = item.channel;
	    //console.log("*&^"+item.ret);
	    var dateMap = channelMap[ch];
	    var tTime = new Date(computetDate*1000).Format("yyyy-MM-dd");
	    if(dateMap == undefined || dateMap == null || dateMap == '') {
                dateMap = {};
          }
	    
	    //console.log("dateMap"+dateMap)
	    $.each(item.ret, function(key,value) {
	    	//console.log("item.ret****"+item.ret[key]);
	    	$.each(item.ret[key], function(key,value) {
	    		var time = key.split(":")[1];
	    	 if(time > anchorTime) {
                //console.log("anchor = "+anchorTime+", time = "+time);
                var valIndex = Math.round((time-computetDate)/daySeconds)-1;//只能计算2天前的注册用户留存                  
                var dateStr = new Date(time*1000).Format("yyyy-MM-dd");
	            var valArr = dateMap[tTime];//得到后更新
	            if(valArr == undefined || valArr == null || valArr == '') {
				    valArr = [];
				}
	            valArr[valIndex] = value+":"+newUser;
                dateMap[tTime] = valArr;
                }
	    	});
	    });
	    channelMap[ch] = dateMap;
	    //console.log(dateMap);
      //console.log(channelMap);
	});
    
    var xArr = [];
    var arr = [];
    var aarr = [];
    var i = 0;
    var title = '';
	$.each(channelMap,function(key,value){
		if(value == null) {
			console.log("value is null");
		} else {
			title = new Date((chartTime) * 1000).Format("yyyy-MM-dd");
			console.log("chartTime:"+chartTime+"%%%%%"+title+"value"+value[chartTime]);
			var lastData = 0;

            if(value[title] != undefined) {
                xArr[i] = key;
                if(value[title][0]!=undefined){
    	        var lastRet = value[title][0].split(":")[0];
    	        }
    	        if(lastRet==""||lastRet==null||lastRet==undefined){
    	        	lastRet = parseInt(lastRet);
    	        	lastRet = 0;
    	        	lastData = 0;
    	        }
    	         if(value[title][0]!=undefined){
    	        var lastNewUser = value[title][0].split(":")[1];
    	        }
    	        if(lastNewUser==""||lastNewUser==null||lastNewUser==undefined){
    	        	lastNewUser = parseInt(lastNewUser);
    	        	lastNewUser = 0;
    	        }else{
    	        	lastRet = parseInt(lastRet);
    	        	lastNewUser = parseInt(lastNewUser);
    	        	if(lastNewUser==0){
    	        		lastData = 0;
    	        	}else{
    	            lastData = lastRet/lastNewUser;
    	           }
    	        }
    	        arr[i] = lastData*100+":"+lastRet+":"+lastNewUser;
    	        aarr[i] = lastData*100;
    	        i++;
            }else{
            	title = new Date((chartTime - daySeconds) * 1000).Format("yyyy-MM-dd");
            	xArr[i] = key;
            	if(value[title]==undefined){
            		lastData = 0;
            	}else{
            		var lastRet = value[title][0].split(":")[0];
    	        if(lastRet==""||lastRet==null||lastRet==undefined){
    	        	lastRet = parseInt(lastRet);
    	        	lastRet = 0;
    	        	lastData = 0;
    	        }
    	        var lastNewUser = value[title][0].split(":")[1];
    	        if(lastNewUser==""||lastNewUser==null||lastNewUser==undefined){
    	        	lastNewUser = parseInt(lastNewUser);
    	        	lastNewUser = 0;
    	        	lastData = 0;
    	        }else{
    	        	lastRet = parseInt(lastRet);
    	        	lastNewUser = parseInt(lastNewUser);
    	            if(lastNewUser==0){
    	        		lastData = 0;
    	        	}else{
    	            lastData = lastRet/lastNewUser;
    	           }
    	        }
            	}
    	        arr[i] = lastData*100+":"+lastRet+":"+lastNewUser;
    	        aarr[i] = lastData*100;
    	        i++;
            }
        }
	});
    //console.log(arr);
    var dataStr = [{
            name: title,
            data: aarr
    }];
    setList(xArr[0],channelMap);
    setChart(xArr, dataStr, channelMap);
    cMap = channelMap;
    setDetailList(xArr,arr,jsonData);
    
}
function getInitialArray(size) {
    var arr = [];
    for(var i = 0; i < size; i++) { 
        arr[i] = 0;
    }
    return arr;
}

function setTable(param){
	var ch = $(param).attr("value");
	setList(ch,cMap);
}
function setDetailList(key, arr,data) {
//	$.each(Numap, function(k,v) {
//	console.log("chmapK:"+k+"chmapV"+v);
////		$.each(v, function(ke,va) {
////			console.log("ke"+ke+"va"+va);
////		});
//	});
	$("#showData").html("<thead><tr><th style='text-align: center;' id='id_channel_name'></th><th style='text-align: center;' id='id_reg_number'></th>"+
	"<th style='text-align: center;' id='id_seclogin_number'></th><th style='text-align: center;' id='id_secday_retention_copy'></th>"+
	"</tr></thead><tbody id='detailbody'></tbody>");
    //次日留存的列表展现形式
   var str='';
    for(var i = 0; i < key.length; i++) {
    	//console.log("arr"+arr[i]);
    	var log = arr[i].split(":")[1];
    	if(log==null||log=="undefined"||log==''){
    		//console.log("what is wrong?")
    		log = parseInt(log);
    		log = 0;
    	}
    	var nUser = arr[i].split(":")[2];
    	if(nUser==null||nUser=="undefined"||nUser==''){
    		nUser = parseInt(nUser);
    		nUser = 0;
    	}
    	var ret = arr[i].split(":")[0];
        str += '<tr><td style = "overflow:hidden;text-align:center"><a href="#jump" title="'+key[i]+'" value="'+key[i]+'" onclick="setTable(this)">'+key[i]+'</a></td>';

//      if(arr[i] == 'NaN'){
//          str += '<td></td></tr>';
//      }else{
	        str += '<td style="text-align: center;">'+nUser+'</td>';
	        str += '<td style="text-align: center;">'+log+'</td>';
            str += '<td style="text-align: center;">'+parseFloat(ret).toFixed(2)+'%</td></tr>';
//       }
    }
    //str += '</tr>';
    $("#detailbody").html(str); 
    getDatatabel();
    loadProperties();
	tableStr = '<table><tr><th>'+getExcelTitle("id_channel_name")+'</th><th>'+getExcelTitle("id_reg_number")+'</th>'+
	           '<th id="">'+getExcelTitle("id_seclogin_number")+'</th><th id="">'+getExcelTitle("id_secday_retention_copy")+'</th></tr>';
    tableStr +=str;
    hideModal();
}

function setList(ch, chMap) {
    var dateMap = chMap[ch];
    if(dateMap == undefined || dateMap == null || dateMap == '') {
        //console.log("undefined");
        $('#listheader').text('No data to display');

    } else {
        //填充列表
        var str;
        var arrMap = [];
		$.each(dateMap, function(key,value) {
            //console.log(date +", value = "+value+"dateMap"+dateMap);
			arrMap[arrMap.length] = key;
			//console.log("&&"+arrMap.length+"key"+key);
		});
		arrMap.sort();
		
		$.each(arrMap, function(index,key) {
			var subMap = dateMap[key];
			str += '<tr><td>'+key+'</td>';
			//console.log("!!!!"+key);
			$.each(subMap, function(dx,val) {
				//console.log("^^%^^"+val);
				var log = val.split(":")[0];
				if(log==null||log==undefined||log==""){
					log = parseInt(log);
					log = 0;
				}
				var newUser = val.split(":")[1];
				if(newUser==null||newUser==undefined||newUser==""){
					newUser = parseInt(newUser);
					newUser = 0;
				}
				if(newUser==0){
					var ret = 0;
				}else{
				   ret = log/newUser;					
				}
				var num = parseFloat(ret*100).toFixed(2);
				if(num == 'NaN'){
					str += '<td></td>';
				}else{
					str += '<td>'+parseFloat(ret*100).toFixed(2) +'%</td>';
				}
			});
			str += '</tr>';
		});
		$("#tbody").html(str);
    }
    $('#listheader').text(ch);

}

function setChart(xArr, dataArr, chMap) {
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
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: dataArr
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
            	$('#id_secretention_detail').html($.i18n.prop('id_secretention_detail'));
            	$('#id_channel_name').html($.i18n.prop('id_channel_name'));
            	$('#id_reg_number').html($.i18n.prop('id_reg_number'));
            	$('#id_seclogin_number').html($.i18n.prop('id_seclogin_number'));
            	$('#id_secday_retention_copy').html($.i18n.prop('id_secday_retention_copy'));
            	$('#id_time').html($.i18n.prop('id_time'));
            	$('#id_second_day').html($.i18n.prop('id_second_day'));
            }
        });
}