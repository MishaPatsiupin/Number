package com.example.demo.utils;

import lombok.Data;

@Data
public class FactRequest {
    private Long number;
    private String type;
    private String fact;
    private String author;
}
