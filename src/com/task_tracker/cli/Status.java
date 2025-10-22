package com.task_tracker.cli;

import java.util.*;
import java.util.stream.Collectors;

public enum Status {

    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String value;
    private static final Map<String, Status> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(
            Arrays.stream(values())
                  .collect(Collectors.toMap(s -> s.value.toLowerCase(), s -> s))
        );
    }

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Status> fromString(String value) {
        if (value == null) return Optional.empty();
        return Optional.ofNullable(LOOKUP.get(value.toLowerCase()));
    }

    @Override
    public String toString() {
        return value;
    }
}
