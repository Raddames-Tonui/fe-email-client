package emailsender.manual;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.*;
import java.util.Properties;


import static emailsender.manual.TemplateLoader.loadTemplateContent;
import static emailsender.manual.EnvLoader.get;

/**
 * A utility class to manually send HTML emails using templates with inline (CID) images.
 * Template files are expected to be in the resources/templates directory.
 */
public class ManualEmailSender {

    static final String senderEmail = get("SENDER_EMAIL");
    static final String senderPassword = get("SENDER_PASSWORD");

    // 1. Directory where email templates are stored (relative to classpath)
    private static final String TEMPLATE_DIR = "";



    /**
     * Sends an email with HTML content and an embedded logo image using Content-ID (CID).
     *
     * @param templateFileName the file name of the email HTML template
     * @param toEmail recipient email address
     * @param subject subject of the email
     */
    public static void sendEmail(String templateFileName, String toEmail, String subject)
            throws MessagingException, IOException {

        // 1. Load HTML content from template
        String htmlContent = loadTemplateContent(templateFileName);

        // 2. Extract name from email and inject if placeholder exists
        String extractedName = extractNameFromEmail(toEmail);
        if (htmlContent.contains("{{name}}")) {
            htmlContent = htmlContent.replace("{{name}}", extractedName);
        }

        // 3. Define SMTP configuration properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");              // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");   // Enable STARTTLS for encryption
        props.put("mail.smtp.host", "smtp.gmail.com");    // SMTP server host
        props.put("mail.smtp.port", "587");               // STARTTLS port (587)


        // 5. Create authenticated mail session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // 6. Create the HTML body part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

        // 7. Create the embedded image part using CID
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource fds = new FileDataSource("src/main/resources/images/tatua-logo.png");
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("Content-ID", "<tatuaLogo>"); // Must match the CID in HTML
        imagePart.setDisposition(MimeBodyPart.INLINE);    // Inline image (not attachment)

        // 8. Combine HTML and image into multipart
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);
        multipart.addBodyPart(imagePart);

        // 9. Compose the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(multipart);

        // 10. Send the email
        Transport.send(message);
        System.out.println("Email sent successfully to " + toEmail);
    }

    /**
     * Extracts the portion of the email address before the '@' and capitalizes the first letter.
     * For example: "john.doe@example.com" → "John.doe"
     */
    private static String extractNameFromEmail(String email) {
        if (email == null || !email.contains("@")) return "there";
        String namePart = email.substring(0, email.indexOf('@'));
        if (namePart.isEmpty()) return "there";
        return Character.toUpperCase(namePart.charAt(0)) + namePart.substring(1);
    }
}
