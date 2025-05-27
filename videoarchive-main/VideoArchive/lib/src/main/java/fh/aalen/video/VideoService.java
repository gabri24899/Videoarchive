package fh.aalen.video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
	
     private List<Video> videolist = new ArrayList<Video>(Arrays.asList(
     new Video("Edge of Tomorrow", "16","In Zeitschleife gefangen bis Aliens vernichtet","SciFi"),
     new Video("Security", "18","Marine rettet Mädchen in Kaufhaus","Action" )
     ));
     public List<Video> getVideoList() {
     return videolist;
     }
     public Video getVideo(String title) {
     for (Video v:videolist) {
     if(v.getTitle().equals(title))
     return v;
     }
     return null;
     }
     public void addVideo(Video video) {
     videolist.add(video);
     }
     public void updateVideo(String title, Video video) {
     for(int i=0; i<videolist.size();i++) {
     Video v = videolist.get(i);
     if(v.getTitle().equals(title)) {
     videolist.set(i,video);
     return;
     }
     }
     }
     public void deleteVideo(String title) {
     for(int i=0; i<videolist.size();i++) {
     Video v = videolist.get(i);
     if(v.getTitle().equals(title)){
     videolist.remove(i);
     return;
     }
     }
     }
     }