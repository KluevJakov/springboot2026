package ru.jafix.springproject.config;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class Roles {
        public static final String ADMIN_CODE = "ADMIN";
        public static final String ADMIN_ROLE = "ROLE_ADMIN";
        public static final String USER_CODE = "USER";

        public static final UUID ADMIN_ID = UUID.fromString("c9b7e95e-16a7-44c2-b7e9-5e16a7c4c2d0");
        public static final UUID USER_ID = UUID.fromString("9b4c1fa7-d2a9-44c7-8c1f-a7d2a984c711");
    }
}
