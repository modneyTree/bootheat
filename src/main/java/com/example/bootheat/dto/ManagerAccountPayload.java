// dto/ManagerAccountPayload.java
package com.example.bootheat.dto;

public record ManagerAccountPayload(
        String username,
        String accountBank,
        String accountNo,
        String accountHolder
) {
    public static ManagerAccountPayload from(com.example.bootheat.domain.ManagerUser m) {
        return new ManagerAccountPayload(
                m.getUsername(),
                m.getAccountBank(),
                m.getAccountNo(),
                m.getAccountHolder()
        );
    }
}
