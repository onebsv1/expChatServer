package com.chatbsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static java.lang.System.exit;

/**
 * Created by Bhargav Srinivasan on 4/13/16.
 */
public class Client {

    public static void main(String args[]){

        Integer clientPort = 24002;
        String userInput;

        try(Socket clientSocket = new Socket()){

            InetAddress serverAddr = InetAddress.getByName(null);
            SocketAddress serverSockAddr = new InetSocketAddress(serverAddr,clientPort);

            //Try to connect to the server within 1 sec.
            try {
                clientSocket.connect(serverSockAddr,1000);
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintStream os = new PrintStream(clientSocket.getOutputStream());

            System.out.println("Enter name:    ");
            String nom = stdIn.readLine();
            String[] textArray = nom.split(" ");
            os.println(textArray[0]);
            os.flush();

            System.out.print("Client:    ");

            while (true){

                while(is.ready()) {
                    System.out.println(is.readLine());
                    System.out.print("Reply:    ");

                }

                if(stdIn.ready()) {
                    System.out.print("Client:    ");
                    userInput = stdIn.readLine();
                    if(userInput.equals("EXIT")){
                        clientSocket.close();
                        exit(0);
                    }
                    os.println(userInput);
                    os.flush();

                }

            }

        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
