package fh.aalen.test;

import org.testng.ITestResult;

import org.testng.TestListenerAdapter;

public class DotTestListener extends TestListenerAdapter {
    private int m_count = 0;

    @Override
    public void onTestFailure(ITestResult tr) {
    	log("\n\n❌ Test fehlgeschlagen: " + tr.getName() + "   \n" );
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
    	log("\n\n⚠️ Test übersprungen: " + tr.getName() + "  \n ");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        log("\n\n✅ Test erfolgreich " + tr.getName()+ "   \n");
    }

    private void log(String string) {
        System.out.print(string);
        if (++m_count % 40 == 0) {
            System.out.println(); // Zeilenumbruch nach 40 Zeichen
        }
    }
}
