package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @Test
    public void testAddTask() {
        // arrange task manager
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        // add all the tasks
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // check we have 3 tasks waiting
        assertEquals(3, taskManager.remainingTaskCount());
    }

    @Test
    public void testExecuteNextTask() {
        // arrange task manager 2 tasks
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // do the first task
        Task executed = taskManager.executeNextTask();

        // make sure we got task1 (first one added)
        assertEquals(task1, executed);
        // should have 1 task left
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @Test
    public void testExecuteTasksInFIFOOrder() {
        // arrange task manager
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        // add 3 tasks in order
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // execute them all
        Task executed1 = taskManager.executeNextTask();
        Task executed2 = taskManager.executeNextTask();
        Task executed3 = taskManager.executeNextTask();

        // check they came out in same order we put them in
        assertEquals(task1, executed1);
        assertEquals(task2, executed2);
        assertEquals(task3, executed3);
    }

    @Test
    public void testExecuteNextTaskWhenEmpty() {
        // arrange empty task manager
        TaskManager taskManager = new TaskManager();

        // try to execute when theres nothing there
        Task executed = taskManager.executeNextTask();

        // should get null back
        assertNull(executed);
    }

    @Test
    public void testUndoLastTask() {
        // arrange task manager with 2 tasks
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // do both tasks
        taskManager.executeNextTask();
        taskManager.executeNextTask();

        // undo the last one (task2)
        Task undone = taskManager.undoLastTask();

        // should get task2 back
        assertEquals(task2, undone);
        // should have 1 task in the queue again
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @Test
    public void testUndoWhenNoCompletedTasks() {
        // arrange empty task manager
        TaskManager taskManager = new TaskManager();

        // try to undo when we havent done anything
        Task undone = taskManager.undoLastTask();

        // should get null
        assertNull(undone);
    }

    @Test
    public void testUndoMultipleTasks() {
        // arrange task manager with 3 tasks
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");
        Task task3 = new Task("Prepare food");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // do all 3 tasks
        taskManager.executeNextTask();
        taskManager.executeNextTask();
        taskManager.executeNextTask();

        // now undo all 3
        Task undone1 = taskManager.undoLastTask();
        Task undone2 = taskManager.undoLastTask();
        Task undone3 = taskManager.undoLastTask();

        // should come back in reverse order
        // last one done (task3) comes back first
        assertEquals(task3, undone1);
        assertEquals(task2, undone2);
        assertEquals(task1, undone3);
    }

    @Test
    public void testExecuteAfterUndo() {
        // arrange task manager
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Set up tables");
        Task task2 = new Task("Decorate venue");

        // add both tasks
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // do both tasks
        taskManager.executeNextTask();
        taskManager.executeNextTask();

        // undo the second one
        taskManager.undoLastTask();

        // now execute again, should get task2 again since we undid it
        Task whatWeGotBack = taskManager.executeNextTask();
        assertEquals(task2, whatWeGotBack);
    }
}