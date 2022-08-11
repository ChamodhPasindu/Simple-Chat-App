package lk.ijse.chatapp.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT=3000;
    private static ServerSocket serverSocket;

    public static void closeServerSocket(){
        try{
            if (serverSocket!=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try{
            serverSocket=new ServerSocket(PORT);
            System.out.println(serverSocket);
            while (!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler clientHandler=new ClientHandler(socket);

                Thread thread=new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            closeServerSocket();
        }
    }


}
