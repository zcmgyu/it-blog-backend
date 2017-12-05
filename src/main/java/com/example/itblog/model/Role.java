package com.example.itblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;

@Document(collection = "Role")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Role {

    @Id
    private long id;

    @NotEmpty
    private String roleName;

    @NotEmpty
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date createAt;

    @NotEmpty
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date modifiedAt;

    public Role(String roleName, Date createAt, Date modifiedAt) {
        this.roleName = roleName;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}

