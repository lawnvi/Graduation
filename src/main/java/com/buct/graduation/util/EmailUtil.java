package com.buct.graduation.util;

import com.buct.graduation.model.pojo.User;
import com.buct.graduation.model.pojo.recruit.Station;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @Description: 发送包含内嵌图片和附件的复杂邮件
 */
public class EmailUtil {
    private static String mailFrom = null;// 指明邮件的发件人
        private static String password_mailFrom = null;// 指明邮件的发件人登陆密码
        private static String mailTo = null; // 指明邮件的收件人
        private static String mailTittle = null;// 邮件的标题
        private static String mailText = null; // 邮件的文本内容
        private static String mail_host = null; // 邮件的服务器域名
        private static String photoSrc = null; // 发送图片的路径
        private static List<String> fileList = new ArrayList<>(); // 发送附件的路径

        public static boolean sendMail(String to, String content){
            mailFrom = "960789981@qq.com";
            password_mailFrom = "qhycpckwpkebbdci";
            mailTo = to;
            mailTittle = "来自buctHelper的邮件";
            mailText = content;
            mail_host = "smtp.qq.com";
            //photoSrc = "resource\\images\\4.jpg";
            //fileList.add(0,"resource\\attachments\\a.7z");
            //fileList.add(1,"resource\\attachments\\b.7z");


//        Properties prop = new Properties();
//        prop.setProperty("mail.host", mail_host);// 需要修改
//        prop.setProperty("mail.transport.protocol", "smtp");
//        prop.setProperty("mail.smtp.auth", "true");

            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host", "smtp.qq.com");//发送邮箱服务器
            properties.setProperty("mail.smtp.port", "465");//发送端口
            properties.setProperty("mail.smtp.auth", "true");//是否开启权限控制
            properties.setProperty("mail.debug", "true");//true 打印信息到控制台
            properties.setProperty("mail.transport", "smtp");//发送的协议是简单的邮件传输协议
            properties.setProperty("mail.smtp.ssl.enable", "true");
            properties.setProperty("mail.transport.protocol", "smtp");

            // 使用JavaMail发送邮件的5个步骤
            // 1、创建session
            Session session = Session.getInstance(properties);
            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            // 2、通过session得到transport对象
            Transport ts = null;
            try {
                ts = session.getTransport();
                // 3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
                ts.connect(mail_host, mailFrom, password_mailFrom);// 需要修改
                // 4、创建邮件
                Message message = createMixedMail(session);
                // 5、发送邮件
                ts.sendMessage(message, message.getAllRecipients());
                ts.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        /**
         * @Method: createMixedMail
         * @Description: 生成一封带附件和带图片的邮件
         */
        public static MimeMessage createMixedMail(Session session) throws Exception {
            MimeMessage message = new MimeMessage(session);
            // 设置邮件的基本信息
            message.setFrom(new InternetAddress(mailFrom));    // 发件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));// 收件人
            message.setSubject(mailTittle);

            // 正文
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(mailText + "<br/><img src='cid:aaa.jpg'>", "text/html;charset=UTF-8");

            // 图片
            //MimeBodyPart image = new MimeBodyPart();
            //image.setDataHandler(new DataHandler(new FileDataSource(photoSrc)));
            //image.setContentID("aaa.jpg");
/*
        // 附件1
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(fileList.get(0)));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());
        // 附件2
        MimeBodyPart attach2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(fileList.get(1)));
        //attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));
*/
            // 描述关系:正文和图片
            MimeMultipart mp1 = new MimeMultipart();
            mp1.addBodyPart(text);
            //mp1.addBodyPart(image);
            mp1.setSubType("related");

            // 描述关系:正文和附件
            MimeMultipart mp2 = new MimeMultipart();
//        mp2.addBodyPart(attach);
//        mp2.addBodyPart(attach2);

            // 代表正文的bodypart
            MimeBodyPart content = new MimeBodyPart();
            content.setContent(mp1);
            mp2.addBodyPart(content);
            mp2.setSubType("mixed");

            message.setContent(mp2);
            message.saveChanges();

            //message.writeTo(new FileOutputStream("D:\\test.eml"));
            // 返回创建好的的邮件
            return message;
        }

        public static String register(String code){
            return "您好:\n"+
                    "\t我是BuctHelper！小助手提醒你请在10分钟内完成注册额！\n" +
                    "快用验证码："+code+"验证注册吧-->> " +
                    "<a href='"+"www.baidu.com"+ "'>BUCTHelper</a>"+
                    "\n祝好!"+
                    "\n"+Utils.getDate().toString()+
                    "\n北京化工大学XX学院人事部";
            //todo 学院信息
        }

    public static String resetPsw(String code){
        return "您好:\n"+
                "\t我是BuctHelper！小助手提醒你请在10分钟内完成重置密码额！\n" +
                "快用验证码："+code+"验证吧-->> " +
                "<a href='"+"www.baidu.com"+ "'>BUCTHelper</a>"+
                "\n祝好!"+
                "\n"+Utils.getDate().toString()+
                "\n北京化工大学XX学院人事部";
        //todo 学院信息
    }

    public static String isPassResume(boolean isPass, User user, Station station){
        if(isPass) {
            return user.getName() + ",您好:\n" +
                    "\t我是BuctHelper！小助手恭喜您已通过岗位: " + station.getTitle() +
                    "的重重面试，现邀请您加入我们，请与我们保持联系完成后续入职操作。" +
                    "\n祝好!" +
                    "\n" + Utils.getDate().toString() +
                    "\n北京化工大学XX学院人事部";
        }
        else {
            return user.getName() + ",您好:\n" +
                    "\t我是BuctHelper！小助手提示您未能通过岗位: " + station.getTitle() +
                    "的面试，但请您不要灰心，我们已将您加入我们的人才储备库，如有与您匹配的岗位会主动与您联系，尽管这是一张好人卡，但还是请您收下。" +
                    "\n祝好!" +
                    "\n" + Utils.getDate().toString() +
                    "\n北京化工大学XX学院人事部";
        }
        //todo 学院信息
    }
}
