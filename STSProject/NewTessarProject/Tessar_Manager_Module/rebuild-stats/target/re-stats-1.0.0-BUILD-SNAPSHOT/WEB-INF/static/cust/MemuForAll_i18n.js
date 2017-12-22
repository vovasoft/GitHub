$(document).ready(function(){
	loadPropertiesForMemuAllCommon();
});



function loadPropertiesForMemuAllCommon() {
        var lan = jQuery.i18n.browserLang();
        //console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
            	$('#id_data_generic').append($.i18n.prop('id_data_generic'));
                $('#id_app_trend').append($.i18n.prop('id_app_trend'));
                $('#id_app_trend_copy').append($.i18n.prop('id_app_trend_copy'));
                $('#id_payment').append($.i18n.prop('id_payment'));
                $('#id_payment_trend').append($.i18n.prop('id_payment_trend'));
                $('#id_channel').append($.i18n.prop('id_channel'));
                $('#id_channel_analyze').append($.i18n.prop('id_channel_analyze'));
                $('#id_confirm').html($.i18n.prop('id_confirm'));
                $('#id_dashboard').html($.i18n.prop('id_dashboard'));
                $('#id_logout').html($.i18n.prop('id_logout'));
            }
        });
}