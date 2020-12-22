package com.codegym.dao.model;

import com.codegym.dao.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "post")
@JsonIgnoreProperties({
        "comments",
        "viewCountStatistics"
})
public class Post extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    @NotBlank
    @Size(max = 50)
    private String title;

    @Column(name = "property_condition", columnDefinition = "TINYINT(1)")
    private boolean condition;

    @Column(columnDefinition = "NVARCHAR(100)")
    @NotBlank
    @Size(max = 100)
    private String address;

    @NotNull
    @Min(0)
    private Double area;

    @NotNull
    @Min(0)
    private Long price;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean deal = false;

    @Column
    @Min(0)
    private Long viewCount = 0L;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    @Size(max = 65535)
    private String content;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean status = true;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean approved;

    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean customerType = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_post_user"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_type_id", foreignKey = @ForeignKey(name = "FK_post_post_type"))
    private PostType postType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(name = "FK_post_region"))
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direction_id", foreignKey = @ForeignKey(name = "FK_post_direction"))
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_post_category"))
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<PostImage> postImages;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ViewCountStatistic> viewCountStatistics;

    public Post() {
    }

    public Post(@NotBlank @Size(max = 50) String title, boolean condition, @NotBlank @Size(max = 100) String address, @NotNull @Min(0) Double area, @NotNull @Min(0) Long price, boolean deal, @Min(0) Long viewCount, @NotBlank @Size(max = 65535) String content, boolean status, boolean approved, boolean customerType, User user, PostType postType, Region region, Direction direction, Category category, Set<PostImage> postImages, Set<Comment> comments, Set<ViewCountStatistic> viewCountStatistics) {
        this.title = title;
        this.condition = condition;
        this.address = address;
        this.area = area;
        this.price = price;
        this.deal = deal;
        this.viewCount = viewCount;
        this.content = content;
        this.status = status;
        this.approved = approved;
        this.customerType = customerType;
        this.user = user;
        this.postType = postType;
        this.region = region;
        this.direction = direction;
        this.category = category;
        this.postImages = postImages;
        this.comments = comments;
        this.viewCountStatistics = viewCountStatistics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isCustomerType() {
        return customerType;
    }

    public void setCustomerType(boolean customerType) {
        this.customerType = customerType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(Set<PostImage> postImages) {
        this.postImages = postImages;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<ViewCountStatistic> getViewCountStatistics() {
        return viewCountStatistics;
    }

    public void setViewCountStatistics(Set<ViewCountStatistic> viewCountStatistics) {
        this.viewCountStatistics = viewCountStatistics;
    }
}
