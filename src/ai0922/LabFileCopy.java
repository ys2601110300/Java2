package ai0922;

import java.io.*;

public class LabFileCopy {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("myData1.txt"));
            FileWriter fw = new FileWriter("newFile.txt");
            String line = "";

            while (true)
            {
                line = br.readLine();
                if (line == null)
                {
                    break;
                }
                fw.write(line + "\n");
            }
            fw.close();
            System.out.println("newFIle.txt 저장 완료");
            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
