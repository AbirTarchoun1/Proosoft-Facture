package Tn.proosoftcloud.services;

import javax.mail.MessagingException;

public interface IEmail {


    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithAttachmentClient(String pathToAttachment , String email) throws MessagingException;
}
