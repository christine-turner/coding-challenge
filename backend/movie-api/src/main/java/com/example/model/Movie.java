package com.example.model;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    private String title;
    private Integer year;
    private Set<String> cast;
    private Set<Genre> genres;
    private String href;
    private String extract;
    private String thumbnail;
    private Long thumbnailWidth;
    private Long thumbnailHeight;

    // Default constructor required by Jackson
    public Movie() {}

    // Getters and setters
    @JsonProperty("title")
    public String getTitle() { return title; }

    @JsonProperty("title")
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("year")
    public Integer getYear() { return year; }

    @JsonProperty("year")
    public void setYear(Integer year) { this.year = year; }

    @JsonProperty("cast")
    public Set<String> getCast() { return cast; }

    @JsonProperty("cast")
    public void setCast(Set<String> cast) { this.cast = cast; }

    @JsonProperty("genres")
    public Set<Genre> getGenres() { return genres; }

    @JsonProperty("genres")
    public void setGenres(Set<Genre> genres) { this.genres = genres; }

    @JsonProperty("href")
    public String getHref() { return href; }

    @JsonProperty("href")
    public void setHref(String href) { this.href = href; }

    @JsonProperty("extract")
    public String getExtract() { return extract; }

    @JsonProperty("extract")
    public void setExtract(String extract) { this.extract = extract; }

    @JsonProperty("thumbnail")
    public String getThumbnail() { return thumbnail; }

    @JsonProperty("thumbnail")
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    @JsonProperty("thumbnail_width")
    public Long getThumbnailWidth() { return thumbnailWidth; }

    @JsonProperty("thumbnail_width")
    public void setThumbnailWidth(Long thumbnailWidth) { this.thumbnailWidth = thumbnailWidth; }

    @JsonProperty("thumbnail_height")
    public Long getThumbnailHeight() { return thumbnailHeight; }

    @JsonProperty("thumbnail_height")
    public void setThumbnailHeight(Long thumbnailHeight) { this.thumbnailHeight = thumbnailHeight; }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", cast=" + cast +
                ", genres=" + genres +
                ", href='" + href + '\'' +
                ", extract='" + extract + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                '}';
    }
}
