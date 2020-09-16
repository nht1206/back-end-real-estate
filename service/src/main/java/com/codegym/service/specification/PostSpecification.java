package com.codegym.service.specification;

import com.codegym.dao.model.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("category").get("id"), categoryId);
        };
    }

    public static Specification<Post> hasRegionId(Long regionId) {
        return (root, query, criteriaBuilder) -> {
            if (regionId == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("region").get("id"), regionId);
        };
    }

    public static Specification<Post> hasPostTypeId(Long postTypeId) {
        return (root, query, criteriaBuilder) -> {
            if (postTypeId == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("postType").get("id"), postTypeId);
        };
    }

    public static Specification<Post> hasCondition(Boolean condition) {
        return (root, query, criteriaBuilder) -> {
            if (condition == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("condition"), condition);
        };
    }

    public static Specification<Post> hasArea(Double area) {
        return (root, query, criteriaBuilder) -> {
            if (area == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.lessThanOrEqualTo(root.get("area"), area);
        };
    }

    public static Specification<Post> hasPrice(Long price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Post> hasDeal(Boolean deal) {
        return (root, query, criteriaBuilder) -> {
            if (deal == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("deal"), deal);
        };
    }

    public static Specification<Post> hasDirectionId(Long directionId) {
        return (root, query, criteriaBuilder) -> {
            if (directionId == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("direction").get("id"), directionId);
        };

    }

    public static Specification<Post> isApproved(Boolean approved) {
        return (root, query, criteriaBuilder) -> {
            if (approved == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("approved"), approved);
        };
    }


    public static Specification<Post> hasStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Post> hasCustomerType(Boolean customerType) {
        return (root, query, criteriaBuilder) -> {
            if (customerType == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("customerType"), customerType);
        };
    }

    public static Specification<Post> textInAllColumns(String keyword, List<String> attributes) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.or(root.getModel().getDeclaredSingularAttributes().stream()
                    .filter(a -> attributes.contains(a.getName()))
                    .map(a -> criteriaBuilder.like(root.get(a.getName()), "%" + keyword + "%"))
                    .toArray(Predicate[]::new));
        };
    }

    public static Specification<Post> hasDirection(String direction) {
        System.out.println(direction);
        return (root, query, criteriaBuilder) -> {
            if (direction == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.like(root.join("direction").get("name"), "%" + direction + "%");
        };
    }
}
