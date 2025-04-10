package util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

    public static void sendEmail(String toEmail, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        final String fromEmail = "your_email@gmail.com"; // 발신자 이메일
        final String password = "your_app_password";     // 앱 비밀번호 (Gmail 2단계 인증용)

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 보안 사용

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail, "Your App Name"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(content); // 일반 텍스트
        // message.setContent(content, "text/html"); // 👉 HTML로 보내고 싶을 경우 사용

        Transport.send(message);
    }
}