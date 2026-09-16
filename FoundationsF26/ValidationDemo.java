package validation;

/**
 * Simple standalone demo for TP1-12, TP1-14, and TP1-15.
 *
 * Run this class in Eclipse to verify the validators before integrating
 * them into the team's FoundationsF26 project.
 */
public class ValidationDemo {

    public static void main(String[] args) {

        System.out.println("=== TP1-12 Username Tests ===");
        testUsername("Tam");
        testUsername("tam123");
        testUsername("tam_bui");
        testUsername("tam-bui");
        testUsername("tam.bui");
        testUsername("tam&bui");
        testUsername("123tam");
        testUsername("_tam");
        testUsername("tam_");
        testUsername("tam&&bui");

        System.out.println("\n=== TP1-15 Email Tests ===");
        testEmail("tam@asu.edu");
        testEmail("tam.bui@gmail.com");
        testEmail("tam+school@gmail.com");
        testEmail("tam");
        testEmail("tam@");
        testEmail("@gmail.com");
        testEmail("tam@gmail");
        testEmail("tam..bui@gmail.com");

        System.out.println("\n=== TP1-14 Length Test ===");
        String longUsername = "a".repeat(InputValidator.MAX_USERNAME_LENGTH + 1);
        System.out.println(UserNameRecognizer.validate(longUsername));
    }

    private static void testUsername(String username) {
        String result = UserNameRecognizer.validate(username);

        if (result.isEmpty()) {
            System.out.println("ACCEPT  username: " + username);
        } else {
            System.out.println("REJECT  username: " + username + " -> " + result);
        }
    }

    private static void testEmail(String email) {
        String result = EmailValidator.validate(email);

        if (result.isEmpty()) {
            System.out.println("ACCEPT  email: " + email);
        } else {
            System.out.println("REJECT  email: " + email + " -> " + result);
        }
    }
}
