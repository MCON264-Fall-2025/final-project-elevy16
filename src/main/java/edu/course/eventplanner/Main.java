package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;


import java.util.*;

public class Main {

    GuestListManager guestManager;
    VenueSelector venueSelector;
    TaskManager taskManager;
    Venue selectedVenue;
    Map<Integer, List<Guest>> seating;

    public Main() {
        guestManager = new GuestListManager();
        taskManager = new TaskManager();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // menu
            System.out.println("\n1. Load sample data");
            System.out.println("2. Add guest");
            System.out.println("3. Remove guest");
            System.out.println("4. Select venue");
            System.out.println("5. Generate seating chart");
            System.out.println("6. Add preparation task");
            System.out.println("7. Execute next task");
            System.out.println("8. Undo last task");
            System.out.println("9. Print event summary");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    loadData(scanner);
                    break;
                case 2:
                    addGuest(scanner);
                    break;
                case 3:
                    removeGuest(scanner);
                    break;
                case 4:
                    pickVenue(scanner);
                    break;
                case 5:
                    makeSeating();
                    break;
                case 6:
                    addTask(scanner);
                    break;
                case 7:
                    doTask();
                    break;
                case 8:
                    undoTask();
                    break;
                case 9:
                    printSummary();
                    break;
            }
        }

        scanner.close();
    }

    private void loadData(Scanner s) {
        System.out.print("How many guests? ");
        int n = s.nextInt();
        s.nextLine();

        // generate sample guests and add them
        List<Guest> guests = Generators.GenerateGuests(n);
        for (Guest g : guests) {
            guestManager.addGuest(g);
        }

        // generate venues for selection
        List<Venue> venues = Generators.generateVenues();
        venueSelector = new VenueSelector(venues);

        System.out.println("Loaded " + n + " guests and " + venues.size() + " venues");
    }

    private void addGuest(Scanner s) {
        System.out.print("Guest name: ");
        String name = s.nextLine();

        System.out.print("Group (family/friends/coworkers/neighbors): ");
        String group = s.nextLine();

        guestManager.addGuest(new Guest(name, group));
        System.out.println("Added " + name);
    }

    private void removeGuest(Scanner s) {
        System.out.print("Guest name: ");
        String name = s.nextLine();

        if (guestManager.removeGuest(name)) {
            System.out.println("Removed " + name);
        } else {
            System.out.println("Not found");
        }
    }

    private void pickVenue(Scanner s) {
        if (venueSelector == null) {
            System.out.println("Load data first");
            return;
        }

        System.out.print("Budget: ");
        double budget = s.nextDouble();

        System.out.print("Guest count: ");
        int count = s.nextInt();
        s.nextLine();

        // find best venue that fits budget and capacity
        selectedVenue = venueSelector.selectVenue(budget, count);

        if (selectedVenue != null) {
            System.out.println("Picked: " + selectedVenue.getName());
            System.out.println("Cost: $" + selectedVenue.getCost());
        } else {
            System.out.println("No venue fits");
        }
    }

    private void makeSeating() {
        if (selectedVenue == null) {
            System.out.println("Pick a venue first");
            return;
        }

        if (guestManager.getGuestCount() == 0) {
            System.out.println("No guests");
            return;
        }

        // group guests by tag and assign to tables
        SeatingPlanner planner = new SeatingPlanner(selectedVenue);
        seating = planner.generateSeating(guestManager.getAllGuests());

        System.out.println("Made seating chart with " + seating.size() + " tables");
    }

    private void addTask(Scanner s) {
        System.out.print("Task: ");
        String desc = s.nextLine();
        taskManager.addTask(new Task(desc));
        System.out.println("Added");
    }

    private void doTask() {
        Task t = taskManager.executeNextTask();
        if (t != null) {
            System.out.println("Did: " + t.getDescription());
        } else {
            System.out.println("No tasks");
        }
    }

    private void undoTask() {
        Task t = taskManager.undoLastTask();
        if (t != null) {
            System.out.println("Undid: " + t.getDescription());
        } else {
            System.out.println("Nothing to undo");
        }
    }

    private void printSummary() {
        System.out.println("\n--- Summary ---");
        System.out.println("Guests: " + guestManager.getGuestCount());

        if (selectedVenue != null) {
            System.out.println("Venue: " + selectedVenue.getName() + " ($" + selectedVenue.getCost() + ")");
        }

        if (seating != null) {
            System.out.println("Tables: " + seating.size());
        }

        System.out.println("Tasks left: " + taskManager.remainingTaskCount());
    }
}