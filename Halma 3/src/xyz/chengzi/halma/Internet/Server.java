package xyz.chengzi.halma.Internet;

import xyz.chengzi.halma.model.ChessBoardLocation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ChessBoardLocation[] chessBoardLocations=new ChessBoardLocation[3];
    public static ChatOnline chatOnline=new ChatOnline(null,null,0,null,0);
    public static OnlineCommunicate onlineCommunicate=new OnlineCommunicate(chessBoardLocations,chatOnline);
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=new ServerSocket(10086);
        System.out.println("Running...");
        while (true){
            Socket accept = serverSocket.accept();
            new Thread(new Service(accept)).start();
            new Thread(new ServiceOut(accept)).start();
            System.out.println("加入线程");
        }
    }
}

class Service implements Runnable{
    private Socket socket;
    public Service(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
                OnlineCommunicate communicate= (OnlineCommunicate)objectInputStream.readObject();
                ChessBoardLocation[] temp = communicate.location;
                ChatOnline chat = communicate.chatOnline;
                if (temp!=null){
                    Server.chessBoardLocations[0]=temp[0];
                    Server.chessBoardLocations[1]=temp[1];
                    Server.chessBoardLocations[2]=temp[2];
                }
                try {
                    if (chat!=null){
                        Server.chatOnline.content=chat.content;
                        Server.chatOnline.player=chat.player;
                        Server.chatOnline.userId=chat.userId;
                        Server.chatOnline.userName=chat.userName;
                        Server.chatOnline.messageId=chat.messageId;
                    }
                }catch (NullPointerException e){

                }
                Thread.sleep(100);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

class ServiceOut implements Runnable{
    private Socket socket;
    public ServiceOut(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                if (Server.chessBoardLocations!=null){
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(Server.onlineCommunicate);
                    objectOutputStream.flush();
                    Thread.sleep(300);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
