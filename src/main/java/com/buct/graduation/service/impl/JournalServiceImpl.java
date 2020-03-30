package com.buct.graduation.service.impl;

import com.buct.graduation.mapper.JournalMapper;
import com.buct.graduation.model.pojo.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalMapper journalMapper;

    @Override
    public Journal findJournalByName(String journal) {
        return journalMapper.findByKeyword(journal);
    }
}
