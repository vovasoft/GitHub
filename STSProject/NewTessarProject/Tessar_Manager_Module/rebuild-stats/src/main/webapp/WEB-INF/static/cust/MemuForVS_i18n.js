$(document).ready(function(){
	loadPropertiesForMemuVSCommon();
});



function loadPropertiesForMemuVSCommon() {
        var lan = jQuery.i18n.browserLang();
        //console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
            	$('#id_visit_stats').append($.i18n.prop('id_visit_stats'));
                $('#id_personal').append($.i18n.prop('id_personal'));
                $('#id_users').append($.i18n.prop('id_users'));
                $('#id_channels').append($.i18n.prop('id_channels'));
                $('#id_add_user').append($.i18n.prop('id_add_user'));
                $('#id_add_channel').append($.i18n.prop('id_add_channel'));
                $('#id_back').append($.i18n.prop('id_back'));
                $('#id_newer_trend').append($.i18n.prop('id_newer_trend'));
                $('#id_channels_info').append($.i18n.prop('id_channels_info'));
                $('#id_confirm').html($.i18n.prop('id_confirm'));
                
                $('#id_dashboard').html($.i18n.prop('id_dashboard'));
                $('#id_logout').html($.i18n.prop('id_logout'));
            }
        });
}