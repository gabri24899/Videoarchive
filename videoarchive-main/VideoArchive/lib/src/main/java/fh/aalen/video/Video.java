package fh.aalen.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primärschlüssel Long

    private String title;
    private String age_rating;
    private String description;
    private String genre;

    public Video() {}

    public Video(String title, String age_rating, String description, String genre) {
        this.title = title;
        this.age_rating = age_rating;
        this.description = description;
        this.genre = genre;
    }

    // Getter und Setter

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAge_rating() { return age_rating; }
    public void setAge_rating(String age_rating) { this.age_rating = age_rating; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}
