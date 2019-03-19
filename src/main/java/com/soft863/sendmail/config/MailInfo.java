package com.soft863.sendmail.config;

import com.soft863.sendmail.uitl.SendEmailUtil;

import java.util.Properties;

/**
 * @ClassName: MailInfo
 * @Author
 * @Date 2019/3/19 0019 16:13
 */
public class MailInfo {
    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    private String Email;
    private String password;
    // 发件人名称
    private String name;
    // 收件人邮箱（替换为自己知道的有效邮箱）
    private String[] toAddress;
    // 抄送人邮箱
    private String[] ccAddress;
    //邮件主题/标题
    private String subject;
    //邮件文本内容
    private String content;
    //附件的本地存储位置
    private String[] attachFile;
    //图片的本地存储位置
    private String[] imagePath;
    private boolean authValidate = true; // 是否需要身份验证
    private boolean starttlsEnable = true;// 是否提供SSL链接
    private boolean starttlsRequired = true;

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public void setCcAddress(String[] ccAddress) {
        this.ccAddress = ccAddress;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachFile(String[] attachFile) {
        this.attachFile = attachFile;
    }

    public void setImagePath(String[] imagePath) {
        this.imagePath = imagePath;
    }

    public String[] getToAddress() {
        return toAddress;
    }

    public String[] getCcAddress() {
        return ccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String[] getAttachFile() {
        return attachFile;
    }

    public String[] getImagePath() {
        return imagePath;
    }

    public void setStarttlsRequired(boolean starttlsEnable) {
        this.starttlsRequired = starttlsRequired;
    }

    public void setStarttlsEnable(boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }

    public boolean isStarttlsRequired() {
        return starttlsRequired;
    }

    public void setAuthValidate(boolean authValidate) {
        this.authValidate = authValidate;
    }

    public boolean isAuthValidate() {
        return authValidate;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //创建参数配置, 用于连接邮件服务器的参数配置
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", new SendEmailUtil().getSMTP(Email));
        //p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", authValidate ? "true" : "false");
        p.put("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        p.put("mail.smtp.starttls.enable", "true"); //开启SSL
        p.put("mail.smtp.starttls.required", "true");
        return p;
    }
}
