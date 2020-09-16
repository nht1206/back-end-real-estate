package com.codegym.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "direction")
@JsonIgnoreProperties({
        "posts"
})
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String name;

    @OneToMany(mappedBy = "direction", fetch = FetchType.LAZY, cascade = {
            CascadeType.ALL
    })
    private Set<Post> posts;

    public Direction() {
    }

    public Direction(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
