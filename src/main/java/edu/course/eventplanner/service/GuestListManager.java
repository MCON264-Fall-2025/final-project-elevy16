package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;

public class GuestListManager {

    // official guest list
    final LinkedList<Guest> guests = new LinkedList<>();
    // map to lookup guests by name
    final Map<String, Guest> guestByName = new HashMap<>();

    public void addGuest(Guest guest) {
        // add guest to linked list
        guests.add(guest);

        // add guest to map
        guestByName.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        // use hash map to find guest object
        Guest removedGuest = guestByName.get(guestName);

        // if not found, return false
        if (removedGuest == null) {
            return false;
        }

        // remove from linked list
        guests.remove(removedGuest);

        // remove from hashmap
        guestByName.remove(guestName);

        return true;
    }

    public Guest findGuest(String guestName) {
        // use hashmap to find guest object
        return guestByName.get(guestName);

    }

    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
