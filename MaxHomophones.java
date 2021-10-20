import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class MaxHomophones {
    public static void main(String[] args) {
        
        File file = new File("./cmudict.0.7a.txt");

        Scanner scnr = new Scanner(System.in);
        int nLines = scnr.nextInt();
        scnr.close();

        displayMaxHomophones(file, nLines);
    }

    static OALDictionary pronunciationDict = new OALDictionary<String, Pronunciation>();
    static ArrayList<String> pronunciationArray = new ArrayList();
    

    public static void displayMaxHomophones(File file, int nLines) {

        processFile(file, nLines);

        int maxHomophones = 1;
        for (Pronunciation p : (ArrayList<Pronunciation>)pronunciationDict.values()) {
            if(pronunciationArray.contains(p.pronunciation)) continue;

            int pHomophones = p.NumOfMatchingKeys(pronunciationDict);
            if(pHomophones < maxHomophones) continue;
            if(pHomophones == maxHomophones) {
                pronunciationArray.add(p.pronunciation);
            }
            if(pHomophones > maxHomophones) {
                pronunciationArray.clear();
                pronunciationArray.add(p.pronunciation);
                maxHomophones = pHomophones;
            }

        }
        

        if(maxHomophones == 1) return;


        //print out
        System.out.println(maxHomophones);
        for (String pronunciation : pronunciationArray) {
            for (Pronunciation p : (ArrayList<Pronunciation>)pronunciationDict.findAll(pronunciation)) {
                System.out.println(p.word);
            }
            System.out.println();
        }
    }

    private static void processFile(File file, int nLines) {
        Scanner scnr;
        try {
            scnr = new Scanner(file);

            int linesRead = 0;
            while(linesRead < nLines && scnr.hasNextLine()) {
                String currentLine = scnr.nextLine();
                if(currentLine.contains(";;;")) continue;
                else {
                    String[] splitLine = currentLine.split("  ");

                    Pronunciation p = new Pronunciation(splitLine[0], splitLine[1]);
                    pronunciationDict.insert(splitLine[1], p);

                    linesRead++;     
                }
            }

            scnr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}