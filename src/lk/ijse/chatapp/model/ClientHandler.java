package lk.ijse.chatapp.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class ClientHandler implements Runnable {

    private static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadCastMessage(clientUsername + " has joined the chat");
            broadCastOnlineClient();
        } catch (IOException e) {
            e.printStackTrace();
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    private void broadCastOnlineClient() {
        if (clientHandlers.size()==1){
            try {
                bufferedWriter.write( "No One Active Now");
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    bufferedWriter.write(clientHandler.clientUsername + "- Active Now");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEveryThing(socket, bufferedReader, bufferedWriter);
            }
        }
    }


    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadCastMessage(messageFromClient);
            } catch (IOException | NullPointerException e) {
                closeEveryThing(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadCastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEveryThing(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadCastMessage(clientUsername + " has left the chat");
    }

    public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        closeClientConnection(socket, bufferedReader, bufferedWriter);
    }

    static void closeClientConnection(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
