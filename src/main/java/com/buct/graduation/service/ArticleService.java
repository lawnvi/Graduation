package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Article;

public interface ArticleService {
    Article findArticleByName(String title);
}
