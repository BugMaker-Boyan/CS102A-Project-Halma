package xyz.chengzi.halma.Internet;

import xyz.chengzi.halma.model.ChessBoardLocation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer extends Thread{
    public static ChessBoardLocation[] chessBoardLocations=new ChessBoardLocation[3];
    public static ChatOnline chatOnline=new ChatOnline(null,null,0,null,0);
    public static OnlineCommunicate onlineCommunicate=new OnlineCommunicate(chessBoardLocations,chatOnline);

    @Override
    public void run() {
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(10086);
        } catch (IOException e) {
            System.out.println("创建本地主机失败");
        }
        System.out.println("Running...");
        while (true){
            Socket accept = null;
            try {
                accept = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new LocalService(accept)).start();
            new Thread(new LocalServiceOut(accept)).start();
            System.out.println("加入线程");
        }
    }

}

class LocalService implements Runnable{
    private Socket socket;
    public LocalService(Socket socket){
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
//                ChessBoardLocation[] temp = (ChessBoardLocation[])objectInputStream.readObject();
                if (temp!=null){
                    LocalServer.chessBoardLocations[0]=temp[0];
                    LocalServer.chessBoardLocations[1]=temp[1];
                    LocalServer.chessBoardLocations[2]=temp[2];
//                    System.out.println("更新成功");
//                    System.out.println(Server.chessBoardLocations[0]);
//                    System.out.println(Server.chessBoardLocations[1]);
                }
//                if (chatOnline!=null){
//                    LocalServer.chatOnline=chatOnline;
////                    System.out.println(LocalServer.chatOnline.content);
//                }
                try {
                    if (chat!=null){
                        LocalServer.chatOnline.content=chat.content;
                        LocalServer.chatOnline.player=chat.player;
                        LocalServer.chatOnline.userId=chat.userId;
                        LocalServer.chatOnline.userName=chat.userName;
                        LocalServer.chatOnline.messageId=chat.messageId;
                    }
                }catch (NullPointerException e){

                }
//                System.out.println("服务器接收："+LocalServer.chatOnline.content);
                Thread.sleep(200);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

class LocalServiceOut implements Runnable{
    private Socket socket;
    public LocalServiceOut(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            try {
                if (LocalServer.chessBoardLocations!=null){
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(LocalServer.onlineCommunicate);
//                    System.out.println("服务器发送："+LocalServer.onlineCommunicate.chatOnline.content);
                    objectOutputStream.flush();
                    Thread.sleep(200);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
