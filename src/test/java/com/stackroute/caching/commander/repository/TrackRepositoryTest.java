package com.stackroute.caching.commander.repository;

import com.stackroute.caching.domain.Track;
import com.stackroute.caching.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;


@SpringBootTest
class TrackRepositoryTest {

    /**
     * Used AutoWire by property to inject TrackRepository here
     */
    @Autowired
    private TrackRepository trackRepository;
    private Track track;

    /**
     * Execute this before each test case
     */
    @BeforeEach
    public void setUp() {
        track = new Track(1, "Alice", "Amazing Voice");
    }


    /**
     * Execute this after every test case
     */
    @AfterEach
    public void tearDown() {
        track = null;
    }

    @Test
    public void givenTrackThenShouldSaveThatTrackFailure() {
        Track track2 = new Track(7, "Unstoppable", "DMC!!!!");

        assertNotSame(track, trackRepository.save(track2));
    }

    @Test
    public void givenTracksThenShouldReturnListOfAllTracks() {
        Track track = new Track(2, "Wrong", "Calienta");
        Track track1 = new Track(3, "Despair", "Rocking!!");
        trackRepository.save(track);
        trackRepository.save(track1);

        List<Track> trackList = (List<Track>) trackRepository.findAll();
        assertEquals("Wrong", trackList.get(1).getName());
    }

    @Test
    public void givenMethodCallToGetAllTracksThenShouldReturnListOfAllTracksFailure() {
        Track track1 = new Track(4, "Dangerous", "Michael Jackson");
        Track track2 = new Track(5, "Thriller", "MJ");
        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track);

        List<Track> list = new ArrayList<>();
        list.add(track1);
        list.add(track2);

        List<Track> trackList = (List<Track>) trackRepository.findAll();
        //assert
        assertNotSame(list, trackList);
    }


    @Test
    public void givenTrackIdThenShouldReturnTrackOfThatId() {
        Track track = new Track(3, "Sugar", "Pharell is awesome");
        Track track1 = trackRepository.save(track);
        Optional<Track> optional = trackRepository.findById(track1.getId());
        assertEquals(track1.getId(), optional.get().getId());
        assertEquals(track1.getName(), optional.get().getName());
        assertEquals(track1.getComments(), optional.get().getComments());
    }

    @Test
    public void givenTrackIdToDeleteThenShouldDeleteTheTrack() {
        trackRepository.save(track);
        trackRepository.deleteById(track.getId());
        Optional optional = trackRepository.findById(track.getId());
        assertEquals(Optional.empty(), optional);
    }

}