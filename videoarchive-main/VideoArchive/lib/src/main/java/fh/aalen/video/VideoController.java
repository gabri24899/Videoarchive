package fh.aalen.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @GetMapping
    public Iterable<Video> getAllVideos() {
        return service.getAllVideos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> video = service.getVideoById(id);
        return video.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return service.addVideo(video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video video) {
        try {
            return ResponseEntity.ok(service.updateVideo(id, video));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        service.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
