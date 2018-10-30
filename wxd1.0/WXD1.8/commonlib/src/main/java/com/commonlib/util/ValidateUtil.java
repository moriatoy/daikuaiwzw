package com.commonlib.util;

import android.text.TextUtils;

import com.commonlib.R;
import com.commonlib.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wjy on 2015/7/13.
 */
public class ValidateUtil {

    /**
     * 手机号码验证
     *
     * @param activity
     * @param phone
     * @return
     */
    public static boolean isValidPhone(BaseActivity activity, String phone) {
        if (TextUtils.isEmpty(phone)) {
            activity.showToast(R.string.tip_input_phone);
            return false;
        }
        if (phone.length() != 11) {
            activity.showToast(R.string.tip_input_11_phone);
            return false;
        }
//        if (!VerifyUtil.isMobileNO(phone)) {
//            activity.showToast("请输入正确的手机号码");
//            return false;
//        }
        return true;
    }

    /**
     * 登录账号验证
     * @param activity
     * @param loginId
     * @return
     */
    public static  boolean isValidLoginId(BaseActivity activity,String loginId){
    if (TextUtils.isEmpty(loginId)) {
        activity.showToast(R.string.tip_input_loginId);
        return false;
    }
    return true;
}
    /**
     * 手机号码验证
     *
     * @param activity
     * @param phone
     * @return
     */
    public static boolean isValidCPhone(BaseActivity activity, String phone) {
        if (TextUtils.isEmpty(phone)) {
            activity.showToast("请输入联系人电话");
            return false;
        }
        if (!ValidateUtil.match(phone, "^\\d{3,4}-?\\d{7,8}$") && !VerifyUtil.isMobileNO(phone)) {
            activity.showToast("请输入正确的联系人电话");
            return false;
        }

//        String[] p = phone.split("-");
//        if (p.length <= 2) {
//            String ph = phone.replaceAll("-", "");
//            if (ph.length() >= 10 && ph.length() <= 13) {
//                if (TextUtils.isDigitsOnly(ph)) {
//                    return true;
//                }else{
//                    activity.showToast("请输入正确的联系人电话");
//                    return false;
//                }
//            } else {
//                activity.showToast("请输入正确的联系人电话");
//                return false;
//            }
//        } else {
//            activity.showToast("请输入正确的联系人电话");
//            return false;
//        }
        return true;
    }

    /**
     * 总机号码验证
     *
     * @param activity
     * @param phone
     * @return
     */
    public static boolean isValidSPhone(BaseActivity activity, String phone) {
        if (TextUtils.isEmpty(phone)) {
            activity.showToast("请输入总机号码");
            return false;
        }
        if (!ValidateUtil.match(phone, "^\\d{3,4}-?\\d{7,8}$") && !VerifyUtil.isMobileNO(phone)) {
            activity.showToast("请输入正确的总机号码");
            return false;
        }
        return true;

//        String[] p = phone.split("-");
//        if (p.length <= 2) {
//            String ph = phone.replaceAll("-", "");
//            if (ph.length() >= 10 && ph.length() <= 13) {
//                if (TextUtils.isDigitsOnly(ph)) {
//                    return true;
//                }else{
//                    activity.showToast("请输入正确的总机号码");
//                    return false;
//                }
//            } else {
//                activity.showToast("请输入正确的总机号码");
//                return false;
//            }
//        } else {
//            activity.showToast("请输入正确的总机号码");
//            return false;
//        }
    }

    /**
     * 验证码验证
     *
     * @param activity
     * @param code
     * @return
     */
    public static boolean isValidCode(BaseActivity activity, String code, int length) {
        if (TextUtils.isEmpty(code)) {
            activity.showToast(R.string.tip_input_code);
            return false;
        }
        if (code.length() != length) {
            activity.showToast(R.string.tip_input_length_code);
            return false;
        }
        return true;
    }

    /**
     * 校验码验证
     *
     * @param activity
     * @param code
     * @return
     */
    public static boolean isValidLocalCode(BaseActivity activity, String code, int length, String localCode) {
        if (TextUtils.isEmpty(code)) {
            activity.showToast("请输入校验码");
            return false;
        }
        if (code.length() != length) {
            activity.showToast(R.string.tip_input_length_local_code);
            return false;
        }
        if (!localCode.equals(code)) {
            activity.showToast("校验码错误");
            return false;
        }
        return true;
    }

    public static boolean find(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean match(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 密码验证
     *
     * @param activity
     * @param password
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidPassword(BaseActivity activity, String password, int min, int max, boolean needCheck) {

        if (TextUtils.isEmpty(password)) {
            activity.showToast(R.string.tip_input_password);
            return false;
        }
        if (password.length() < min || password.length() > max) {
            activity.showToast(R.string.tip_input_length_password);
            return false;
        }
        if (needCheck) {
            String regex = "[a-zA-Z]+";
            String regex1 = "[0-9]+";
            if (!(find(password, regex) && find(password, regex1))) {
                activity.showToast("密码必须同时包含字母、数字");
                return false;
            }
        }
        return true;
    }

    /**
     * 旧密码验证
     *
     * @param activity
     * @param password
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidOldPassword(BaseActivity activity, String password, int min, int max) {
        if (TextUtils.isEmpty(password)) {
            activity.showToast(R.string.tip_input_old_password);
            return false;
        }
        if (password.length() < min || password.length() > max) {
            activity.showToast(R.string.tip_input_length_old_password);
            return false;
        }
        String regex = "[a-zA-Z]+";
        String regex1 = "[0-9]+";
        if (!(find(password, regex) && find(password, regex1))) {
            activity.showToast("旧密码必须同时包含字母、数字");
            return false;
        }
        return true;
    }

    /**
     * 新密码验证
     *
     * @param activity
     * @param password
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidNewPassword(BaseActivity activity, String password, int min, int max) {
        if (TextUtils.isEmpty(password)) {
            activity.showToast(R.string.tip_input_new_password);
            return false;
        }
        if (password.length() < min || password.length() > max) {
            activity.showToast(R.string.tip_input_length_new_password);
            return false;
        }
        String regex = "[a-zA-Z]+";
        String regex1 = "[0-9]+";
        if (!(find(password, regex) && find(password, regex1))) {
            activity.showToast("新密码必须同时包含字母、数字");
            return false;
        }
        return true;
    }

    /**
     * 确认密码验证
     *
     * @param activity
     * @param password
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidConfirmPassword(BaseActivity activity, String password, String password2, int min, int max) {
        if (TextUtils.isEmpty(password2)) {
            activity.showToast(R.string.tip_input_confirm_password);
            return false;
        }
        if (password2.length() < min || password2.length() > max) {
            activity.showToast(R.string.tip_input_length_confirm_password);
            return false;
        }
        if (!password2.equals(password)) {
            activity.showToast(R.string.tip_password_different);
            return false;
        }
        String regex = "[a-zA-Z]+";
        String regex1 = "[0-9]+";
        if (!(find(password, regex) && find(password, regex1))) {
            activity.showToast("确认密码必须同时包含字母、数字");
            return false;
        }
        return true;
    }

    /**
     * 姓名验证
     *
     * @param activity
     * @param name
     * @return
     */
    public static boolean isValidName(BaseActivity activity, String name) {
        if (TextUtils.isEmpty(name)) {
            activity.showToast(R.string.tip_input_name);
            return false;
        }
        return true;
    }

    /**
     * 年龄验证
     *
     * @param activity
     * @param age
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidAge(BaseActivity activity, String age, int min, int max) {
        if (TextUtils.isEmpty(age)) {
            activity.showToast(R.string.tip_input_age);
            return false;
        }
        if (age.length() < min || age.length() > max) {
            activity.showToast(R.string.tip_input_correct_age);
            return false;
        }
        return true;
    }

    /**
     * 电话号码验证
     *
     * @param activity
     * @param telphone
     * @return
     */
    public static boolean isValidTelPhone(BaseActivity activity, String telphone) {
        if (TextUtils.isEmpty(telphone)) {
            activity.showToast(R.string.tip_input_telphone);
            return false;
        }
        if (telphone.length() < 6) {
            activity.showToast(R.string.tip_input_6_telphone);
            return false;
        }
        return true;
    }

    /**
     * 银行卡号验证
     *
     * @param activity
     * @param bankcard
     * @return
     */
    public static boolean isValidBankCard(BaseActivity activity, String bankcard) {
        if (TextUtils.isEmpty(bankcard)) {
            activity.showToast(R.string.tip_input_bankcard);
            return false;
        }
        if (bankcard.length() != 16 && bankcard.length() != 19) {
            activity.showToast(R.string.tip_input_16_19_bankcard);
            return false;
        }
        return true;
    }

    /**
     * 身份证号验证
     *
     * @param activity
     * @param idcard
     * @return
     */
    public static boolean isValidIDCard(BaseActivity activity, String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            activity.showToast(R.string.tip_input_idcard);
            return false;
        }
        if (idcard.length() != 15 && idcard.length() != 18) {
            activity.showToast(R.string.tip_input_15_18_idcard);
            return false;
        }
        return true;
    }

    /**
     * 体重验证
     *
     * @param activity
     * @param weight
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidWeight(BaseActivity activity, String weight, float min, float max) {
        if (TextUtils.isEmpty(weight)) {
            activity.showToast(R.string.tip_input_weight);
            return false;
        }
        if (StrParseUtil.parseFloat(weight) < min || StrParseUtil.parseFloat(weight) > max) {
            activity.showToast(R.string.tip_input_correct_weight);
            return false;
        }
        return true;
    }

    /**
     * 身高验证
     *
     * @param activity
     * @param height
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidHeight(BaseActivity activity, String height, float min, float max) {
        if (TextUtils.isEmpty(height)) {
            activity.showToast(R.string.tip_input_height);
            return false;
        }
        if (StrParseUtil.parseFloat(height) < min || StrParseUtil.parseFloat(height) > max) {
            activity.showToast(R.string.tip_input_correct_height);
            return false;
        }
        return true;
    }

    /**
     * 邮箱验证
     *
     * @param activity
     * @param email
     * @return
     */
    public static boolean isValidEmail(BaseActivity activity, String email) {
        if (TextUtils.isEmpty(email)) {
            activity.showToast(R.string.tip_input_email);
            return false;
        }
        if (email.trim().length() > 100) {
            activity.showToast("Email过长");
            return false;
        }
        if (!ValidateUtil.match(email, "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$")) {
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activity.showToast(R.string.tip_input_correct_email);
            return false;
        }
        return true;
    }

    /**
     * 微信号验证
     *
     * @param activity
     * @param wechat
     * @return
     */
    public static boolean isValidWeChat(BaseActivity activity, String wechat) {
        if (TextUtils.isEmpty(wechat)) {
            activity.showToast(R.string.tip_input_wechat);
            return false;
        }
        return true;
    }

    /**
     * QQ号验证
     *
     * @param activity
     * @param qq
     * @return
     */
    public static boolean isValidQQ(BaseActivity activity, String qq) {
        if (TextUtils.isEmpty(qq)) {
            activity.showToast(R.string.tip_input_qq);
            return false;
        }
        return true;
    }

    /**
     * 地址验证
     *
     * @param activity
     * @param address
     * @return
     */
    public static boolean isValidAddress(BaseActivity activity, String address) {
        if (TextUtils.isEmpty(address)) {
            activity.showToast(R.string.tip_input_address);
            return false;
        }
        return true;
    }

    /**
     * 意见反馈验证
     *
     * @param activity
     * @param feedback
     * @return
     */
    public static boolean isValidFeedBack(BaseActivity activity, String feedback) {
        if (TextUtils.isEmpty(feedback)) {
            activity.showToast(R.string.tip_input_feedback);
            return false;
        }
        return true;
    }

    /**
     * 帖子标题验证
     *
     * @param activity
     * @param title
     * @return
     */
    public static boolean isValidTitle(BaseActivity activity, String title) {
        if (TextUtils.isEmpty(title)) {
            activity.showToast(R.string.tip_input_title);
            return false;
        }
        return true;
    }

    /**
     * 帖子内容验证
     *
     * @param activity
     * @param content
     * @return
     */
    public static boolean isValidContent(BaseActivity activity, String content) {
        if (TextUtils.isEmpty(content)) {
            activity.showToast(R.string.tip_input_content);
            return false;
        }
        return true;
    }

    /**
     * 聊天内容验证
     *
     * @param activity
     * @param chattext
     * @return
     */
    public static boolean isValidChatText(BaseActivity activity, String chattext) {
        if (TextUtils.isEmpty(chattext)) {
            activity.showToast(activity.getString(R.string.tip_input_chattext));
            return false;
        }
        return true;
    }

    /**
     * 搜索内容验证
     *
     * @param activity
     * @param search
     * @return
     */
    public static boolean isValidSearch(BaseActivity activity, String search) {
        if (TextUtils.isEmpty(search)) {
            activity.showToast(activity.getString(R.string.tip_input_search));
            return false;
        }
        return true;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断只包含数字和字母
     */
    public static boolean isOnlyContainCharacterAndDigital(String str){
        String regex = "^[a-zA-Z0-9]+$";
        return find(str,regex);
    }
}
