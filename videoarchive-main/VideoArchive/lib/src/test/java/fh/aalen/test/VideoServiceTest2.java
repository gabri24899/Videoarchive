package fh.aalen.test;


import static org.testng.Assert.assertEquals;


import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fh.aalen.*;
import fh.aalen.person.*;
import fh.aalen.video.*;

@SpringBootTest (classes = fh.aalen.Anwendung.class)
public class VideoServiceTest2 extends AbstractTestNGSpringContextTests {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;
    
    
    
    
    @BeforeMethod
    public void setup() {
    	assertNotNull(videoRepository, "videoRepository wurde nicht injiziert!");
        videoRepository.deleteAll(); // saubere Testumgebung
    }
    
    
    
    
//  Test 1: Video speichern & abrufen mit ergebnissen 2 Passes aus 
    @Test
    public void testAddAndGetVideo() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        Video saved = videoService.addVideo(video);

        Optional<Video> found = videoService.getVideoById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Matrix");
    }

//  Test 1: Video speichern & abrufen gibt 2 Failures aus 
    @Test
    public void testAddAndGetVideoFail() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        Video saved = videoService.addVideo(video);

        Optional<Video> found = videoService.getVideoById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Spongebob");
    }


}

