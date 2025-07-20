package emailsender.manual;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

/**
 * A utility class to manually send HTML emails using templates with inline (CID) images.
 * Template files are expected to be in the resources/templates directory.
 */
public class ManualEmailSender {

    // 1. Directory where email templates are stored (relative to classpath)
    private static final String TEMPLATE_DIR = "templates/";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String templateFile = null;

            // 2. Prompt user to select email template
            while (templateFile == null) {
                System.out.println("Choose an email template to send:");
                System.out.println("  1. newsletter.html");
                System.out.println("  2. trial-expiration.html");
                System.out.println("  3. welcome-email.html");
                System.out.print("Enter template number (1-3): ");

                if (scanner.hasNextInt()) {
                    int templateChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (templateChoice) {
                        case 1 -> templateFile = "newsletter.html";
                        case 2 -> templateFile = "trial-expiration.html";
                        case 3 -> templateFile = "welcome-email.html";
                        default -> System.err.println("❌ Invalid choice. Please enter 1-3.");
                    }
                } else {
                    System.err.println("❌ Invalid input. Must be a number.");
                    scanner.next(); // Consume invalid token
                }
            }

            // 3. Ask user for recipient email
            System.out.print("Enter recipient email: ");
            String toEmail = scanner.nextLine().trim();

            // 4. Ask for email subject
            System.out.print("Enter email subject: ");
            String subject = scanner.nextLine().trim();

            // 5. Send the email
            sendEmail(templateFile, toEmail, subject);

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

        // 2. Define SMTP configuration properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");              // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");   // Enable STARTTLS for encryption
        props.put("mail.smtp.host", "smtp.gmail.com");    // SMTP server host
        props.put("mail.smtp.port", "587");               // STARTTLS port (587)

        // 3. Define sender credentials (use App Password for Gmail)
        final String senderEmail = "raddamestonui48@gmail.com";
        final String senderPassword = "midj ypab telg ";

        // 4. Create authenticated mail session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // 5. Create the HTML body part
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

        // 6. Create the embedded image part using CID
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource fds = new FileDataSource("src/main/resources/images/tatua-logo.png");
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("Content-ID", "<tatuaLogo>"); // Must match the CID in HTML
        imagePart.setDisposition(MimeBodyPart.INLINE);    // Inline image (not attachment)

        // 7. Combine HTML and image into multipart
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);
        multipart.addBodyPart(imagePart);

        // 8. Compose the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(multipart);

        // 9. Send the email
        Transport.send(message);
        System.out.println("✅ Email sent successfully to " + toEmail);
    }

    /**
     * Loads an HTML file from the resources/templates/ directory in the classpath.
     *
     * @param fileName the name of the file
     * @return the contents of the file as a string
     * @throws IOException if file not found or read fails
     */
    public static String loadTemplateContent(String fileName) throws IOException {
        String fullPath = TEMPLATE_DIR + fileName;

        // 1. Load file from classpath
        InputStream inputStream = ManualEmailSender.class
                .getClassLoader()
                .getResourceAsStream(fullPath);

        if (inputStream == null) {
            throw new IOException("Template not found in resources: " + fullPath);
        }

        // 2. Read file content line by line
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            return content.toString();
        }
    }
}