package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookRepository {
    public ArrayList<Book> select(String searchWord, int selectedIndex) throws SQLException {
        ArrayList<Book> bookList = new ArrayList<>();

        String[] columnName = {"name", "publish_name", "author_name"};

        String sql = "SELECT isbn, name, author_name, price, inventory, category_name,  publish_name from book b, category c, publish p " +
                "where b.category_id = c.category_id and b.publish_id = p.publish_id " +
                " and " + columnName[selectedIndex] + " like ? order by isbn";
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchWord + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setIsbn(rs.getString("isbn"));
                    book.setName(rs.getString("name"));
                    book.setAuthor(rs.getString("author_name"));
                    book.setPrice(rs.getInt("price"));
                    book.setInventory(rs.getInt("inventory"));
                    book.setCategory_name(rs.getString("category_name"));
                    book.setPublish_name(rs.getString("publish_name"));
                    bookList.add(book);
                }
            }
        }

        return bookList;
    }

    public Map<Integer, String> selectPublishers() throws SQLException {
        return selectOptions("select publish_id, publish_name from publish order by publish_id");
    }

    public Map<Integer, String> selectCategories() throws SQLException {
        return selectOptions("select category_id, category_name from category order by category_id");
    }

    private Map<Integer, String> selectOptions(String sql) throws SQLException {
        Map<Integer, String> options = new LinkedHashMap<>();
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                options.put(rs.getInt(1), rs.getString(2));
            }
        }
        return options;
    }

    public void insert(Book book, int categoryId, int publishId) throws SQLException {
        String sql = "insert into book (isbn, name, author_name, price, inventory, category_id, publish_id) values(?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getName());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getPrice());
            pstmt.setInt(5, book.getInventory());
            pstmt.setInt(6, categoryId);
            pstmt.setInt(7, publishId);

            pstmt.executeUpdate();
        }
    }

    public void delete(String isbn) throws SQLException {
        String sql = "delete from book where isbn=?";
        Connection con = JDBCConnector.getConnection();
        PreparedStatement pstmt = null;

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int toPublishId(String publishName){
        int publish_id = 0;
        switch (publishName){
            case  "한빛아카데미":
                publish_id = 1;
                break;
            case  "생능출판사":
                publish_id = 2;
                break;
            case  "민음사":
                publish_id = 3;
                break;
            case  "해냄출판사":
                publish_id = 4;
                break;
        }
        return publish_id;
    }

    public int toCategoryId(String categoryName) {
        int category_id = 0;
        switch (categoryName) {
            case " IT도서":
                category_id = 10;
                break;
            case " 소설":
                category_id = 20;
                break;
            case " 수필":
                category_id = 30;
                break;
            case " 시":
                category_id = 40;
                break;
        }
        return category_id;
    }

    public boolean update(Book book) throws SQLException {
        String sql = "merge into book b using (" +
                "select c.category_id, p.publish_id from category c cross join publish p " +
                "where c.category_name=? and p.publish_name=?" +
                ") ids on (b.isbn=?) when matched then update set " +
                "b.name=?, b.author_name=?, b.price=?, b.inventory=?, " +
                "b.category_id=ids.category_id, b.publish_id=ids.publish_id";
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, book.getCategory_name());
            pstmt.setString(2, book.getPublish_name());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getName());
            pstmt.setString(5, book.getAuthor());
            pstmt.setInt(6, book.getPrice());
            pstmt.setInt(7, book.getInventory());
            return pstmt.executeUpdate() > 0;
        }
    }

}
