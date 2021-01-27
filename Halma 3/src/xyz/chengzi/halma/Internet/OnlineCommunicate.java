package xyz.chengzi.halma.Internet;

import xyz.chengzi.halma.model.ChessBoardLocation;

import java.io.Serializable;

public class OnlineCommunicate implements Serializable {
    private static final long serialVersionUID=1L;
    public ChessBoardLocation[] location;
    public ChatOnline chatOnline;

    public OnlineCommunicate(ChessBoardLocation[] location, ChatOnline chatOnline) {
        this.location = location;
        this.chatOnline = chatOnline;
    }
}
