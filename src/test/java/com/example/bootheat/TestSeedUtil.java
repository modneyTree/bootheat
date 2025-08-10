// src/test/java/com/example/bootheat/TestSeedUtil.java
package com.example.bootheat;

import com.example.bootheat.domain.*;
import com.example.bootheat.repository.*;

import java.util.List;

public final class TestSeedUtil {
    private TestSeedUtil() {}

    public static Booth seedBoothWithTablesAndMenus(
            BoothRepository boothRepo,
            BoothTableRepository tableRepo,
            MenuItemRepository menuRepo
    ) {
        Booth booth = Booth.builder().name("핫도그부스").location("A동 앞").build();
        booth = boothRepo.save(booth);

        for (int n = 1; n <= 3; n++) {
            tableRepo.save(BoothTable.builder()
                    .booth(booth).tableNumber(n).active(true).build());
        }

        menuRepo.saveAll(List.of(
                MenuItem.builder().booth(booth).name("핫도그").price(4000).available(true).build(),
                MenuItem.builder().booth(booth).name("치즈핫도그").price(5000).available(true).build(),
                MenuItem.builder().booth(booth).name("콜라").price(2000).available(true).build()
        ));
        return booth;
    }
}
