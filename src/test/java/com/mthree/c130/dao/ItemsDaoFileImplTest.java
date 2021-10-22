package com.mthree.c130.dao;

import com.mthree.c130.dto.Item;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemsDaoFileImplTest {

    ItemsDao testDao;
    ItemsDao testDao2;

    @BeforeEach
    void setUp() {
        testDao = new ItemsDaoFileImpl("daoTest.txt");
        testDao2 = new ItemsDaoFileImpl("blank.txt");
    }

    @org.junit.jupiter.api.Test
    void getAllItems() throws ItemFilePersistenceException {
        List<Item> items = testDao.getAllItems();
        assertNotNull(items);
        assertEquals(5, items.size());
    }

    @org.junit.jupiter.api.Test
    void getAllItemsBlank() throws ItemFilePersistenceException {
        assertTrue(testDao2.getAllItems().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getItem() throws ItemFilePersistenceException {
        // There is no create item method in this program so stateful testing is harder.
        List<Item> items = testDao.getAllItems();
        Item testItem = testDao.getItem("pepsi");
        assertEquals(testItem.getQuantity(),10);
        testItem.setQuantity(testItem.getQuantity()-1);
        testDao.save();
        List<Item> items2 = testDao.getAllItems();
        Item testItem2 = testDao.getItem("pepsi");
        assertEquals(testItem2.getQuantity(),9);
        // reset quantity
        testItem2.setQuantity(testItem2.getQuantity()+1);
        testDao.save();

    }


}