package com.soft863.sendmail.config;

import com.soft863.sendmail.uitl.SendEmailUtil;
import com.soft863.sendmail.uitl.StmpUtil;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName: MailInfo
 * @Author
 * @Date 2019/3/19 0019 16e:13
 */
public class MailInfo {
    /**
     * 发件人的邮箱
     */
    private String fromEmail;
    /**
     * 发送人的邮箱密码
     */
    private String fromPassword;
    /**
     * 发送人的名称
     */
    private String fromName;
    private String fromAddress;
    /**
     * 收件人邮箱
     */
    private String[] toAddress;
    /**
     * 抄送人邮箱
      */
    private String[] ccAddress;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件文本内容
     */
    private String content;
    /**
     * 附件的本地存储位置
     */
    private String[] attachFile;
    /**
     * 图片的本地存储位置
     */
    private String[] imagePath;
    /**
     * 是否需要身份验证
     */
    private boolean authValidate = true;
    /**
     * 是否提供SSL链接
     */
    private boolean starttlsEnable = true;
    private boolean starttlsRequired = true;

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromPassword() {
        return fromPassword;
    }

    public void setFromPassword(String fromPassword) {
        this.fromPassword = fromPassword;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String[] getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String[] ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String[] attachFile) {
        this.attachFile = attachFile;
    }

    public String[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(String[] imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAuthValidate() {
        return authValidate;
    }

    public void setAuthValidate(boolean authValidate) {
        this.authValidate = authValidate;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public boolean isStarttlsRequired() {
        return starttlsRequired;
    }

    public void setStarttlsRequired(boolean starttlsRequired) {
        this.starttlsRequired = starttlsRequired;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * 创建参数配置, 用于连接邮件服务器的参数配置
     * @return
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", SendEmailUtil.getSMTP(fromEmail));
        //p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", authValidate ? "true" : "false");
        // 使用的协议（JavaMail规范要求）
        p.put("mail.transport.protocol", "smtp");
        //开启SSL
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.starttls.required", "true");
        return p;
    }

}
