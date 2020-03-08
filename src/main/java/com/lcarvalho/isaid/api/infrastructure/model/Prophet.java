package com.lcarvalho.isaid.api.infrastructure.model;

public class Prophet {

    private final long id;
    private final String login;

    public Prophet(long id, String login) {
        this.id = id;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
