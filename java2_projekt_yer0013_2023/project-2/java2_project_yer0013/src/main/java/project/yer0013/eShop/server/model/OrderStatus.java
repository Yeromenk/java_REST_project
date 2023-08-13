package project.yer0013.eShop.server.model;

public enum OrderStatus {
    DEFAULT(0), BOUGHT(1), SENT(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
