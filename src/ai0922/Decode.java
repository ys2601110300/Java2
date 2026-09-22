package ai0922;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Decode {
    public static void main(String[] args) {
        try {
            String decode = "";
            String inStr = "";
            BufferedReader br = new BufferedReader(new FileReader("Encryption.txt"));
            FileWriter fw = new FileWriter("Decode.txt");

            while(true)
            {
                inStr = br.readLine();

                decode = "";
                if(inStr == null)
                    break;

                for (int i = 0; i < inStr.length(); i++)
                {
                    int num = (int) inStr.charAt(i);
                    num -= 100;
                    decode += (char) num;
                }
                fw.write(decode + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
