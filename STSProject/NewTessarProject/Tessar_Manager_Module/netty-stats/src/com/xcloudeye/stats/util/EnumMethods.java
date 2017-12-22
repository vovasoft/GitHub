package com.xcloudeye.stats.util;

/**
 * Created by 瑞刚 on 2016/1/18.
 */
public class EnumMethods {

    public enum method {
        // The url is "app/*"
        app_rt("AppRt"),
        app_trend("AppTrend"),
        user_new("AppUserNew"),
        user_pay("AppUserPay"),
        user_active("AppUserActive"),
        user_retention("AppUserRetention"),
        ret_query("retQuery"),
        ch_detail("AppChDetail"),
        ch_list("AppChList"),
        ch_track("AppChTrack"),
        pay_sort("AppPaySort"),
        pay_sort_ch("AppPayChSort"),
        pay_trend("AppPayTrend"),
        pay_hobby("AppPayHobby"),
        fiveminutes_act("ActInFiveMinutes"),
        repeatfiveminutes_act("RepeatFiveMinutes"),
        five_new_user("FiveNewUser"),
        logout("userLogout"),
        detail_ch_order("AppDetailChOrder"),
        roi_query("RoiQuery"),

        user_query("userQuery"),
        user_order("userOrder"),
        order_track("orderTrack"),
        pay_order("payOrder"),
        active_download("activeQuery"),
        payment_download("paymentQuery"),



        //The url is "main/*"
        trend("mainTrend"),
        generic("mainGneric"),
        pay("mainPay"),
        channel("mainChannel"),
        manage_user("mangeUser"),
        manage_ch("mangeChannels"),


        add_ch("addChannel"),
        edit_ch("editChannel"),
        delete_ch("deleteChannel"),
        change_ch("changeChannelName"),
        ch_status("chsChangedStatus"),
        ch_query("queryUsersByCh"),

        session("sessionTest1"),
        //The url is "use/*"
        change_pwd("userChangPassword"),
        user_reg("userRegist"),
        delete_user("deleteUser"),
        edit_user("editUser"),
        user_login("userLogin");


        private String MethodName;

        private method(String MethodName) {
            this.MethodName = MethodName;
        }

        @Override
        public String toString() {
            return String.valueOf(this.MethodName);
        }
    }


    public static void main(String[] args) {

        System.out.print(EnumMethods.method.valueOf("trend"));
    }
}
