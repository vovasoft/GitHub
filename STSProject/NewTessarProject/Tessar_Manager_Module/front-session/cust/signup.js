$(document).ready(function(){
	$(".commo").css("display", "block");
})
function reg(){
	$(".common").text("");
	$(".common").css("display","none");
	var username = $("#username").val();
	/*var us = filterUserName();
	if(!us){
		return false;
	}*/
	var password = $("#password").val();
	/*var psw = filterPassword();
	if(!psw){
		return false;
	}*/
	var rePassword = $("#rePassword").val();
	/*var repsw = filterRePassword();
	if(!repsw){
		return false;
	}*/
	var email = $("#email").val();
	if(!filter(email)){
		return false;
	}
	password = convernMD5(password);
	var addtime = parseInt(new Date().getTime()/1000);
	var skype = $("#skype").val();
    var path = getServerPath()+"user/user_reg?username="+
    username+"&password="+password+"&email="+email+"&skype="+skype+
    "&group=ad&status=1&addtime="+addtime+"&changetime="+addtime;
    //console.log("path:"+path);
    $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	//console.log("flag:"+data.flag);
        	switch(data.flag) {
			   	case 102:
			   	parent.location.href = "login.html"
			   	break
			   	case 103:
			   	$(".common").text("username has exist");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 104:
			   	$(".common").text("Regist failure, please try again later");
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
		reg();
	}
}

function remCss(){
	$(".common").text("");
	$(".common").css("display","none");
	
}

function filterUserName(){
	var username = $("#username").val();
	if(! (validate(username,'username'))){
		$("#username").parent().addClass("has-error");
		return false;
	}
	var filterUserName  = /^([a-zA-Z0-9_])+$/;
    if (!filterUserName.test(username)){
    	$("#username").parent().addClass("has-error");
    	$(".common").text("Username should use 'a-z', 'A-Z', 0-9 and '_'");
		$(".common").css("display","block");
    	return false;
    }
      remCss();
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
	var filterpassword  = /^[^\s]{6,20}$/;
    if (!filterpassword.test(password)){
    	$("#password").parent().addClass("has-error");
    	$(".common").text("Password length should between 6 - 20, NO SPACE.");
		$(".common").css("display","block");
    	return false;
    }
      remCss();
    $("#password").parent().removeClass("has-error");
    return true;
}

function filterRePassword(){
	var us = filterUserName();
	if(!us){
		return false;
	}
	var psw = filterPassword();
	if(!psw){
		return false;
	}
	var password = $("#password").val();
	var rePassword = $("#rePassword").val();
	if(! (validate(rePassword,'Confirm password'))){
		$("#password").parent().addClass("has-error");
		return false;
	}
	var filterpassword  = /^[^\s]{6,20}$/;
	if (!filterpassword.test(rePassword)){
		$("#rePassword").parent().addClass("has-error");
    	$(".common").text("RePassword length should between 6 - 20, NO SPACE.");
		$(".common").css("display","block");
    	return false;
    }
	if(password != rePassword){
		$("#password").parent().addClass("has-error");
		$("#rePassword").parent().addClass("has-error");
		$(".common").text("password and Confirm password is not equeals");
		$(".common").css("display","block");
		return false;
	}
	remCss();
	$("#password").parent().removeClass("has-error");
	$("#rePassword").parent().removeClass("has-error");
	return true;
}

function convernMD5(param){
	var key_hash = CryptoJS.MD5("1234567812345678");
    var key = CryptoJS.enc.Utf8.parse(key_hash);
    var iv  = CryptoJS.enc.Utf8.parse('8765432187654321');
    var password = CryptoJS.AES.encrypt(param, key,
    	{ iv: iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
    return password;
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

function filter(email){
	var us = filterUserName();
	if(!us){
		return false;
	}
	var psw = filterPassword();
	if(!psw){
		return false;
	}
	var repsw = filterRePassword();
	if(!repsw){
		return false;
	}
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+)+((\.[a-zA-Z0-9]+){0,4})+$/;
    if (!filter.test(email)){
    	$("#email").parent().addClass("has-error");
    	$(".common").text("Mailbox format is not correct (example:xx@xx / xx@xx.xx)");
		$(".common").css("display","block");
          return false;
    }else{
      	remCss();
		$("#email").parent().removeClass("has-error");
    	return true;
    }
}
function loadPropertiesSignup() {
        var lan = jQuery.i18n.browserLang();
        console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
                $('#id_signup').html($.i18n.prop('id_signup'));
                $('#id_signup_copy').html($.i18n.prop('id_signup_copy'));
                $('#id_signup_info').html($.i18n.prop('id_signup_info'));
                $('#id_forget_password').html($.i18n.prop('id_forget_password'));
                $('#id_login').html($.i18n.prop('id_login'));
            }
        });
}
