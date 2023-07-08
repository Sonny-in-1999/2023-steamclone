package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.entity.single.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findLibraryById(Long libraryId);
}
