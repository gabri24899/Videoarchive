package fh.aalen.test;

import static org.testng.Assert.*;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import fh.aalen.video.Video;

public class SimpleTestExample extends AbstractVideoTestBase {

	private static final Logger log = LoggerFactory.getLogger(SimpleTestExample.class);
	
	
@BeforeClass 
public void videoInitalisation  () throws InterruptedException {
	 log.info("\n\nStart der Testklasse \n");
	videoService.getAllVideos();
	Thread.sleep(5000);
	
}

@AfterClass
public void beforeClass() throws InterruptedException {
    log.info("\n\n Löschen der DB Einträge \n");
    Thread.sleep(5000);
    videoRepository.deleteAll(); 
}

@BeforeMethod
public void logTestStart(java.lang.reflect.Method method) throws InterruptedException {
   log.info("\n\n Der aktuelle Test : {}", method.getName() + " Wird Ausgeführt \n");
   Thread.sleep(2000); // kurze Pause zur Hervorhebung
}
	


    @Test
    public void AddVideoTest () throws InterruptedException {
    	
        Video testVideo = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        Video savedVideo = videoService.addVideo(testVideo);
        Thread.sleep(5000); // kurze Pause für Präsentation
        assertEquals(savedVideo.getTitle(), "Matrix");
        assertNotNull(savedVideo.getId());
        assertTrue(savedVideo.getGenre().equals("SciFi"));
    }
    
    @Test
    public void testAddVideoFail() throws InterruptedException {
    	Video testVideo = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        Video savedVideo = videoService.addVideo(testVideo);
        Thread.sleep(5000); // kurze Pause für Präsentation
        assertEquals(savedVideo.getTitle(), "Spongebob");
        
    }
   


   
    
}
