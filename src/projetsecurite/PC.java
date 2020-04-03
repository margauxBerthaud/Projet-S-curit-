/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsecurite;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.*;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import java.net.UnknownHostException;

public class PC {

    static ServerSocket server;
    static int clientID = 0;
    static SecretKey key;

    public static void main(String ard[]) {

        try {
            // créé un nouveau fichier pour stocker la clé
            File f = new File("key.txt");
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            SecretKey key = generator.generateKey();
            String k2w;
            byte[] encoded = key.getEncoded();
            k2w = Base64.encode(encoded);
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(k2w);
            bw.close();
            fw.close();
            System.out.println("la clé est : " + k2w);
            server = new ServerSocket(4444, 5);//5 connexions clientes max
            go();
        } catch (Exception e) {
        }
    }

    public static void go() {

        try {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true)//
                    {
                        try {
                            Socket client = server.accept();
                            // Faire tourner le socket qui s'occupe de ce client dans son propre thread et revenir en attente de la prochaine connexion
                            // Le chat avec l'entité connectée est encapsulé par une instance de ChatServer
                            Thread tAccueil = new Thread(new ChatServer(client, clientID));
                            tAccueil.start();
                            clientID++;
                        } catch (Exception e) {
                        }
                    }
                }
            });
            t.start();

        } catch (Exception i) {
            System.out.println("Impossible d'écouter sur le port 4444: serait-il occupé?");
            i.printStackTrace();
        }
    }
}
