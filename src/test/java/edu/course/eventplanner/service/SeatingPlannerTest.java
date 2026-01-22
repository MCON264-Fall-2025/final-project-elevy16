package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {

    @Test
    public void testGenerateSeating() {
        // arrange venue and seating planner
        Venue wedding = new Venue("wedding", 10000.00, 200, 20, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(wedding);

        // create guests
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Riva", "family"));
        guests.add(new Guest("Rachel", "friend"));
        guests.add(new Guest("Rivka", "coworker"));
        guests.add(new Guest("Reesa", "neighbor"));

        // generate seating
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        // assert no seats empty
        assertNotNull(seating, "Seating map should not be null.");
        assertFalse(seating.isEmpty(), "Seating map should not be empty.");

        // assert all guests are seated
        int totalSeated = 0;
        for (List<Guest> guestList : seating.values()) {
            totalSeated += guestList.size();
        }
        assertEquals(5, totalSeated, "All 5 guests should be seated.");
    }

    @Test
    public void testSameGroupsSitAtSameTable() {
        // arrange venue and seating planner
        Venue wedding = new Venue("wedding", 10000.00, 200, 20, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(wedding);

        // create guests
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Riva", "family"));
        guests.add(new Guest("Rachel", "family"));
        guests.add(new Guest("Rivka", "family"));
        guests.add(new Guest("Reesa", "family"));

        // generate seating
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        // assert that all guests sit at same table
        assertEquals(1, seating.size(), "Should only need 1 table for 5 guests with same group.");

    }

    @Test
    public void testDifferentGroupsSitAtDifferentTables() {
        // arrange venue and seating planner
        Venue wedding = new Venue("wedding", 10000.00, 200, 20, 10);
        SeatingPlanner seatingPlanner = new SeatingPlanner(wedding);

        // create guests
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Riva", "friend"));
        guests.add(new Guest("Rachel", "coworker"));
        guests.add(new Guest("Rivka", "neighbor"));
        guests.add(new Guest("Reesa", "family friend"));

        // generate seating
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        // assert that all guests sit at different tables
        assertTrue(seating.size() <= 5, "Should use at most 5 tables for 5 different groups.");
    }

    @Test
    public void testLargeGroupSpansMultipleTables() {
        // arrange venue and seating planner
        Venue party = new Venue("birthday party", 500.00, 20, 10, 2);
        SeatingPlanner seatingPlanner = new SeatingPlanner(party);

        // create guests
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Riva", "family"));
        guests.add(new Guest("Rachel", "family"));
        guests.add(new Guest("Rivka", "family"));
        guests.add(new Guest("Reesa", "family"));

        // generate seating
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        // assert that guests span multiple tables
        assertEquals(3, seating.size(), "5 guests should span 3 tables.");
    }

    @Test
    public void testTablesFilledOneAtATime() {
        // arrange venue and seating planner
        Venue party = new Venue("birthday party", 500.00, 20, 10, 2);
        SeatingPlanner seatingPlanner = new SeatingPlanner(party);

        // create guests
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Rikki", "family"));
        guests.add(new Guest("Riva", "family"));
        guests.add(new Guest("Rachel", "family"));
        guests.add(new Guest("Rivka", "family"));
        guests.add(new Guest("Reesa", "family"));

        // generate seating
        Map<Integer, List<Guest>> seating = seatingPlanner.generateSeating(guests);

        // check table sizes
        List<Guest> table1 = seating.get(1);
        List<Guest> table2 = seating.get(2);
        List<Guest> table3 = seating.get(3);

        // assert that tables are filled one at a time
        assertEquals(2, table1.size(), "Table 1 should be full.");
        assertEquals(2, table2.size(), "Table 2 should be full.");
        assertEquals(1, table3.size(), "Table 3 should have remaining guest.");
    }
}