package ru.jafix.springproject.config;

import java.util.UUID;

public interface Constants {

    interface Roles {
        String ADMIN_CODE = "ADMIN";
        String ADMIN_ROLE = "ROLE_ADMIN";
        String USER_CODE = "USER";

        UUID ADMIN_ID = UUID.fromString("c9b7e95e-16a7-44c2-b7e9-5e16a7c4c2d0");
        UUID USER_ID = UUID.fromString("9b4c1fa7-d2a9-44c7-8c1f-a7d2a984c711");
    }
}
