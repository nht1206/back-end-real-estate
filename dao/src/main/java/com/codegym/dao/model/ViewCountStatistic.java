package com.codegym.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "view_count_statistic")
public class ViewCountStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Date dateStatistic;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_view_count_statistic_post"))
    @JsonBackReference
    private Post post;

    @Column(nullable = false)
    private Long viewCount = 1L;

    public ViewCountStatistic() {
    }

    public ViewCountStatistic(Date dateStatistic, Post post, Long viewCount) {
        this.dateStatistic = dateStatistic;
        this.post = post;
        this.viewCount = viewCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateStatistic() {
        return dateStatistic;
    }

    public void setDateStatistic(Date dateStatistic) {
        this.dateStatistic = dateStatistic;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
