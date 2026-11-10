package ai1103;

import ai1027.SetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuTestAssignment extends JFrame {
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    JFrame frame;
    String[] btnIconFileNames = {"new", "open", "save", "close", "orange", "blue", "white", "yellow"};
    String[] btnNames = {"New", "Open", "Save", "Close", "Orange", "Blue", "White", "Yellow"};
    Color[] ColorNames = {Color.ORANGE, Color.BLUE, Color.WHITE, Color.YELLOW};
    JButton[] btns = new JButton[8];

    public MenuTestAssignment() {
        frame = this;
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        JToolBar toolBar = new JToolBar();
        add("North", toolBar);

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(btnNames[i], new ImageIcon("toolbar/assignment/" + btnIconFileNames[i] + ".png"));
            btns[i].addActionListener(btnListener);
            toolBar.add(btns[i]);
        }

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem closeMenuItem = new JMenuItem("Close");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(closeMenuItem);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File selectedFile;
                switch (actionEvent.getActionCommand()) {
                    case "New":
                        textArea.setText("");
                        break;
                    case "Open":

                        if (fileChooser.showOpenDialog(frame) == 0) {
                            selectedFile = fileChooser.getSelectedFile();
                            textArea.setText(readFile(selectedFile));
                        }
                        break;
                    case "Save":
                        if (fileChooser.showSaveDialog(frame) == 0) {
                            selectedFile = fileChooser.getSelectedFile();
                            saveFile(selectedFile);
                        }
                        break;
                    case "Close":
                        System.exit(0);
                        break;
                }
            }
        };

        newMenuItem.addActionListener(actionListener);
        openMenuItem.addActionListener(actionListener);
        saveMenuItem.addActionListener(actionListener);
        closeMenuItem.addActionListener(actionListener);

        JMenuItem whiteMenuItem = new JMenuItem("White");
        JMenuItem blueMenuItem = new JMenuItem("Blue");
        JMenuItem orangeMenuItem = new JMenuItem("Orange");
        JMenuItem yellowMenuItem = new JMenuItem("Yellow");
        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(orangeMenuItem);
        editMenu.add(yellowMenuItem);

        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        orangeMenuItem.addActionListener(backgroundListener);
        yellowMenuItem.addActionListener(backgroundListener);

        SetFrame.setFrame(this, "메모장", 650, 500);
        setTitle("메모장");
    }

    ActionListener btnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton eBtn = (JButton) e.getSource();
            File selectedFile;
            if (eBtn == btns[0]) {
                textArea.setText("");
            } else if (eBtn == btns[1]) {
                if (fileChooser.showOpenDialog(frame) == 0) {
                    selectedFile = fileChooser.getSelectedFile();
                    textArea.setText(readFile(selectedFile));
                }
            } else if (eBtn == btns[2]) {
                if (fileChooser.showSaveDialog(frame) == 0) {
                    selectedFile = fileChooser.getSelectedFile();
                    saveFile(selectedFile);
                }
            } else if (eBtn == btns[3]) {
                System.exit(0);
            }

            for (int i = 0; i < ColorNames.length; i++) {
                if (eBtn == btns[i + 4]) {
                    textArea.setBackground(ColorNames[i]);
                }
            }
        }
    };

    ActionListener backgroundListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = null;
            switch (e.getActionCommand()) {
                case "White":
                    color = Color.WHITE;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "Orange":
                    color = Color.ORANGE;
                    break;
                case "Yellow":
                    color = Color.YELLOW;
                    break;
            }
            textArea.setBackground(color);
        }
    };

    public String readFile(File selectedFile) {
        String totalLines = "";
        try {
            String line = "";
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                totalLines += line + "\n";
            }
            br.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "파일을 찾을 수 없습니다.");
            return textArea.getText();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "파일을 읽을 수 없습니다.");
            return textArea.getText();
        }
        return totalLines;
    }

    public void saveFile(File selectedFile) {
        try {
            FileWriter fw = new FileWriter(selectedFile);
            fw.write(textArea.getText());
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "파일을 저장할 수 없습니다.");
        }
    }

    public static void main(String[] args) {
        new MenuTestAssignment();
    }
}
