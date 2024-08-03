package org.example;

import java.util.Objects;
import java.util.stream.Stream;

public enum Action {
    EXIT(0, false),
    CREATE(1, true),
    UPDATE(2, true),
    DELETE(3, true),
    STATS_BY_COURSE(4, false),
    STATS_BY_CITY(5,true),
    SEARCH(6, true),
    ERROR(-1, false);

    private Integer code;
    private Boolean requireIsAdditionalData;

    Action(Integer code, Boolean requireIsAdditionalData) {
        this.code = code;
        this.requireIsAdditionalData = requireIsAdditionalData;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getRequireIsAdditionalData() {
        return requireIsAdditionalData;
    }

    public static Action fromCode(Integer code) {
        return Stream.of(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Неверный код");
                    return Action.ERROR;
                });
    }
}
