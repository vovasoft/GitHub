$(document).ready(function() {
	showModal();
	getuserinfo();
})

function getuserinfo() {
	var sessionid = $.cookie("sessionid");
	if(sessionid ==null&&sessionid==""){
		parent.location.href="login.html";
	}else
	var path = getServerPath() + "user/session?sessionid=" + sessionid;
	$.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {
			switch (data.flag) {
				case 502:
					if (data.user != null) {
						$("#UserInfo").text(data.user.username);
						//addMenuByCookie();
						if (data.user.group == "admin") {
							$("#menu").css("display", "block");
							showUsers();
							$("body").css("display", "block");
						} else {
							parent.location.href = "login.html";
						}
					} else {
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
var ajax;

function showUsers() {
	var path = getServerPath() + "manage/manage_user?page=1";
	ajax = $.ajax({
		type: "GET",
		url: path,
		async: false,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {
			setData(data);
		}
	});
}
var tableStr = '';

function setData(data) {
	//console.log("data.detail:"+data.detail);
	if (data == "")
		return;
	var str = '';
	if (data.detail.length == 0 || data.detail == null) {
		$("#tbody").html("");
		return;
	}
	var detailArr = data.detail.sort(function(a, b) {
		return a.addtime - b.addtime;
	});
	$("#showData").html("<thead><tr><th style='text-align: center;' id='id_name'></th>" +
		"<th style='text-align: center;' id='id_reg_date'></th>" +
		"<th style='text-align: center;' id='id_group'></th>" +
		"<th style='text-align: center;' id='id_channels_copy'></th>" +
		"<th style='text-align: center;' id='id_total_reg'></th>" +
		"<th style='text-align: center;' id='id_payed_number'></th>" +
		"<th style='text-align: center;' id='id_status'></th>" +
		"<th style='text-align: center;' id='id_actions'></th></tr></thead><tbody id='tbody'></tbody>");
	$.each(detailArr, function(index, item) {
		if (item.username != "admin") {
			str += "<tr>"
			str += "<td style='text-align: center;' class=' sorting_1'>" + item.username + "</td>";
			str += "<td style='text-align: center;'>" + new Date(item.addtime * 1000).Format("yyyy-MM-dd hh:mm:ss") + "</td>";
			str += "<td style='text-align: center;'>" + item.group + "</td>";
			str += "<td style='text-align: center;'>" + item.ch_total + "</td>";
			str += "<td style='text-align: center;'>" + item.total + "</td>";
			str += "<td style='text-align: center;'>" + item.payed + "</td>";
			switch (item.status) {
				case 0:
					str += "<td style='text-align: center;'><span class='label-success label label-danger'>Banned</span></td>";
					break
				case 1:
					str += "<td style='text-align: center;'><span class='label-success label label-default'>Active</span></td>";
					break
			}
			str += "<td style='text-align: center;'><a class='btn btn-success btn-sm' onclick=view(" + item.uid + ")>" +
				"<i class='glyphicon glyphicon-zoom-in icon-white'></i> View</a> " +
				"<a id=" + item.uid + " class='btn btn-info btn-sm' href='#editModal' data-toggle='modal' data-backdrop='static'" +
				" onclick=edituser('" + item.uid + "','" + item.username +
				"'," + item.addtime + ",'" + item.group + "'," + item.ch_total + "," + item.total + "," +
				item.payed + ",'" + item.email + "'," + item.status + ")>" +
				"<i class='glyphicon glyphicon-edit icon-white'></i> Edit</a> " +
				"<a class='btn btn-danger btn-sm' onclick=deluser(" + item.uid + ",'" + item.username + "')>" +
				"<i class='glyphicon glyphicon-zoom-in icon-white'></i> Delete</a></td>";
			str += "</tr>";
		}
	});
	$("#tbody").html(str);
	getDatatabel();
	adminloadProperties();
	tableStr = '<table><tr><th>' + getExcelTitle("id_name") + '</th><th>' + getExcelTitle("id_reg_date") + '</th>' +
		'<th>' + getExcelTitle("id_group") + '</th><th>' + getExcelTitle("id_channels_copy") + '</th>' +
		'<th>' + getExcelTitle("id_total_reg") + '</th><th>' + getExcelTitle("id_payed_number") + '</th>' +
		'<th>' + getExcelTitle("id_status") + '</th><th>' + getExcelTitle("id_actions") + '</th></tr>';
	tableStr += str;
	hideModal();
}

var flag = 0;

function view(uid) {
	$("#id_edit").text("View");
	$("#eusername").attr("disabled", "disabled");
	$("#epassword").attr("disabled", "disabled");
	$("#erepassword").attr("disabled", "disabled");
	$("#eemail").attr("disabled", "disabled");
	$("#eskype").attr("disabled", "disabled");
	var esoptions = $("input[name=esoptions]");
	for (var i = 0; i < esoptions.length; i++) {
		$(esoptions[i]).attr("disabled", "disabled");
	}
	var eoptions = $("input[name=eoptions]");
	for (var i = 0; i < eoptions.length; i++) {
		$(eoptions[i]).attr("disabled", "disabled");
	}
	$("#saveuser").attr("disabled", "disabled");
	$("#saveuser").removeClass("btn btn-primary");
	$("#saveuser").addClass("btn btn-default");
	flag = 1;
	$("#" + uid).click();
}

function deluser(uid, username) {
	if (confirm("Delete user " + username + "  ?")) {
		var path = getServerPath() + "manage/delete_user?uid=" + uid;
		$.ajax({
			type: "GET",
			url: path,
			dataType: "jsonp",
			jsonpCallback: "apiStatus",
			success: function(data) {
				switch (data.flag) {
					case 802:
						//			   	window.location.reload();
						showUsers();
						break
					case 805:
						alert("delete user failed");
						return false;
						break
					case 806:
						alert("user is not exist");
						return false;
						break
				}
			}
		})
	}
}

function edituser(uid, username, addtime, group, ch_total, total, payed, email, status) {
	if (flag == 0) {
		$("#id_edit").text("Edit");
		$("#epassword").removeAttr("disabled");
		$("#erepassword").removeAttr("disabled");
		$("#eemail").removeAttr("disabled");
		$("#eskype").removeAttr("disabled");
		$("#saveuser").removeAttr("disabled");
		$("#saveuser").removeClass("btn btn-default");
		$("#saveuser").addClass("btn btn-primary");
		var esoptions = $("input[name=esoptions]");
		for (var i = 0; i < esoptions.length; i++) {
			$(esoptions[i]).removeAttr("disabled")
		}
		var eoptions = $("input[name=eoptions]");
		for (var i = 0; i < eoptions.length; i++) {
			$(eoptions[i]).removeAttr("disabled")
		}
	}
	$("#epassword").val("NANANA");
	$("#erepassword").val("NANANA");
	flag = 0;
	remCss();
	var time = new Date(addtime * 1000).Format("yyyy-MM-dd hh:mm:ss");
	$("#euid").val(uid);
	$("#eusername").val(username);
	$("#eaddTime").val(time);
	$("#ech_total").val(ch_total);
	$("#etotal").val(total);
	$("#eemail").val(email);
	$("#epayed").val(payed);
	switch (status) {
		case 0:
			$("#Banned").prop("checked", true);
			break
		case 1:
			$("#Active").prop("checked", true);
			break
	}
	switch (group) {
		case 'admin':
			$("#admin").prop("checked", true);
			break
		case 'xcloud':
			$("#xcloud").prop("checked", true);
			break
		case 'ad':
			$("#ad").prop("checked", true);
			break
		case 'personal':
			$("#personal").prop("checked", true);
			break
	}

}

function saveuser() {
	remCss();
	var uid = $("#euid").val();
	var username = $("#eusername").val();

	if (!(validate(username, 'username'))) {
		return false;
	}
	var password = $("#epassword").val();
	var repassword = $("#erepassword").val();
	if (password != repassword) {
		$(".common").text("password and Confirm password is not equeals");
		$(".common").css("display", "block");
		return false;
	}
	if (password == "" && repassword == "") {
		password = "NA";
	}
	var ch_total = $("#ech_total").val();
	var total = $("#etotal").val();
	var payed = $("#epayed").val();
	var email = $("#eemail").val();
	if (!(validate(email, 'email'))) {
		return false;
	}
	if (!filterEmail(email)) {
		return false;
	}
	if (password != "NANANA") {
		password = convernMD5(password);
	}
	var skype = $("#eskype").val();
	var status = $("input[name=esoptions]:checked").val();
	var group = $("input[name=eoptions]:checked").val();
	var changetime = parseInt(new Date().getTime() / 1000);
	var path = getServerPath() + "manage/edit_user?uid=" + uid + "&username=" + username + "&password=" + password +
		"&email=" + email + "&skype=" + skype + "&group=" + group + "&status=" + status + "&changetime=" + changetime;
	$.ajax({
		type: "GET",
		url: path,
		dataType: "jsonp",
		jsonpCallback: "apiStatus",
		success: function(data) {
			switch (data.flag) {
				case 402:
					$('#editModal').modal('hide');
					showUsers();
					getDatatabel();
					break
				case 403:
					$(".common").text("username has exist, please input again");
					$(".common").css("display", "block");
					return false;
					break
				case 405:
					$(".common").text("edit user failed");
					$(".common").css("display", "block");
					return false;
					break
			}
		}
	});

}

function filterEuserName() {
	var username = $("#eusername").val();
	if (!(validate(username, 'username'))) {
		$("#eusername").parent().addClass("has-error");
		return false;
	}
	var filterEuserName = /^([a-zA-Z0-9_])+$/;
	if (!filterEuserName.test(username)) {
		$("#eusername").parent().addClass("has-error");
		$(".common").text("Username should use 'a-z', 'A-Z', 0-9 and '_'");
		$(".common").css("display", "block");
		return false;
	}
	remCss();
	$("#eusername").parent().removeClass("has-error");
	return true;
}

function filterEpassword() {
	var password = $("#epassword").val();
	if (!(validate(password, 'password'))) {
		$("#epassword").parent().addClass("has-error");
		return false;
	}
	var filterEpassword = /^[^\s]{6,20}$/;
	if (!filterEpassword.test(password)) {
		$("#epassword").parent().addClass("has-error");
		$(".common").text("Password length should between 6 - 20, NO SPACE.");
		$(".common").css("display", "block");
		return false;
	}
	remCss();
	$("#epassword").parent().removeClass("has-error");
	return true;
}

function filterErePassword() {
//	var psw = filterEpassword();
//	if (!psw) {
//		return false;
//	}
	var password = $("#epassword").val();
	var rePassword = $("#erepassword").val();
	if (!(validate(rePassword, 'Confirm password'))) {
		$("#epassword").parent().addClass("has-error");
		return false;
	}
	var filterEpassword = /^[^\s]{6,20}$/;
	if (!filterEpassword.test(rePassword)) {
		$("#erepassword").parent().addClass("has-error");
		$(".common").text("RePassword length should between 6 - 20, NO SPACE.");
		$(".common").css("display", "block");
		return false;
	}
	if (password != rePassword) {
		$("#epassword").parent().addClass("has-error");
		$("#erepassword").parent().addClass("has-error");
		$(".common").text("password and Confirm password is not equeals");
		$(".common").css("display", "block");
		return false;
	}
	remCss();
	$("#epassword").parent().removeClass("has-error");
	$("#erepassword").parent().removeClass("has-error");
	return true;
}

function convernMD5(param) {
	var key_hash = CryptoJS.MD5("1234567812345678");
	var key = CryptoJS.enc.Utf8.parse(key_hash);
	var iv = CryptoJS.enc.Utf8.parse('8765432187654321');
	var password = CryptoJS.AES.encrypt(param, key, {
		iv: iv,
		mode: CryptoJS.mode.CBC,
		padding: CryptoJS.pad.ZeroPadding
	});
	return password;
}

function filterEemail(email) {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+)+((\.[a-zA-Z0-9]+){0,4})+$/;
	if (!filter.test(email)) {
		$(".common").text("Mailbox format is not correct (example:xx@xx  /  xx@xx.xx)");
		$(".common").css("display", "block");
		return false;
	} else {
		return true;
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function refuseData() {
	ajax.abort();
}

function adminloadProperties() {
	var lan = jQuery.i18n.browserLang();
	console.log(jQuery.i18n.browserLang());
	jQuery.i18n.properties({
		name: 'strings',
		path: './i18n/',
		mode: 'map',
		language: lan,
		encoding: "UTF-8",
		callback: function() {
			$('#id_add_user_copy').html($.i18n.prop('id_add_user_copy'));
			$('#id_select_status').html($.i18n.prop('id_select_status'));
			$('#id_active').html($.i18n.prop('id_active'));
			$('#id_banned').html($.i18n.prop('id_banned'));
			$('#id_select_group').html($.i18n.prop('id_select_group'));
			$('#id_admin').html($.i18n.prop('id_admin'));
			$('#id_xcloud').html($.i18n.prop('id_xcloud'));
			$('#id_ad').html($.i18n.prop('id_ad'));
			$('#id_personal_permission').html($.i18n.prop('id_personal_permission'));
			$('#id_cancel').html($.i18n.prop('id_cancel'));
			$('#id_save').html($.i18n.prop('id_save'));
			$('#id_user_list').html($.i18n.prop('id_user_list'));
			$('#id_name').html($.i18n.prop('id_name'));
			$('#id_reg_date').html($.i18n.prop('id_reg_date'));
			$('#id_group').html($.i18n.prop('id_group'));
			$('#id_channels_copy').html($.i18n.prop('id_channels_copy'));
			$('#id_total_reg').html($.i18n.prop('id_total_reg'));
			$('#id_payed_number').html($.i18n.prop('id_payed_number'));
			$('#id_status').html($.i18n.prop('id_status'));
			$('#id_actions').html($.i18n.prop('id_actions'));
			$('#id_edit').html($.i18n.prop('id_edit'));
			$('#id_uid').html($.i18n.prop('id_uid'));
			$('#id_user_en').html($.i18n.prop('id_user_en'));
			$('#id_new_pwd').html($.i18n.prop('id_new_pwd'));
			$('#id_confirm_pwd').html($.i18n.prop('id_confirm_pwd'));
			$('#id_add_date').html($.i18n.prop('id_add_date'));
			$('#id_total_channels').html($.i18n.prop('id_total_channels'));
			$('#id_total_reg_copy').html($.i18n.prop('id_total_reg_copy'));
			$('#id_payed_number_copy').html($.i18n.prop('id_payed_number_copy'));
			$('#id_email').html($.i18n.prop('id_email'));
			$('#id_skype').html($.i18n.prop('id_skype'));
			$('#id_select_status_copy').html($.i18n.prop('id_select_status_copy'));
			$('#id_active_copy').html($.i18n.prop('id_active_copy'));
			$('#id_banned_copy').html($.i18n.prop('id_banned_copy'));
			$('#id_select_group_copy').html($.i18n.prop('id_select_group_copy'));
			$('#id_admin_copy').html($.i18n.prop('id_admin_copy'));
			$('#id_xcloud_copy').html($.i18n.prop('id_xcloud_copy'));
			$('#id_ad_copy').html($.i18n.prop('id_ad_copy'));
			$('#id_personal_permission_copy').html($.i18n.prop('id_personal_permission_copy'));
			$('#id_cancel_copy').html($.i18n.prop('id_cancel_copy'));
			$('#id_save_copy').html($.i18n.prop('id_save_copy'));
		}
	});
}