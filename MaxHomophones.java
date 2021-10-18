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

    public static OALDictionary pronunciationDict = new OALDictionary<String, String>();
    public static ArrayList<String> keyArray = new ArrayList();
    public static ArrayList<Integer> numDuplicatesArray = new ArrayList();
    

    public static void displayMaxHomophones(File file, int nLines) {
        OALDictionary maxHomophDict = new OALDictionary<String, String>();

        processFile(file, nLines);

        int maxHomophones = 0;
        for (int i = 0; i < keyArray.size(); i++) {
            int numOfHomoph = 1;
            for (int j = 0; j < keyArray.size(); j++) {
                if(i == j) continue;
                if (keyArray.get(i).equals(keyArray.get(j))){
                    numOfHomoph++;
                }
            }
            if(maxHomophones < numOfHomoph)
                maxHomophones = numOfHomoph;
            numDuplicatesArray.add(i,numOfHomoph);
        }

        ArrayList<Integer> corrIndexValues = new ArrayList<>();
        for (int i = 0; i < numDuplicatesArray.size(); i++) {
                if(numDuplicatesArray.get(i) == maxHomophones)
                    corrIndexValues.add(i);
        }

        ArrayList<String> corrKeys = new ArrayList<>();
        for (int i = 0; i < corrIndexValues.size(); i++) {
            if(!corrKeys.contains(keyArray.get(corrIndexValues.get(i))))
            corrKeys.add(keyArray.get(corrIndexValues.get(i)));
        }



        System.out.println(maxHomophones);
        for (String key : corrKeys) {
            for (var string : pronunciationDict.findAll(key)) {
                System.out.println(string.toString());
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

                    pronunciationDict.insert(splitLine[1], splitLine[0]);
                    keyArray.add(splitLine[1]);

                    linesRead++;     
                }
            }

            scnr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}