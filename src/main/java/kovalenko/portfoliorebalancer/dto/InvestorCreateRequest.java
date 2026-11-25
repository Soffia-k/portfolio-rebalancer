package kovalenko.portfoliorebalancer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InvestorCreateRequest(
        @NotBlank(message = "fullName must not be blank")
        @Size(min = 2, max = 100, message = "fullName must be between 2 and 100 characters")
        String fullName
) {}