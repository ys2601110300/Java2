package ai1103;

import ai1027.SetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuTest extends JFrame {
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    JFrame frame;

    public MenuTest(){
        frame = this;
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem closeMenuItem = new JMenuItem("Close");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(closeMenuItem);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File selectedFile;
                switch (actionEvent.getActionCommand()){
                    case "New":
                        textArea.setText("");
                        break;
                    case "Open":
                        fileChooser.showOpenDialog(frame);
                        selectedFile = fileChooser.getSelectedFile();
                        textArea.setText(readFile(selectedFile));
                        break;
                    case "Close":
                        System.exit(0);
                        break;
                }
            }
        };

        newMenuItem.addActionListener(actionListener);
        openMenuItem.addActionListener(actionListener);
        closeMenuItem.addActionListener(actionListener);

        JMenuItem whiteMenuItem = new JMenuItem("White");
        JMenuItem blueMenuItem = new JMenuItem("Blue");
        JMenuItem orangeMenuItem = new JMenuItem("Orange");
        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(orangeMenuItem);

        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        orangeMenuItem.addActionListener(backgroundListener);
        SetFrame.setFrame(this, "메뉴만들기", 500, 500);
    }

    ActionListener backgroundListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = null;
            switch (e.getActionCommand()){
                case "White":
                    color = Color.WHITE;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "Orange":
                    color = Color.ORANGE;
                    break;

            }
            textArea.setBackground(color);
        }
    };

    public String readFile(File selectedFile){
        String totalLines = "";
        try{
            String line = " ";
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null){
                totalLines += line + "\n";
            }
        } catch (FileNotFoundException e) {
            textArea.setText("File not found");
        }catch (IOException e) {
            throw new RuntimeException();
        }

        return totalLines;
    }

    public static void main(String[] args) {
        new  MenuTest();
    }
}
