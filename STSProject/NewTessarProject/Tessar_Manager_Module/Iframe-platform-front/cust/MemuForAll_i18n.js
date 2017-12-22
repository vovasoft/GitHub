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
                $('#id_new_user').html($.i18n.prop('id_new_user'));
                $('#id_payer').html($.i18n.prop('id_payer'));
                $('#id_active_user').html($.i18n.prop('id_active_user'));
                $('#id_payed_order').html($.i18n.prop('id_payed_order'));
                $('#id_payed_order_copy').html($.i18n.prop('id_payed_order_copy'));
                $('#id_payed_order_copy1').html($.i18n.prop('id_payed_order_copy1'));
                $('#id_payed_order_copy2').html($.i18n.prop('id_payed_order_copy2'));
                $('#id_new_user_copy2').html($.i18n.prop('id_new_user_copy2'));
                $('#id_all_app_copy').html($.i18n.prop('id_all_app_copy'));
                $('#id_payer_copy2').html($.i18n.prop('id_payer_copy2'));
                $('#id_payer_copy3').html($.i18n.prop('id_payer_copy3'));
                $('#id_order').html($.i18n.prop('id_order'));
                $('#id_new_payer').html($.i18n.prop('id_new_payer'));
                $('#id_total_income_copy').html($.i18n.prop('id_total_income_copy'));
                $('#id_total_income_copy1').html($.i18n.prop('id_total_income_copy1'));
                $('#id_dayly_income').html($.i18n.prop('id_dayly_income'));
                $('#id_trend_payer').html($.i18n.prop('id_trend_payer'));
                $('#id_trend_order').html($.i18n.prop('id_trend_order'));
                $('#id_trend_payed_order').html($.i18n.prop('id_trend_payed_order'));
                $('#id_trend_dayly_income').html($.i18n.prop('id_trend_dayly_income'));
                $('#id_RT_new_user').html($.i18n.prop('id_RT_new_user'));
                $('#id_RT_active_user').html($.i18n.prop('id_RT_active_user'));
                $('#id_RT_payer').html($.i18n.prop('id_RT_payer'));
                $('#id_RT_new_payer').html($.i18n.prop('id_RT_new_payer'));
                $('#id_RT_payed_money').html($.i18n.prop('id_RT_payed_money'));
                $('#id_RT_order_number').html($.i18n.prop('id_RT_order_number'));
                $('#id_new_payer_copy').html($.i18n.prop('id_new_payer_copy'));
                $('#id_new_active').html($.i18n.prop('id_new_active'));
                $('#id_old_active').html($.i18n.prop('id_old_active'));
                $('#id_total_income_copy2').html($.i18n.prop('id_total_income_copy2'));
                $('#id_trend_new_user').html($.i18n.prop('id_trend_new_user'));
                $('#id_trend_active_user').html($.i18n.prop('id_trend_active_user'));
                $('#id_active_compose').html($.i18n.prop('id_active_compose'));
                $('#id_trend_payer').html($.i18n.prop('id_trend_payer'));
                $('#id_trend_payed_money').html($.i18n.prop('id_trend_payed_money'));
                $('#id_trend_new_payer').html($.i18n.prop('id_trend_new_payer'));
            }
        });
}