package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import java.util.*;

public class SeatingPlanner {

    private final Venue venue;

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }

    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        // get table capacity
        int seatsPerTable = venue.getSeatsPerTable();

        // create seating result map
        Map<Integer, List<Guest>> seating = new HashMap<>();

        // create grouped guests map
        Map<String, Queue<Guest>> groupedGuests = new HashMap<>();

        // loop through guest list and group guests by tag
        for (Guest guest : guests) {
            // get group tag
            String tag = guest.getGroupTag();

            // get the queue, or create it if it doesn't exist
            Queue<Guest> queue = groupedGuests.get(tag);
            if (queue == null) {
                queue = new LinkedList<>();
                groupedGuests.put(tag, queue);
            }


            // add guest to grouped queue
            queue.add(guest);
        }

        int currentTableNumber = 1;
        List<Guest> currentTable = new ArrayList<>();

        // for each group
        for (Queue<Guest> group : groupedGuests.values()) {

            // while there are guests in this group
            while (!group.isEmpty()) {

                // if current table is full, start at a new table
                if (currentTable.size() >= seatsPerTable) {
                    // save current table
                    seating.put(currentTableNumber, currentTable);
                    currentTableNumber++;
                    currentTable = new ArrayList<>();
                }

                // add next guest from queue to current table
                Guest guest = group.poll();
                currentTable.add(guest);

            }
        }

        // add last table if it has guests
        if (!currentTable.isEmpty()) {
            seating.put(currentTableNumber, currentTable);
        }

        return seating;

    }
}
