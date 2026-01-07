package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) { this.venues = venues; }

    public Venue selectVenue(double budget, int guestCount) {
        // The program asks the user for event budget and number of guests

        // venue is valid if:
        // Its cost is less than or equal to the budget
        // Its capacity is greater than or equal to the number of guests

        // From all valid venues, select the best venue:
        // (Lowest cost If tied, smallest capacity that still fits


    }
}
