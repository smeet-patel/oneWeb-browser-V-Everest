package com.example.smeet.oneweb;



public class History {

    private String timeList = "";
    private String urlList = "";

    public History(String timeList, String urlList) {
        this.timeList = timeList;
        this.urlList = urlList;
    }

    public String getTimeList() {
        return timeList;
    }

    public void setTimeList(String timeList) {
        this.timeList = timeList;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setUrlList(String urlList) {
        this.urlList = urlList;
    }

    @Override
    public String toString() {
        return "History{" +
                "timeList='" + timeList + '\'' +
                ", urlList='" + urlList + '\'' +
                '}';
    }
}