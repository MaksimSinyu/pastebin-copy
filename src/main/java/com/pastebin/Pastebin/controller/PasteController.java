package com.pastebin.Pastebin.controller;

import com.pastebin.Pastebin.database.dto.PasteDto;
import com.pastebin.Pastebin.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paste")
public class PasteController {
    @Autowired
    private PasteService pasteService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public ResponseEntity<PasteDto> createPaste(@RequestBody String text) {
        PasteDto pasteDto = pasteService.createPaste(text);
        return ResponseEntity.ok(pasteDto);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<PasteDto> getPasteByHash(@PathVariable String hash) {
        PasteDto pasteDto = pasteService.getPasteByHash(hash);
        if (pasteDto != null) {
            return ResponseEntity.ok(pasteDto);
        }
        return ResponseEntity.notFound().build();
    }
}
