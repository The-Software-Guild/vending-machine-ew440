package com.mthree.c130.controller;

import com.mthree.c130.dao.ItemFilePersistenceException;
import com.mthree.c130.dto.CoinEnums;
import com.mthree.c130.dto.Item;
import com.mthree.c130.service.InsufficientFundsException;
import com.mthree.c130.service.ItemsServiceLayer;
import com.mthree.c130.service.Total;
import com.mthree.c130.ui.ItemsView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsController {

    private ItemsView view;
    private ItemsServiceLayer service;

    public ItemsController(ItemsServiceLayer service, ItemsView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection;
        try {
            while (keepGoing) {
                view.displayMessage("Money entered (Pence) : " + Total.getTotal());
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        purchaseItem();
                        break;
                    case 2:
                        listItems();
                        break;
                    case 3:
                        getCoin();
                        break;
                    case 4:
                        getChange();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (ItemFilePersistenceException | InsufficientFundsException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    public void purchaseItem() throws ItemFilePersistenceException, InsufficientFundsException {
        String choice = view.getSelection();
        String message = service.purchaseItem(choice);
        view.displayMessage(message);
    }

    private void listItems() throws ItemFilePersistenceException {
        List<Item> ItemList = service.getAllItems();
        List<Item> filtered = ItemList.stream()
                .filter(b -> b.getQuantity() > 0)
                .collect(Collectors.toList());
        view.displayItems(filtered);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    public void getChange() throws ItemFilePersistenceException {
        List<Integer> coins = new ArrayList<>(Arrays.asList(100, 50, 20, 10, 5, 2, 1));
        List<Integer> change = service.generateChange(Total.getTotal(), coins);
        Total.setTotal(0);
        view.displayChange(change);
    }

    private CoinEnums.coin convertChoice(int choice) {
        if (choice == 1) {
            return CoinEnums.coin.ONEPENCE;
        }
        if (choice == 2) {
            return CoinEnums.coin.TWOPENCE;
        }
        if (choice == 3) {
            return CoinEnums.coin.FIVEPENCE;
        }
        if (choice == 4) {
            return CoinEnums.coin.TENPENCE;
        }
        if (choice == 5) {
            return CoinEnums.coin.TWENTYPENCE;
        }
        if (choice == 6) {
            return CoinEnums.coin.FIFTYPENCE;
        } else {
            return CoinEnums.coin.ONEPOUND;
        }
    }

    private void getCoin() {
        view.displayInsertMoney();
        int choice = view.getCoin();
        CoinEnums.coin value = convertChoice(choice);
        Total.increaseTotal(service.getCoinValue(value));
    }
}
