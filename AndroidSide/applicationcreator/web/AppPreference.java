package com.example.applicationcreator.web;

public class AppPreference {
    private TempUser tempUser;
    private static AppPreference appPreference;

    public static AppPreference getInstance() {
        if (appPreference == null) {
            appPreference = new AppPreference();
        }
        return appPreference;
    }

    public void setTempUser(TempUser tempUser) {
        this.tempUser = tempUser;
    }

    public TempUser getTempUser() {
        return tempUser;
    }
}
