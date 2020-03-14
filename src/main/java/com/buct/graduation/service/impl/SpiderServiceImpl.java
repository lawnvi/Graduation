package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.ArticleMapper;
import com.buct.graduation.mapper.JournalMapper;
import com.buct.graduation.mapper.UserMapper;
import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.pojo.JournalArticles;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.Utils;
import com.buct.graduation.util.spider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.List;

@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Article searchPaperByName(String name) {
        Article article = articleMapper.findByName(name);
        if(article != null){
            return article;
        }
        SpiderWOS spiderWOS = new SpiderWOS();
        article = spiderWOS.getESIandtimes(name);
        if(article == null) {
            article = new Article();
            article.setName(name);
            article.setAddWay(GlobalName.addWay_missing_c);
        }
        else {
            article.setSci(true);
            Journal journal = getJournal(article.getJournalName());
            if(journal != null) {
                article.setJournal(journal);
                article.setJid(journal.getId());
                article.setAddWay(GlobalName.addWay_System);
            }else {
                article.setNotes(article.getNotes()+"期刊未找到");
                article.setAddWay(GlobalName.addWay_missing_c);
            }
        }
//        articleMapper.insertArticle(article);
        return article;
    }

    @Override
    public PeriodicalTable searchJournals(String name, int year) {
        SpiderAPI api = new SpiderAPI();
        return api.getAPI_1(name, year);
    }

    @Override
    public Periodical searchJournal(String name, int year) {
        SpiderAPI api = new SpiderAPI();
        return api.getAPI_2(name, year);
    }

    @Override
    @Transactional
    public Journal getJournal(String keyword) {
        Journal journal = journalMapper.findByKeyword(keyword);
        if(journal != null){
            return journal;
        }
        SpiderLetpub letpub = new SpiderLetpub();
        journal = letpub.getJournal(keyword);
        if(journal != null){
            journalMapper.addJournal(journal);
            journal = journalMapper.findByKeyword(journal.getISSN());
        }
        return journal;
    }

    @Override
    public List<Periodical> getJournals(String keyword) {
        SpiderLetpub letpub = new SpiderLetpub();
        PeriodicalTable table = letpub.getPeriodicals(keyword);
        return table.getList();
    }

}
