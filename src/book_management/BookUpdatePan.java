package book_management;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class BookUpdatePan extends JPanel {
    JTable table;
    DefaultTableModel model;
    ArrayList<Book> bookList;
    String[] tableHeader = {    "도서 번호", "도서명", "저자명", "가격", "재고량", "출판사",  "카테고리"};
    String[] header = {"도서번호", "도서명", "저자명", "가격", "재고량", "출판사", "카테고리"};
    JLabel[] lbls = new JLabel[header.length];
    JTextField[] tfs = new JTextField[header.length - 2];
    JComboBox<String> comboCategory = new JComboBox<>();
    JComboBox<String> comboPublish = new JComboBox<>();
    JButton btnUpdate = new JButton("도서수정");
    JPanel panS = new JPanel(new GridLayout(4, 4, 20, 2));

    public BookUpdatePan() {
        bookList = new ArrayList<Book>();
        setLayout(new BorderLayout());

        for (int i = 0; i < tableHeader.length; i++) {
            lbls[i] = new JLabel(header[i], JLabel.RIGHT);
            panS.add(lbls[i]);
            if (i < tableHeader.length - 2) {
                tfs[i] = new JTextField();
                panS.add(tfs[i]);
            } else if (i == 5) {
                panS.add(comboPublish);
            } else {
                panS.add(comboCategory);
            }
        }
        tfs[0].setEditable(false);
        JLabel emptylbl = new JLabel(" ");
        panS.add(emptylbl);
        panS.add(btnUpdate);
    }

    public void setTextField(int rowIndex) {
        for(int i = 0; i< tfs.length; i++) {
            tfs[i].setText(String.valueOf(model.getValueAt(rowIndex, i)));
        }
        comboPublish.setSelectedItem(model.getValueAt(rowIndex, 5));
        comboCategory.setSelectedItem(model.getValueAt(rowIndex, 6));
    }


    public void initView() {
        model = new DefaultTableModel(tableHeader, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panS, BorderLayout.SOUTH);
        putSearchResult();
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public void setOptions(Map<Integer, String> publishers, Map<Integer, String> categories) {
        comboPublish.setModel(new DefaultComboBoxModel<>(publishers.values().toArray(String[]::new)));
        comboCategory.setModel(new DefaultComboBoxModel<>(categories.values().toArray(String[]::new)));
    }

    public void putSearchResult() {
        table.clearSelection();
        model.setRowCount(bookList.size());
        Book book = null;

        for (int i = 0; i < bookList.size(); i++) {
            book = bookList.get(i);
            model.setValueAt(book.getIsbn(), i, 0);
            model.setValueAt(book.getName(), i, 1);
            model.setValueAt(book.getAuthor(), i, 2);
            model.setValueAt(book.getPrice(), i, 3);
            model.setValueAt(book.getInventory(), i, 4);
            model.setValueAt(book.getPublish_name(), i, 5);
            model.setValueAt(book.getCategory_name(), i, 6);
        }
    }

    public Book getBook() {
        if (table.getSelectedRow() < 0) {
            throw new IllegalArgumentException("목록에서 수정할 도서를 선택해 주세요.");
        }

        String name = tfs[1].getText().strip();
        String author = tfs[2].getText().strip();
        if (name.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("도서명과 저자명을 입력해 주세요.");
        }

        int price;
        int inventory;
        String numberMessage = "가격과 재고량은 0 이상의 정수를 입력해 주세요.";
        try {
            price = Integer.parseInt(tfs[3].getText().strip());
            inventory = Integer.parseInt(tfs[4].getText().strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(numberMessage, e);
        }
        if (price < 0 || inventory < 0) {
            throw new IllegalArgumentException(numberMessage);
        }

        String publisher = (String) comboPublish.getSelectedItem();
        String category = (String) comboCategory.getSelectedItem();
        if (publisher == null || category == null) {
            throw new IllegalArgumentException("출판사와 카테고리를 선택해 주세요.");
        }

        Book book = new Book();
        book.setIsbn(tfs[0].getText());
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setInventory(inventory);
        book.setPublish_name(publisher);
        book.setCategory_name(category);
        return book;
    }

    public void clearFields() {
        for (JTextField field : tfs) {
            field.setText("");
        }
        comboPublish.setSelectedIndex(-1);
        comboCategory.setSelectedIndex(-1);
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JTable getTable() {
        return table;
    }
}
