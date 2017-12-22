$(document).ready(function(){
	loadPropertiesForMemuAppCommon();
});



function loadPropertiesForMemuAppCommon() {
        var lan = jQuery.i18n.browserLang();
        //console.log(jQuery.i18n.browserLang());
        jQuery.i18n.properties({
            name : 'strings',
            path : './i18n/',
            mode : 'map',
            language : lan,
            encoding: "UTF-8",
            callback : function() {
            	$('#id_generic').append($.i18n.prop('id_generic'));
                $('#id_realtime_data').html($.i18n.prop('id_realtime_data'));
                $('#id_fiveminutes_active_user').html($.i18n.prop('id_fiveminutes_active_user'));
                $('#id_five_new_user').html($.i18n.prop('id_five_new_user'));
                $('#id_general_trend').html($.i18n.prop('id_general_trend'));
                
                $('#id_user_analyze').append($.i18n.prop('id_user_analyze'));
                $('#id_new_user_copy').html($.i18n.prop('id_new_user_copy'));
                $('#id_ch_new_user').html($.i18n.prop('id_ch_new_user'));
                $('#id_ch_active_user').html($.i18n.prop('id_ch_active_user'));
                $('#id_ch_payer').html($.i18n.prop('id_ch_payer'));
                $('#id_ch_payed_money').html($.i18n.prop('id_ch_payed_money'));
                $('#id_new_user').html($.i18n.prop('id_new_user'));
                $('#id_payer').html($.i18n.prop('id_payer'));
                $('#id_payer_copy').html($.i18n.prop('id_payer_copy'));
                $('#id_payer_copy1').html($.i18n.prop('id_payer_copy1'));
                $('#id_active_user').html($.i18n.prop('id_active_user'));
                $('#id_active_user_copy').html($.i18n.prop('id_active_user_copy'));
                $('#id_active_user_copy1').html($.i18n.prop('id_active_user_copy1'));
                $('#id_user_retention').html($.i18n.prop('id_user_retention'));
                $('#id_ret_query').html($.i18n.prop('id_ret_query'));
                $('#id_payed_money').html($.i18n.prop('id_payed_money'));
                
                
//              $('#id_channel_analyze').append($.i18n.prop('id_channel_analyze'));
                $('#id_hourly_data').html($.i18n.prop('id_hourly_data'));
                $('#id_channel_list').html($.i18n.prop('id_channel_list'));
                $('#id_channel_track').html($.i18n.prop('id_channel_track'));
                
                $('#id_payment_analyze').append($.i18n.prop('id_payment_analyze'));
                $('#id_top_payer').html($.i18n.prop('id_top_payer'));
                $('#id_summary_channel_payment').html($.i18n.prop('id_summary_channel_payment'));
                $('#id_detail_channel_payment').html($.i18n.prop('id_detail_channel_payment'));
                $('#id_payment_trend').html($.i18n.prop('id_payment_trend'));
                $('#id_payment_hobby').html($.i18n.prop('id_payment_hobby'));
                
                $('#id_query').append($.i18n.prop('id_query'));
                $('#id_user_query').html($.i18n.prop('id_user_query'));
                $('#id_order_query').html($.i18n.prop('id_order_query'));
                $('#id_order_track').html($.i18n.prop('id_order_track'));
                $('#id_payed_order').html($.i18n.prop('id_payed_order'));
                
                $('#id_advance').append($.i18n.prop('id_advance'));
                $('#id_cust_query').html($.i18n.prop('id_cust_query'));
                
                $('#id_realtime').append($.i18n.prop('id_realtime'));
                $('#id_app_generic').append($.i18n.prop('id_app_generic'));
                $('#id_detail_trend').append($.i18n.prop('id_detail_trend'));
                $('#id_newer_trend').append($.i18n.prop('id_newer_trend'));
                $('#id_payer_trend').append($.i18n.prop('id_payer_trend'));
                $('#id_au_trend').append($.i18n.prop('id_au_trend'));
                $('#id_secday_retention').append($.i18n.prop('id_secday_retention'));
                $('#id_ret_show').append($.i18n.prop('id_ret_show'));
                 $('#id_ROI_show').append($.i18n.prop('id_ROI_show'));
                $('#id_hourly_data_copy').append($.i18n.prop('id_hourly_data_copy'));
                $('#id_channel_list_copy').append($.i18n.prop('id_channel_list_copy'));
                $('#id_channel_track_copy').append($.i18n.prop('id_channel_track_copy'));
                $('#id_top_payer_copy').append($.i18n.prop('id_top_payer_copy'));
                $('#id_summary_channel_payment_copy').html($.i18n.prop('id_summary_channel_payment_copy'));
                $('#id_detail_channel_payment_copy').html($.i18n.prop('id_detail_channel_payment_copy'));
                $('#id_payment_trend_copy').append($.i18n.prop('id_payment_trend_copy'));
                $('#id_payment_hobby_copy').append($.i18n.prop('id_payment_hobby_copy'));
                 $('#id_day_payer').html($.i18n.prop('id_day_payer'));
                 $('#id_day_pay_money').html($.i18n.prop('id_day_pay_money'));
                  $('#id_day_payer_copy').html($.i18n.prop('id_day_payer_copy'));
                 $('#id_day_pay_money_copy').html($.i18n.prop('id_day_pay_money_copy'));
                
                
                $('#id_confirm').html($.i18n.prop('id_confirm'));
                $('#id_dashboard').html($.i18n.prop('id_dashboard'));
                $('#id_logout').html($.i18n.prop('id_logout'));
            }
        });
}