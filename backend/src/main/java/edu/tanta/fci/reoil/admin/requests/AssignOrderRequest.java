package edu.tanta.fci.reoil.admin.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignOrderRequest(@NotNull @Positive Long orderId, @NotNull @Positive Long workerId) {
}
