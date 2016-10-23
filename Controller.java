package com.piotrek;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* Napisz program laczacy sie ze strona podana jako argument wywolania programu i wypisujacy wszystkie znalezione na
niej linki i adresy email wykorzystujac w tym celu wyrazenia regularne (pakiet java.util.regex). Oprocz tego program ma
zapisac do pliku wszystkie parametry polaczenia, adres IP komputera na ktorym znajduje sie strona oraz naglowek strony
(zawartosc sekcji <head>).
 */
public class Controller {

    public static void main(String[] args) {
        Controller control = new Controller();
        String emailsFile = "emails.txt";
        String url = args[0];
        control.linkGetter(url);
        control.emailGetter(url);
        control.headGetter(url);
    }

    public void connectToSite(String url){
        String ip = "";
        String host = "";
        try {
            URL myURL = new URL(url);
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            host = myURL.getHost();
            InetAddress adress = InetAddress.getByName(host);
            ip = adress.getHostAddress();
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("MalformedURLException");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }
        System.out.println("Connected with: " + url);
        System.out.println("IP: " + ip);
        System.out.println("Host: " + host);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("I:\\IdeaProjects\\WebsiteCodeGetter\\connection.txt"));
            bw.write("URL: " + url.toString() + "\nIP: " + ip + "\nHOST: " + host);
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void linkGetter(String url){
        connectToSite(url);
        LinkGetter linkGet = new LinkGetter();
        linkGet.getLinks(url);
    }

    public void emailGetter(String url){
        connectToSite(url);
        EmailGetter emailGetter = new EmailGetter();
        emailGetter.getEmails(url);
    }

    public void headGetter(String url){
        connectToSite(url);
        HeadGetter headGetter = new HeadGetter();
        headGetter.getHead(url);
    }
}
