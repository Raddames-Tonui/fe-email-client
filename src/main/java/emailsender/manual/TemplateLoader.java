package emailsender.manual;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TemplateLoader {

    private static final String TEMPLATE_DIR = "templates/";

    /**
     * Loads the HTML template content from the templates directory in the resources folder.
     * @param fileName The name of the template file (e.g., "welcome-email.html").
     * @return The content of the HTML file as a string.
     * @throws IOException if the file is not found or can't be read.
     */
    public static String loadTemplateContent(String fileName) throws IOException {
        String fullPath = TEMPLATE_DIR + fileName;

        InputStream inputStream = TemplateLoader.class
                .getClassLoader()
                .getResourceAsStream(fullPath);

        if (inputStream == null) {
            throw new IOException("Template not found in resources: " + fullPath);
        }

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
