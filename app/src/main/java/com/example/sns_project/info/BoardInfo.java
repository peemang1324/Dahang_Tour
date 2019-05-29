package com.example.sns_project.info;

public class BoardInfo {
    private String boardId;
    private String boardTitle;

    public BoardInfo(String boardId, String boardTitle) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }
}
