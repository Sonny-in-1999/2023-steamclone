package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.CartItem;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.CartItemRepository;
import com.neurotoxin.steamclone.repository.single.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    // 멤버의 장바구니에 게임 추가(멤버와 장바구니 연동)
    @Transactional
    public void create(Member member, CartItem cartItem) {
        Member findMember = memberRepository.findMemberById(member.getId());
        findMember.getCart().add(cartItem);
        cartItem.setMember(findMember);
    }

    // 장바구니 게임 전건 조회
    public List<CartItem> findAllCartItems() {
        return cartItemRepository.findAll();
    }

    // 장바구니 게임 단일 조회
    public CartItem findCartItemById(Long cartItemId) {
        return cartItemRepository.findCartItemById(cartItemId);
    }

    // 장바구니 게임 삭제
    @Transactional
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
