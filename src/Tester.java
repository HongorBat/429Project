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
import model.InventoryItem;
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.Patron;
import model.PatronCollection;
import model.Teller;
import model.Vendor;

public class Tester {
	public static void main(String[] args) {
		newTest();
	}
	
	private static void newTest() {
		// this one is for the group project
		
		getInventoryItemCollection();
		
		//addInventoryItemType();
		//addInventoryItem();
		// addVendor();
		
	}
	
	
	private static void getInventoryItemCollection() {
		InventoryItemCollection iic = new InventoryItemCollection("InventoryItem");
		iic.getInventoryItemNamesLike("a");
		System.out.println(iic.toArrayList().size() + " <<<");
		iic.updateInventoryItemWithName("asdf", "VendorId", "1111");
	}
	
	private static void addInventoryItem() {
		
		// create the properties of the inventoryIte,
		Properties p1 = new Properties();
		//p1.setProperty("InventoryItemId", "1"); // this is auto incremented dont touch
		p1.setProperty("Barcode", "12343");
		p1.setProperty("InventoryItemTypeName", "Jimmy Neutron");
		p1.setProperty("VendorId", "11414");
		p1.setProperty("DateReceived", "10/10/10");
		p1.setProperty("DateOfLastUse", "02/02/01");
		p1.setProperty("Notes", "special note");
		p1.setProperty("Status", "Available");
		
		// Create the Inventory Item object with the properties we made
		InventoryItem it = new InventoryItem(p1);
		// add it to the db
		it.update();
	}
	
	private static void editInventoryItem() {
		
	}
	
	private static void addInventoryItemType() {
		
		// Give the object some properties
		Properties p1 = new Properties();
		//p1.setProperty("ItemTypeId", "");
		p1.setProperty("Units", "111");
		p1.setProperty("UnitMeasure", "Lbs");
		p1.setProperty("ValidityDays", "20");
		p1.setProperty("ReorderPoint", ".012");
		p1.setProperty("Notes", "this is a note");
		p1.setProperty("ItemTypeName", "Jimmy Neutron");
		
		InventoryItemType iit = new InventoryItemType(p1);
		iit.update();
	}
	
	private static void addVendor() {
		// add the properties
		Properties p2 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		p2.setProperty("Name", " Shrek the Ogre");
		p2.setProperty("PhoneNumber", "585-585-5855");
		p2.setProperty("Status", "Active");
		
		// create item using properties, then add to db
		Vendor ve = new Vendor(p2);
		ve.update();
	}
	
	
	/*
	private static void oldTest() {
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
	}*/
}
