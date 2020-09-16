package com.codegym.dao.model;

import com.codegym.dao.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
public class PostImage extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(255)", nullable = false)
    private String image;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean status = true;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_post_image_post"))
    @JsonBackReference
    private Post post;

    public PostImage() {
    }

    public PostImage(String image, boolean status, Post post) {
        this.image = image;
        this.status = status;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
