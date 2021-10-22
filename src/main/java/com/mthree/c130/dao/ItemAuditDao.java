package com.mthree.c130.dao;

public interface ItemAuditDao {

    public void writeAuditEntry(String entry) throws ItemFilePersistenceException;

}
