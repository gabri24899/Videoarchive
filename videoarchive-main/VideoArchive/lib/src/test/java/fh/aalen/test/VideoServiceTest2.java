package fh.aalen.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import fh.aalen.Anwendung;
import fh.aalen.video.Video;
import fh.aalen.video.VideoRepository;
import fh.aalen.video.VideoService;

@Listeners(DotTestListener.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Anwendung.class)
public class VideoServiceTest2 extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceTest2.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    Video latestVideo;

    @BeforeSuite(alwaysRun = true)
    public static void beforeSuite() {
        log.info("🚀 Start der Test-Suite (GlobalSuiteLogger)");
    }

    @AfterSuite(alwaysRun = true)
    public static void afterSuite() {
        log.info("🏁 Ende der Test-Suite (GlobalSuiteLogger)");
    }

    @AfterMethod
    public void afterEachTest() {
        log.info("🧹 Nach dem Test: Aufräumarbeiten oder Logging");
    }

    @AfterClass
    public void cleanDatabaseAfterClass() throws InterruptedException {
        log.info("⏳ 30 Sekunden Zeit, um Datenbank zu prüfen...");
        log.info("🔎 Datenbank manuell inspizieren (z. B. SELECT * FROM VIDEO; in PostgreSQL)");
        Thread.sleep(30000);
        videoRepository.deleteAll();
        log.info("🧼 Datenbank geleert (alle Videos gelöscht).");
    }

    @Test(priority = 1, groups = {"crud"})
    public void testAddAndGetVideo() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(video);

        Optional<Video> found = videoService.getVideoById(latestVideo.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Matrix");
    }

    @Test(priority = 2, groups = {"crud"})
    public void testUpdateVideo() throws InterruptedException {
        Video created = videoService.addVideo(new Video("Matrix", "16", "Cyberwelt", "SciFi"));

        log.info("✅ Vor Update:");
        log.info(" → ID: {}", created.getId());
        log.info(" → Title: {}", created.getTitle());
        log.info(" → Age Rating: {}", created.getAge_rating());
        log.info(" → Description: {}", created.getDescription());
        log.info(" → Genre: {}", created.getGenre());

        log.info("⏳ 30 Sekunden Pause für manuelle Prüfung in PostgreSQL...");
        Thread.sleep(30000);

        Video update = new Video("Matrix Reloaded", "18", "Zweiter Teil", "Action");
        Video updated = videoService.updateVideo(created.getId(), update);

        Optional<Video> found = videoService.getVideoById(updated.getId());
        assertTrue(found.isPresent());

        log.info("✅ Nach Update:");
        log.info(" → ID: {}", found.get().getId());
        log.info(" → Title: {}", found.get().getTitle());
        log.info(" → Age Rating: {}", found.get().getAge_rating());
        log.info(" → Description: {}", found.get().getDescription());
        log.info(" → Genre: {}", found.get().getGenre());

        log.info("⏳ 30 Sekunden Pause nach Update für PostgreSQL-Prüfung...");
        Thread.sleep(30000);

        assertEquals(found.get().getTitle(), "Matrix Reloaded");
        assertEquals(found.get().getAge_rating(), "18");
        assertEquals(found.get().getDescription(), "Zweiter Teil");
        assertEquals(found.get().getGenre(), "Action");
    }

    @Test(priority = 3, groups = {"crud"})
    public void testDeleteVideo() throws InterruptedException {
        Video video = new Video("Zum Löschen", "12", "Testvideo", "Test");
        Video saved = videoService.addVideo(video);

        log.info("✅ Video gespeichert: ID = {}", saved.getId());
        log.info("⏳ 30 Sekunden Pause vor dem Löschen...");
        Thread.sleep(30000);

        videoService.deleteVideo(saved.getId());
        Optional<Video> found = videoService.getVideoById(saved.getId());

        log.info("🗑️ Video gelöscht.");
        log.info("⏳ 30 Sekunden Pause nach dem Löschen...");
        Thread.sleep(30000);

        assertTrue(found.isEmpty(), "Das Video sollte nach dem Löschen nicht mehr vorhanden sein");
    }

    @Test(priority = 4, groups = {"crud"})
    public void testGetAllVideos() throws InterruptedException {
        int initialSize = (int) videoService.getAllVideos().spliterator().getExactSizeIfKnown();
        log.info("📊 Anzahl Videos vorhin: {}", initialSize);
        log.info("⏳ 30 Sekunden Pause...");
        Thread.sleep(30000);

        videoService.addVideo(new Video("Video A", "16", "Beschreibung A", "Genre A"));
        videoService.addVideo(new Video("Video B", "12", "Beschreibung B", "Genre B"));

        int newSize = (int) videoService.getAllVideos().spliterator().getExactSizeIfKnown();
        log.info("📊 Neue Anzahl: {}", newSize);
        Thread.sleep(30000);

        assertTrue(newSize >= initialSize + 2);
    }

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
        log.info("✅ Video mit Genre '{}' hinzugefügt. ID: {}", genre, saved.getId());
        Thread.sleep(20000);
        assertEquals(saved.getGenre(), genre);
    }

    @Test(priority = 6, expectedExceptions = RuntimeException.class, groups = {"negative"})
    public void testUpdateVideoWithInvalidIdThrowsException() {
        Video update = new Video("Nicht vorhanden", "0", "Kein Film", "None");
        videoService.updateVideo(9999L, update);
    }

    @Test(priority = 7, groups = {"negative", "demo"})
    public void testAddAndGetVideoFail() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(video);
        Optional<Video> found = videoService.getVideoById(latestVideo.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Spongebob");
    }
}
