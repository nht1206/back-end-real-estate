package com.codegym.service.serviceImpl;

import com.codegym.dao.model.Reason;
import com.codegym.dao.repository.ReasonRepository;
import com.codegym.service.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReasonServiceImpl implements ReasonService {
    private ReasonRepository reasonRepository;

    @Autowired
    public void setReasonRepository(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }


    @Override
    public void save(Reason reason) {
        reasonRepository.save(reason);
    }

    @Override
    public List<Reason> findAll() {
        return reasonRepository.findAll();
    }

    @Override
    public Reason findById(Long id) {
        return reasonRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        reasonRepository.deleteById(id);
    }
}
