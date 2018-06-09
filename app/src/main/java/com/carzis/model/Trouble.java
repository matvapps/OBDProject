package com.carzis.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Alexandr.
 */

@Entity
public class Trouble {
    @PrimaryKey
    @NonNull
    private String code;

    private String en_desc;
    private String ru_desc;
    private String full_desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEn_desc() {
        return en_desc;
    }

    public void setEn_desc(String en_desc) {
        this.en_desc = en_desc;
    }

    public String getRu_desc() {
        return ru_desc;
    }

    public void setRu_desc(String ru_desc) {
        this.ru_desc = ru_desc;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }
}
