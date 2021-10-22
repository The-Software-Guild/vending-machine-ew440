package com.mthree.c130.dao;

import com.mthree.c130.dto.Item;

import java.util.List;

public interface ItemsDao {

    List<Item> getAllItems() throws ItemFilePersistenceException;

    Item getItem(String ItemId) throws ItemFilePersistenceException;

    void save() throws ItemFilePersistenceException;

}
