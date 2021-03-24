package com.stackroute.caching.repository;

import com.stackroute.caching.domain.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository marks the specific class as a Data Access Object
 */
@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {
}


