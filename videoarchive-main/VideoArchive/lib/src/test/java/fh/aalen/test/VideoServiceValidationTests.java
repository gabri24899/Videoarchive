package fh.aalen.test;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import fh.aalen.video.Video;

public class VideoServiceValidationTests extends AbstractVideoTestBase {

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void testAddNullVideo() {
        videoService.addVideo(null);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testUpdateNonExistentVideo() {
        Video update = new Video("Nicht vorhanden", "12", "Update", "SciFi");
        videoService.updateVideo(99999L, update);
    }

    @Test
    public void testDeleteNonExistentVideo() {
        videoService.deleteVideo(88888L);
        assertTrue(true);
    }
}
