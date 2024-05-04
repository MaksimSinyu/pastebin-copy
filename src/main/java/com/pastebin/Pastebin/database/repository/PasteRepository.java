package com.pastebin.Pastebin.database.repository;

import com.pastebin.Pastebin.database.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Paste findByHash(String hash);
}
