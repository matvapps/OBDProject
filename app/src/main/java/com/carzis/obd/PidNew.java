package com.carzis.obd;

/**
 * Created by Alexandr
 */
public class PidNew {

    private String name;
    private String pid;
    private String equation;
    private String header;

    public PidNew(String name, String pid, String equation, String header) {
        this.name = name;
        this.pid = pid;
        this.equation = equation;
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
