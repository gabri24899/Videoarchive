package fh.aalen.test;

import fh.aalen.video.Video;
import fh.aalen.video.VideoRepository;
import fh.aalen.video.VideoService;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Tests for VideoService showing method dependencies and priorities.
 */
public class VideoServiceTest {

    /**
     * Helper subclass to allow injecting a mock repository via constructor.
     */
    static class TestableVideoService extends VideoService {
        TestableVideoService(VideoRepository repo) {
            ReflectionTestUtils.setField(this, "repository", repo);
        }
    }

    /** Simple in-memory repository used instead of Mockito. */
    static class InMemoryVideoRepository implements VideoRepository {
        private final Map<Long, Video> store = new HashMap<>();
        private long nextId = 1L;

        @Override
        public <S extends Video> S save(S entity) {
            if (entity.getId() == null) {
                ReflectionTestUtils.setField(entity, "id", nextId++);
            }
            store.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public <S extends Video> Iterable<S> saveAll(Iterable<S> entities) {
            for (S e : entities) {
                save(e);
            }
            return entities;
        }

        @Override
        public Optional<Video> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public boolean existsById(Long id) {
            return store.containsKey(id);
        }

        @Override
        public Iterable<Video> findAll() {
            return store.values();
        }

        @Override
        public Iterable<Video> findAllById(Iterable<Long> ids) {
            List<Video> list = new ArrayList<>();
            for (Long id : ids) {
                if (store.containsKey(id)) {
                    list.add(store.get(id));
                }
            }
            return list;
        }

        @Override
        public long count() {
            return store.size();
        }

        @Override
        public void deleteById(Long id) {
            store.remove(id);
        }

        @Override
        public void delete(Video entity) {
            store.remove(entity.getId());
        }

        @Override
        public void deleteAllById(Iterable<? extends Long> ids) {
            for (Long id : ids) {
                store.remove(id);
            }
        }

        @Override
        public void deleteAll(Iterable<? extends Video> entities) {
            for (Video v : entities) {
                store.remove(v.getId());
            }
        }

        @Override
        public void deleteAll() {
            store.clear();
        }
    }

    private VideoRepository repository;
    private VideoService service;
    private Video sample;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        System.out.println("VideoServiceTest.setup");
        repository = new InMemoryVideoRepository();
        service = new TestableVideoService(repository);
        sample = new Video("Edge of Tomorrow", "16", "Zeitschleife", "SciFi");
    }

    @Test(priority = 1, groups = {"crud"}, description = "Add video and verify repository call")
    public void createVideo() {
        Video saved = service.addVideo(sample);
        assertEquals(saved.getTitle(), sample.getTitle());
        assertTrue(repository.findById(saved.getId()).isPresent());
    }

    @Test(priority = 2, dependsOnMethods = "createVideo", description = "Fetch video by id")
    public void readVideo() {
        repository.save(sample);
        Optional<Video> result = service.getVideoById(sample.getId());
        assertTrue(result.isPresent());
        assertEquals(result.get().getTitle(), sample.getTitle());
    }

    @Test(priority = 3, dependsOnMethods = "readVideo", description = "Update video")
    public void updateVideo() {
        repository.save(sample);
        Video updated = new Video("Security", "18", "Marine rettet Mädchen", "Action");
        Video result = service.updateVideo(sample.getId(), updated);
        assertEquals(result.getGenre(), "Action");
    }

    @Test(priority = 4, dependsOnMethods = "updateVideo", description = "Delete video")
    public void deleteVideo() {
        repository.save(sample);
        Long id = sample.getId();
        service.deleteVideo(id);
        assertFalse(repository.findById(id).isPresent());
    }
}