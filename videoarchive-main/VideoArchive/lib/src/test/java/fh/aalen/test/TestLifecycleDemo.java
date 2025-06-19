package fh.aalen.test;

import org.testng.annotations.*;
import static org.testng.Assert.*;

/**
 * Demonstriert die verschiedenen Lebenszyklus-Annotationen von TestNG.
 * Diese helfen, Testumgebungen vorzubereiten und aufzuräumen – sehr nützlich für komplexere Teststrukturen.
 */
public class TestLifecycleDemo {

    // Wird ein einziges Mal VOR allen Tests der gesamten Suite aufgerufen
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite: wird einmal VOR allen Tests der Suite ausgeführt");
    }

    // Wird ein einziges Mal NACH allen Tests der gesamten Suite aufgerufen
    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite: wird einmal NACH allen Tests der Suite ausgeführt");
    }

    // Wird vor jedem <test>-Block in testng.xml aufgerufen
    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest: wird vor einem <test> Block in testng.xml ausgeführt");
    }

    // Wird nach jedem <test>-Block in testng.xml aufgerufen
    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest: wird nach einem <test> Block in testng.xml ausgeführt");
    }

    // Wird einmal vor allen @Test-Methoden in dieser Klasse aufgerufen
    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass: wird einmal vor der ersten @Test-Methode in dieser Klasse ausgeführt");
    }

    // Wird einmal nach allen @Test-Methoden in dieser Klasse aufgerufen
    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass: wird einmal nach der letzten @Test-Methode in dieser Klasse ausgeführt");
    }

    // Wird vor jeder einzelnen @Test-Methode ausgeführt
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod: wird vor jeder einzelnen @Test-Methode ausgeführt");
    }

    // Wird nach jeder einzelnen @Test-Methode ausgeführt
    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod: wird nach jeder einzelnen @Test-Methode ausgeführt");
    }

    // Wird einmal vor allen Tests einer bestimmten Gruppe (z. B. "crud") ausgeführt
    @BeforeGroups("crud")
    public void beforeCrudGroups() {
        System.out.println("BeforeGroups: Vorbereitung für die Gruppe 'crud'");
    }

    // Wird einmal nach allen Tests einer bestimmten Gruppe ausgeführt
    @AfterGroups("crud")
    public void afterCrudGroups() {
        System.out.println("AfterGroups: Aufräumen nach der Gruppe 'crud'");
    }

    // Ein einfacher Test, der zur Gruppe "demo" gehört und die Priorität 1 hat
    @Test(groups = {"demo"}, priority = 1, description = "Einfacher Demo-Test")
    public void demoTest() {
        System.out.println("Running demoTest");
    }

    /**
     * Ein Test, dem über die testng.xml ein Parameter übergeben wird.
     * Das ist z. B. für Konfigurationen oder Umgebungsunterschiede nützlich.
     */
    @Parameters({"greeting"})
    @Test(description = "Parametrisierter Test mit Übergabe aus testng.xml")
    public void parameterizedTest(String greeting) {
        System.out.println("Greeting parameter: " + greeting);

        // Prüft, ob der Parameter nicht null ist
        assertNotNull(greeting);
    }
}
