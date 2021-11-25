package com.example.api.users;

public record User(
    String id,
    String name,
    String email,
    String company
) {
}
