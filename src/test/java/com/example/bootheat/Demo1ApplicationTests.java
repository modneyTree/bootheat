package com.example.bootheat;

import com.example.bootheat.Member.Member;
import com.example.bootheat.Member.MemberRepository;
import com.example.bootheat.Member.MemberService;
import com.example.bootheat.domain.Booth;
import com.example.bootheat.domain.BoothTable;
import com.example.bootheat.domain.MenuItem;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.BoothTableRepository;
import com.example.bootheat.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class Demo1ApplicationTests {

//	@Autowired
//	MemberService memberService;
//
//	@Autowired
//	MemberRepository memberRepository;
	@Autowired
	BoothRepository boothRepository;
	@Autowired
	BoothTableRepository boothTableRepository;
	@Autowired
	MenuItemRepository menuItemRepository;

	@Test
	void contextLoads() {
	}

//	@BeforeEach
//	void setUp() {
//		Booth booth = boothRepository.save(new Booth("핫도그부스", "A동 앞"));
//
//		boothTableRepository.saveAll(List.of(
//				new BoothTable(booth, 1, true),
//				new BoothTable(booth, 2, true),
//				new BoothTable(booth, 3, true)
//		));
//
//		menuItemRepository.saveAll(List.of(
//				new MenuItem(booth, "핫도그", 4000, true),
//				new MenuItem(booth, "치즈핫도그", 5000, true),
//				new MenuItem(booth, "콜라", 2000, true)
//		));
//	}
//
//	@Test
//	void 메뉴조회_테스트() {
//		var menus = menuItemRepository.findByBoothId(1L);
//		assertThat(menus).hasSize(3);
//	}

}
