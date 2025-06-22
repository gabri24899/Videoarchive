package fh.aalen.test;

import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fh.aalen.video.Video;



public class DataDrivenTests extends AbstractVideoTestBase {

    @DataProvider(name = "genreProvider")
    public Object[][] provideGenres() {
        return new Object[][] {
            {"Action"}, {"SciFi"}, {"Dokumentation"}, {"Drama"}, {"Komödie"}
        };
    }

    @Test(dataProvider = "genreProvider", priority = 5, groups = {"dataprovider"})
    public void testAddVideoWithGenre(String genre) throws InterruptedException {
        Video video = new Video("Genre-Test", "12", "Test für Genre", genre);
        Video saved = videoService.addVideo(video);
        Thread.sleep(20000);
        assertEquals(saved.getGenre(), genre);
    }
}
