package book_management.controller;

import ai1027.SetFrame;
import book_management.BookInsertPan;
import book_management.BookSearchPan;
import book_management.BookUpdatePan;
import book_management.domain.Book;
import book_management.repository.BookRepository;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class BookController extends JFrame {
    private final BookRepository repository = new BookRepository();
    private final BookSearchPan searchPan = new BookSearchPan();
    private final BookInsertPan insertPan = new BookInsertPan();
    private final BookUpdatePan updatePan = new BookUpdatePan();

    public BookController() {
        searchPan.setBookList(new ArrayList<>());
        searchPan.initView();
        insertPan.initView();
        updatePan.initView();

        searchPan.getBtnSearch().addActionListener(e -> searchBooks());
        insertPan.getBtnInsert().addActionListener(e -> insertBook());
        updatePan.getBtnUpdate().addActionListener(e -> updateBook());
        JTable updateTable = updatePan.getTable();
        updateTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int rowIndex = updateTable.getSelectedRow();
            if (rowIndex < 0) {
                updatePan.clearFields();
            } else {
                updatePan.setTextField(updateTable.convertRowIndexToModel(rowIndex));
            }
        });
        updateTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2 || !SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                int rowIndex = updateTable.rowAtPoint(e.getPoint());
                if (rowIndex < 0 || updateTable.columnAtPoint(e.getPoint()) < 0) {
                    return;
                }
                int modelRow = updateTable.convertRowIndexToModel(rowIndex);
                String isbn = (String) updateTable.getModel().getValueAt(modelRow, 0);
                deleteBook(isbn);
            }
        });

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.add("도서 검색", searchPan);
        tabbedPane.add("도서 추가", insertPan);
        tabbedPane.add("도서 수정 및 삭제", updatePan);
        add(tabbedPane);
        SetFrame.setFrame(this, "도서관리 시스템", 700, 600);

        try {
            ArrayList<Book> books = repository.select("", 0);
            searchPan.setBookList(books);
            searchPan.putSearchResult();
            showBookLists(books);
            loadOptions();
        } catch (SQLException e) {
            showDatabaseError("도서 정보를 불러오지 못했습니다.", e);
        }
    }

    private void loadOptions() throws SQLException {
        Map<Integer, String> publishers = repository.selectPublishers();
        Map<Integer, String> categories = repository.selectCategories();
        insertPan.setOptions(publishers, categories);
        updatePan.setOptions(publishers, categories);
    }

    private void searchBooks() {
        try {
            refreshSearchResults();
        } catch (SQLException e) {
            showDatabaseError("도서를 검색하지 못했습니다.", e);
        }
    }

    private void refreshSearchResults() throws SQLException {
        searchPan.setBookList(repository.select(searchPan.getSearchWord(), searchPan.getCombo().getSelectedIndex()));
        searchPan.putSearchResult();
    }

    private void showBookLists(ArrayList<Book> books) {
        insertPan.setBookList(books);
        insertPan.putSearchResult();
        updatePan.setBookList(books);
        updatePan.putSearchResult();
    }

    private void insertBook() {
        try {
            if (insertPan.getSelectedPublisherId() == null || insertPan.getSelectedCategoryId() == null) {
                loadOptions();
            }
            Book book = insertPan.getBook();
            repository.insert(book, insertPan.getSelectedCategoryId(), insertPan.getSelectedPublisherId());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "도서 추가", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (SQLException e) {
            showDatabaseError("도서를 추가하지 못했습니다.", e);
            return;
        }

        insertPan.clearFields();
        refreshAfterSave();
    }

    private void updateBook() {
        try {
            if (!repository.update(updatePan.getBook())) {
                JOptionPane.showMessageDialog(this, "수정할 도서나 선택한 출판사 또는 카테고리를 찾을 수 없습니다.",
                        "도서 수정", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "도서 수정", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (SQLException e) {
            showDatabaseError("도서를 수정하지 못했습니다.", e);
            return;
        }

        refreshAfterSave();
    }

    private void deleteBook(String isbn) {
        try {
            repository.delete(isbn);
        } catch (SQLException e) {
            showDatabaseError("도서를 삭제하지 못했습니다.", e);
            return;
        }
        refreshAfterSave();
    }

    private void refreshAfterSave() {
        try {
            showBookLists(repository.select("", 0));
            refreshSearchResults();
        } catch (SQLException e) {
            showDatabaseError("도서 변경 사항을 반영했습니다. 목록을 새로 불러오지 못했습니다.", e);
        }
    }

    private void showDatabaseError(String message, SQLException e) {
        JOptionPane.showMessageDialog(this, message + "\n" + e.getMessage(),
                "도서관리 시스템", JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookController::new);
    }
}
