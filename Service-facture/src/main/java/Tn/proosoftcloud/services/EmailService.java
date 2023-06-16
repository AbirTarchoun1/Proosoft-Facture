package Tn.proosoftcloud.services;
import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.entities.User;
import Tn.proosoftcloud.repository.IFactureRepository;
import Tn.proosoftcloud.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailService implements IEmail {

@Autowired
private JavaMailSender emailSender;
    @Autowired
    private IFactureRepository factureRepository;
    @Autowired
    private IUserRepository userRepository;



    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tarchounaaboura@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachmentClient(String pathToAttachment, String email) throws MessagingException {
        // Retrieve the user based on the codeClient
        Facture facture = new Facture();
         String codeClient ="";
        User user = userRepository.findByCodeClient(codeClient);
        if (user != null && facture.getClient().equals(user.getCodeClient())) {
     // CodeClient matches, send email to the user's email
        String userEmail = user.getEmail();
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Facture";
        String text = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<p style='color: #1c87c9; font-size: 18px;'>Dear " + user.getUsername() + ",</p>"
                + "<p style='color: #333333; font-size: 16px;'>Please find attached the facture.</p>"
                + "</body></html>";

        helper.setFrom("tarchounaaboura@gmail.com");
        helper.setTo(userEmail);
        helper.setSubject(subject);
        helper.setText(text, true);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("facture.pdf", file);
            // Send the email
            emailSender.send(message);
        } else {
            // CodeClient doesn't match or user not found, handle the error accordingly
            return;
        }

    }
}
