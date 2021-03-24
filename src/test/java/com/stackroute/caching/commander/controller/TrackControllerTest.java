package com.stackroute.caching.commander.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.caching.controller.TrackController;
import com.stackroute.caching.domain.Track;
import com.stackroute.caching.service.TrackService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    private MockMvc mockMvc;

    /**
     * Create a mock for TrackService
     */
    @Mock
    TrackService trackService;

    /**
     * Inject the mocks as dependencies into TrackController
     */
    @InjectMocks
    private TrackController trackController;

    private Track track;
    private List<Track> trackList;

    /**
     * Run this before each test case
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        track = new Track();
        track.setId(1);
        track.setName("Still");
        track.setComments("Best HipHop");
        trackList = new ArrayList<>();
        trackList.add(track);
    }

    /**
     * Run this after each test case
     */
    @AfterEach
    void tearDown() {
        track = null;
    }

    @Test
    void givenPostMappingUrlThenShouldReturnTheSavedTrack() throws Exception {
        when(trackService.saveTrack(any())).thenReturn(track);
        mockMvc.perform(post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(trackService).saveTrack(any());
    }

    @Test
    void givenGetMappingUrlThenShouldReturnListOfAllTracks() throws Exception {
        when(trackService.getAllTracks()).thenReturn(trackList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tracks")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andDo(MockMvcResultHandlers.print());
        verify(trackService).getAllTracks();
        verify(trackService, times(1)).getAllTracks();

    }

    @Test
    void givenTrackIdThenShouldReturnRespectiveTrack() throws Exception {
        when(trackService.getTrackById(track.getId())).thenReturn(track);
        mockMvc.perform(get("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenDeleteMappingUrlAndTrackIdThenShouldReturnDeletedTrack() throws Exception {
        when(trackService.deleteTrackById(track.getId())).thenReturn(track);
        mockMvc.perform(delete("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }


    @Test
    void givenPutMappingUrlAndTrackThenShouldReturnUpdatedTrack() throws Exception {
        when(trackService.updateTrack(any())).thenReturn(track);
        mockMvc.perform(put("/api/v1/track").contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}