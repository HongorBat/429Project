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
		p1.setProperty("author", "randomAuthorA");
		p1.setProperty("title", "randomTitleT");
		p1.setProperty("pubYear", "1998");
		p1.setProperty("status", "");
		Book b = new Book(p1);
		b.update();
		
		System.out.println("----------------------------------------");
		bc.findBooksWithTitleLike("P");
		System.out.println(bc.print());
		System.out.println("----------------------------------------");
		bc.findBooksOlderThanDay("2000");
		System.out.println(bc.print());
		System.out.println("----------------------------------------");
		

		// EXAMPLE OF SETTING UP A NEW PATRON
		PatronCollection pc = new PatronCollection("Patron");
		Properties p2 = new Properties();
		p2.setProperty("patronId", "222");
		p2.setProperty("name", "patronName");
		p2.setProperty("address", "patronAddress");
		p2.setProperty("city", "patronCity");
		p2.setProperty("stateCode", "13131");
		p2.setProperty("zip", "2345678");
		p2.setProperty("email","patronName@example.com");
		p2.setProperty("dateOfBirth", "06/10/1997");
		p2.setProperty("status", "Alive");
		Patron p = new Patron(p2);
		p.update();
		
		System.out.println("----------------------------------------");
		pc.findPatronsYoungerThan("09/1/1958");
		System.out.println(pc.print());
		System.out.println("----------------------------------------");
		pc.findPatronsAtZipCode("10694");
		System.out.println(pc.print());
		System.out.println("----------------------------------------");
		
		
	}
}
