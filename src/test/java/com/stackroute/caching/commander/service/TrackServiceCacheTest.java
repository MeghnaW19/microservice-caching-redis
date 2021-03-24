package com.stackroute.caching.commander.service;

import com.stackroute.caching.commander.config.RedisConfig;
import com.stackroute.caching.domain.Track;
import com.stackroute.caching.repository.TrackRepository;
import com.stackroute.caching.service.TrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RedisConfig.class)
public class TrackServiceCacheTest {
    @Mock
    private TrackRepository trackRepository;
    @Autowired
    @InjectMocks
    private TrackServiceImpl trackService;
    private Track track1;
    private Track track2;
    private Track track3;
    private List<Track> trackList;

    @BeforeEach
    public void setUp() {
        trackList = new ArrayList<>();
        track1 = new Track(1, "Killer", "Michael jackson");
        track2 = new Track(2, "Star Boy", "Good Lyrics");
        track3 = new Track(3, "Don't Stop", "King James");
        trackList.add(track1);
        trackList.add(track2);
    }

    @AfterEach
    public void tearDown() {
        track1 = track2 = track3 = null;
        trackList = null;
    }

    @Test
    void givenTrackToSaveThenShouldReturnSavedTrack() {
        when(trackRepository.save(any())).thenReturn(track1);
        assertEquals(track1, trackService.saveTrack(track1));
        verify(trackRepository, times(1)).save(any());
    }

    @Test
    void givenCallToGetAllTracksThenShouldReturnListOfAllTracks() {
        trackService.saveTrack(track1);
        trackService.saveTrack(track2);
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        verify(trackRepository, times(1)).findAll();
    }

    @Test
    void givenTrackIdThenShouldReturnTrackWithThatId() {
        when(trackRepository.findById(anyInt())).thenReturn(Optional.of(track1));
        trackService.saveTrack(track1);
        trackService.saveTrack(track2);
        trackService.getTrackById(track1.getId());
        trackService.getTrackById(track1.getId());
        verify(trackRepository, times(1)).findById(track1.getId());

    }

    @Test
    void givenTrackToSaveThenShouldEvictCache() {
        when(trackRepository.save(any())).thenReturn(track1);
        when(trackRepository.findById(anyInt())).thenReturn(Optional.of(track1));
        trackService.saveTrack(track1);
        trackService.saveTrack(track2);
        trackService.getTrackById(track1.getId());
        trackService.getTrackById(track1.getId());
        verify(trackRepository, times(1)).findById(track1.getId());
        trackService.saveTrack(track3);
        verify(trackRepository, times(1)).findById(track1.getId());
    }

    @Test
    void givenTrackToDeleteThenShouldEvictCache() {
        trackService.saveTrack(track1);
        trackService.saveTrack(track2);
        trackService.deleteTrackById(1);
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        verify(trackRepository, times(1)).findAll();
    }

    @Test
    void givenTrackToUpdateThenShouldEvictCache() {
        trackService.saveTrack(track1);
        trackService.saveTrack(track2);
        trackService.updateTrack(track1);
        track1.setComments("King of Pop");
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        trackService.getAllTracks();
        assertEquals("King of Pop", track1.getComments());
        verify(trackRepository, times(1)).findAll();
    }


}