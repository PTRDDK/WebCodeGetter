package com.piotrek;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Piotrek on 2016-10-22.
 */
public class LinkGetter implements Serializable{
    static ArrayList<String> linkList = new ArrayList<>();

    public LinkGetter(){}

    public ArrayList getLinks(String url){

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String input;
            StringBuilder sourceCode = new StringBuilder();
            //pobór kodu źrółowego strony
            while((input = br.readLine()) != null){
                sourceCode.append(input);
            }
            br.close();
            Pattern pl = Pattern.compile("\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]");
            Matcher ml = pl.matcher(sourceCode);
            //wyszukiwanie linków
            while(ml.find()){
                String link = ml.group().replaceFirst("href=\"", "").replaceFirst("\">", "").replaceFirst("\"[\\s]?target=\"[a-zA-Z_0-9]*", "");
                linkList.add(link);
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("MalformedURLException");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }
        showLinkList(linkList);
        saveData();
        return linkList;
    }

    public void showLinkList(ArrayList<String> linkList){
        for(int i = 0; i < linkList.size(); i++){
            System.out.println(linkList.get(i));
        }
    }

    public void saveData(){
        try{
            Path out = Paths.get("I:\\\\IdeaProjects\\\\WebsiteCodeGetter\\\\link.txt");
            Files.write(out,linkList, Charset.defaultCharset());
            System.out.println("FILE SAVED!");
        }
        catch(FileNotFoundException e){
            System.out.println("No such a file!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
