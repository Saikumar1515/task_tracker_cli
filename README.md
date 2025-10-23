# task_tracker_cli

A simple Command-Line Interface (CLI) based Task Tracker built using Java.  
You can add, update, delete, and list tasks directly from the terminal.

---

## Features

- Add new tasks  
- Update task descriptions  
- Delete tasks  
- Mark tasks as **In Progress** or **Done**  
- List all tasks or filter by status (Todo / In Progress / Done)  
- Persistent storage using JSON file (`tasks.json`)  

---

## GitHub Repository

[https://github.com/Saikumar1515/task_tracker_cli](https://github.com/Saikumar1515/task_tracker_cli)

---

## Quick Start (compile & run)

Open a terminal in the project root and run:

```bash
# Compile all Java sources into bin/
javac -d bin src/com/task_tracker/cli/*.java

# Add a new task
java -cp bin com.task_tracker.cli.TaskTrackerApp add "Learn Spring Boot"

# List all tasks
java -cp bin com.task_tracker.cli.TaskTrackerApp list

# Update the description of task with id 1
java -cp bin com.task_tracker.cli.TaskTrackerApp update 1 "Learn Spring Boot - Deep Dive"

# Mark task as in progress
java -cp bin com/task_tracker.cli.TaskTrackerApp mark-in-progress 1

# Mark task as done
java -cp bin com/task_tracker.cli.TaskTrackerApp mark-done 1

# Delete a task
java -cp bin com/task_tracker.cli.TaskTrackerApp delete 1

## Project Source

This project was built as part of the **[Task Tracker Project on roadmap.sh](https://roadmap.sh/projects/task-tracker)
