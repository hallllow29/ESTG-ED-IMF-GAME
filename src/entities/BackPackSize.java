package entities;

public enum BackPackSize {
    SMALL(1),
    MEDIUM(2),
    LARGE(5),
    TRY_HARD(0);

    private final int capacity;

    BackPackSize(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

}
