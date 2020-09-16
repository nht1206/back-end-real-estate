package com.codegym.service;

import com.codegym.dao.model.ViewCountStatistic;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

public interface ViewCountStatisticService {

    void save(ViewCountStatistic viewCountStatistic);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<ViewCountStatistic> findAll();

    ViewCountStatistic findById(Long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Long id);

    ViewCountStatistic findLastViewCountStatistic(Long postId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List getListViewCountStatistic(Date startDay, Date endDay);
}
