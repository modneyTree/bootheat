package com.example.bootheat;

import com.example.bootheat.domain.*;
import com.example.bootheat.dto.TableInfoResponse;
import com.example.bootheat.repository.*;
import com.example.bootheat.service.QueryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// @Transactional  // ❌ 커밋 위해 사용하지 않음

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SeedAndQueryTest {

    @Autowired BoothRepository boothRepository;
    @Autowired BoothTableRepository boothTableRepository;
    @Autowired MenuItemRepository menuItemRepository;
    @Autowired QueryService queryService;

    private Long savedBoothId;

    @BeforeEach
    void setUp() {
        // 🔎 콘솔 확인용으로 데이터 누적을 원하면 아래 3줄은 주석 처리하세요.
        // menuItemRepository.deleteAll();
        // boothTableRepository.deleteAll();
        // boothRepository.deleteAll();

        // booth 저장
        Booth booth = Booth.builder()
                .name("핫도그부스")
                .location("A동 앞")
                .build();
        booth = boothRepository.save(booth);
        savedBoothId = booth.getBoothId(); // ← 하드코딩 1L 대신 실제 ID 사용

        // tables: 1,2,3
        for (int n = 1; n <= 3; n++) {
            boothTableRepository.save(
                    BoothTable.builder()
                            .booth(booth)
                            .tableNumber(n)
                            .active(true)
                            .build()
            );
        }

        // menus
        menuItemRepository.saveAll(List.of(
                MenuItem.builder().booth(booth).name("핫도그").price(4000).available(true).build(),
                MenuItem.builder().booth(booth).name("치즈핫도그").price(5000).available(true).build(),
                MenuItem.builder().booth(booth).name("콜라").price(2000).available(true).build()
        ));
    }

    @Test
    void 테이블메뉴조회_API서비스_검증() {
        // when: 하드코딩 1L 대신 savedBoothId 사용
        TableInfoResponse res = queryService.getTableInfo(savedBoothId, 1);

        // then
        assertThat(res.boothId()).isEqualTo(savedBoothId);
        assertThat(res.tableNo()).isEqualTo(1);
        assertThat(res.menus()).hasSize(3);
        assertThat(res.menus().stream().map(TableInfoResponse.Menu::name))
                .containsExactlyInAnyOrder("핫도그","치즈핫도그","콜라");
    }
}
