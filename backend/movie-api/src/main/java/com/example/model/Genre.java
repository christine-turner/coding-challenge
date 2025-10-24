package com.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATED("Animated"),
    BIOGRAPHY("Biography"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DANCE("Dance"),
    DISASTER("Disaster"),
    DOCUMENTARY("Documentary"),
    DRAMA("Drama"),
    EROTIC("Erotic"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    FOUND_FOOTAGE("Found Footage"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    INDEPENDENT("Independent"),
    LEGAL("Legal"),
    LIVE_ACTION("Live Action"),
    MARTIAL_ARTS("Martial Arts"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    NOIR("Noir"),
    PERFORMANCE("Performance"),
    POLITICAL("Political"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE_FICTION("Science Fiction"),
    SHORT("Short"),
    SILENT("Silent"),
    SLASHER("Slasher"),
    SPORT("Sport"),
    SPORTS("Sports"),
    SPY("Spy"),
    SUPERHERO("Superhero"),
    SUPERNATURAL("Supernatural"),
    SUSPENSE("Suspense"),
    TEEN("Teen"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Genre fromString(String value) {
        for (Genre genre : Genre.values()) {
            if (genre.displayName.equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + value);
    }
}
