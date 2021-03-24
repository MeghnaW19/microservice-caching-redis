package com.stackroute.caching.commander.controller;

import com.stackroute.caching.commander.config.RedisConfig;
import com.stackroute.caching.domain.Track;
import com.stackroute.caching.service.TrackService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = RedisConfig.class)
public class TrackControllerIntegrationTest {
    @Autowired
    private TrackService trackService;

    private Track track;
    private List<Track> trackList;

    @BeforeEach
    void setUp() {
        track = new Track(1,  "Intentions", "2020 Top 10");
        trackList = new ArrayList<>();
        trackList.add(track);
    }

    @AfterEach
    void tearDown() {
        track = null;
    }

    @Test
    void givenCallToGetAllTracksThenListShouldNotBeNull() throws Exception {
        List<Track> retrievedTracks = trackService.getAllTracks();
        assertNotNull(retrievedTracks);
    }

    @Test
    void givenTrackToUpdateThenShouldReturnUpdatedTrack() throws Exception {
        track.setComments("Updated Track content");
        trackService.updateTrack(track);
        assertEquals("Updated Track content", track.getComments());
    }
}