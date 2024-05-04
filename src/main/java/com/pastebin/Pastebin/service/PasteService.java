package com.pastebin.Pastebin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pastebin.Pastebin.database.dto.PasteDto;
import com.pastebin.Pastebin.database.model.Paste;
import com.pastebin.Pastebin.database.repository.PasteRepository;
import com.pastebin.Pastebin.util.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@Service
public class PasteService {
    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    private AmazonS3 s3client;

    private static final String BUCKET_NAME = "pastebincopy";

    public PasteDto createPaste(String text) {
        String hash = HashGenerator.getInstance().generateHash(text);
        saveToS3(text, hash);
        saveHashToDatabase(hash);

        PasteDto dto = new PasteDto();
        dto.setHash(hash);
        return dto;
    }

    public PasteDto getPasteByHash(String hash){
        Paste paste = pasteRepository.findByHash(hash);
        if (paste != null) {
            String text = getTextFromS3(hash);
            PasteDto dto = new PasteDto();
            dto.setHash(hash);
            dto.setText(text);
            dto.setCreatedAt(paste.getCreatedAt());
            return dto;
        }
        return null;
    }

    private void saveToS3(String text, String hash) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
        metadata.setContentLength(text.getBytes().length);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, hash, inputStream, metadata));
    }

    private void saveHashToDatabase(String hash) {
        Paste paste = new Paste();
        paste.setHash(hash);
        paste.setCreatedAt(LocalDateTime.now());
        pasteRepository.save(paste);
    }

    private String getTextFromS3(String hash) {
        return s3client.getObjectAsString(BUCKET_NAME, hash);
    }
}
