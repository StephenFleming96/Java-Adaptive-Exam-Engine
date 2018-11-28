package Model.User;

import AEE.Callbacks.TimerLifecycle;
import AEE.Timer.TimerType;
import AEE.TimerManager;

import java.util.Random;
import java.util.Timer;

/**
 * Model for User
 * Contains details specific to a user, including id, name, school and their PIN.
 *
 * Stephen Fleming 100963909
 */
public class User {
    public String userId;
    public String userName;
    public String userSchool;

    public boolean termsConditionsAgreed;

    public String PIN;

    public boolean stillCanLogin;

    /**
     * user
     * @param ID    ID string, should be all digits
     * @param name  Name string, should be alpha characters, optional space
     * @param school    School name string, should be alpha characters, optional space
     * @param TCConfirmed   User accepts terms and conditions
     */
    public User(String ID, String name, String school, boolean TCConfirmed) {
        userId = ID;
        userName = name;
        userSchool = school;

        termsConditionsAgreed = TCConfirmed;

        stillCanLogin = true;
    }


    /**
     * Generate a new PIN for the user, store it in this.PIN
     */
    public void generatePIN() {
        char[] chars = "1234567890".toCharArray();
        String newPin = "";

        for (int i = 0; i < 4; i++) {
            newPin += chars[new Random().nextInt(chars.length)];
        }

        PIN = newPin;

        TimerManager.getInstance().setTimer(TimerType.PIN, 20, 00, new TimerLifecycle() {
            @Override
            public void onUpdate(int minutes, int seconds) { }

            @Override
            public void onFinished() {
                stillCanLogin = false;
            }
        });

        TimerManager.getInstance().startTimer(TimerType.PIN);
    }
}
