package cn.zplayer.mc.framework.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2015/12/5 0005.
 */
public class MatcheUtils {

    /**
     * 匹配手机号 进行验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "[1]\\d{10}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 匹配身份证 进行验证
     *  这里只是校验位数和最后一位x
     * @param idCard
     * @return
     */
    public static boolean isIDCard(String idCard) {
        String telRegex = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
        if (TextUtils.isEmpty(idCard))
            return false;
        else
            return idCard.matches(telRegex);
    }

    /**
     * ****
     *
     * @param idCard
     * @return
     */
    public static String isFormatIDCard(String idCard) {
        String value;
        if(idCard != null && (idCard.length() == 15 || idCard.length() == 18) ){
            value = idCard.substring(0, idCard.length() - 8);
            value = value + "****" + idCard.substring(idCard.length() - 4, idCard.length());
        }else{
            value = idCard;
        }
        return value;

    }

    public static boolean isPhoneCode(String mobiles) {
        String telRegex = "\\d{6}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 手机号中间四位密文显示
     *
     * @param tel
     * @return
     */
    public static String getPassTel(String tel) {
        String phone = tel;
        if (tel != null && tel.length() == 11) {
            phone = tel.substring(0, 3);
            int length = tel.length();
            phone = phone + "****" + tel.substring(length - 4, length);
        }
        return phone;
    }

    /**
     * 匹配字符转 ","切割
     *
     * @param str
     */
    public static String[] splitShow(String str) {
        if (str != null) {
            return str.split("\\,");
        } else {
            return new String[0];
        }
    }

}
