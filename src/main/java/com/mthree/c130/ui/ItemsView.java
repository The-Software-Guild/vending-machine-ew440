package com.mthree.c130.ui;

import com.mthree.c130.dto.Item;

import java.util.List;

public class ItemsView {

    private UserIO io;

    public ItemsView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("1. Purchase Item");
        io.print("2. View products");
        io.print("3. Insert a coin");
        io.print("4. Get Change");
        io.print("5. Exit");
        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public String getSelection() {
        return io.readString("Please enter product choice.");
    }

    public void displayItems(List<Item> ItemsList) {
        for (Item currentItem : ItemsList) {
            String itemInfo = String.format("Name : %s Price: %s  Quantity: %s ",
                    currentItem.getName(),
                    currentItem.getPrice(),
                    currentItem.getQuantity());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayItemBanner() {
        io.print("Please enter product name");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayChange(List<Integer> list) {
        io.print("Your change is:");
        io.print(list.get(0) + " Pound coins");
        io.print(list.get(1) + " 50 pence pieces");
        io.print(list.get(2) + " 20 pence pieces");
        io.print(list.get(3) + " 10 pence pieces");
        io.print(list.get(4) + " 5 pence pieces");
        io.print(list.get(5) + " 2 pence pieces");
        io.print(list.get(6) + " 1 pence pieces");
        io.readString("Press Enter to continue.");

    }

    public void displayInsertMoney() {
        io.print("Please choose a coin to enter");
    }

    public int getCoin() {
        io.print("1. 1p");
        io.print("2. 2p");
        io.print("3. 5p");
        io.print("4. 10p");
        io.print("5. 20p");
        io.print("6. 50p");
        io.print("7. 1 Pound");
        return io.readInt("Please enter the coin to insert (1-7)", 1, 7);
    }

    public void displayMessage(String message) {
        io.print(message);
    }


}
