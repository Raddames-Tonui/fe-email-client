package emailsender.manual;

import java.util.Scanner;

import static emailsender.manual.ManualEmailSender.sendEmail;

public class Main {

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
                        default -> System.err.println("Invalid choice. Please enter 1-3.");
                    }
                } else {
                    System.err.println("Invalid input. Must be a number.");
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
            System.err.println("Error: " + e.getMessage());
        }
    }
}
