package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String base64;

    @Column
    private Date createdAt;

    public Image(String base64, Date date) {
        this.base64 = base64;
        this.createdAt = date;
    }

    public Image() {

    }
}