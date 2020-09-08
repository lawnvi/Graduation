package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Journal;

import java.util.List;

public interface JournalService {
    Journal findJournalByName(String journal);

    List<Journal> getAllJournals();

    List<Journal> getAllOldJournals(String date);

    int updateJournal(Journal journal);
}
