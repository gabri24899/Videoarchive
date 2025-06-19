package fh.aalen.test;

import _VideoArchive.Library;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 
Simple test demonstrating a basic TestNG @Test method with assertions.*/
public class LibraryTest {

    @Test(description = "Verify someLibraryMethod returns true")
    public void testSomeLibraryMethod() {
        Library lib = new Library();
        assertTrue(lib.someLibraryMethod());
    }
}