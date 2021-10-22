package com.mthree.c130.service;

import com.mthree.c130.dao.ItemFilePersistenceException;
import com.mthree.c130.dto.CoinEnums;
import com.mthree.c130.dto.Item;

import java.util.ArrayList;
import java.util.List;

public interface ItemsServiceLayer {

    List<Item> getAllItems() throws ItemFilePersistenceException;

    Item getItem(String itemId) throws ItemFilePersistenceException;

    int getCoinValue(CoinEnums.coin coin);

    String purchaseItem(String Choice) throws ItemFilePersistenceException, InsufficientFundsException;

    public ArrayList<Integer> generateChange(int amount, List<Integer> divisors);

}
