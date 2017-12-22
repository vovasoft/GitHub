
function remCss(){
	$(".common").text("");
	$(".common").css("display","none");
}

function adduser(){
	remCss();
	var username = $("#username").val();
	if(! (validate(username,'username'))){
		return false;
	}
	
	var password = $("#password").val();
	if(! (validate(password,'password'))){
		return false;
	}
	
	var rePassword = $("#rePassword").val();
	if(! (validate(rePassword,'Confirm password'))){
		return false;
	}
	
	if(password != rePassword){
		$(".common").text("password and Confirm password is not equeals");
		$(".common").css("display","block");
		return false;
	}
	var email = $("#email").val();
	if(! (validate(email,'email'))){
		return false;
	}
	
	if(!filter(email)){
		return false;
	}
	password = convernMD5(password);
	
	var skype = $("#skype").val();
	var status = $("input[name=soptions]:checked").val();
	var group = $("input[name=options]:checked").val();
	var addtime = parseInt((new Date().getTime())/1000);
	var path = getServerPath()+"user/user_reg?username="+
    username+"&password="+password+"&email="+email+"&skype="+skype+"&group="+group+
    "&status="+status+"&addtime="+addtime+"&changetime="+addtime;
    $.ajax({
        type: "GET",
        url: path,
        dataType: "jsonp",
        jsonpCallback: "apiStatus",
        success: function(data) {
        	//console.log("flag:"+data.flag);
        	switch(data.flag) {
			   	case 102:
	  			$('#myModal').modal('hide');
	  			showUsers();
			   	break
			   	case 103:
			   	$(".common").text("username has exist");
				$(".common").css("display","block");
				return false;
			   	break
			   	case 104:
			   	$(".common").text("add user failure, please try again later");
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

function remData(){
	$("#username").val("");
	$("#password").val("");
	$("#rePassword").val("");
	$("#email").val("");
	$("#skype").val("");
	remCss();
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
//		$('#myModal').modal('show');
		return false;
	}else{
		return true;
	}
}

function filterEmail(email){
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+)+((\.[a-zA-Z0-9]+){0,4})+$/;
    if (!filter.test(email)){
    	$(".common").text("Mailbox format is not correct (example:xx@xx  /  xx@xx.xx)");
		$(".common").css("display","block");
          return false;
    }else{
    	return true;
    }
}
