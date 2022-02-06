package com.example.telephony.tts.persistance.domain;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "generated_sound")
public class GeneratedSound extends BaseEntity {
    /**
     * Uri for access to file
     */
    @Column(name = "uri")
    private String uri;

    /**
     * File path
     */
    @Column(name = "path")
    private String path;

    /**
     * Synthesized text
     */
    @Column(name = "text")
    private String text;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GeneratedSound that = (GeneratedSound) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
