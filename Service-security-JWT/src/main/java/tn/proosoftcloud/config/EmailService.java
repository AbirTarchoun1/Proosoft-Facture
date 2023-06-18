package tn.proosoftcloud.config;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

/**
 *
 * @author Tarchoun Abir
 *
 */

@Service
public class EmailService {

    public void sendNewPasswordEmail(String userame, String password, String email) throws MessagingException {
        Message message = createEmail(userame, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    public void sendNotif( String email,String username) throws MessagingException {
        Message message = StatusEmail(email,username);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    public void sendContactConfirm( String email,String companyname) throws MessagingException {
        Message message = StatusContanct(email,companyname);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }


    public void sendSellersConfirm( String email,String companyname) throws MessagingException {
        Message message = ConfirmedSellersPost(email,companyname);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    public void sendSellersRejected( String email,String companyname) throws MessagingException {
        Message message = RejectedSellersPost(email,companyname);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    public void sendNotifBlockedAccount( String email,String username) throws MessagingException {
        Message message = BlockedAccountEmail(email,username);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER, EmailConstant.USERNAME, EmailConstant.PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    private Message createEmail(String username, String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT);
        message.setText("Hello " + username + ", \n \n Your new account password is: " + password + "\n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    public Message BlockedAccountEmail(String email, String username) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT1); message.setText("Hello " + username + ", \n \n Your account have been Blocked." + "\n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    public Message ConfirmedSellersPost(String email, String companyname) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT4); message.setText("Hello " + companyname +
                ", \n \n  \n  We are happy to inform you , that your request as a seller have been accepted," +
                "for further informations about how we do business please attend to us at society . " + "\n \n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    public Message RejectedSellersPost(String email, String companyname) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT5); message.setText("Hello " + companyname +
                ", \nWe are sorry to inform you,that your request as a seller have been rejected," +
                "for further informations get in touch with our assistance."+"\nthe Support Team.");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    private Message StatusEmail( String email,String username) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT2);
        message.setText("Hello "+ username +", \n \n Your account have been actived , you can logged in Now " + "\n \n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    private Message StatusContanct( String email,String companyname) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));
        message.setSubject(EmailConstant.EMAIL_SUBJECT3);
        message.setText("Hello "+ companyname +", \n \n \n Your request contact message have been accepted , " + "\n \n " +
                " Get in touch with our assistance For more details " + "\n  \n  \n" + "The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
        private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(EmailConstant.SMTP_HOST, EmailConstant.GMAIL_SMTP_SERVER);
        properties.put(EmailConstant.SMTP_AUTH, true);
        properties.put(EmailConstant.SMTP_PORT, EmailConstant.DEFAULT_PORT);
        properties.put(EmailConstant.SMTP_STARTTLS_ENABLE, true);
        properties.put(EmailConstant.SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}
