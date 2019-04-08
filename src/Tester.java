import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import database.JDBCBroker;
import database.SQLQueryStatement;
import database.SQLSelectStatement;
import database.SQLStatement;
import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;
import model.Teller;

public class Tester {
	public static void main(String[] args) {
		
		
		// EXAMPLE OF SETTING UP A NEW BOOK
		BookCollection bc = new BookCollection("Book");
		Properties p1 = new Properties();
		//p1.setProperty("bookId", "222");
		p1.setProperty("author", "Jane Doe");
		p1.setProperty("title", "Jane's Adventures");
		p1.setProperty("pubYear", "1998");
		p1.setProperty("status", "");
		Book b = new Book(p1);
		b.update();
		
		System.out.println("----------------------------------------");
		//bc.findBooksWithTitleLike("Jane");
		System.out.println(bc.print());
		System.out.println("----------------------------------------");
		//bc.findBooksOlderThanDay("1997");
		//System.out.println(bc.print());
		System.out.println("----------------------------------------");
		

		// EXAMPLE OF SETTING UP A NEW PATRON
		PatronCollection pc = new PatronCollection("Patron");
		Properties p2 = new Properties();
		//p2.setProperty("patronId", "12");
		p2.setProperty("name", "John Doe");
		p2.setProperty("address", "123 cool st");
		p2.setProperty("city", "Hampton");
		p2.setProperty("stateCode", "31");
		p2.setProperty("zip", "2347");
		p2.setProperty("email","johnDoe99@example.com");
		p2.setProperty("dateOfBirth", "06/13/2002");
		p2.setProperty("status", "Alive");
		Patron p = new Patron(p2);
		p.update();
		
		System.out.println("----------------------------------------");
		pc.findPatronsYoungerThan("09/1/2008");
		System.out.println(pc.print());
		System.out.println("----------------------------------------");
		pc.findPatronsAtZipCode("2347");
		System.out.println(pc.print());
		System.out.println("----------------------------------------");
		
		
	}
}
