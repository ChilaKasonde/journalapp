package io.exception.journalapp;

import android.support.annotation.NonNull;

/**
 * Created by C. Kasonde on 7/1/2018.
 */
public class EntryID {
    public String entryId;

    public <T extends EntryID> T withID(@NonNull final String id){
        this.entryId = id;
        return (T) this;
    }

}
