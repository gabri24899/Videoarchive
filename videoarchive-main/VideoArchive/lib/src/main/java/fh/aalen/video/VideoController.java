package fh.aalen.video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
	@Autowired
	VideoService videoService;
	
	
	@RequestMapping("/videos")
	public List<Video> getVideoList() {
		return videoService.getVideoList();
	}
	@RequestMapping("/videos/{title}")
	public Video getVideo(@PathVariable("title") String title) {
		return videoService.getVideo(title);
		
	}
	@PostMapping(value="/videos")
	public void addVideo(@RequestBody Video video) {
		videoService.addVideo(video);
	}
	@PutMapping("/videos/{title}")
	public void updateVideo(@PathVariable("title")String title,@RequestBody Video video) {
		videoService.updateVideo(title, video);
	}
	@DeleteMapping("/videos/{title}")
	public void deleteVideo(@PathVariable("title")String title) {
		videoService.deleteVideo(title);
	}
}