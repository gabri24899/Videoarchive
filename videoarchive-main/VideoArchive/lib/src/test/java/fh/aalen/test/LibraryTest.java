package fh.aalen.test;

// Importiert die zu testende Klasse aus dem Projekt
import _VideoArchive.Library;

// Importiert die TestNG-Annotation @Test
import org.testng.annotations.Test;

// Importiert statisch die Assert-Funktionen von TestNG, z. B. assertTrue()
import static org.testng.Assert.*;

/**
 * Einfache Testklasse, die demonstriert, wie mit TestNG getestet wird.
 * Es wird eine Methode getestet, die einen booleschen Wert zurückliefert.
 */
public class LibraryTest {

    // Diese Methode ist ein automatisierter Testfall.
    // Die Beschreibung hilft z. B. in Testberichten oder IDEs.
    @Test(description = "Prüft, ob someLibraryMethod true zurückliefert")
    public void testSomeLibraryMethod() {
        // Erstellt ein Objekt der zu testenden Klasse
        Library lib = new Library();

        // Führt die Methode aus und überprüft mit assertTrue, ob das Ergebnis true ist
        assertTrue(lib.someLibraryMethod());

        // Falls das Ergebnis false ist, schlägt der Test fehl
    }
}
