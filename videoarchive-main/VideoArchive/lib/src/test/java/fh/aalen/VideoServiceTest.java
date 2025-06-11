package fh.aalen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import fh.aalen.video.Video;
import fh.aalen.video.VideoService;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest
public class VideoServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private VideoService videoService;

    @Test
    public void testGetVideoListIsNotNull() {
        List<Video> videos = videoService.getVideoList();
        assertNotNull(videos, "Die Liste darf nicht null sein");
        assertTrue(videos.size() > 0, "Es sollte mindestens ein Video geben");
       
    }
}