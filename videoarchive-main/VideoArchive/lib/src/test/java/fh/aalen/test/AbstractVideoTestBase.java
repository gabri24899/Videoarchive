package fh.aalen.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


import fh.aalen.Anwendung;
import fh.aalen.video.Video;
import fh.aalen.video.VideoRepository;
import fh.aalen.video.VideoService;

@SpringBootTest(classes = Anwendung.class)
@ActiveProfiles("test")
public abstract class AbstractVideoTestBase extends AbstractTestNGSpringContextTests {

    

    @Autowired
    protected VideoService videoService;

    @Autowired
    protected VideoRepository videoRepository;

    protected Video latestVideo;

   
}
