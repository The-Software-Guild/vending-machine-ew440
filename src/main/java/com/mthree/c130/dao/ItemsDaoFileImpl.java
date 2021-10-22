package com.mthree.c130.dao;

import com.mthree.c130.dto.Item;

import java.io.*;
import java.util.*;

public class ItemsDaoFileImpl implements ItemsDao {

    public final String ITEMS_FILE;
    public static final String DELIMITER = "::";

    private Map<String, Item> Items = new HashMap<>();

    public ItemsDaoFileImpl(){
        ITEMS_FILE = "items.txt";
    }

    public ItemsDaoFileImpl(String textFile){
        ITEMS_FILE = textFile;
    }

    @Override
    public List<Item> getAllItems() throws ItemFilePersistenceException {
        loadInventory();
        return new ArrayList(Items.values());
    }

    @Override
    public Item getItem(String itemName) throws ItemFilePersistenceException {
        try {
            // normalise case
            itemName = itemName.substring(0, 1).toUpperCase() + itemName.substring(1).toLowerCase();
            loadInventory();
            return Items.get(itemName);
        } catch (StringIndexOutOfBoundsException e) {
            // returns no product found, if nothing entered
            return Items.get(itemName);
        }
    }

    @Override
    public void save() throws ItemFilePersistenceException {
        writeInventory();
    }

    private Item unmarshallItem(String ItemAsText) {
        String[] ItemTokens = ItemAsText.split(DELIMITER);
        String name = ItemTokens[0];
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        Item ItemFromFile = new Item(name);
        ItemFromFile.setPrice(Integer.parseInt(ItemTokens[1]));
        ItemFromFile.setQuantity(Integer.parseInt(ItemTokens[2]));
        return ItemFromFile;
    }

    private void loadInventory() throws ItemFilePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEMS_FILE)));
        } catch (FileNotFoundException e) {
            throw new ItemFilePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            Items.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
    }

    private String marshallItem(Item aItem) {
        String ItemAsText = aItem.getName() + DELIMITER;
        ItemAsText += aItem.getPrice() + DELIMITER;
        ItemAsText += aItem.getQuantity();
        return ItemAsText;
    }

    private void writeInventory() throws ItemFilePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ITEMS_FILE));
        } catch (IOException e) {
            throw new ItemFilePersistenceException(
                    "Could not save Item data.", e);
        }
        String ItemAsText;
        List<Item> ItemList = this.getAllItems();
        for (Item currentItem : ItemList) {
            ItemAsText = marshallItem(currentItem);
            out.println(ItemAsText);
            out.flush();
        }
        out.close();
    }
}
