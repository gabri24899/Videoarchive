package fh.aalen.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
	
	
	private List<Video> videolist = new ArrayList<Video>(Arrays.asList(
		     new Video("Edge of Tomorrow", "16","In Zeitschleife gefangen bis Aliens vernichtet","SciFi"),
		     new Video("Security", "18","Marine rettet Mädchen in Kaufhaus","Action" )
		     ));
		     public List<Video> getVideoList() {
		     return videolist;
		     }

    @Autowired
    private VideoRepository repository;
    
    @PostConstruct
    private void initVideoList() {
        if (repository.count() == 0) {
            repository.saveAll(videolist);
        }
    }

    public Iterable<Video> getAllVideos() {
        return repository.findAll();
    }

    public Optional<Video> getVideoById(Long id) {
        return repository.findById(id);
    }
    
    
    

    public Video addVideo(Video video) {
        return repository.save(video);
    }

    public Video updateVideo(Long id, Video videoDetails) {
        return repository.findById(id).map(v -> {
            v.setTitle(videoDetails.getTitle());
            v.setAge_rating(videoDetails.getAge_rating());
            v.setDescription(videoDetails.getDescription());
            v.setGenre(videoDetails.getGenre());
            return repository.save(v);
        }).orElseThrow(() -> new RuntimeException("Video nicht gefunden"));
    }

    public void deleteVideo(Long id) {
        repository.deleteById(id);
    }
}
