package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {
    @Test
    public void testAddGuest() {
        // arrange guests
        GuestListManager guestListManager = new GuestListManager();
        Guest guest1 = new Guest("Riva", "family");
        Guest guest2 = new Guest("Shmuel", "couple");
        Guest guest3 = new Guest("Leah", "friends");

        // add guests
        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);
        guestListManager.addGuest(guest3);

        // assert that guests were added to data structures:
        // linked list
        assertEquals(3, guestListManager.guests.size());
        // hashmap
        assertTrue(guestListManager.guestByName.containsKey("Riva"));
        assertTrue(guestListManager.guestByName.containsKey("Shmuel"));
        assertTrue(guestListManager.guestByName.containsKey("Leah"));
    }

    @Test
    public void testRemoveGuest() {
        // arrange guests
        GuestListManager guestListManager = new GuestListManager();
        Guest guest1 = new Guest("Riva", "family");
        Guest guest2 = new Guest("Shmuel", "couple");
        Guest guest3 = new Guest("Leah", "friends");

        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);
        guestListManager.addGuest(guest3);

        // assert guest count
        assertEquals(3, guestListManager.getGuestCount());

        // remove guests booleans
        boolean removed1 = guestListManager.removeGuest("Riva");
        boolean removed2 = guestListManager.removeGuest("Shmuel");
        boolean removed3 = guestListManager.removeGuest("Leah");

        // assert that guests were removed
        assertTrue(removed1, "Guest should have been removed");
        assertTrue(removed2, "Guest should have been removed");
        assertTrue(removed3, "Guest should have been removed");

    }

    @Test
    public void testFindGuest() {
        // arrange guests
        GuestListManager guestListManager = new GuestListManager();
        Guest guest1 = new Guest("Riva", "family");
        Guest guest2 = new Guest("Shmuel", "couple");
        Guest guest3 = new Guest("Leah", "friends");

        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);
        guestListManager.addGuest(guest3);

        // assert found correct guest
        assertEquals(guest1, guestListManager.findGuest("Riva"));
        assertEquals(guest2, guestListManager.findGuest("Shmuel"));
        assertEquals(guest3, guestListManager.findGuest("Leah"));
    }

    @Test
    public void testGetGuestCount() {
        // arrange guests
        GuestListManager guestListManager = new GuestListManager();
        Guest guest1 = new Guest("Riva", "family");
        Guest guest2 = new Guest("Shmuel", "couple");
        Guest guest3 = new Guest("Leah", "friends");

        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);
        guestListManager.addGuest(guest3);

        // assert guest count
        assertEquals(3, guestListManager.getGuestCount());

    }

    @Test
    public void testGetAllGuests() {
        // arrange guests
        GuestListManager guestListManager = new GuestListManager();
        Guest guest1 = new Guest("Riva", "family");
        Guest guest2 = new Guest("Shmuel", "couple");
        Guest guest3 = new Guest("Leah", "friends");

        guestListManager.addGuest(guest1);
        guestListManager.addGuest(guest2);
        guestListManager.addGuest(guest3);

        // get guest list
        List<Guest> allGuests = guestListManager.getAllGuests();

        // assert guest list
        assertEquals(3, allGuests.size()); // assert size
        assertTrue(allGuests.contains(guest1));     // assert contains guest 1
        assertTrue(allGuests.contains(guest2));     // assert contains guest 2
        assertTrue(allGuests.contains(guest3));     // assert contains guest 3
    }
}
