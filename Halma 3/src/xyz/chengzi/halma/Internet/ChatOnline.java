package xyz.chengzi.halma.Internet;

import java.io.Serializable;

public class ChatOnline implements Serializable {
    private static final long serialVersionUID=1L;
    public int messageId=0;
    public String content;
    public String userName;
    public int userId;
    public String player;//哪一方

    public ChatOnline(String content, String userName, int userId, String player,int messageId) {
        this.content = content;
        this.userName = userName;
        this.userId = userId;
        this.player = player;
        this.messageId=messageId;
    }

    @Override
    public String toString() {
        String str=String.format("%s(%s，id：%d）：%s",userName,player,userId,content);
        return str;
    }
}
