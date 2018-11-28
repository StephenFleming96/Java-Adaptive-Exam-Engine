package AEE;

import AEE.Timer.AsyncTimer;
import Controller.Test.TestController;
import Model.Test.ListeningTestModel;
import Model.Test.MathTestModel;
import Model.Test.SpellingTestModel;
import View.Test.ListeningTestView;
import View.Test.MathTestView;
import View.Test.SpellingTestView;

import View.View;

import java.util.*;

/**
 * TestManager.java
 * Manages references to test controllers.
 *
 * Stephen Fleming 100963909
 */
public class TestManager {
    private TestController currentTest;
    private HashMap<String, TestController> testMap;

    private ArrayList<TestResultStamp> results;
    private TestResultStamp resultTotal = null;

    private static TestManager instance;

    private TestManager() {
        testMap = new HashMap<String, TestController>();
        results = new ArrayList<TestResultStamp>();

        loadTests();
    }

    /**
     * Get singleton instance of TestManager
     * @return Instance of TestManager
     */
    public synchronized static TestManager getInstance() {
        if (instance == null)
            instance = new TestManager();

        return instance;
    }

    /**
     * Begin the currently selected test.
     * Usually related to timing.
     */
    public void runCurrentTest() {
        currentTest.getView().onLoad();
    }

    /**
     * @return Current test view, as Renderable.
     */
    public View getViewForCurrentTest() {
        return currentTest.getView();
    }

    /**
     * Activate the already loaded test with the name testName
     * @param testName  Accurate name for pre-loaded test
     */
    public void activateTest(String testName) {
        currentTest = testMap.get(testName);
    }

    public synchronized ArrayList<String> getTestNames() {
        ArrayList<String> result = new ArrayList<String>();

        for (String s : testMap.keySet()) {
            result.add(s);
        }

        return result;
    }

    public HashMap<String, TestController> getTestMap() {
        return testMap;
    }

    public void onTestTimeout() {
        currentTest.stopTest();
    }

    public void setCurrentSetComplete() {
        if (currentTest != null)
            currentTest.getTest().setTestCompleted(true);
    }

    /**
     * Take a test name and return the total results for that test
     * @param testName
     * @return
     */
    public TestResultStamp getResultForTest(String testName) {
        for (String s : testMap.keySet()) {
            if (s.equals(testName))
                return testMap.get(s).getTest().getTotalResultForTest();
        }

        return null;
    }

    /**
     * @return An Arraylist containing all result stamps from all tests
     */
    public ArrayList<TestResultStamp> getResultList() {
        if (results.size() == 0) {
            for (TestController c : testMap.values()) {
                c.getTest().getTestResult().forEach(tRes ->  results.add(tRes) );
            }

            //in debug mode
            if(results.size() == 0)
                fakeResults();

            //sort results
            //source: https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
            Collections.sort(results, new Comparator<TestResultStamp>() {
                @Override
                public int compare(TestResultStamp lhs, TestResultStamp rhs) {
                    int leftTimestamp = lhs.timeStamp.toSecondsTotal();
                    int rightTimestamp = rhs.timeStamp.toSecondsTotal();
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return (leftTimestamp > rightTimestamp) ? -1 : ((leftTimestamp < rightTimestamp) ? 1 : 0);
                }
            });
            //end source
        }

        return results;
    }

    /**
     * Create a TestResultStamp containing combined results for all tests
     * @return
     */
    public TestResultStamp getFinalTestResults() {
        if (results.size() == 0)
            getResultList();

        resultTotal = new TestResultStamp();

        results.forEach(result -> {
            resultTotal.correct += result.correct;
            resultTotal.incorrect += result.incorrect;
            resultTotal.score += result.score;
        });

        return resultTotal;
    }

    /**
     *  Set all tests completed.
     *  Used when main timer runs out
     */
    public void setAllTestsComplete() {
        testMap.values().forEach(test -> {
            test.getTest().setTestCompleted(true);
        });

        AdaptiveExamEngine.getUiController().refreshViews();
    }

    /**
     * create the test controllers and load them into the map
     */
    private void loadTests() {
        TestController[] tests = {
                new TestController("Spelling", new SpellingTestView(), new SpellingTestModel()),
                new TestController("Mathematics", new MathTestView(), new MathTestModel()),
                new TestController("Listening", new ListeningTestView(), new ListeningTestModel())
        };

        for(TestController t : tests) {
          testMap.put(t.getTestName(), t);
        }
    }

    /**
     * Generate fake test results for debugging
     */
    private void fakeResults() {
        Random r = new Random(9993);

        TestResultStamp tmp;

        for (int i = 0; i < 50; i++) {
            tmp = new TestResultStamp();
            if (r.nextInt(15) > 4) {
                tmp.correct = 1;
                tmp.incorrect = 0;
            } else {
                tmp.incorrect = 1;
                tmp.correct = 0;
            }

            tmp.timeStamp = AsyncTimer.generateFakeStamp(r.nextInt(14));

            results.add(tmp);
        }
    }
}

