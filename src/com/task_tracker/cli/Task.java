package com.task_tracker.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Map;

public class Task {

	private static final AtomicInteger lastId = new AtomicInteger(0);

	private final int id;
	private String description;
	private Status status;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	public Task(String description) {
		this.id = lastId.incrementAndGet();
		this.description = description;
		this.status = Status.TODO;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Task(int id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

		if (id > lastId.get()) {
			lastId.set(id);
		}
	}

	public int getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void markInProgress() {
		this.status = Status.IN_PROGRESS;
		this.updatedAt = LocalDateTime.now();
	}

	public void markDone() {
		this.status = Status.DONE;
		this.updatedAt = LocalDateTime.now();
	}

	public void updateDescription(String description) {
		this.description = description;
		this.updatedAt = LocalDateTime.now();
	}

	public String toJson() {
		return String.format(
				"{\"id\":%d, \"description\":\"%s\", \"status\":\"%s\", \"createdAt\":\"%s\", \"updatedAt\":\"%s\"}",
				id, description.strip(), status, createdAt.format(formatter), updatedAt.format(formatter));
	}

	public static Task fromJson(String json) {
		json = json.trim().replace("{", "").replace("}", "").replace("\"", "");

		String[] parts = json.split(",");
		Map<String, String> map = new HashMap<>();
		for (String part : parts) {
			String[] keyValue = part.split(":", 2);
			if (keyValue.length == 2) {
				map.put(keyValue[0].trim(), keyValue[1].trim());
			}
		}

		int id = Integer.parseInt(map.get("id"));
		String description = map.get("description");
		Status status = Status.valueOf(map.get("status").toUpperCase().replace(" ", "_"));
		LocalDateTime createdAt = LocalDateTime.parse(map.get("createdAt"), formatter);
		LocalDateTime updatedAt = LocalDateTime.parse(map.get("updatedAt"), formatter);

		return new Task(id, description, status, createdAt, updatedAt);
	}

	@Override
	public String toString() {
		return String.format("id: %d, description: %s, status: %s, createdAt: %s, updatedAt: %s", id,
				description.strip(), status, createdAt.format(formatter), updatedAt.format(formatter));
	}
}
