package org.example.powerplant.domain.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum POWER_PLANT_TYPE {
    GASFIRED("gasfired"), TURBOJET("turbojet"), WINDTURBINE("windturbine");

    private final String value;

    POWER_PLANT_TYPE(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static POWER_PLANT_TYPE from_value(String value) {
        for (POWER_PLANT_TYPE type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type de centrale inconnu : " + value);
    }
}
