/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.utils;

import sim.data.domain.utils.MessageReturnPojo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailMail {

    @Autowired
    JavaMailSender mailSender;
    
    private String mailfrom = "noreply@poe.pl.ua";
    private final String MAILFROM_NAME = "АТ \"ПОЛТАВАОБЛЕНЕРГО\"";

//    public void sendMailDefault(String from, String to, String subject, String msg) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(msg);
//       mailSender.send(message);
//    }
// Code
    public void sendMailtest(String to, String subject, String dear, String content, String base64pdf) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();

                SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom("noreply@poe.pl.ua");
        sm.setTo(to);
        sm.setSubject(subject);
        sm.setText("тестовое сообщение отправки файла пдф");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(),MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(sm.getSubject());
            helper.setText(String.format(
                    sm.getText(), dear, content));

            File tempFile = File.createTempFile("testfile", "pdf", null);

            byte[] decodePdf = Base64.getDecoder().decode(base64pdf.getBytes(StandardCharsets.UTF_8));
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(decodePdf);

//		FileSystemResource file = new FileSystemResource("C:\\log.txt");
            helper.addAttachment("test.pdf", tempFile);

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }

        public MessageReturnPojo sendMail(String to, String subject, String base64pdf, String mailbody) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();

        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom("noreply@poe.pl.ua");
        sm.setTo(to);
        sm.setSubject(subject);
        if (mailbody != null && !mailbody.isEmpty()) {
            sm.setText(mailbody);
        } else {    
            sm.setText(subject);
        }        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(), MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(subject);
            helper.setText(String.format(sm.getText().replaceAll("%", "%%")));

           
            if (base64pdf != null) {
                
                File tempFile = File.createTempFile("рахунок", "pdf", null);
            
            
                byte[] decodePdf = Base64.getDecoder().decode(base64pdf.getBytes(StandardCharsets.UTF_8));
                
                
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(decodePdf);
                fos.close();

//		FileSystemResource file = new FileSystemResource("C:\\log.txt");
                helper.addAttachment("рахунок.pdf", tempFile);
              

            } 
            
   

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        
        try { 
            mailSender.send(message);
            return new MessageReturnPojo(1, subject + " - Успішно");
        }catch(MailSendException ex){
            return new MessageReturnPojo(-1, ex.getMessage());
        }
        
       
    }
        
        public MessageReturnPojo sendMailAttachFile(String[] to, String subject, String filedir, String mailbody, String filename) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();

                SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom(mailfrom);
        sm.setTo(to);
        sm.setSubject(subject);
        if (mailbody != null && !mailbody.isEmpty()) {
            sm.setText(mailbody);
        } else {    
            sm.setText(subject);
        }        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(),MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(sm.getSubject());
            helper.setText(String.format(sm.getText()));

//		FileSystemResource file = new FileSystemResource(filedir);
                File file = new File(filedir);
                helper.addAttachment(filename, file);

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        
        try { 
            mailSender.send(message);
            return new MessageReturnPojo(1, subject + " - ok");
        }catch(MailSendException ex){
            return new MessageReturnPojo(-1, ex.getMessage());
        }
        
       
    }
        
    public MessageReturnPojo sendSimpleMessage(String[] to, String subject, String mailbody) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();

        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom("noreply@poe.pl.ua");
        sm.setTo(to);
        sm.setSubject(subject);
        if (mailbody != null && !mailbody.isEmpty()) {
            sm.setText(mailbody);
        } else {    
            sm.setText(subject);
        }        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(),MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(sm.getSubject());
            helper.setText(String.format(sm.getText()));
   
            mailSender.send(message);
            return new MessageReturnPojo(1, subject + " - Успішно");
            
        } catch (MessagingException e) {
            throw new MailParseException(e);
            
        }catch(MailSendException ex){
            return new MessageReturnPojo(-1, ex.getMessage());
        }
        
       
    }
    
public MessageReturnPojo sendSingleSimpleMessage(String to, String subject, String mailbody) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();

        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom("noreply@poe.pl.ua");
        sm.setTo(to);
        sm.setSubject(subject);
        if (mailbody != null && !mailbody.isEmpty()) {
            sm.setText(mailbody);
        } else {    
            sm.setText(subject);
        }        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(),MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(sm.getSubject());
            helper.setText(String.format(sm.getText()));
   
            mailSender.send(message);
            return new MessageReturnPojo(1, subject + " - Успішно");
            
        } catch (MessagingException e) {
            throw new MailParseException(e);
            
        }catch(MailSendException ex){
            return new MessageReturnPojo(-1, ex.getMessage());
        }
        
       
    }
    public MessageReturnPojo sendMailb64(String to, String subject, String body, String file_name, String file_type, String base64pdf) throws IOException {

        MimeMessage message = mailSender.createMimeMessage();
        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setFrom("noreply@poe.pl.ua");
        sm.setTo(to);
        sm.setSubject(subject);
        sm.setText(body);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sm.getFrom(),MAILFROM_NAME);
            helper.setTo(sm.getTo());
            helper.setSubject(sm.getSubject());
            helper.setText(String.format(
                    sm.getText(), body));
             String filetype = file_name.substring(file_name.lastIndexOf(".") + 1);
            
            File tempFile = File.createTempFile(file_name.replace(filetype, ""), filetype, null);

            byte[] decodePdf = Base64.getDecoder().decode(base64pdf.getBytes(StandardCharsets.UTF_8));
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(decodePdf);

//		FileSystemResource file = new FileSystemResource("C:\\log.txt");
//            helper.addAttachment(file_name + "." +  file_type, tempFile);
            helper.addAttachment(file_name, tempFile);
mailSender.send(message);
            return new MessageReturnPojo(1,"Надіслано успішно");
        } catch (MessagingException e) {
            return new MessageReturnPojo(1,e.getMessage());
        }
       
    }

}
