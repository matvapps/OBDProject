package com.carzis.model;

/**
 * Created by Alexandr.
 */
// NOT USED
public class TroubleItem {
    private String code;
    private String desc;
    private String fullDesc;


    public TroubleItem(String code) {
        this.code = code;
    }

    public TroubleItem(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public TroubleItem(String code, String desc, String fullDesc) {
        this.code = code;
        this.desc = desc;
        this.fullDesc = fullDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }
}
