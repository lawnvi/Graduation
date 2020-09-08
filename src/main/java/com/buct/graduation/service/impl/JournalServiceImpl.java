package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.JournalMapper;
import com.buct.graduation.model.pojo.Journal;
import com.buct.graduation.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalMapper journalMapper;

    @Override
    public Journal findJournalByName(String journal) {
        return journalMapper.findByKeyword(journal);
    }

    @Override
    public List<Journal> getAllJournals() {
        return journalMapper.findAll();
    }

    @Override
    public List<Journal> getAllOldJournals(String date) {
        return journalMapper.findAllOld(date);
    }

    @Override
    public int updateJournal(Journal journal) {
        return journalMapper.update(journal);
    }
}
