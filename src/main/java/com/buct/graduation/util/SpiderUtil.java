package com.buct.graduation.util;

import com.buct.graduation.mapper.ArticleMapper;
import com.buct.graduation.mapper.JournalMapper;
import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;
import com.buct.graduation.util.spider.SpiderAPI;
import com.buct.graduation.util.spider.SpiderLetpub;
import com.buct.graduation.util.spider.SpiderWOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SpiderUtil {
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private ArticleMapper articleMapper;

    private static SpiderUtil spiderUtil;

    @PostConstruct
    public void init(){
        spiderUtil = this;
    }

    public Article searchPaperByName(String name) {
        Article article = articleMapper.findByName(name);
        if(article == null || !article.getAddWay().equals(GlobalName.addWay_System)){
            SpiderWOS spiderWOS = new SpiderWOS();
            article = spiderWOS.getESIandtimes(name);
        }
        if(article == null) {
            article = new Article();
            article.setName(name);
            article.setAddWay(GlobalName.addWay_missing_c);
        }
        else {
            article.setSci(true);
        }
        if(article.getJid() == null || article.getJid() == '\0'){
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
        return article;
    }

    public PeriodicalTable searchJournals(String name, int year) {
        SpiderAPI api = new SpiderAPI();
        return api.getAPI_1(name, year);
    }

    public Periodical searchJournal(String name, int year) {
        SpiderAPI api = new SpiderAPI();
        return api.getAPI_2(name, year);
    }

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

    public List<Periodical> getJournals(String keyword) {
        SpiderLetpub letpub = new SpiderLetpub();
        PeriodicalTable table = letpub.getPeriodicals(keyword);
        return table.getList();
    }

}
