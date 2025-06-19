package fh.aalen.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Diese einfache Testklasse demonstriert den Einsatz von @BeforeMethod und @Test.
 * Sie ist gut geeignet, um den grundsätzlichen Ablauf von TestNG zu verstehen.
 */
public class SimpleTest {

    // Attribut, das vor jedem Test gesetzt wird
    private String value;

    /**
     * Diese Methode wird vor jedem einzelnen Test ausgeführt.
     * Sie initialisiert das Attribut "value" mit dem Wert "set".
     */
    @BeforeMethod
    public void setup() {
        value = "set";
    }

    /**
     * Diese Testmethode prüft, ob das Attribut "value" korrekt gesetzt wurde.
     * Wenn der Wert nicht "set" ist, schlägt der Test fehl.
     */
    @Test
    public void testValue() {
        assert "set".equals(value);  // Erwartet, dass value den String "set" enthält
    }
}
