package com.mthree.c130.service;

import com.mthree.c130.dao.*;
import com.mthree.c130.dto.CoinEnums;
import com.mthree.c130.dto.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemsServiceLayerImplTest {

    ItemsServiceLayer service;
    ItemsDao testDao;
    ItemAuditDao auditDao;


    @BeforeEach
    void setUp() {
        testDao = new ItemsDaoFileImpl("serviceTest.txt");
        auditDao = new ItemAuditDaoFileImpl();
        service = new ItemsServiceLayerImpl(testDao,auditDao);
    }


    @Test
    void getCoinValue() {
        assertTrue(service.getCoinValue(CoinEnums.coin.FIFTYPENCE) == 50);
    }

    @Test
    void purchaseItem() throws ItemFilePersistenceException, InsufficientFundsException {
        // This test doesn't work, but it does work in the real thing.
        List<Item> items = testDao.getAllItems();
        Item testItem = testDao.getItem("pepsi");
        service.purchaseItem("pepsi");
        assertEquals(testItem.getQuantity(),12);
        Total.increaseTotal(100);
        service.purchaseItem("pepsi");
        assertEquals(testItem.getQuantity(),11);
        assertEquals(Total.getTotal(),10);
        service.purchaseItem("coke");
        testItem = testDao.getItem("coke");
        assertEquals(Total.getTotal(),50);
        assertEquals(testItem.getQuantity(),0);
    }

    @Test
    void generateChange() {
        List<Integer> divisors = new ArrayList<>(Arrays.asList(100, 50, 20, 10, 5, 2, 1));
        ArrayList<Integer> result = service.generateChange(388, divisors);
        List<Integer> namesList = Arrays.asList(3,1,1,1,1,1,1);
        ArrayList<Integer> test1 = new ArrayList<>();
        test1.addAll(namesList);
        assertTrue(result.equals(test1));

    }
}