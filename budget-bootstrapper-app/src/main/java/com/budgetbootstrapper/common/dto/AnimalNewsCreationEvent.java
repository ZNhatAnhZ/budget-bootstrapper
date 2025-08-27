package com.budgetbootstrapper.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class AnimalNewsCreationEvent {
    private int id;

    private String title;

    private String date;

    // Key: image name, Value: image URL
    private Map<String, String> images;

    private String category;

    private String content;
}
