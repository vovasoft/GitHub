$(document).ready(function(){
	getuserinfo();
	loadProperties();
})

function getuserinfo(){
	var sessionid = $.cookie("sessionid");
	console.log("personal==="+sessionid);
	if(sessionid ==null&&sessionid==""){
		parent.location.href="login.html";
	}else
	var path = getServerPath()+"user/session?sessionid="+sessionid;
	console.log(path+"----");
	$.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	console.log("user"+data.user);
        	switch(data.flag){
        		case 502:
        		if(data.user != null){
    				$("#Fusername").text(data.user.username);
    				$("#UserInfo").text(data.user.username);
    				//$("#username").text(data.user.username);
        			$("#email").text(data.user.email);
        			$("#group").text(data.user.group);
        			$("#skype").text(data.user.skype);
        			if (data.user.group == "admin" || data.user.group == "xcloud"){
        				$("#menu").css("display","block");
        				//addMenuByCookie();
						if(data.user.group == "admin"){
							$("#user_admin").css("display","block");
						}
					}
        			$("body").css("display","block");
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
function remCss(){
	$(".common").text("");
	$(".common").css("display","none");
	
}
function changePassword(){
	
	$(".common").text("");
	$(".common").css("display","none");
	var username = $("#Fusername").text();
	if(! (validate(username,'username'))){
		return false;
	}
	
	var oldPassword = $("#oldPassword").val();
	if(! (validate(oldPassword,'Old Password'))){
		return false;
	}
	
	var new_password = $("#new_password").val();
	if(! (validate(new_password,'New Password'))){
		return false;
	}
	
	var con_new_password = $("#con_new_password").val();
	if(! (validate(con_new_password,'Confirm New Password'))){
		return false;
	}
	
	if(new_password != con_new_password){
		$(".common").text("New Password and Confirm New Password is not equeals");
		$(".common").css("display","block");
		return false;
	}
	oldPassword = convernMD5(oldPassword);
	new_password = convernMD5(new_password);
	
	var path  = getServerPath()+"user/change_pwd?username="+username+"&oldpassword="+oldPassword+
	"&newpassword="+new_password;
	$.ajax({
	        type: "GET",
	        url: path,
	        dataType: "jsonp",
	        jsonpCallback: "apiStatus",
	        success: function(data) {
				switch(data.flag) {
			   	case 302:
			   	$("#oldPassword").val("");
			   	$("#new_password").val("");
			   	$("#con_new_password").val("");
			   	$("#myTab a:first").tab("show");
			   	alert("Password changed");
			   	break
			   	case 303:
			   	$(".common").text("User is not exist");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 304:
			   	$(".common").text("Change password failed");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 305:
			   	$(".common").text("Username or password is null");
				$(".common").css("display","block");
				return false;
			   	break
			}
	        }
	    });
}

function filterPassword(){
	var password = $("#new_password").val();
	if(! (validate(password,'password'))){
		$("#new_password").parent().addClass("has-error");
		return false;
	}
	var filterpassword  = /^[^\s]{6,20}$/;
    if (!filterpassword.test(password)){
    	$("#new_password").parent().addClass("has-error");
    	$(".common").text("Password length should between 6 - 20, NO SPACE.");
		$(".common").css("display","block");
    	return false;
    }
      remCss();
    $("#new_password").parent().removeClass("has-error");
    return true;
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
function loadProperties() {
        var lan = jQuery.i18n.browserLang();
        console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
                $('#id_personal_info').append($.i18n.prop('id_personal_info'));
                $('#id_information').html($.i18n.prop('id_information'));
                $('#id_change_pwd').html($.i18n.prop('id_change_pwd'));
                $('#id_user_en').html($.i18n.prop('id_user_en'));
                $('#id_email').html($.i18n.prop('id_email'));
                $('#id_group').html($.i18n.prop('id_group'));
                $('#id_skype').html($.i18n.prop('id_skype'));
				$('#id_submit').html($.i18n.prop('id_submit'));
            }
        });
}
