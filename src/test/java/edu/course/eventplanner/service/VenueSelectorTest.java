package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {

    @Test
    public void testOneValidVenue() {
        // arrange venue and venue selector
        Venue weddingVenue = new Venue("wedding", 10000.00, 200, 20, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(weddingVenue);
        VenueSelector venueSelector = new VenueSelector(venues);

        // select budget and guest count
        Venue selected = venueSelector.selectVenue(10000.00, 100);

        // assert valid venue
        assertEquals(selected, weddingVenue);
    }

}
