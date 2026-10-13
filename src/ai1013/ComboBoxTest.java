package ai1013;

import center.CenterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxTest extends JFrame{
    JLabel lbl = new JLabel();
    public ComboBoxTest() {
        setTitle("문자열 입력 컴포넌트");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        String[] items = {"강아지", "고양이", "토끼", "사막여우", "뱁새"};
        JComboBox comboBox = new JComboBox(items);
        comboBox.addItemListener(comboListner);


        add("North", comboBox);
        add("Center", lbl);

        int w = 250, h = 250;
        int[] locations = CenterFrame.getLocation(w, h);
        setBounds(locations[0], locations[1], w, h);
        setVisible(true);
    }
    ItemListener comboListner = new ItemListener(){
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            ImageIcon icon = null;
            int imgPath = 0;
            String selectedItem = (String) e.getItem();
            switch (selectedItem)
            {
                case "강아지":
                    imgPath = 1;
                    break;
                case "고양이":
                    imgPath = 2;
                    break;
                case "사막여우":
                    imgPath = 3;
                    break;
                case "뱁새":
                    imgPath = 4;
                    break;
                case "토끼":
                    imgPath = 5;
                    break;
            }

            icon  = new ImageIcon("imgs/pet"+imgPath+".png");
            lbl.setIcon(icon);
        }
    };

    public static void main(String[] args) {
        new ComboBoxTest();
    }}
