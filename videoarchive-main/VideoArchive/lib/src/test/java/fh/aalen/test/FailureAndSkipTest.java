package fh.aalen.test;

import fh.aalen.video.Video;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FailureAndSkipTest {

    /**
     * Creates a {@link Video} and performs an intentionally wrong assertion on
     * one of its properties so the test fails. This demonstrates how TestNG
     * reports failed tests using domain classes from the video archive.
     */
    @Test(description = "Failing test using the Video domain object")
    public void failingVideoTest() {
        Video video = new Video("Blade Runner", "16", "Sci-fi classic", "SciFi");
        // Intentionally wrong expected title to force a failure
        assertEquals(video.getTitle(), "Star Wars", "Titles should not match");
    }

    /**
     * Example for a skipped test that references the video archive. Imagine a
     * future feature that searches videos by rating which is not implemented
     * yet. We skip this test until the feature exists.
     */
    @Test(description = "Skipped test referencing a future video feature")
    public void skippedSearchFeatureTest() {
        throw new SkipException("Search by rating not implemented yet");
    }
}