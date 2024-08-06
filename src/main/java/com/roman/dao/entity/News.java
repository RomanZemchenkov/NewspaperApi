package com.roman.dao.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Clock;
import java.time.Instant;

@Getter
@Setter
@ToString(exclude = "category")
@EqualsAndHashCode(of = {"id","title"})
@Entity
@Table(name = "news")
@NamedEntityGraph(
        name = "News.withCategory",
        attributeNodes = @NamedAttributeNode(value = "category")
)
public class News implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false,unique = true)
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "date_of_publication")
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public News(){}

    public News(String title, String text, Category category) {
        this.title = title;
        this.text = text;
        this.date = Instant.now(Clock.systemDefaultZone());
        this.category = category;
        category.getNews().add(this);
    }
}
