package com.example.sprintdev.model;

public enum TicketSize {
    NOT_SIZED(0, "UNSIZED"),
    SIZE_1(1, "S"), SIZE_2(2, "S"), SIZE_3(3, "S"),
    SIZE_5(5, "M"), SIZE_8(8, "M"), SIZE_13(13, "M"),
    SIZE_20(20, "L"), SIZE_40(40, "L"),
    SIZE_100(100, "XL");

    private final int size;
    private final String tshirtSize;

    TicketSize(int size, String tshirtSize) {
        this.size = size;
        this.tshirtSize = tshirtSize;
    }

    public int getSize() {
        return size;
    }

    public String getTshirtSize() {
        return tshirtSize;
    }

    public String toString() {
        return this.tshirtSize + "(" + this.size + ")";
    }
}
