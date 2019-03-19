package com.soft863.sendmail;

import com.soft863.sendmail.config.MailInfo;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;

/**
 * @ClassName: SendMail
 * @Author
 * @Date 2019/3/19 0019 16:10
 */
public class SendMail {
    public static String send(MailInfo email) throws Exception {

       /*// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", new MailUtil().getSMTP(myEmailAccount));   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证*/

        // 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(email.getProperties());
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, email);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(email.getEmail(), email.getPassword());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();

        return "发送成功";
    }

    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    public static MimeMessage createMimeMessage(Session session, MailInfo mail) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(mail.getEmail(), mail.getName(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）

        String[] tccs=mail.getToAddress();
        // 为每个邮件接收者创建一个地址
        Address[] toAdresses = new InternetAddress[tccs.length];
        for (int i=0; i<tccs.length; i++){
            toAdresses[i] = new InternetAddress(tccs[i]);
        }
        // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
        message.setRecipients(Message.RecipientType.TO, toAdresses);

        if(mail.getCcAddress()!=null){
            String[] ccs=mail.getCcAddress();
            // 为每个邮件接收者创建一个地址
            Address[] ccAdresses = new InternetAddress[ccs.length];
            for (int i=0; i<ccs.length; i++){
                ccAdresses[i] = new InternetAddress(ccs[i]);
            }
            // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
            message.setRecipients(MimeMessage.RecipientType.CC, ccAdresses);
        }

        // 4. Subject: 邮件主题
        message.setSubject(mail.getSubject(), "UTF-8");

        /*
         * 下面是邮件内容的创建:
         */

        //  10.设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        // 5. 创建图片“节点”
        if(mail.getImagePath()!=null){
            for (int i=0;i<mail.getImagePath().length;i++){
                MimeBodyPart image = new MimeBodyPart();
                DataHandler dh = new DataHandler(new FileDataSource(mail.getImagePath()[i])); // 读取本地文件
                image.setDataHandler(dh);		            // 将图片数据添加到“节点”
                image.setContentID("image_fairy_tail"+mail.getImagePath()[i]);	    // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
                // 6. 创建文本“节点”
                MimeBodyPart text = new MimeBodyPart();
                //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
                if(i==0){
                    text.setContent(mail.getContent()+"<br/><img src='cid:image_fairy_tail'"+mail.getImagePath()[i]+"/>", "text/html;charset=UTF-8");
                }else {
                    text.setContent("<br/><img src='cid:image_fairy_tail'"+mail.getImagePath()[i]+"/>", "text/html;charset=UTF-8");
                }
                // 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
                MimeMultipart mm_text_image = new MimeMultipart();
                mm_text_image.addBodyPart(text);
                mm_text_image.addBodyPart(image);
                mm_text_image.setSubType("related");	// 关联关系
                // 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
                //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
                //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
                MimeBodyPart text_image = new MimeBodyPart();
                text_image.setContent(mm_text_image);
                mm.addBodyPart(text_image);
            }

        }else {
            MimeBodyPart text = new MimeBodyPart();
            //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
            text.setContent(mail.getContent(), "text/html;charset=UTF-8");
            MimeMultipart mm_text_image = new MimeMultipart();
            mm_text_image.addBodyPart(text);
            MimeBodyPart text_image = new MimeBodyPart();
            text_image.setContent(mm_text_image);
            mm.addBodyPart(text_image);

        }

        if(mail.getAttachFile()!=null){
            for (int a=0;a<mail.getAttachFile().length;a++){
                // 9. 创建附件“节点”
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dh2 = new DataHandler(new FileDataSource(mail.getAttachFile()[a]));  // 读取本地文件
                attachment.setDataHandler(dh2);			                                    // 将附件数据添加到“节点”
                attachment.setFileName(MimeUtility.encodeText(dh2.getName()));	            // 设置附件的文件名（需要编码）
                mm.addBodyPart(attachment);		// 如果有多个附件，可以创建多个多次添加
            }
        }

        mm.setSubType("mixed");			// 混合关系

        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

        // 12. 设置发件时间
        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();

        return message;
    }

}
