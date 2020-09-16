package com.codegym.service.specification;

import com.codegym.dao.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.List;

public class UserSpecification {
    public static Specification<User> textInAllColumns(String keyword, List<String> attributes) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.or(root.getModel().getDeclaredSingularAttributes().stream()
                    .filter(a -> attributes.contains(a.getName()))
                    .map(a -> criteriaBuilder.like(root.get(a.getName()), "%" + keyword + "%"))
                    .toArray(Predicate[]::new));
        };
    }

    public static Specification<User> hasRole(ERole role) {
        return ((root, query, criteriaBuilder) -> {
            SetJoin<User, Role> roleJoin = root.join(User_.roles);
            query.distinct(true);
            return criteriaBuilder.equal(roleJoin.get(Role_.roleName), role);
        });
    }

    public static Specification<User> hasOneRole() {
        return ((root, query, criteriaBuilder) -> {
            SetJoin<User, Role> roleJoin = root.join(User_.roles);
            query.distinct(true);
            return criteriaBuilder.equal(criteriaBuilder.size(root.get("roles")), 1);
        });
    }

    public static Specification<User> isUser() {
        return Specification.where(hasRole(ERole.ROLE_USER))
                .and(hasOneRole());
    }

    public static Specification<User> isAdmin() {
        return ((root, query, criteriaBuilder)
                -> hasRole(ERole.ROLE_USER).toPredicate(root, query, criteriaBuilder).not());
    }

}
