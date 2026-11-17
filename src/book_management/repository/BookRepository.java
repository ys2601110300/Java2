package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository {
    ArrayList<Book> bookList = new ArrayList<>();

    public ArrayList<Book> select (String searchWord, int selectedIndex){
        bookList = new ArrayList<>();

        String[] columnName = {"name", "publish_name", "author_name"};

        String sql = "SELECT isbn, name, author_name, price, inventory, category_name,  publish_name from book b, category c, publish p " +
                "where b.category_id = c.category_id and b.publish_id = p.publish_id " +
                " and " + columnName[selectedIndex] + " like ?";
        try (Connection con = JDBCConnector.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchWord + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()){
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

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return bookList;
    }
}
