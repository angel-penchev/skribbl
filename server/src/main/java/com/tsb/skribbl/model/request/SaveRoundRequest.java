package com.tsb.skribbl.model.request;

import org.springframework.web.multipart.MultipartFile;

public class SaveRoundRequest {
    private String word;
    private String drawingUserName;
    private MultipartFile board;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDrawingUserName() {
            return drawingUserName;
    }

    public void setDrawingUserName(String drawingUserName) {
        this.drawingUserName = drawingUserName;
    }

    public MultipartFile getBoard() {
        return board;
    }

    public void setBoard(MultipartFile board) {
        this.board = board;
    }
}
