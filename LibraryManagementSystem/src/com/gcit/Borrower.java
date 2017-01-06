package com.gcit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import com.gcit.LMS1;


public class Borrower {
   
	 private PreparedStatement pstmt;
	 ResultSet borr1Rs;
	 ResultSet lib2Rs;
	 ResultSet lib3Rs;
	 ResultSet lib4Rs;
	 ResultSet lib5Rs;
	 ResultSet dateRs;
	 ResultSet libbookRs;
	 ResultSet ReturnRs;
	 int borrCardId;
	 int borr1;
	 int borr2;
	 int lib2;
	 int lib3;
	 int cardNo = 1;
	 int branchId1 = 1;
	 int branchId = 1;
	 int bookId = 1;
	 Date date;
	 
	  private String db ="jdbc:mysql://localhost/library";
	  private String user = "root";
	  private String pass = "gandalf325";
	  private Connection connect2 = null;
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
	 
		
		public void borr0() throws SQLException
		{
			
		connect2 = openConnection(db,user,pass);
		System.out.println("you are in borrower class");	
		System.out.println("Enter the your Card Number: ");
		Scanner sc = new Scanner(System.in);
		borrCardId =sc.nextInt();
	    LMS1 obj = new LMS1();
		String lib2Query = "select * from tbl_borrower where cardNo = ?"; 
		pstmt = connect2.prepareStatement(lib2Query);
		pstmt.setInt(1, borrCardId);
		borr1Rs = pstmt.executeQuery();	
		
		while(borr1Rs.next())
		   {
			try {
				borr1();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			}	  
		System.out.println("invalid card\n"+"end of loop");  //end of the loop
		obj.mainMenu();
		   }
		

	public void borr1() throws SQLException, ParseException 
	{
		System.out.println("\nvalid card\n");
		
		System.out.println("1)Check out a book"
				+"\n2)Return a book"
				+"\n3)Quit to Previous");
		Scanner sc = new Scanner(System.in);
		borr1 = sc.nextInt();
	    LMS1 obj = new LMS1();
		switch(borr1)
		{
		case(1):
		    {
			System.out.println("Pick the Branch you want to check out from:");
			borrlib2();
			obj.mainMenu();
			break;
		    }
		case(2):
		    {
			System.out.println("Pick the Branch you want to Return the book:");
			borrReturnbookLiblist();
			obj.mainMenu();
			break;
		    }
			
		case(3): 
		    {
			obj.mainMenu();
			break;
		    }
		}
		
	
	}
	public void borrReturnbookLiblist() throws SQLException, ParseException
	{
    branchId1 = 1;
	String lib2Query = "select * from tbl_library_branch"; 
	pstmt = connect2.prepareStatement(lib2Query);
	lib2Rs = pstmt.executeQuery();
    
	while(lib2Rs.next())
	   {
		System.out.println("Branch Id-- "+ lib2Rs.getInt("branchId"));
		System.out.println("Branch Name-- "+ lib2Rs.getString("branchName"));
		System.out.println("Address-- "+ lib2Rs.getString("branchAddress"));
		System.out.println("-------------------------");
		System.out.println();
	branchId1 = branchId1 + 1;
	   }   
	System.out.println(branchId1+")Quit to previous");
	System.out.println("Select an Option");
	Scanner sc = new Scanner(System.in);
	lib2 =sc.nextInt();
//	branchName = lib2Rs.getString("branchName");
	if(lib2 == branchId1)
	   {
		borr1();
	   }

  returnbook();
	}

	private void returnbook() throws SQLException
	{
		// TODO Auto-generated method stub
	
		String returnbookQuery = "SELECT b.bookId , b.title from tbl_book as b JOIN tbl_book_loans as loans ON loans.bookId = b.bookId JOIN tbl_library_branch as lib ON lib.branchId = loans.branchId JOIN tbl_borrower as borr ON borr.cardNo = loans.cardNo where lib.branchId = ? and borr.cardNo = ?"; 
		pstmt = connect2.prepareStatement(returnbookQuery);
		pstmt.setInt(1, lib2);
		pstmt.setInt(2, borrCardId);
		ResultSet rbRs = pstmt.executeQuery();
		while(rbRs.next())
		{
			System.out.println("These are the books you owe-- "+ rbRs.getInt("bookId")+")"+rbRs.getString("title")); 
		}
		System.out.println("select the book you want to return");
		Scanner sc = new Scanner(System.in);
		int returnbook = sc.nextInt();
		
		pstmt = connect2.prepareStatement("update tbl_book_loans set dateIn = curdate() where bookId = ?");
		pstmt.setInt(1, returnbook);
		pstmt.executeUpdate();
		System.out.println("book returned successfully");
		
		
		String lib5Query1 = "select copies.noOfCopies from tbl_book_copies as copies JOIN tbl_book as book ON book.bookId = copies.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where book.bookId = ? and lib.branchId = ?";
		pstmt = connect2.prepareStatement(lib5Query1);
		pstmt.setInt(1, returnbook);
		pstmt.setInt(2, lib2);
		ResultSet rbRs1;
		rbRs1 = pstmt.executeQuery();
		 int updcopies = 95;
		while(rbRs1.next())
	    {   
	        System.out.println("the number of copies now are "+(rbRs1.getInt("noOfCopies")+1)+"\n");
	    updcopies = (rbRs1.getInt("noOfCopies")+1);
	    } 
		
		 String CopiesAfterReturnQuery = "update tbl_book_copies set noOfCopies = ? where bookId = ? ";
         pstmt = connect2.prepareStatement(CopiesAfterReturnQuery);
         pstmt.setInt(1,updcopies);
         pstmt.setInt(2, returnbook);
         pstmt.executeUpdate();   
		
		
	}


	public void borrlib2() throws SQLException, ParseException
	{
    branchId = 1;
	String lib2Query = "select * from tbl_library_branch"; 
	pstmt = connect2.prepareStatement(lib2Query);
	lib2Rs = pstmt.executeQuery();
    
	while(lib2Rs.next())
	   {
		System.out.println("Branch Id-- "+ lib2Rs.getInt("branchId"));
		System.out.println("Branch Name-- "+ lib2Rs.getString("branchName"));
		System.out.println("Address-- "+ lib2Rs.getString("branchAddress"));
		System.out.println("-------------------------");
		System.out.println();
	branchId = branchId + 1;
	   }
	
		// TODO Auto-generated method stub
		
	System.out.println(branchId+" Quit to previous");
	System.out.println("Select an Option");
	Scanner sc = new Scanner(System.in);
	lib2 =sc.nextInt();
//	branchName = lib2Rs.getString("branchName");
	if(lib2 == branchId)
	   {
		borr1();
	   }
	    borr1BookCopies();    
	}



	public void borr2BookLoans() throws SQLException, ParseException 
	{
		// TODO Auto-generated method stub
		String newdate = "2016-12-19";
		Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(newdate);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
		
		String bookLoansQuery = "insert into tbl_book_loans (bookId,branchId,cardNo,dateOut) values (?,?,?,curdate())"; // dateOut = '2016-12-10 00:00:00' and dueDate = '2016-12-17 00:00:00'"; 
		pstmt = connect2.prepareStatement(bookLoansQuery);
		
		pstmt.setInt(1, borr2);
		pstmt.setInt(2, lib2);
		pstmt.setInt(3, borrCardId);
		pstmt.executeUpdate();
		
		pstmt = connect2.prepareStatement("update tbl_book_loans set dueDate = ? where cardNo = ?");
		pstmt.setString(1, newdate);
		pstmt.setInt(2, borrCardId);
		pstmt.executeUpdate();
		System.out.println("book checked out successfully"
				+ "\ndueDate is 1 week from today");
		System.out.println("--------------------------------------------");
      //  System.exit(status);
	//     System.out.println("if it is updated then you will be a Gold Coast IT Java developer Aneesh Ankem");
	}


	public void borr1BookCopies() throws SQLException, ParseException 
	{
	
		String lib4BookIdQuery = "select book.bookId from tbl_book as book JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where copies.noOfCopies > 1 and lib.branchId = ?";
		String lib4BookTitleQuery = "select book.title from tbl_book as book JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";
		String libAuthorName4Query = "select auth.authorName from tbl_author as auth JOIN tbl_book_authors as bauth ON bauth.authorId = auth.authorId JOIN tbl_book as book ON book.bookId = bauth.bookId JOIN tbl_book_copies as copies ON copies.bookId = book.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";
		//String lib4Query1 = "select copies.noOfCopies from tbl_book_copies as copies JOIN tbl_book as book ON book.bookId = copies.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ?";                             
		
		pstmt = connect2.prepareStatement(lib4BookIdQuery);
		pstmt.setInt(1, lib2);
		lib3Rs = pstmt.executeQuery();
				
		pstmt = connect2.prepareStatement(lib4BookTitleQuery);
		pstmt.setInt(1, lib2);
		lib4Rs = pstmt.executeQuery();
				
		pstmt = connect2.prepareStatement(libAuthorName4Query);
		pstmt.setInt(1, lib2);
		lib5Rs = pstmt.executeQuery();
		bookId = 1;
		 while(lib3Rs.next() && lib4Rs.next() && lib5Rs.next())
			    {   
			        System.out.print(lib3Rs.getInt("bookId")+" ");
			    	System.out.print(lib4Rs.getString("title")+" by ");
			    	System.out.println(lib5Rs.getString("authorName"));
			    	bookId = bookId + 1;
			    } 
		 System.out.println(bookId+")Quit to cancel operation");
		 Scanner sc = new Scanner(System.in);
			borr2 =sc.nextInt();				
			if(borr2 == bookId)
			   {
				borr1();
			   }
		
			String DuplicateCoutquery = "select * from tbl_book_loans where bookId = ? and branchId = ? and cardNo =?"; 
			pstmt = connect2.prepareStatement(DuplicateCoutquery);
			    pstmt.setInt(1, borr2);
				pstmt.setInt(2, lib2);
				pstmt.setInt(3, borrCardId);
				ResultSet DupRs;
				DupRs = pstmt.executeQuery();
				int dupbook = 0;
				int dupbranch = 0;
				int dupcard = 0;
				while(DupRs.next())
			    {   
			        System.out.println("bookId-- "+(DupRs.getInt("bookId")));
			        System.out.println("branchId-- "+(DupRs.getInt("branchId"))); 
			        System.out.println("cardNo-- "+(DupRs.getInt("cardNo")));
			        dupbook = DupRs.getInt("bookId");
			        dupbranch = DupRs.getInt("branchId");
			        dupcard  = DupRs.getInt("cardNo");
			    } 
	         if(borr2==dupbook && lib2==dupbranch && borrCardId ==dupcard )
	         {
	        	 System.out.println("book already checked out");
	        	 borr1();
	         }
	     	String lib5Query = "select copies.noOfCopies from tbl_book_copies as copies JOIN tbl_book as book ON book.bookId = copies.bookId JOIN tbl_library_branch as lib ON lib.branchId = copies.branchId where lib.branchId = ? and book.bookId = ?";
			pstmt = connect2.prepareStatement(lib5Query);
			pstmt.setInt(1, lib2);
			pstmt.setInt(2, borr2);
			ResultSet lib6Rs;
			lib6Rs = pstmt.executeQuery();
			 int updcopies = 95;
			while(lib6Rs.next())
		    {   
		        System.out.println("the number of copies now are "+(lib6Rs.getInt("noOfCopies")-1)+"\n");
		    updcopies = (lib6Rs.getInt("noOfCopies")-1);
		    } 
			
			 String CopiesAfterCoutQuery = "update tbl_book_copies set noOfCopies = ? where bookId = ? ";
	         pstmt = connect2.prepareStatement(CopiesAfterCoutQuery);
	         pstmt.setInt(1,updcopies);
	         pstmt.setInt(2, borr2);
	         pstmt.executeUpdate();  
			    borr2BookLoans();		
	} 

	
}
