package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findLibraryById(Long libraryId);
}
