// dto/ToggleAvailableRequest.java
package com.example.bootheat.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ToggleAvailableRequest(
        @JsonProperty("menu_item") Long menuItem,
        Boolean available
) {}
