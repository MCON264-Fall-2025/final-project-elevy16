package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) { this.venues = venues; }

    public Venue selectVenue(double budget, int guestCount) {

        // Filter good venues
        List<Venue> goodVenues = new ArrayList<>();

        for (Venue venue : venues) {
            if (venue.getCost() <= budget && venue.getCapacity() >= guestCount) {
                goodVenues.add(venue);
            }
        }

        // Check if found any good venues
        if (goodVenues.isEmpty()) {
            return null;
        }

        // Sort good venues
        goodVenues.sort(new Comparator<Venue>() {

            @Override
            public int compare(Venue v1, Venue v2) {
                // First compare by cost (ascending)
                int costComparison = Double.compare(v1.getCost(), v2.getCost());
                if (costComparison != 0) {
                    return costComparison;
                }

                // If costs are equal, compare by capacity (ascending)
                return Integer.compare(v1.getCapacity(), v2.getCapacity());
            }
        });
        // return the first venue (best match)
        return goodVenues.get(0);
    }}