package kovalenko.portfoliorebalancer.dto;

public record PortfolioResponse(Long id, Long investorId, String name, double toleranceBand) {}