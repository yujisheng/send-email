package com.soft863.sendmail.uitl;

import com.soft863.sendmail.SendMail;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * @ClassName: SendEmailUtil
 * @Author
 * @Date 2019/3/19 0019 16:12
 */
public class SendEmailUtil {
    /**
     * 确认发件人邮箱的 SMTP 服务器地址
     */
    public static String getSMTP(String myEmailAccount){
        String myEmailSMTPHost = null;
        String[] split = myEmailAccount.split("@");
        if(StmpUtil.STMP_QQ.equals(split[1])){
            myEmailSMTPHost="smtp.qq.com";
        }
        if (StmpUtil.STMP_163.equals(split[1])){
            myEmailSMTPHost="smtp.163.com";
        }
        if(StmpUtil.STMP_126.equals(split[1])){
            myEmailSMTPHost="smtp.126.com";
        }
        if(StringUtils.isEmpty(myEmailSMTPHost)){
            throw new SendMailException("发件人邮箱的 SMTP 服务器地址“myEmailSMTPHost”不能为空",13001);
        }
        return myEmailSMTPHost;
    }

}
