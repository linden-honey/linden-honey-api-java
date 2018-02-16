package com.github.lindenhoney.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.DB;

@ChangeLog
public class InitialDataChangelog {

    @ChangeSet(order = "001", id = "loadData", author = "Alexander Babai")
    public void loadData(DB db){
        //TODO implement loadData logic
    }
}
