package com.codegym.service.serviceImpl;

import com.codegym.dao.model.Category;
import com.codegym.dao.model.PostType;
import com.codegym.dao.repository.PostTypeRepository;
import com.codegym.service.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypeServiceImpl implements PostTypeService {
    private PostTypeRepository postTypeRepository;

    @Autowired
    public void setPostTypeRepository(PostTypeRepository postTypeRepository) {
        this.postTypeRepository = postTypeRepository;
    }

    @Override
    public List<PostType> findAll() {
        return postTypeRepository.findAll();
    }

    @Override
    public Page<PostType> findAll(Pageable pageable) {
        return postTypeRepository.findAll(pageable);
    }

    @Override
    public PostType findById(Long id) {
        return postTypeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(PostType postType) {
        PostType availablePostType = this.findByName(postType.getName());
        if (availablePostType != null) {
            return false;
        }
        postTypeRepository.save(postType);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        postTypeRepository.deleteById(id);
    }

    @Override
    public PostType findByName(String inputName) {
        return postTypeRepository.findByName(inputName);
    }
}
