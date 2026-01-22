package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private Main main;

    @BeforeEach
    public void setup() {
        main = new Main();
    }

    @Test
    public void testMainConstructor() {
        // test that main creates guest manager
        assertNotNull(main.guestManager);

        // test that main creates task manager
        assertNotNull(main.taskManager);
    }

    @Test
    public void testGuestManagerStartsEmpty() {
        // guest list should start with 0 guests
        assertEquals(0, main.guestManager.getGuestCount());
    }

    @Test
    public void testTaskManagerStartsEmpty() {
        // task queue should start with 0 tasks
        assertEquals(0, main.taskManager.remainingTaskCount());
    }

    @Test
    public void testCanAddGuest() {
        // add a guest directly to manager
        Guest guest = new Guest("Rachie", "family");
        main.guestManager.addGuest(guest);

        // should have 1 guest now
        assertEquals(1, main.guestManager.getGuestCount());
    }

    @Test
    public void testCanRemoveGuest() {
        // add then remove guest
        main.guestManager.addGuest(new Guest("Rikki", "friends"));
        boolean removed = main.guestManager.removeGuest("Rikki");

        // should have removed successfully
        assertTrue(removed);
        assertEquals(0, main.guestManager.getGuestCount());
    }

    @Test
    public void testVenueSelectorGetsCreated() {
        // create venue selector like loadData does
        List<Venue> venues = Generators.generateVenues();
        main.venueSelector = new VenueSelector(venues);

        // venue selector should exist
        assertNotNull(main.venueSelector);
    }

    @Test
    public void testCanSelectVenue() {
        // setup venues
        List<Venue> venues = Generators.generateVenues();
        main.venueSelector = new VenueSelector(venues);

        // select venue
        main.selectedVenue = main.venueSelector.selectVenue(2000, 30);

        // should have selected a venue
        assertNotNull(main.selectedVenue);
    }

    @Test
    public void testCanAddTask() {
        // add task
        main.taskManager.addTask(new Task("Set up tables"));

        // should have 1 task
        assertEquals(1, main.taskManager.remainingTaskCount());
    }

    @Test
    public void testCanExecuteTask() {
        // add and execute task
        main.taskManager.addTask(new Task("Decorate"));
        main.taskManager.executeNextTask();

        // should have 0 tasks left
        assertEquals(0, main.taskManager.remainingTaskCount());
    }

    @Test
    public void testCanUndoTask() {
        // add and execute
        main.taskManager.addTask(new Task("Clean up"));
        main.taskManager.executeNextTask();

        // now undo
        main.taskManager.undoLastTask();

        // task should be back in queue
        assertEquals(1, main.taskManager.remainingTaskCount());
    }

    @Test
    public void testSeatingStartsNull() {
        // seating should start as null
        assertNull(main.seating);
    }

    @Test
    public void testSelectedVenueStartsNull() {
        // selected venue should start as null
        assertNull(main.selectedVenue);
    }
}
