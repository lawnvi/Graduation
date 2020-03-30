package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Article;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.model.spider.Periodical;
import com.buct.graduation.model.spider.PeriodicalTable;

import java.util.List;

public interface SpiderService {
    Article searchPaper(Article article);

    PeriodicalTable searchJournals(String name, int year);

    Periodical searchJournal(String name, int year);

    //letpub
    Journal getJournal(String keyword);

    List<Periodical> getJournals(String keyword);
}
