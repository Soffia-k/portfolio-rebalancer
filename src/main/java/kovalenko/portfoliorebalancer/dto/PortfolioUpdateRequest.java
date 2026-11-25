package kovalenko.portfoliorebalancer.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PortfolioUpdateRequest(
        @Size(min = 2, max = 100, message = "name must be between 2 and 100 characters")
        String name,

        @Positive(message = "toleranceBand must be positive")
        Double toleranceBand
) {}