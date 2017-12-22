$(document).ready(function(){
	$(".commo").css("display", "block");
})
function login(){
	/*
	var path = "http://192.168.1.121:8080/stats/user/session_test";
	$.ajax({
		type: "GET",
        url: "http://",
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	//console.log("data.username:"+data.username)
        }
	});*/
	$(".common").text("");
	$(".common").css("display","none");
	var username = $("#username").val();
	if(! (validate(username,'username'))){
		$("#username").parent().addClass("has-error");
		return false;
	}
	$("#username").parent().removeClass("has-error");
	var password = $("#password").val();
	if(! (validate(password,'password'))){
		$("#password").parent().addClass("has-error");
		return false;
	}
	$("#password").parent().removeClass("has-error");
	
	password = convernMD5(password);
	
    var path = getServerPath()+"user/user_login?username="+username+"&password="+password;
    $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	//console.log("data:"+data);
        	switch(data.flag) {
			   	case 202:
			   	if(data.group=="xcloud"){
			   	window.location.href = "index.html";
			   	}else{
			   		window.location.href = "personal.html";
			   	}
			   	break
			   	case 203:
			   	$(".common").text("username is not exist");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 204:
			   	$(".common").text("username or password is not correct");
				$(".common").css("display","block");
				return false;
			   	break
			}
        },
		error: function(data){
			$(".common").text("Server exception, please try again later");
			$(".common").css("display","block");
		}
    });
}

function keyDown(e) {
	var ev = window.event || e;
	if (ev.keyCode == 13) {
		login();
	}
}
function filterUserName(){
	remCss();
	var username = $("#username").val();
	if(! (validate(username,'username'))){
		$("#username").parent().addClass("has-error");
		return false;
	}
    $("#username").parent().removeClass("has-error");
    return true;
}

function filterPassword(){
	var us = filterUserName();
	if(!us){
		return false;
	}
	var password = $("#password").val();
	if(! (validate(password,'password'))){
		$("#password").parent().addClass("has-error");
		return false;
	}
    $("#password").parent().removeClass("has-error");
    return true;
}

function remCss(){
	$(".common").text("");
	$(".common").css("display","none");
	
}

function convernMD5(param){
	var key_hash = CryptoJS.MD5("1234567812345678");
    var key = CryptoJS.enc.Utf8.parse(key_hash);
    var iv  = CryptoJS.enc.Utf8.parse('8765432187654321');
    param = CryptoJS.AES.encrypt(param, key,
    	{ iv: iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
    return param;
}

function validate(param,str){
	if(param == '' || param == null || param == 'null'){
		$(".common").text(str + " should not be null");
		$(".common").css("display","block");
		return false;
	}else{
		return true;
	}
}
function loadPropertiesLogin() {
        var lan = jQuery.i18n.browserLang();
        console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
                $('#id_login').html($.i18n.prop('id_login'));
                $('#id_login_copy').html($.i18n.prop('id_login_copy'));
                $('#id_login_info').html($.i18n.prop('id_login_info'));
                $('#id_forget_password').html($.i18n.prop('id_forget_password'));
                $('#id_signup').html($.i18n.prop('id_signup'));
            }
        });
}
