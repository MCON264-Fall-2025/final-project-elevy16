package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @Test
    public void testAddTask() {
        // Create task manager and 3 tasks
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        // Add all 3 tasks
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Assert: Should have 3 tasks in the queue
        assertEquals(3, taskManager.remainingTaskCount(), "Should have 3 tasks");
    }

    @Test
    public void testExecuteNextTask() {
        // Create tasks and add them
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Execute the first task
        Task executed = taskManager.executeNextTask();

        // Assert:
        // Correct task was executed
        assertEquals(task1, executed, "Should execute first task added");
        // Count decreased by 1
        assertEquals(2, taskManager.remainingTaskCount(), "Should have 2 tasks remaining");
    }

    @Test
    public void testExecuteTasksInFIFOOrder() {
        // Arrange
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Execute all 3 tasks
        Task executed1 = taskManager.executeNextTask();
        Task executed2 = taskManager.executeNextTask();
        Task executed3 = taskManager.executeNextTask();

        // Assert: still in FIFO order
        assertEquals(task1, executed1, "First task should be task1");
        assertEquals(task2, executed2, "Second task should be task2");
        assertEquals(task3, executed3, "Third task should be task3");
        assertEquals(0, taskManager.remainingTaskCount(), "No tasks should remain");
    }

    @Test
    public void testUndoLastTask() {
        // Arrange
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Execute both tasks
        taskManager.executeNextTask();  // Executes task1
        taskManager.executeNextTask();  // Executes task2

        // Undo the last executed task
        Task undone = taskManager.undoLastTask();

        // Assert:
        // Correct task was undone
        assertEquals(task2, undone, "Should undo task2 (last executed)");
        // Task is back in the queue
        assertEquals(1, taskManager.remainingTaskCount(), "Should have 1 task back in queue");
    }

    @Test
    public void testUndoMultipleTasks() {
        // Arrange
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Execute all tasks
        taskManager.executeNextTask();  // task1
        taskManager.executeNextTask();  // task2
        taskManager.executeNextTask();  // task3

        assertEquals(0, taskManager.remainingTaskCount(), "All tasks completed");

        // Undo all 3 tasks
        Task undone1 = taskManager.undoLastTask();
        Task undone2 = taskManager.undoLastTask();
        Task undone3 = taskManager.undoLastTask();

        // Assert: still in LIFO order
        assertEquals(task3, undone1, "Should undo task3 first");
        assertEquals(task2, undone2, "Should undo task2 second");
        assertEquals(task1, undone3, "Should undo task1 third");
        assertEquals(3, taskManager.remainingTaskCount(), "All tasks back in queue");
    }


}
