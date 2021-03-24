package com.stackroute.caching.controller;

import com.stackroute.caching.domain.Track;
import com.stackroute.caching.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController

/**
 * RequestMapping annotation maps HTTP requests to handler methods
 */
@RequestMapping(value = "/api/v1/")
public class TrackController {

    private static final Logger logger = LoggerFactory.getLogger(TrackController.class);
    private TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }


    /**
     * save a new track
     */
    @PostMapping("track")
    public ResponseEntity<Track> saveTrack(@RequestBody Track track) {
        Track savedTrack = trackService.saveTrack(track);
        return new ResponseEntity<>(savedTrack, HttpStatus.CREATED);
    }


    /**
     * retrieve all tracks
     */
    @GetMapping("tracks")
    public ResponseEntity<List<Track>> getAllTracks() {
        logger.info(".... Fetching all Tracks");
        ResponseEntity responseEntity;
        List<Track> retrievedTracks = trackService.getAllTracks();
        responseEntity = new ResponseEntity<List<Track>>((List<Track>) retrievedTracks, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * retrieve track by id
     */
    @GetMapping("track/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable int id) {
        ResponseEntity responseEntity;
        Track retrievedTrack = trackService.getTrackById(id);
        responseEntity = new ResponseEntity<Track>(retrievedTrack, HttpStatus.OK);
        return responseEntity;
    }


    /**
     * delete track by id
     */
    @DeleteMapping("track/{trackId}")
    public ResponseEntity<Track> deleteTrack(@PathVariable("trackId") int trackId) {
        ResponseEntity responseEntity;
        Track deletedTrack = trackService.deleteTrackById(trackId);
        responseEntity = new ResponseEntity<Track>(deletedTrack, HttpStatus.OK);

        return responseEntity;
    }

    /**
     * update track
     */
    @PutMapping("track")
    public ResponseEntity<Track> updateTrack(@RequestBody Track track) {
        logger.info(".... Updating Track Content of id: " + track.getId());
        Track updatedTrack = trackService.updateTrack(track);
        return new ResponseEntity<>(updatedTrack, HttpStatus.OK);
    }


}