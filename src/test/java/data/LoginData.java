package data;

public class LoginData {

    // Valid credentials
    public static final String VALID_EMAIL = "sinatra@gmail.com";
    public static final String VALID_PASSWORD = "Sinatra@0212";

    // Invalid credentials
    public static final String INVALID_EMAIL = "invalid@example.com";
    public static final String INVALID_PASSWORD = "wrongpassword";

    // Empty credentials
    public static final String EMPTY_EMAIL = "";
    public static final String EMPTY_PASSWORD = "";

    // Registration data
    public static class Registration {
        public static final String FULL_NAME = "ramaa singh";
        public static final String EMAIL = "rama@gmail.com";
        public static final String PASSWORD = "rama@0212";
        public static final String CONFIRM_PASSWORD = "rama@0212";
        public static final String MISMATCHED_CONFIRM = "differentpassword";
    }

    // Test user data
    public static class TestUser {
        public static final String EMAIL = "rama@gmail.com";
        public static final String PASSWORD = "rama@0212";
    }

    // Invalid formats
    public static class InvalidData {
        public static final String INVALID_EMAIL_FORMAT = "notanemail";
        public static final String SHORT_PASSWORD = "123";
    }

}
