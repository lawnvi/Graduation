package com.buct.graduation.service;

import com.buct.graduation.model.pojo.Journal;

public interface JournalService {
    Journal findJournalByName(String journal);
}
