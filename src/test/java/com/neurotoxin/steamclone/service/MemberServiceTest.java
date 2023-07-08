package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {


    @Autowired
    MemberService memberService;
    @Autowired
    LibraryService libraryService;
    @Autowired
    WishListGameService wishListGameService;
    @Autowired
    WishListItemService wishListItemService;
    @Autowired
    GameService gameService;
    @Autowired
    TagService tagService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartItemGameService cartItemGameService;

    @Test
    @DisplayName("회원 생성")
    @DirtiesContext
    @Transactional
    public void createMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setEmail("example1@gmail.com");
        member1.setLoginName("login-example1");
        member1.setPassword("password123");
        member1.setPhoneNumber("010-1234-5678");
        member1.setGrade(Grade.DEVELOPER);

        Member member2 = new Member();
        member2.setEmail("example2@gmail.com");
        member2.setLoginName("login-example2");
        member2.setPassword("password123");
        member2.setPhoneNumber("010-1234-5678");
        member2.setGrade(Grade.USER);

        //when
        memberService.register(member1);
        memberService.register(member2);
        //then
        assertThat(member1).isEqualTo(memberService.findMemberById(member1.getId()));
        assertThat(member2).isEqualTo(memberService.findMemberById(member2.getId()));
        assertThat(member1.getLibrary().getId()).isEqualTo(2);
        assertThat(member1.getLibrary().getMember().getId()).isEqualTo(1);
        assertThat(member2.getLibrary().getId()).isEqualTo(4);
        assertThat(member2.getLibrary().getMember().getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("중복 회원 검증")
    @DirtiesContext
    public void validateDupMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setLoginName("login-example1");

        Member member2 = new Member();
        member2.setLoginName("login-example1");
        //when
        memberService.register(member1);
        //then
        assertThrows(IllegalStateException.class, () ->
                memberService.register(member2));
    }

    @Test
    @DisplayName("회원 탈퇴")
    @DirtiesContext
    public void memberDelete() throws Exception {

        Member member = new Member();
        member.setNickName("nickname1");
        member.setLoginName("loginName1");

        memberService.register(member);
        List<Member> allMembers = memberService.findAllMembers();
        assertThat(allMembers.size()).isEqualTo(1);

        memberService.delete(member.getId());
        List<Member> allMembersAfterDelete = memberService.findAllMembers();
        assertThat(allMembersAfterDelete).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 회원 검증")
    @DirtiesContext
    public void unknownMember() throws Exception {
        //given
        Member member = new Member();
        member.setLoginName("member");

        //then
        assertThrows(NullPointerException.class, () ->
                memberService.delete(999L));
    }

    @Test
    @DisplayName("회원 정보 수정")
    @DirtiesContext
    public void updateMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setEmail("example1@gmail.com");
        member1.setPassword("password123");
        member1.setPhoneNumber("010-1234-5678");
        member1.setGrade(Grade.DEVELOPER);
        memberService.register(member1);

        Member newMember = new Member();
        newMember.setEmail("NMH@naver.com");
        newMember.setPassword("0523");
        newMember.setPhoneNumber("010-0523-0523");
        newMember.setGrade(Grade.USER);
        assertThat(newMember.getEmail()).isEqualTo("NMH@naver.com");
        //when
        memberService.update(member1.getId(), newMember);
        Member updatedMember = memberService.findMemberById(member1.getId());
        //then
        assertThat(updatedMember.getEmail()).isEqualTo(newMember.getEmail());
        assertThat(updatedMember.getPassword()).isEqualTo(newMember.getPassword());
        assertThat(updatedMember.getPhoneNumber()).isEqualTo(newMember.getPhoneNumber());
        assertThat(updatedMember.getGrade()).isEqualTo(newMember.getGrade());
    }

    @Test
    @DisplayName("멤버의 찜 목록에 게임 추가")
    @DirtiesContext
    @Transactional
    public void memberAddWishListItem() throws Exception {
        //given
        // 게임과 멤버를 하나씩 생성해서 DB에 저장
        Member member = new Member();
        memberService.register(member);
        List<WishListGame> wishListGames = new ArrayList<>();

        Game game = new Game();
        Tag tag = new Tag();
        tag.setName("adult");
        tagService.create(tag);

        Game game1 = gameService.create(game, tag.getName());
        WishListItem wishListItem = new WishListItem(member, wishListGames);
        WishListGame wishListGame = new WishListGame(game, wishListItem);

        //when
        // 멤버의 찜목록에 게임을 추가
        wishListItemService.create(wishListGame);
        Member findMember = memberService.findMemberById(member.getId());
        List<WishListItem> wishListItems = new ArrayList<>();
        wishListItems.add(wishListItem);
        findMember.setWishList(wishListItems);

        //then
        assertThat(wishListItems).isEqualTo(findMember.getWishList());
    }

    @Test
    @DisplayName("멤버의 장바구니에 게임 추가")
    @DirtiesContext
    @Transactional
    public void memberAddCartItem() throws Exception {
        //given
        // 게임과 멤버를 하나씩 생성해서 DB에 저장
        Member member = new Member();
        memberService.register(member);

        //when
        // 멤버의 장바구니에 게임 추가
        Game game = new Game();
        Tag tag = new Tag();
        tag.setName("adult");
        tagService.create(tag);

        Game savedGame = gameService.create(game, tag.getName());
        CartItem cartItem = new CartItem();
        CartItemGame cartItemGame = cartItemGameService.create(savedGame, cartItem);
        Member findMember = memberService.findMemberById(member.getId());
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        member.setCart(cartItems);

        //then
        assertThat(cartItems).isEqualTo(member.getCart());
    }
}
