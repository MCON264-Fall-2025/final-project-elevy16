package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {

    // simple test case: only one venue exists and is good for our needs
    @Test
    public void testOneGoodVenue() {
        // arrange venue and venue selector
        Venue weddingVenue = new Venue("wedding", 10000.00, 200, 20, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(weddingVenue);
        VenueSelector venueSelector = new VenueSelector(venues);

        // select budget and guest count
        Venue selected = venueSelector.selectVenue(10000.00, 100);

        // assert good venue
        assertEquals(selected, weddingVenue);
    }

    // test no venue fits our guest count
    @Test
    public void testNoGoodVenue() {
        // Arrange small venue that only holds 50 people
        Venue smallVenue = new Venue("small hall", 1000.00, 50, 5, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(smallVenue);
        VenueSelector venueSelector = new VenueSelector(venues);

        // Try to fit 100 guests (more than capacity)
        Venue selected = venueSelector.selectVenue(5000.00, 100);

        // Should return null (no good venue)
        assertNull(selected);
    }

    // test venue too expensive
    @Test
    public void testNoVenueWithinBudget() {
        // Arrange expensive venue that costs 20000
        Venue expensiveVenue = new Venue("luxury hall", 20000.00, 200, 20, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(expensiveVenue);
        VenueSelector venueSelector = new VenueSelector(venues);

        // Only have 10000 budget
        Venue selected = venueSelector.selectVenue(10000.00, 100);

        // Should return null (too expensive)
        assertNull(selected);
    }

    // test multiple venues work, pick cheapest one
    @Test
    public void testPickLowestCostVenue() {
        // Arrange three venues, all fit, different costs
        Venue cheapVenue = new Venue("budget hall", 5000.00, 150, 15, 10);
        Venue mediumVenue = new Venue("standard hall", 8000.00, 150, 15, 10);
        Venue expensiveVenue = new Venue("premium hall", 12000.00, 150, 15, 10);

        List<Venue> venues = new ArrayList<>();
        venues.add(expensiveVenue);
        venues.add(cheapVenue);
        venues.add(mediumVenue);

        VenueSelector venueSelector = new VenueSelector(venues);

        // Budget is 15000 (all three fit)
        Venue selected = venueSelector.selectVenue(15000.00, 100);

        // Should pick the cheapest one
        assertEquals(cheapVenue, selected);
    }

    // test when costs are equal, pick one with smallest capacity that still fits
    @Test
    public void testPickSmallestCapacityWhenCostsTied() {
        // Arrange three venues, same cost but different capacities
        Venue largeVenue = new Venue("large hall", 10000.00, 300, 30, 10);
        Venue mediumVenue = new Venue("medium hall", 10000.00, 200, 20, 10);
        Venue smallVenue = new Venue("small hall", 10000.00, 150, 15, 10);

        List<Venue> venues = new ArrayList<>();
        venues.add(largeVenue);
        venues.add(smallVenue);
        venues.add(mediumVenue);

        VenueSelector venueSelector = new VenueSelector(venues);

        // Need space for 100 guests
        Venue selected = venueSelector.selectVenue(10000.00, 100);

        // Should pick smallest capacity
        assertEquals(smallVenue, selected);
    }

    // complex test: pick best venue when costs and capacity varies
    @Test
    public void testMultipleVenuesSelectBest() {
        // Arrange four venues with various costs and capacities
        Venue venue1 = new Venue("venue A", 8000.00, 200, 20, 10);
        Venue venue2 = new Venue("venue B", 6000.00, 180, 18, 10);
        Venue venue3 = new Venue("venue C", 7000.00, 150, 15, 10);
        Venue venue4 = new Venue("venue D", 6000.00, 160, 16, 10);

        List<Venue> venues = new ArrayList<>();
        venues.add(venue1);
        venues.add(venue2);
        venues.add(venue3);
        venues.add(venue4);

        VenueSelector venueSelector = new VenueSelector(venues);

        // Need space for 140 guests
        Venue selected = venueSelector.selectVenue(10000.00, 140);

        // Should select venue4
        assertEquals(venue4, selected);
    }

    // edge case: if budget and cap are exact matches
    @Test
    public void testExactBudgetAndCapacity() {
        // Arrange venue costs exactly 10000 and holds exactly 100
        Venue perfectVenue = new Venue("perfect hall", 10000.00, 100, 10, 10);
        List<Venue> venues = new ArrayList<>();
        venues.add(perfectVenue);
        VenueSelector venueSelector = new VenueSelector(venues);

        // Budget is exactly 10000, need exactly 100 guests
        Venue selected = venueSelector.selectVenue(10000.00, 100);

        // Should still work
        assertEquals(perfectVenue, selected);
    }

}
