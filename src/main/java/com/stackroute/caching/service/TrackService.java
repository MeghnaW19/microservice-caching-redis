package com.stackroute.caching.service;

import com.stackroute.caching.domain.Track;

import java.util.List;

public interface TrackService {
    /**
     * AbstractMethod to save a track
     */
    public Track saveTrack(Track track);

    /**
     * AbstractMethod to get all tracks
     */
    public List<Track> getAllTracks();

    /**
     * AbstractMethod to get track by id
     */
    public Track getTrackById(int trackId);

    /**
     * AbstractMethod to delete track by id
     */
    public Track deleteTrackById(int trackId);

    /**
     * AbstractMethod to update a track
     */
    public Track updateTrack(Track track);
}
