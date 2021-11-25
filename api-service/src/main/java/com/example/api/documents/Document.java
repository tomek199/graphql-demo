package com.example.api.documents;

import com.example.api.users.User;

public record Document(
    String id,
    String type,
    String name,
    User user
) {
}
