package edu.tanta.fci.reoil.exceptions;

import java.time.LocalDateTime;

public record ApiError(
    Object error,
    String path,
    int status,
    LocalDateTime timestamp
) {
}
