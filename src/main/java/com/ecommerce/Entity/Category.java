package com.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean status = true;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> list = new ArrayList<>();

    @PrePersist
    protected void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

}
