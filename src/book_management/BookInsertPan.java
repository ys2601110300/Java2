package book_management;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class BookInsertPan extends JPanel {
    JTable table;
    DefaultTableModel model;
    ArrayList<Book> bookList;
    String[] tableHeader = {    "도서 번호", "도서명", "저자명", "출판사", "가격", "재고량", "카테고리"};
    String[] header = {"도서번호", "도서명", "저자명", "가격", "재고량", "출판사", "카테고리"};
    JLabel[] lbls = new JLabel[header.length];
    JTextField[] tfs = new JTextField[header.length - 2];
    JComboBox<Integer> comboCategory = new JComboBox<>();
    JComboBox<Integer> comboPublish = new JComboBox<>();
    private Map<Integer, String> publishers = Map.of();
    private Map<Integer, String> categories = Map.of();
    JButton btnInsert = new JButton("도서추가");
    JPanel panS = new JPanel(new GridLayout(4, 4, 20, 2));


    public BookInsertPan() {
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

        panS.add(new JLabel());
        panS.add(btnInsert);
    }


    public void initView() {
        model = new DefaultTableModel(tableHeader, bookList.size()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        TableColumnModel tcm = table.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(5).setPreferredWidth(100);
        tcm.getColumn(6).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(table);
        putSearchResult();

        add("Center", scroll);
        add("South", panS);
    }

    public void putSearchResult() {
        model.setRowCount(bookList.size());
        Book book = null;

        for (int i = 0; i < bookList.size(); i++) {
            book = bookList.get(i);
            model.setValueAt(book.getIsbn(), i, 0);
            model.setValueAt(book.getName(), i, 1);
            model.setValueAt(book.getAuthor(), i, 2);
            model.setValueAt(book.getPublish_name(), i, 3);
            model.setValueAt(book.getPrice(), i, 4);
            model.setValueAt(book.getInventory(), i, 5);
            model.setValueAt(book.getCategory_name(), i, 6);
        }
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public void setOptions(Map<Integer, String> publishers, Map<Integer, String> categories) {
        this.publishers = publishers;
        this.categories = categories;
        setComboOptions(comboPublish, publishers);
        setComboOptions(comboCategory, categories);
    }

    private void setComboOptions(JComboBox<Integer> combo, Map<Integer, String> namesById) {
        combo.setModel(new DefaultComboBoxModel<>(namesById.keySet().toArray(Integer[]::new)));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                String name = value == null ? "" : namesById.get(value);
                return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
            }
        });
    }

    public Integer getSelectedPublisherId() {
        return (Integer) comboPublish.getSelectedItem();
    }

    public Integer getSelectedCategoryId() {
        return (Integer) comboCategory.getSelectedItem();
    }

    public Book getBook() {
        String isbn = tfs[0].getText().strip();
        String name = tfs[1].getText().strip();
        String author = tfs[2].getText().strip();
        if (isbn.isEmpty() || name.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("도서번호, 도서명, 저자명을 입력해 주세요.");
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
        Integer publisherId = getSelectedPublisherId();
        Integer categoryId = getSelectedCategoryId();
        if (publisherId == null || categoryId == null) {
            throw new IllegalArgumentException("출판사와 카테고리를 선택해 주세요.");
        }

        Book book = new Book();
        book.setIsbn(isbn);
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setInventory(inventory);
        book.setPublish_name(publishers.get(publisherId));
        book.setCategory_name(categories.get(categoryId));
        return book;
    }

    public void clearFields() {
        for (JTextField field : tfs) {
            field.setText("");
        }
        tfs[0].requestFocusInWindow();
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }
}

