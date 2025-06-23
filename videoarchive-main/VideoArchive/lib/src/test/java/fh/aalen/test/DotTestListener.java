package fh.aalen.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;


import org.testng.TestListenerAdapter;

public class DotTestListener extends TestListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(DotTestListener.class);


    //wird jedesmal ausgeführt bei Fehler eines Tests
    @Override
    public void onTestFailure(ITestResult tr) {
        log.info("\n\n Test fehlgeschlagen: " + tr.getName() + "   \n" );
    }

    //wird jedesmal ausgeführt wenn ein Test geskipped wird
    @Override
    public void onTestSkipped(ITestResult tr) {
        log.info("\n\n Test übersprungen: " + tr.getName() + "  \n ");
    }

   //wird jedesmal ausgeführt wenn ein Test erfolgreich war 
    @Override
    public void onTestSuccess(ITestResult tr) {
       log.info("\n\n Test erfolgreich " + tr.getName()+ "   \n");
    }
}