package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.ArticleMapper;
import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article findArticleByName(String title) {
        return articleMapper.findByName(title);
    }

    @Override
    public List<Article> findByUid(int uid) {
        return articleMapper.findByIds(uid);
    }

    @Override
    public List<Article> findByUidStatus(int uid, String status) {
        return articleMapper.findByStatusUid(uid, status);
    }
}
