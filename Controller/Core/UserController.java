package Controller.Core;

import Model.User.User;

/**
 * UserController.java
 * Manages currently logged in user, creates new user
 *
 * Stephen Fleming 100963909
 */

public class UserController {
    private User currentUser;

    public UserController() { }

    /**
     *
     * @param ID
     * @param name
     * @param school
     * @param TCConfirmed
     * @return
     */
    public boolean logUserIntoSystem (String ID, String name, String school, boolean TCConfirmed) {
        //check if current user in

        if (!TCConfirmed)
            return false;

        currentUser = new User(ID, name, school, TCConfirmed);
        currentUser.generatePIN();

        return true;
    }

    /**
     *
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
