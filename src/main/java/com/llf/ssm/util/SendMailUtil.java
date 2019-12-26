package com.llf.ssm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailUtil {
	
	private static Logger logger = LogManager.getLogger();
	
	public static void sendMail(String mailHost,String from, String formKey, String to ) {
		Properties properties=new Properties();
		properties.setProperty("mail.debug", "true");
		properties.setProperty("mail.host", mailHost);
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		// 开启SSL加密，否则会失败
		try {
	        MailSSLSocketFactory sf = new MailSSLSocketFactory();
	        sf.setTrustAllHosts(true);
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.ssl.socketFactory", sf);
		    // 获取默认session对象
		    Session session = Session.getDefaultInstance(properties);
		    // 通过session得到transport对象
	        Transport ts = session.getTransport();
	        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
	        ts.connect(mailHost, from, formKey);
	        // 后面的字符是授权码，用qq密码失败了
	
	        // 创建邮件
	        Message message = createSimpleMail(session,from,to);
	        // 发送邮件
	        ts.sendMessage(message, message.getAllRecipients());
	        ts.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
		}
       
	}

	/**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String from, String to)
            throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(from));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 邮件的标题
        message.setSubject("报告新增");
        // 邮件的文本内容
        message.setContent("您有新的报告啦!", "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }
}
