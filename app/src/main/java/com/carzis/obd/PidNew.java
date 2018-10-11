package com.carzis.obd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

/**
 * Created by Alexandr
 */

@Entity
public class PidNew {

    @PrimaryKey
    @NonNull
    private String pid;

    private String mode;
    private String name;
    private String equation;
    private String header;

    @Ignore
    public static final String[] PIDS = {
            "0100", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109", "010A", "010B", "010C", "010D", "010E", "010F",
            "0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117", "0118", "0119", "011A", "011B", "011C", "011D", "011E", "011F",
            "0120", "0121", "0122", "0123", "0124", "0125", "0126", "0127", "0128", "0129", "012A", "012B", "012C", "012D", "012E", "012F",
            "0130", "0131", "0132", "0133", "0134", "0135", "0136", "0137", "0138", "0139", "013A", "013B", "013C", "013D", "013E", "013F",
            "0140", "0141", "0142", "0143", "0144", "0145", "0146", "0147", "0148", "0149", "014A", "014B", "014C", "014D", "014E", "014F",
            "0150", "0151", "0152", "0153", "0154", "0155", "0156", "0157", "0158", "0159", "015A", "015B", "015C", "015D", "015E", "015F",
            "0160", "0161", "0162", "0163", "0164", "0165"};


    @Ignore
    public PidNew(@NonNull String pidCode) {
        this.pid = pidCode.substring(0, 2);
        this.pid = pidCode.substring(2, 4);
    }


    public PidNew(@NonNull String pid, String mode, String name, String equation, String header) {
        this.pid = pid;
        this.mode = mode;
        this.name = name;
        this.equation = equation;
        this.header = header;
    }

    public String getValue(int A, int B/*, int C, int D*/) {
        equation = equation.toLowerCase();
        if (equation.equals(""))
            return "-";

        Function At = new Function("At(a,b) = " + equation);

        Argument a = new Argument("a = " + A);
        Argument b = new Argument("b = " + B);
//        Argument c = new Argument("c = " + C);
//        Argument d = new Argument("d = " + D);
        Expression expression =
                new Expression("At(a,b)", At, a, b/*, c, d*/);

        return String.valueOf(expression.calculate());
    }

    public static boolean contains(String pid) {
        for (String item :PIDS) {
            if (item.equals(pid))
                return true;
        }
        return false;
    }

    public String getPidCode() {
        return mode + pid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
