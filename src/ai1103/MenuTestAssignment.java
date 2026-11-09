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
    // 버튼 이미지 순서와 배경색 순서를 같게 맞춤
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
            // 미리 24 x 24 크기로 맞춘 이미지와 기능 이름 사용
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
        JMenuItem saveMenuItem = new JMenuItem("Save"); // [추가 1] 저장 메뉴
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
                        // 선택을 완료했을 때만 읽기: 0은 확인, 나머지는 취소/오류
                        if (fileChooser.showOpenDialog(frame) == 0) {
                            selectedFile = fileChooser.getSelectedFile();
                            textArea.setText(readFile(selectedFile));
                        }
                        break;
                    case "Save": // [추가 1] 작성한 내용을 파일로 저장
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
        JMenuItem yellowMenuItem = new JMenuItem("Yellow"); // [추가 2] 노란색 메뉴
        editMenu.add(whiteMenuItem);
        editMenu.add(blueMenuItem);
        editMenu.add(orangeMenuItem);
        editMenu.add(yellowMenuItem);

        whiteMenuItem.addActionListener(backgroundListener);
        blueMenuItem.addActionListener(backgroundListener);
        orangeMenuItem.addActionListener(backgroundListener);
        yellowMenuItem.addActionListener(backgroundListener);

        SetFrame.setFrame(this, "메모장", 650, 500);
        // 공통 SetFrame에서 설정한 제목을 이 창에서만 변경
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
                // [추가 1] Save 아이콘도 메뉴와 동일하게 저장
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
                case "Yellow": // [추가 2] 글 입력 영역을 노란색으로 변경
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

    // [추가 1] ai0922의 FileWriter 예제와 같은 방식으로 저장
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
