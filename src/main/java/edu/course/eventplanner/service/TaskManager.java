package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Queue<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();

    public void addTask(Task task) {
        upcoming.add(task);
    }

    public Task executeNextTask() {
        // get next task from queue
        Task task = upcoming.poll();

        // if task exists, add to completed stack
        if (task != null) {
            completed.push(task);
        }

        return task;
    }

    public Task undoLastTask() {
        // pop last completed task from stack
        if (completed.isEmpty()) {
            return null;
        }

        Task task = completed.pop();

        // put it back at front of upcoming queue
        ((LinkedList<Task>) upcoming).addFirst(task);

        return task;
    }

    public int remainingTaskCount() { return upcoming.size(); }
}
