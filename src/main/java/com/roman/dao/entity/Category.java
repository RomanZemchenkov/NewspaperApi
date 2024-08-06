package com.roman.dao.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
@ToString(exclude = "news")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Category implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    private List<News> news = new ArrayList<>();

    public Category(){}

    public Category(String title) {
        this.title = title;
    }
}
