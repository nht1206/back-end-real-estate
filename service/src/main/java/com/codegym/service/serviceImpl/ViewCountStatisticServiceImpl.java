package com.codegym.service.serviceImpl;

import com.codegym.dao.model.ViewCountStatistic;
import com.codegym.dao.repository.ViewCountStatisticRepository;
import com.codegym.service.ViewCountStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ViewCountStatisticServiceImpl implements ViewCountStatisticService {
    private ViewCountStatisticRepository viewCountStatisticRepository;

    @Autowired
    public void setViewCountStatisticRepository(ViewCountStatisticRepository viewCountStatisticRepository) {
        this.viewCountStatisticRepository = viewCountStatisticRepository;
    }


    @Override
    public void save(ViewCountStatistic viewCountStatistic) {
        viewCountStatisticRepository.save(viewCountStatistic);
    }

    @Override
    public List<ViewCountStatistic> findAll() {
        return viewCountStatisticRepository.findAll();
    }

    @Override
    public ViewCountStatistic findById(Long id) {
        return viewCountStatisticRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        viewCountStatisticRepository.deleteById(id);
    }

    @Override
    public ViewCountStatistic findLastViewCountStatistic(Long postId) {
        return viewCountStatisticRepository.findLastViewCountStatistic(postId);
    }

    @Override
    public List getListViewCountStatistic(Date startDay, Date endDay) {
        return viewCountStatisticRepository.getListViewCountStatistic(startDay, endDay);
    }
}
