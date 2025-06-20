package fh.aalen.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fh.aalen.Anwendung;
import fh.aalen.video.Video;
import fh.aalen.video.VideoRepository;
import fh.aalen.video.VideoService;

@ActiveProfiles("test") // Aktiviert application-test.properties
@SpringBootTest(classes = Anwendung.class)
public class VideoServiceTest2 extends AbstractTestNGSpringContextTests {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    Video latestVideo;

    @BeforeMethod
    public void setup() {
        assertNotNull(videoRepository, "videoRepository wurde nicht injiziert!");
        // Entfernt, damit Daten in der Datei-basierten H2-Datenbank erhalten bleiben:
        // videoRepository.deleteAll();
    }

    // ✅ Test 1: Video speichern & abrufen
    @Test
    public void testAddAndGetVideo() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(video);

        Optional<Video> found = videoService.getVideoById(latestVideo.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Matrix");
    }

    // ✅ Test 2: Update durchführen und H2-Zustand sichtbar machen
    @Test
    public void testUpdateVideo() throws InterruptedException {
        Video created = videoService.addVideo(
            new Video("Matrix", "16", "Cyberwelt", "SciFi")
        );

        System.out.println("✅ Vor Update:");
        System.out.println(" → ID: " + created.getId());
        System.out.println(" → Title: " + created.getTitle());
        System.out.println(" → Age Rating: " + created.getAge_rating());
        System.out.println(" → Description: " + created.getDescription());
        System.out.println(" → Genre: " + created.getGenre());

        System.out.println("\n🔎 Jetzt H2-Konsole öffnen: http://localhost:8080/h2-console");
        System.out.println("→ JDBC URL: jdbc:h2:file:C:/Users/Manue/Desktop/Video-TestNG/Videoarchive/database/testdb");
        System.out.println("→ User: sa | Passwort: <leer>");
        System.out.println("⏳ 30 Sekunden Pause für H2-Check...");
        

        Video update = new Video("Matrix Reloaded", "18", "Zweiter Teil", "Action");
        Video updated = videoService.updateVideo(created.getId(), update);

        Optional<Video> found = videoService.getVideoById(updated.getId());
        assertTrue(found.isPresent());

        System.out.println("\n✅ Nach Update:");
        System.out.println(" → ID: " + found.get().getId());
        System.out.println(" → Title: " + found.get().getTitle());
        System.out.println(" → Age Rating: " + found.get().getAge_rating());
        System.out.println(" → Description: " + found.get().getDescription());
        System.out.println(" → Genre: " + found.get().getGenre());

        System.out.println("\n🔎 Jetzt nochmal H2-Konsole prüfen: SELECT * FROM VIDEO;");
        System.out.println("⏳ 30 Sekunden Pause nach Update...");
        

        assertEquals(found.get().getTitle(), "Matrix Reloaded");
        assertEquals(found.get().getAge_rating(), "18");
        assertEquals(found.get().getDescription(), "Zweiter Teil");
        assertEquals(found.get().getGenre(), "Action");
    }

    // ❌ Fehlerhafter Test (bewusst so belassen für Demonstration)
    @Test
    public void testAddAndGetVideoFail() {
        Video video = new Video("Matrix", "16", "Cyberwelt", "SciFi");
        latestVideo = videoService.addVideo(video);

        Optional<Video> found = videoService.getVideoById(latestVideo.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getTitle(), "Spongebob"); // Erwartet absichtlich den falschen Wert
    }
}
