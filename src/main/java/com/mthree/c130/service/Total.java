package com.mthree.c130.service;

public class Total {

    // This class was made a static class as creating an object didn't make too much sense to me, but i think i could be wrong.

    private static int total = 0;

    public static void setTotal(int newTotal) {
        total = newTotal;
    }

    public static int getTotal() {
        return total;
    }

    public static void increaseTotal(int increase) {
        total += increase;
    }

    public static void decreaseTotal(int decrease) {
        total -= decrease;
    }

}
