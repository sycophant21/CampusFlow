package com.example.applicationcreator.web;

public class Comparator implements java.util.Comparator<Application> {
    @Override
    public int compare(Application o1, Application o2) {
        if (o1.getStatus().getStatusType().equals(o2.getStatus().getStatusType())) {
            if (o1.getInitialisingDate().equals(o2.getInitialisingDate())) {
                return 0;
            }
            else {
                return o2.getInitialisingDate().compareTo(o1.getInitialisingDate());
            }
        }
        else {
            if (o1.getStatus().getStatusType() == StatusType.PENDING && (o2.getStatus().getStatusType() == StatusType.ACCEPTED || o2.getStatus().getStatusType() == StatusType.REJECTED)) {
                return 1;
            }
            else if (o2.getStatus().getStatusType() == StatusType.PENDING && (o1.getStatus().getStatusType() == StatusType.ACCEPTED || o1.getStatus().getStatusType() == StatusType.REJECTED)) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    @Override
    public java.util.Comparator<Application> reversed() {
        return null;
    }
    //    <path
    //        android:fillColor="#777777"
    //        android:pathData="M18.5,100 L50,30 L81.5,100"/>
}
