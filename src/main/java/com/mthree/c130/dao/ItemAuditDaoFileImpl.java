package com.mthree.c130.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ItemAuditDaoFileImpl implements ItemAuditDao {

    public static final String AUDIT_FILE = "audit.txt";

    public void writeAuditEntry(String entry) throws ItemFilePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new ItemFilePersistenceException("Could not persist audit information.", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
}
