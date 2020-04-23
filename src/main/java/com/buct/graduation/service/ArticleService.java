package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Article;

import java.util.List;

public interface ArticleService {
    Article findArticleByName(String title);

    List<Article> findByUid(int uid);

    List<Article> findByUidStatus(int uid, String status);
}
