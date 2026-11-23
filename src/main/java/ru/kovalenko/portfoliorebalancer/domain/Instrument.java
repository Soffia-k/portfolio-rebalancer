package ru.kovalenko.portfoliorebalancer.domain;

public class Instrument {
    public enum Type { STOCK, BOND }

    private Long id;
    private String ticker;
    private String name;
    private Type type;

    public Instrument() {}

    public Instrument(Long id, String ticker, String name, Type type) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}