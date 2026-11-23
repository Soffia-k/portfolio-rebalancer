package ru.kovalenko.portfoliorebalancer.domain;

public class Portfolio {
    private Long id;
    private Long investorId;
    private String name;
    private double toleranceBand; // допуск в п.п.

    public Portfolio() {}

    public Portfolio(Long id, Long investorId, String name, double toleranceBand) {
        this.id = id;
        this.investorId = investorId;
        this.name = name;
        this.toleranceBand = toleranceBand;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getToleranceBand() { return toleranceBand; }
    public void setToleranceBand(double toleranceBand) { this.toleranceBand = toleranceBand; }
}