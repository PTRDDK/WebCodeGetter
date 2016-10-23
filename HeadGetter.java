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
 * Created by Piotrek on 2016-10-23.
 */
public class HeadGetter implements Serializable {
    static StringBuilder headCode = new StringBuilder();

    public HeadGetter(){}

    public StringBuilder getHead(String url){

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String input;
            StringBuilder sourceCode = new StringBuilder();
            //pobór kodu źrółowego strony
            while((input = br.readLine()) != null){
                sourceCode.append(input);
            }
            br.close();
            Pattern ph = Pattern.compile("<head>(.*?)</head>");
            Matcher mh = ph.matcher(sourceCode);
            //wyszukiwanie head
            //StringBuilder headCode = new StringBuilder();
            while(mh.find()){
                headCode.append(sourceCode);
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("MalformedURLException");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }
        saveData();
        return headCode;
    }

    public void showEmailList(ArrayList<String> headList){
        for(int i = 0; i < headList.size(); i++){
            System.out.println(headList.get(i));
        }
    }

    public void saveData(){
        try{
            File file = new File("I:\\IdeaProjects\\WebsiteCodeGetter\\head.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(headCode.toString());
            System.out.println("FILE SAVED!");
        }
        catch(FileNotFoundException e){
            System.out.println("No such a file!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
