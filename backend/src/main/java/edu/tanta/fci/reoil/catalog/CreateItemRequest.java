package edu.tanta.fci.reoil.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateItemRequest(@NotNull @NotBlank String name, @NotNull  @Positive Double quantity, @Positive @NotNull Long points, @NotNull @NotBlank String unit) {
}
