package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.ArticleMapper;
import com.buct.graduation.mapper.JournalMapper;
import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;
import com.buct.graduation.service.SpiderService;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.spider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private JournalMapper journalMapper;

    /**
     * from db or web
     * @param a
     * @return
     */
    @Override
    @Transactional
    public Article searchPaper(Article a) {
        Article article = articleMapper.findByName(a.getName());
        if(article == null || !article.getAddWay().equals(GlobalName.addWay_System) || article.getJid() == null ){
            SpiderWOS spiderWOS = new SpiderWOS();
            article = spiderWOS.getESIandtimes(a.getName());
        }else {
            return article;
        }
        if(article == null) {
            article = a;
            article.setAddWay(GlobalName.addWay_missing_c);
        } else {
            article.setSci(true);
            article.setId(a.getId());
        }
        if((article.getJid() == null || article.getJid() == '\0') && !(article.getJournalIssn() == null || article.getJournalIssn().equals(""))){
            Journal journal = getJournal(article.getJournalIssn());
            if(journal != null) {
                article.setJournal(journal);
                article.setJid(journal.getId());
                article.setAddWay(GlobalName.addWay_System);
            }else {
                article.setNotes(article.getNotes()+"期刊未找到");
                article.setAddWay(GlobalName.addWay_missing_c);
            }
        }
        if(a.getId() > 0 ) {
            article.setId(a.getId());
            articleMapper.update(article);
        }
        article.setUploadEmail(a.getUploadEmail());
//        System.out.println("ceshi:"+article.getYear());
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
        try {
            Random random = new Random();
            int awsl = random.nextInt(5);
            Thread.sleep(1000*(awsl + 5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
        journal = letpub.getJournal(keyword);
        if(journal != null){
            journalMapper.addJournal(journal);
            journal = journalMapper.findByKeyword(journal.getISSN());
        }
        return journal;
    }

    @Override
    public List<Periodical> getJournals(String keyword) {
        SpiderLetpubJournal letpub = new SpiderLetpubJournal();
        PeriodicalTable table = letpub.getPeriodicals(keyword);
        return table.getList();
    }

}
