package Model.User;

import java.util.regex.Pattern;

/**
 * Static container for validating user details.
 *
 * Stephen Fleming 100963909
 */
public class InputValidator {
    private static String DIGIT_INPUT_REGEX = "([0-9]{4,10})+";
    private static String TEXT_INPUT_REGEX = "([A-Za-z ]{2,15})+";

    /**
     * Determine if passed ID is valid user ID
     * @param id input to test
     * @return True, if a valid ID
     */
    public static boolean validUserId(String id) {
        return inputMatchesRegex(id, DIGIT_INPUT_REGEX);
    }

    /**
     * Determine if passed name is valid name
     * @param name input to test
     * @return True, if name is valid
     */
    public static boolean validUserName(String name) {
        return inputMatchesRegex(name, TEXT_INPUT_REGEX);
    }

    /**
     * Determine if passed school is a valid name
     * @param school input to test
     * @return True, if school name is valid
     */
    public static boolean validUserSchool(String school) {
        return inputMatchesRegex(school, TEXT_INPUT_REGEX);
    }

    /**
     * Take an input and a regex pattern, return true if input matches pattern.
     * @param input Input string to test
     * @param pattern   Pattern to compare to
     * @return  True, if input matches pattern
     */
    private static boolean inputMatchesRegex(String input, String pattern) {
        //source https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
        Pattern regex = Pattern.compile(pattern);
        return regex.matcher(input).matches();

        //end source
    }
}
