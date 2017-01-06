package com.gcit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class LMS1 {
 
	int mainMenu;
	int lib1;
	int lib2;
	int lib4;
	int noOfcopies;
	ResultSet lib2Rs;
	ResultSet lib3Rs;
	ResultSet lib4Rs;
	ResultSet lib5Rs;
	ResultSet lib6Rs;
	String branchName;
	int BranchId =1;
	int bookId =1;
	int bk;
	  private String db ="jdbc:mysql://localhost/library";
	  private String user = "root";
	  private String pass = "gandalf325";
	  private Connection connect = null;
	  private Connection connect1;
	  private Connection connect2;
	  private PreparedStatement pstmt = null;
	
	public Connection openConnection(String url, String uname, String pwd) throws SQLException
	{	
		Connection conn = null;	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, uname, pwd);
	return conn;
	}	

	
	public void mainMenu() 
	{
		LMS1 tempob = new LMS1();
		Borrower bobj = new Borrower(); 
		Administrator aobj = new Administrator();
		System.out.println("\nWelcome to the GCIT Library "
				+ "Management System. Which category of a user are you"
				+  "\n 1)Librarian \n 2)Administrator \n 3)Borrower \n 4)Quit from the system");
		Scanner sc = new Scanner(System.in);
		mainMenu = Integer.parseInt(sc.nextLine());
		
		if(mainMenu == 1)
		{
			try {
				connect = openConnection(db,user,pass);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				lib1();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(mainMenu == 2)
		  {
		  try {
			aobj.admin0();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  }
		if(mainMenu == 3)
		  {
			try {
				bobj.borr0();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   }	
		System.exit(0);
	
	}
	
		

	public void lib1() throws SQLException
	{
		LMS1 tempob = new LMS1();
		System.out.println("1)enter the branch you manage from the list \n2)quit to previous ");
		Scanner sc = new Scanner(System.in);
		lib1 = Integer.parseInt(sc.nextLine());
		switch(lib1) 
		{
		case(1): 
		   {
			lib2();
			break;
		   }
		case(2):
		   {
			mainMenu();
		   }
		
		}		
		
	}	
	
	public void lib2() throws SQLException
	{
     BranchId = 1;
	String lib2Query = "select * from tbl_library_branch"; 
	pstmt = connect.prepareStatement(lib2Query);
	lib2Rs = pstmt.executeQuery();
    
	while(lib2Rs.next())
	   {
		System.out.println("Branch Id-- "+ lib2Rs.getInt("branchId"));
		System.out.println("Branch Name-- "+ lib2Rs.getString("branchName"));
		System.out.println("Address-- "+ lib2Rs.getString("branchAddress"));
		System.out.println("-------------------------");
		System.out.println();
	BranchId = BranchId + 1;
	   }   
	System.out.println(BranchId+")Quit to previous");
	System.out.println("Select an Option");
	Scanner sc = new Scanner(System.in);
	lib2 =sc.nextInt();
//	branchName = lib2Rs.getString("branchName");
	if(lib2 == BranchId)
	   {
		lib1();
	   }
	    lib3();
	} 

	public void lib3() throws SQLException
	{
		System.out.println("1)Update the details of the Library "
				+ "\n2)Add copies of Book to the Branch"+ "\n3)Quit to previous");
		Scanner sc = new Scanner(System.in);
		int lib3 =sc.nextInt();
		//String branchName = lib2Rs.getString(lib2);
		switch(lib3)
		{
		case(1):
		   { 
				System.out.println("You have chosen to update the Branch with Branch Id: "+lib2+" and "
					+ "Branch Name: "//+ lib2Rs.getString("branchName")
					+"\n Enter ‘quit’ at any prompt to cancel operation.");
		
			String response = sc.next();
			if(response.equals("quit"))
			{
			lib2();	
			}
			else
			{
					
				System.out.println("Please enter new branch name or enter N/A for no change:");
				String Lib3UpdatedName = sc.nextLine();
				System.out.println("Please enter new branch address or enter N/A for no change:");
				String Lib3UpdatedAddress = sc.nextLine();
				
				String lib3Query1 = "update tbl_library_branch set branchName = ? where branchId = ?";
				String lib3Query2 = "update tbl_library_branch set branchAddress = ? where branchId = ?";
				pstmt = connect.prepareStatement(lib3Query1);
				pstmt.setString(1,Lib3UpdatedName);  //index is based on the number of ?
				pstmt.setInt(2, lib2);
			int x= 	pstmt.executeUpdate();
				System.out.println(x);
				
				pstmt = connect.prepareStatement(lib3Query2);
				pstmt.setString(1, Lib3UpdatedAddress);
				pstmt.setInt(2, lib2);
				pstmt.executeUpdate();
                System.out.println("Successfully Updated");
                lib3();					
			}
			
			
		   }
		
		case(2):
		        {
			lib4();
			break;
			}
		case(3):
		             {
			lib2();
		             }
			
			
		        }
				
	}
	

	
	
	private void lib4() throws SQLException 
	{
		// TODO Auto-generated method stub
		String lib4BookIdQuery = "select book.bookId from tbl_book as book JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";
		String lib4BookTitleQuery = "select book.title from tbl_book as book JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";
		String libAuthorName4Query = "select auth.authorName from tbl_author as auth JOIN tbl_book_authors as bauth ON bauth.authorId = auth.authorId JOIN tbl_book as book ON book.bookId = bauth.bookId JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";
		//String noOfcopies = "select copies.noOfCopies from tbl_book_copies as copies JOIN tbl_book as book ON book.bookId = copies.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";                             
		
		pstmt = connect.prepareStatement(lib4BookIdQuery);
		pstmt.setInt(1, lib2);
		lib3Rs = pstmt.executeQuery();
				
		pstmt = connect.prepareStatement(lib4BookTitleQuery);
		pstmt.setInt(1, lib2);
		lib4Rs = pstmt.executeQuery();
				
		pstmt = connect.prepareStatement(libAuthorName4Query);
		pstmt.setInt(1, lib2);
		lib5Rs = pstmt.executeQuery();
		
//		pstmt = connect.prepareStatement(noOfcopies);
//		pstmt.setInt(1, lib2);
//		
		
		bookId =1;
		 while(lib3Rs.next() && lib4Rs.next() && lib5Rs.next())
			    {   
			        System.out.print(lib3Rs.getInt("bookId")+" ");
			    	System.out.print(lib4Rs.getString("title")+" by ");
			    	System.out.println(lib5Rs.getString("authorName"));
			    	bookId = bookId + 1;
			    } 
		 System.out.println(bookId+")Quit to cancel operation");
		 Scanner sc = new Scanner(System.in);
		 noOfcopies =sc.nextInt();
		 if(noOfcopies == bookId)
		   {
			lib3();
		   }
		    lib5();
	}	
		


	public void lib5() throws SQLException 
	{
		// TODO Auto-generated method stub
		
		String lib5Query1 = "select copies.noOfCopies from tbl_book_copies as copies JOIN tbl_book as book ON book.bookId = copies.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ? and book.bookId = ?";
		pstmt = connect.prepareStatement(lib5Query1);
		pstmt.setInt(1, lib2);
		pstmt.setInt(2, noOfcopies);
		lib6Rs = pstmt.executeQuery();
		int x = 0;
		 while(lib6Rs.next())
		    {   
		        System.out.println("the number of copies are "+lib6Rs.getInt("noOfCopies"));
		        x = lib6Rs.getInt("noOfCopies");
		    } 
		 System.out.println("enter new number of copies to add");  
		 Scanner sc = new Scanner(System.in);
         int newCopies = sc.nextInt();
         newCopies = newCopies + x;
         String newCopiesQuery = "update tbl_book_copies set noOfCopies = ? where bookId = ? ";
         pstmt = connect.prepareStatement(newCopiesQuery);
         pstmt.setInt(1,newCopies);
         pstmt.setInt(2, noOfcopies);
         pstmt.executeUpdate();   
         lib3();

	}


	public static void main(String args[]) throws SQLException
	{
		LMS1 obj = new LMS1();
		obj.mainMenu();
	}
	
	
}
