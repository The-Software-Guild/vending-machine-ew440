package com.mthree.c130.service;

import com.mthree.c130.dao.ItemAuditDao;
import com.mthree.c130.dao.ItemFilePersistenceException;
import com.mthree.c130.dao.ItemsDao;
import com.mthree.c130.dto.CoinEnums;
import com.mthree.c130.dto.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemsServiceLayerImpl implements ItemsServiceLayer {

    private ItemAuditDao auditDao;
    ItemsDao dao;

    public ItemsServiceLayerImpl(ItemsDao dao, ItemAuditDao auditDao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public List<Item> getAllItems() throws ItemFilePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String ItemId) throws ItemFilePersistenceException {
        return dao.getItem(ItemId);
    }

    @Override
    public int getCoinValue(CoinEnums.coin coin) {
        switch (coin) {
            case ONEPENCE:
                return 1;
            case TWOPENCE:
                return 2;
            case FIVEPENCE:
                return 5;
            case TENPENCE:
                return 10;
            case TWENTYPENCE:
                return 20;
            case FIFTYPENCE:
                return 50;
            default:
                return 100;
        }
    }

    @Override
    public String purchaseItem(String choice) throws ItemFilePersistenceException {
        Item item = getItem(choice);
        if (item != null) {
            try {
                if (item.getPrice() > Total.getTotal()) {
                    String auditEntryNoFunds = "Product " + item.getName() + " not purchased due to lack of funds.";
                    auditDao.writeAuditEntry(auditEntryNoFunds);
                    throw new InsufficientFundsException("Not enough funds!");

                }
                if (item.getQuantity() < 1) {
                    String auditEntryNoStock = "Product " + item.getName() + " not purchased due to no remaining stock.";
                    auditDao.writeAuditEntry(auditEntryNoStock);
                    throw new NoItemInventoryException("Item is out of stock");
                }
                item.setQuantity(item.getQuantity() - 1);
                Total.decreaseTotal(item.getPrice());
                dao.save();
                String auditEntrySuccess = "Product " + item.getName() + " Purchased. ";
                auditDao.writeAuditEntry(auditEntrySuccess);
                return ("Item purchased");
            } catch (InsufficientFundsException e) {
                return ("Insufficient funds. Returning to menu");
            } catch (NoItemInventoryException f) {
                return ("Item is out of stock. Returning to menu");
            }
        }
        return ("Item does not exist. Please enter an existing item");
    }

    @Override
    public ArrayList<Integer> generateChange(int amount, List<Integer> divisors) {
        int currentAmount = amount;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int divisor : divisors) {
            int noCoins = currentAmount / divisor;
            result.add(noCoins);
            currentAmount = currentAmount % divisor;
        }
        return result;
    }
}

