package kovalenko.portfoliorebalancer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PortfolioCreateRequest(
        @NotNull(message = "investorId must not be null")
        @Positive(message = "investorId must be positive")
        Long investorId,

        @NotBlank(message = "name must not be blank")
        @Size(min = 2, max = 100, message = "name must be between 2 and 100 characters")
        String name,

        @Positive(message = "toleranceBand must be positive")
        double toleranceBand
) {}