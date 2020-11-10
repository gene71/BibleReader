package com.example.biblereader.data;

public class Verse {
    String bookName;
    int chpNum;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getChpNum() {
        return chpNum;
    }

    public void setChpNum(int chpNum) {
        this.chpNum = chpNum;
    }

    public int getVerseNum() {
        return verseNum;
    }

    public void setVerseNum(int verseNum) {
        this.verseNum = verseNum;
    }

    int verseNum;
}
