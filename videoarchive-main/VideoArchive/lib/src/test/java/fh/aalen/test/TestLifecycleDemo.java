package fh.aalen.test;

import org.testng.annotations.*;
import static org.testng.Assert.*;

/**
 * Demonstrates various TestNG lifecycle annotations.
 */
public class TestLifecycleDemo {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite: runs once before all tests");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite: runs once after all tests");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest: runs before <test> tag in testng.xml");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest: runs after <test> tag in testng.xml");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass: runs before the first method in the class");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass: runs after the last method in the class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod: runs before each @Test method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod: runs after each @Test method");
    }

    @BeforeGroups("crud")
    public void beforeCrudGroups() {
        System.out.println("BeforeGroups: setup for group 'crud'");
    }

    @AfterGroups("crud")
    public void afterCrudGroups() {
        System.out.println("AfterGroups: teardown for group 'crud'");
    }

    @Test(groups = {"demo"}, priority = 1, description = "Simple demo test")
    public void demoTest() {
        System.out.println("Running demoTest");
    }

    /**
     * Example showing parameter injection from testng.xml using @Parameters.
     */
    @Parameters({"greeting"})
    @Test(description = "Parameterized test using @Parameters")
    public void parameterizedTest(String greeting) {
        System.out.println("Greeting parameter: " + greeting);
        assertNotNull(greeting);
    }
}