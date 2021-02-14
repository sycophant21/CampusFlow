package com.example.applicationcreator.web;

public class Flag {
    private static int flag = 0;
    public static int getInstance() {
        return flag ;
    }
    public static void invertFlag() {
        if (flag == 0) {
            flag = 1;
        }
        else {
            flag = 0 ;
        }
    }
}
