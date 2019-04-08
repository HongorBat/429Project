package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

import database.JDBCBroker;

public class BookCollection extends EntityBase {

    private Vector<Book> bookList;
    private String updateStatusMessage = "";

    public BookCollection(String tableName) {
    	super(tableName);
        bookList = new Vector<Book>();
    }

    public void findBooksOlderThanDay(String year) { 
    	try {
    		bookList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE pubYear < " + year;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		bookList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    public void findBooksNewerThanDate(String year) { 
    	try {
    		bookList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE pubYear > " + year;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		bookList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    public void findBooksWithTitleLike(String title) {
    	try {
    		bookList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE title LIKE '%" + title + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		bookList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    public void findBooksWithAuthorLike(String author) {
        try {
    		bookList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE author LIKE '%" + author + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		bookList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < bookList.size(); i++) {
    		Book b = (Book)bookList.get(i);
    		sb.append(b.toString() + "\n");
    	}
    	return sb.toString();
    }
    
    /**
     * @return the array of books
     */
    public ArrayList<MutableBook> toArrayList() {
    	ArrayList<MutableBook> list = new ArrayList<>();
    	for (int i = 0; i < bookList.size(); i++) {
    		Book b = (Book)bookList.get(i);
    		list.add(new MutableBook(b));
    	}
    	Collections.sort(list);
    	return list;
    }
    
	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub
		
	}
}