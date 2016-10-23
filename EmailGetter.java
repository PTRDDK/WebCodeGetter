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
public class EmailGetter implements Serializable {

    static ArrayList<String> emailList = new ArrayList<>();

    public EmailGetter(){}

    public ArrayList getEmails(String url){

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String input;
            StringBuilder sourceCode = new StringBuilder();
            //pobór kodu źrółowego strony
            while((input = br.readLine()) != null){
                sourceCode.append(input);
            }
            br.close();
            Pattern pe = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
            Matcher me = pe.matcher(sourceCode);
            //wyszukiwanie maili
            while(me.find()){
                String email = me.group();
                emailList.add(email);
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("MalformedURLException");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }
        showEmailList(emailList);
        saveData();
        return emailList;
    }

    public void showEmailList(ArrayList<String> emailList){
        for(int i = 0; i < emailList.size(); i++){
            System.out.println(emailList.get(i));
        }
    }

    public void saveData(){
        try{
            Path out = Paths.get("I:\\\\IdeaProjects\\\\WebsiteCodeGetter\\\\emails.txt");
            Files.write(out,emailList, Charset.defaultCharset());
            System.out.println("FILE SAVED!");
        }
        catch(FileNotFoundException e){
            System.out.println("No such a file!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
