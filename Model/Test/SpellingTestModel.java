package Model.Test;

import java.util.ArrayList;

/**
 * SpellingTestModel.java
 * Model for Spelling Test.
 * User must correctly spell words given by a hint.
 *
 * Stephen Fleming 100963909
 */
public class SpellingTestModel extends TestModel {

    //container for a single word from the test
    public class SpellingTestWordData extends TestDataContainer {
        String word;    // UPPERCASE word to spell
        String hint;    // A description of the word
        boolean completed;

        public SpellingTestWordData(String wrd, String hnt, int diff) {
            word = wrd;
            hint = hnt;
            difficulty = diff;
            completed = false;
        }

        public String getWord() {
            return word;
        }

        public String getHint() {
            return hint;
        }

        public void setCompleted(boolean val) {
            completed = val;
        }

        public boolean isCompleted() {
            return completed;
        }

        public Character getFirstLetter() {
            return word.charAt(0);
        }
    }

    private ArrayList<TestDataContainer> completedWords;

    public SpellingTestModel() {
        super();

        totalTestTime.setSeconds(20);

        completedWords = new ArrayList<TestDataContainer>();

        questions.add(new SpellingTestWordData("GALAXY", "Big, round, space thing", 0));
        questions.add(new SpellingTestWordData("GRAVITY", "What goes up must come down", 0));
        questions.add(new SpellingTestWordData("GERMANY", "Central European country", 0));
        questions.add(new SpellingTestWordData("GEOGRAPHY", "Natural features such as hills, mountains, plains, etc", 0));

        questions.add(new SpellingTestWordData("JOURNALIST", "News people", 1));
        questions.add(new SpellingTestWordData("JOURNEY", "Travel", 1));
        questions.add(new SpellingTestWordData("JEWELRY", "Fancy metals and rare stones are made into this", 1));
        questions.add(new SpellingTestWordData("JUDGEMENT", "Opinion or decision", 1));

        questions.add(new SpellingTestWordData("ZOOLOGY", "Study of animals", 2));
        questions.add(new SpellingTestWordData("ZOOMING", "Moving quickly", 2));
        questions.add(new SpellingTestWordData("ZOMBIES", "Fictional, previously dead, plural", 2));
        questions.add(new SpellingTestWordData("ZODIAC", "Fictional, related to stars", 2));
    }

    @Override
    public TestDataContainer getCurrentQuestion() {
        return completedWords.get(completedWords.size() - 1);
    }

    @Override
    public TestDataContainer getNextQuestion() {
        for (TestDataContainer d : questions) {
            if (d.difficulty == difficulty) {
                if (!completedWords.contains(d)) {
                    completedWords.add(d);

                    return d;
                }
            }
        }

        //no more options left, return
        testCompleted = true;

        return null;
    }
}