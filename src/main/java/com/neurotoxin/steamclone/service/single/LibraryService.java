package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.CartItem;
import com.neurotoxin.steamclone.entity.single.Library;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryService {

    private final LibraryRepository libraryRepository;

    // 라이브러리 생성
    @Transactional
    public Library create(Library library) {
        return libraryRepository.save(library);
    }

    // 라이브러리 전건 조회
    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    // 라이브러리 단일 조회
    public Library findLibraryById(Long libraryId) {
        return libraryRepository.findLibraryById(libraryId);
    }

    public Library findLibraryByMember(Member member) {
        return libraryRepository.findLibraryById(member.getLibrary().getId());
    }

    // 라이브러리 삭제
    @Transactional
    public void delete(Library library) {
        libraryRepository.delete(library);
    }

    // 게임 구매
    @Transactional
    public void add(CartItem newPurchasedGame) {
        newPurchasedGame.getMember().getLibrary().getPurchasedGame().add(newPurchasedGame);
    }

    // 게임 환불
    @Transactional
    public void refund(CartItem cartItem) {
        cartItem.getLibrary().getPurchasedGame().remove(cartItem);
    }

}
