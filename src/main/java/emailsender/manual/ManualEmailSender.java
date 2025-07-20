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
 * Utility class for sending HTML emails with embedded images using JavaMail API.
 *
 * The email templates are HTML files stored in the resources directory,
 * and they may include placeholders like {{name}} and CID references to images.
 */
public class ManualEmailSender {

    /**
     * Retrieve sender credentials from environment (via EnvLoader abstraction)
     * */
    static final String senderEmail = get("SENDER_EMAIL");
    static final String senderPassword = get("SENDER_PASSWORD");

    // Directory for templates – currently unused but can be used to specify path prefix
    private static final String TEMPLATE_DIR = "";

    /**
     * Sends an HTML email with an embedded image using a specified template.
     *
     * @param templateFileName File name of the HTML template (e.g., "welcome.html")
     * @param toEmail Recipient email address
     * @param subject Subject line of the email
     * @throws MessagingException if there's an error during email composition or sending
     * @throws IOException if template file reading fails
     */
    public static void sendEmail(String templateFileName, String toEmail, String subject)
            throws MessagingException, IOException {

        // 1. Load the HTML email template content
        String htmlContent = loadTemplateContent(templateFileName);

        // 2. Personalize the email by replacing the placeholder with the user's name
        String extractedName = extractNameFromEmail(toEmail);
        if (htmlContent.contains("{{name}}")) {
            htmlContent = htmlContent.replace("{{name}}", extractedName);
        }

        // 3. Define SMTP configuration properties for sending through Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");                 // SMTP authentication enabled
        props.put("mail.smtp.starttls.enable", "true");      // Enable STARTTLS encryption
        props.put("mail.smtp.host", "smtp.gmail.com");       // Gmail SMTP server
        props.put("mail.smtp.port", "587");                  // Port for TLS connection

        // 4. Create a mail session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // 5. Create the HTML part of the email body
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

        // 6. Create the image part (to be embedded inline using CID)
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource fds = new FileDataSource("src/main/resources/images/tatua-logo.png"); // Path to image
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("Content-ID", "<tatuaLogo>"); // Must match src="cid:tatuaLogo" in HTML
        imagePart.setDisposition(MimeBodyPart.INLINE);    // Prevents image from appearing as an attachment

        // 7. Combine both parts into a multipart message
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);  // Add HTML body
        multipart.addBodyPart(imagePart); // Add image as inline content

        // 8. Construct the final email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(multipart); // Set the multipart (HTML + image)

        // 9. Send the email using the SMTP Transport
        Transport.send(message);
        System.out.println("Email sent successfully to " + toEmail);
    }

    /**
     * Extracts and capitalizes the first part of the email before '@'.
     * E.g., john.doe@example.com → John.doe
     *
     * @param email the recipient email address
     * @return extracted name or "there" if invalid
     */
    private static String extractNameFromEmail(String email) {
        if (email == null || !email.contains("@")) return "there";
        String namePart = email.substring(0, email.indexOf('@'));
        if (namePart.isEmpty()) return "there";
        return Character.toUpperCase(namePart.charAt(0)) + namePart.substring(1);
    }
}
