package Model.Test;

import java.util.Random;

/**
 * MathTestModel.java
 * Model for Math Test.
 * User must answer mathmatical questions.
 *
 * Stephen Fleming 100963909
 */
public class MathTestModel extends TestModel {
    public enum MathQuestionType { ADD, SUBTRACT, MULTIPLY, DIVIDE }

    /**
     * container for a math question
     */
    public class MathQuestionData extends TestDataContainer {
        public int firstNum;
        public int secondNum;

        public MathQuestionType qType;
        public float result; //answer

        public boolean correct;
    }

    private Random rGen;

    //upper bound for random generator
    private final int MAX_QUESTION_VALUE = 5;

    public MathTestModel() {
        super();

        totalTestTime.setMinutes(2);

        rGen = new Random(1234);
    }

    /**
     * Create a new unique question
     * @return The question data container
     */
    @Override
    public MathQuestionData getNextQuestion() {
        MathQuestionData q = null;

        if (!testCompleted) {
            q = generateNewUniqueTest();

            if (difficulty == 0)
                q.qType = (rGen.nextInt(10) > 5) ? MathQuestionType.SUBTRACT : MathQuestionType.ADD;
            else if (difficulty == 1)
                q.qType = MathQuestionType.MULTIPLY;
            else
                q.qType = MathQuestionType.DIVIDE;

            q.result = calculateExpression(q.firstNum, q.secondNum, q.qType);
        }

        questions.add(q);

        return q;
    }

    /**
     * Get the most recently generated question
     * @return
     */
    @Override
    public TestDataContainer getCurrentQuestion() {
        return questions.get(questions.size() - 1);
    }

    /**
     * Repeatedly generate a new test until a unique test is generated
     * @return New unique test question
     */
    private MathQuestionData generateNewUniqueTest() {
        MathQuestionData q;

        do {
            q = generateNewTest();
        } while (!testIsUnique(q));

        return q;
    }

    /**
     * @return A new MathQuestionData object
     */
    private MathQuestionData generateNewTest() {
        MathQuestionData question = new MathQuestionData();

        //must be greater than 0
        question.firstNum = rGen.nextInt(MAX_QUESTION_VALUE) + 2;
        question.secondNum = rGen.nextInt(MAX_QUESTION_VALUE) + 2;
        question.difficulty = difficulty;
        question.correct = false;

        return question;
    }

    /**
     * Determine if a question has been used before
     * @param newQ The MathQuestionData to evaluate
     * @return True if question is unique
     */
    private boolean testIsUnique(MathQuestionData newQ) {
        MathQuestionData q;
        for (TestDataContainer toCmp : questions) {
            q = (MathQuestionData)toCmp;
            if (q.firstNum == newQ.firstNum && newQ.secondNum == q.secondNum && newQ.difficulty == q.difficulty)
                return false;
        }

        return true;
    }

    /**
     * Calculate the value of an expression, given its inputs
     * @param a Number A
     * @param b Number B
     * @param exprType Expression type (+-*\)
     * @return Result of equation
     */
    private float calculateExpression(int a, int b, MathQuestionType exprType) {
        switch(exprType) {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return (float)a / (float)b;
            default:
                return -1;
        }
    }
}
