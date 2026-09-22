package ai0922;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CharEncryption {
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            FileWriter fw = new FileWriter("Encryption.txt");
            String secure = "";
            String inStr = "";

            while(true)
            {
                inStr = s.nextLine();

                if(inStr.equals("exit"))
                    break;

                secure = "";

                for (int i = 0; i < inStr.length(); i++)
                {
                    int num = (int) inStr.charAt(i);
                    num += 100;
                    secure += (char) num;
                }
                fw.write(secure + "\n");
            }
            fw.close();
            s.close();
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
