package com.task_tracker.cli;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TaskManager {

    private static final Path FILE_PATH = Path.of("tasks.json");
    private final List<Task> tasks;

    public TaskManager() {
        this.tasks = loadTasks();
    }

    private List<Task> loadTasks() {
        List<Task> storedTasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return storedTasks;
        }

        try {
            String jsonContent = Files.readString(FILE_PATH).trim();
            if (jsonContent.isEmpty() || jsonContent.equals("[]")) {
                return storedTasks;
            }

            String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");
            for (String taskJson : taskList) {
                taskJson = taskJson.trim();
                if (!taskJson.endsWith("}")) {
                    taskJson += "}";
                }
                storedTasks.add(Task.fromJson(taskJson));
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }

        return storedTasks;
    }

    public void saveTasks() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        try {
            Files.writeString(FILE_PATH, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
        saveTasks();
        System.out.println("✅ Task added successfully (ID: " + newTask.getId() + ")");
    }

    public void updateTask(String id, String newDescription) {
        Task task = findTask(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.updateDescription(newDescription);
        saveTasks();
        System.out.println("✏️ Task updated successfully (ID: " + id + ")");
    }

    public void deleteTask(String id) {
        Task task = findTask(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        tasks.remove(task);
        saveTasks();
        System.out.println("🗑️ Task deleted successfully (ID: " + id + ")");
    }

    public void markInProgress(String id) {
        Task task = findTask(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.markInProgress();
        saveTasks();
        System.out.println("🚧 Task marked as In Progress (ID: " + id + ")");
    }

    public void markDone(String id) {
        Task task = findTask(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        task.markDone();
        saveTasks();
        System.out.println("✅ Task marked as Done (ID: " + id + ")");
    }

    public void listTasks(String type) {
        boolean showAll = type.equalsIgnoreCase("All");
        System.out.println("📋 Task List (" + type + "):");
        for (Task task : tasks) {
            String status = task.getStatus().toString();
            if (showAll || status.equalsIgnoreCase(type)) {
                System.out.println(task);
            }
        }
    }

    private Optional<Task> findTask(String id) {
        try {
            int parsedId = Integer.parseInt(id);
            return tasks.stream().filter(task -> task.getId() == parsedId).findFirst();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid task ID: " + id);
        }
    }
}
