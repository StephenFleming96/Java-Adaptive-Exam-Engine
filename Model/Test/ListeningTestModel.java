package Model.Test;

/**
 * ListeningTestModel.java
 * Model for the Listening Test.
 * User must listen to an audio file and identify keywords from the recording.
 *
 * Stephen Fleming 100963909
 */
public class ListeningTestModel extends TestModel {
    public class ListeningTestData extends TestDataContainer {
        public String file;
        public String[] keywords;
        public String[] incorrectWords;

        public ListeningTestData(String fName, String[] words, String[] falseWords, int diff) {
            file = fName;

            keywords = words;
            incorrectWords = falseWords;

            difficulty = diff;

            totalTestTime.setMinutes(2);
        }
    }

    public ListeningTestModel() {
        loadQuestionData();
    }

    @Override
    public ListeningTestData getNextQuestion() {
        ListeningTestData nextQ = (ListeningTestData)(questions.get(difficulty));

        if (nextQ.completed)
            testCompleted = true;
        else
            nextQ.completed = true;

        return nextQ;
    }

    @Override
    public ListeningTestData getCurrentQuestion() {
        return getNextQuestion();

    }

    private void loadQuestionData() {
        questions.add(new ListeningTestData("easy0.wav", new String[] {"SIMPLE"}, new String[] {"SAMPLE"},0));
        questions.add(new ListeningTestData("medium0.wav", new String[] {"COMPLEX", "COMPOUND"}, new String[] {"SIMPLEX", "COMPILE"},1));
        questions.add(new ListeningTestData("hard0.wav", new String[] {"RELATIVE", "CONJUNCTION", "FINAL"}, new String[] {"JUNCTION", "REFLEX", "FIRMER"},2));
    }
}
