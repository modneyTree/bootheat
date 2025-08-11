package com.example.bootheat;

import com.example.bootheat.domain.Booth;
import com.example.bootheat.dto.CreateManagerUserRequest;
import com.example.bootheat.dto.ManagerUserDto;
import com.example.bootheat.repository.BoothRepository;
import com.example.bootheat.repository.ManagerUserRepository;
import com.example.bootheat.service.ManagerUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired BoothRepository boothRepo;
    @Autowired ManagerUserService managerUserService;
    @Autowired ManagerUserRepository managerRepo;

    @Test
    void createManager_success() {
        // given: 부스 생성
        Booth booth = Booth.builder().name("테스트부스").location("L1").build();
        Long boothId = boothRepo.save(booth).getBoothId();

        // when: 매니저 등록
        CreateManagerUserRequest req =
                new CreateManagerUserRequest("manager01", "P@ssw0rd!", "MANAGER");
        ManagerUserDto dto = managerUserService.create(boothId, req);

        // then: DTO 필드 확인
        assertNotNull(dto.managerId());
        assertEquals(boothId, dto.boothId());
        assertEquals("manager01", dto.username());
        assertEquals("MANAGER", dto.role());

        // 그리고 실제 저장된 엔티티도 검증(패스워드는 해시여야 함)
        var entity = managerRepo.findById(dto.managerId()).orElseThrow();
        assertNotEquals("P@ssw0rd!", entity.getPasswordHash()); // BCrypt 해시 확인
        assertEquals("MANAGER", entity.getRole());
        assertEquals(boothId, entity.getBooth().getBoothId());
    }
}
