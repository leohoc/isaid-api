package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.domain.entity.Prophecy;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProphecyDTO {

    private String prophetCode;
    private LocalDateTime prophecyTimestamp;
    private String summary;
    private String description;

    public ProphecyDTO() {}

    public ProphecyDTO(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    public ProphecyDTO(String prophetCode, LocalDateTime prophecyTimestamp, String summary, String description) {
        this.prophetCode = prophetCode;
        this.prophecyTimestamp = prophecyTimestamp;
        this.summary = summary;
        this.description = description;
    }

    public ProphecyDTO(Prophecy prophecy) {
        this.prophetCode = prophecy.getProphetCode();
        this.prophecyTimestamp = prophecy.getProphecyTimestamp();
        this.summary = prophecy.getSummary();
        this.description = prophecy.getDescription();
    }

    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }

    public LocalDateTime getProphecyTimestamp() {
        return prophecyTimestamp;
    }

    public void setProphecyTimestamp(LocalDateTime prophecyTimestamp) {
        this.prophecyTimestamp = prophecyTimestamp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProphecyDTO that = (ProphecyDTO) o;
        return Objects.equals(prophetCode, that.prophetCode) &&
                Objects.equals(prophecyTimestamp, that.prophecyTimestamp) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prophetCode, prophecyTimestamp, summary, description);
    }
}
