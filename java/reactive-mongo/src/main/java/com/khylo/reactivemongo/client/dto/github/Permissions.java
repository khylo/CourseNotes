package com.khylo.reactivemongo.client.dto.github;

public record Permissions(Boolean admin, Boolean  maintain, Boolean  push, Boolean  triage, Boolean  pull ) {
}
