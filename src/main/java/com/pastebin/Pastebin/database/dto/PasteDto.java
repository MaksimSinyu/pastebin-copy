package com.pastebin.Pastebin.database.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PasteDto {
    private String hash;
    private String text;
    private LocalDateTime createdAt;
}
