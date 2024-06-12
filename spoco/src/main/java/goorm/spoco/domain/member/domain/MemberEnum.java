package goorm.spoco.domain.member.domain;

import lombok.Getter;

@Getter
public enum MemberEnum {
    ADMIN("ENUM_ADMIN"),
    ACTIVE("ENUM_ACTIVE"),
    DELETE("ENUM_DELETE");

    private final String value;

    MemberEnum(String value) {
        this.value = value;
    }
}
