package com.escrow.backend.dto;

import java.util.List;
import java.util.Map;

public class DatasetPayload {

    private String description;
    private List<Map<String, String>> rows;

    public String getDescription() {
        return description;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }
}
