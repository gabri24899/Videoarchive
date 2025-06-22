package fh.aalen.test;

import static org.testng.Assert.*;

import java.util.Optional;

import org.testng.annotations.Test;

import fh.aalen.video.Video;

public class NegativeScenarioTests extends AbstractVideoTestBase {

    @Test(priority = 6, expectedExceptions = RuntimeException.class, groups = {"negative"})
    public void testUpdateVideoWithInvalidIdThrowsException() {
        Video update = new Video("Nicht vorhanden", "0", "Kein Film", "None");
        videoService.updateVideo(9999L, update);
    }

    @Test(priority = 7, groups = {"negative", "demo"})
    public void testAddAndGetVideoFail() throws InterruptedException {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(video);
        Thread.sleep(10000);

        Optional<Video> found = videoService.getVideoById(latestVideo.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Spongebob");
    }
}
