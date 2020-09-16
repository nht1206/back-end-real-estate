package com.codegym.service.serviceImpl;

import com.codegym.dao.model.Support;
import com.codegym.dao.repository.SupportRepository;
import com.codegym.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {
    private SupportRepository supportRepository;

    @Autowired
    public void setSupportRepository(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    @Override
    public Page<Support> findAll(Pageable pageable) {
        return supportRepository.findAll(pageable);
    }

    @Override
    public Support findById(Long id) {
        return supportRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Support support) {
        supportRepository.save(support);
    }

    @Override
    public void deleteById(Long id) {
        supportRepository.deleteById(id);
    }

    @Override
    public Page<Support> findByReasonId(Long reasonId, Pageable pageable) {
        return supportRepository.getSupportsByReason_Id(reasonId, pageable);
    }


}
