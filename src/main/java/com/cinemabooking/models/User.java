package com.cinemabooking.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "customer")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+",
            message = "Name should be in this format: 'Name Surname'")
    @NotBlank(message = "Cannot be blank!")
    @Size(min = 6, max = 40, message = "Name must contain 6 to 40 characters.")
    private String fullName;

    @Column(name = "registered_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date registeredAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
