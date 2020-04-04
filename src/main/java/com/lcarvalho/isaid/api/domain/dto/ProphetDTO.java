package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.domain.entity.Prophet;

import java.util.Objects;

public class ProphetDTO {

    private String login;
    private String prophetCode;

    public ProphetDTO() {}

    public ProphetDTO(String login) {
        this.login = login;
    }

    public ProphetDTO(String login, String prophetCode) {
        this.login = login;
        this.prophetCode = prophetCode;
    }

    public ProphetDTO(Prophet prophet) {
        this.login = prophet.getLogin();
        this.prophetCode = prophet.getProphetCode();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProphetDTO that = (ProphetDTO) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(prophetCode, that.prophetCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, prophetCode);
    }
}
