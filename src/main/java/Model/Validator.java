package Model;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Full validation is only needed during registration to prevent creation of invalid users.
// Login requires only basic validation (e.g. format checks), since the user already exists.
// Invalid users should not exist if registration validation is working correctly.
// However, if invalid users do exist (e.g. from legacy data), they should still be able to log in
// to allow account recovery, updates, or migration. (Admins will handle it :P)

public class Validator implements Serializable {
    private static String emailPattern = "([a-zA-Z0-9]+)(([._-])([a-zA-Z0-9]+))*(@)([a-z]+)(.)([a-z]{3})((([.])[a-z]{0,2})*)";
    // private static String namePattern = "([A-Z][a-z]+[\\s])+[A-Z][a-z]*"; // Unused for now, if we want to check names uncomment
    private static String passwordPattern = ".{4,}"; //Simple check for at least 4 characters
    private static String phonePattern = "\\d+";

    public Validator() {
        //
    }

    public static boolean validate(String pattern, String input){       
        Pattern regEx = Pattern.compile(pattern);       
        Matcher match = regEx.matcher(input);       
        return match.matches(); 
    }

    public static boolean validateEmail(String email) {
        return validate(emailPattern, email);
    }

    // public static boolean validateName(String name) {
    //     return validate(namePattern, name);
    // }

    public static boolean validatePassword(String password) {
        return validate(passwordPattern, password);
    }

    public static boolean validatePhone(String phone) {
        return validate(phonePattern, phone);
    }
}
