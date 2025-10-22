package com.task_tracker.cli;

import java.util.Arrays;

public class TaskTrackerApp {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        if (args.length < 1) {
            printUsage();
            return;
        }

        String command = args[0].toLowerCase();

        try {
            switch (command) {

                case "add" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: TaskTrackerApp add <description>");
                        return;
                    }
                    String description = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    manager.addTask(description);
                }

                case "update" -> {
                    if (args.length < 3) {
                        System.out.println("Usage: TaskTrackerApp update <id> <new description>");
                        return;
                    }
                    String newDescription = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    manager.updateTask(args[1], newDescription);
                }

                case "delete" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: TaskTrackerApp delete <id>");
                        return;
                    }
                    manager.deleteTask(args[1]);
                }

                case "mark-in-progress" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: TaskTrackerApp mark-in-progress <id>");
                        return;
                    }
                    manager.markInProgress(args[1]);
                }

                case "mark-done" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: TaskTrackerApp mark-done <id>");
                        return;
                    }
                    manager.markDone(args[1]);
                }

                case "list" -> {
                    if (args.length < 2) {
                        manager.listTasks("All");
                    } else {
                        try {
                            Status filterStatus = Status.valueOf(args[1].toUpperCase().replace("-", "_"));
                            manager.listTasks(filterStatus.toString());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status: " + args[1]);
                        }
                    }
                }

                case "help" -> printUsage();

                default -> {
                    System.out.println("❌ Unknown command: " + command);
                    printUsage();
                }
            }

        } catch (Exception e) {
            System.err.println("⚠️ Error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("""
                Usage: TaskTrackerApp <command> [arguments]
                Commands:
                  add <description>             Add a new task
                  update <id> <description>     Update task description
                  delete <id>                   Delete a task
                  mark-in-progress <id>         Mark task as in progress
                  mark-done <id>                Mark task as done
                  list [status]                 List tasks (All, Todo, In Progress, Done)
                  help                          Show this help message
                """);
    }
}
