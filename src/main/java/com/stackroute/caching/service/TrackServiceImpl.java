package com.stackroute.caching.service;

import com.stackroute.caching.domain.Track;

import com.stackroute.caching.repository.TrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    to define cache configuration
*/
@CacheConfig(cacheNames = "track")
/**
 * @Service indicates annotated class is a service which hold business logic in the Service layer
 */
@Service
public class TrackServiceImpl implements TrackService {
    private TrackRepository trackRepository;

    public TrackServiceImpl() {
    }

    
    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /*
        to update the value of the cache
    */
    @Caching(evict = {
            @CacheEvict(value = "alltrackcache", allEntries = true),
            @CacheEvict(value = "trackcache", key = "#track.id")
    })
    /**
     * Implementation of saveTrack method
     */
    @Override
    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }


    /*
        to cache the result of this method
    */
    @Cacheable(value = "alltrackcache")
    /**
     * Implementation of getAllTracks method
     */
    @Override
    public List<Track> getAllTracks() {
        return (List<Track>) trackRepository.findAll();

    }

    /*
        to cache the result of this method
    */
    @Cacheable(value = "trackcache", key = "#trackId")
    /**
     * Implementation of getTrackById method
     */
    @Override
    public Track getTrackById(int trackId) {
        Track retrievedTrack = null;
        retrievedTrack = trackRepository.findById(trackId).get();
        return retrievedTrack;
    }


    /*
        to remove data from from the cache
    */
    @Caching(evict = {
            @CacheEvict(value = "alltrackcache", allEntries = true),
            @CacheEvict(value = "trackcache", key = "#trackId")
    })
    /**
     * Implementation of deleteTrackById method
     */
    @Override
    public Track deleteTrackById(int trackId) {
        Track track = null;
        Optional optional = trackRepository.findById(trackId);
        if (optional.isPresent()) {
            track = trackRepository.findById(trackId).get();
            trackRepository.deleteById(trackId);
        }
        return track;
    }

    /*
        to update the cache with the result of the method execution
    */
    @CachePut(key = "#track.id")
    /**
     * Implementation of updateTrack method
     */
    @Override
    public Track updateTrack(Track track) {
        Track updatedTrack = null;
        Optional optional = trackRepository.findById(track.getId());
        if (optional.isPresent()) {
            Track getTrack = trackRepository.findById(track.getId()).get();
            getTrack.setComments(track.getComments());
            getTrack.setName(track.getName());
            updatedTrack = saveTrack(getTrack);
        }
        return updatedTrack;

    }

}
