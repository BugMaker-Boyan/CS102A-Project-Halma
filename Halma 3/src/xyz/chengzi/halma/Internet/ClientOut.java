package xyz.chengzi.halma.Internet;

import xyz.chengzi.halma.model.ChessBoardLocation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientOut extends Thread implements Serializable {
    private static final long serialVersionUID=1L;
    private Socket socket;
    public ChessBoardLocation[] location;
    public ChatOnline chatOnline;
    public OnlineCommunicate onlineCommunicate;
    public ClientOut(Socket socket){
        this.socket=socket;
        location=new ChessBoardLocation[3];
        chatOnline=new ChatOnline(null,null,0,null,0);
        this.onlineCommunicate=new OnlineCommunicate(location,chatOnline);
        System.out.println("Out线程创建成功");
    }
    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(onlineCommunicate);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
