package com.gcit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Administrator
{

	
	  private String db ="jdbc:mysql://localhost/library";
	  private String user = "root";
	  private String pass = "gandalf325";
	  private Connection connect3 = null;
	  private PreparedStatement pstmt;
	  
	  
	  
	  int bId;
	  
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
	 
	  public void admin0() throws SQLException
	  {
			
			connect3 = openConnection(db,user,pass);
			LMS1 obj = new LMS1();
			System.out.println("\n you are in Administrator mode");
			System.out.println( "\n 1)Book \n 2)Author \n 3)Publisher "
					+ "\n 4)Library Branches \n 5)Borrower \n 6)Book Loan \n 7)Exit");
	  System.out.println("..................................................");
	  System.out.println("select an option");
		Scanner sc = new Scanner(System.in);
		int admin0 = Integer.parseInt(sc.nextLine());

		switch(admin0)
		{
		case(1): 
		   {
			book();
			admin0();
			break;
		   }
		case(2):
		   {
			author();
			admin0();
			break;
		   }
			
		case(3): 
		   {
			Publisher();
			admin0();
			break;
		   }
		case(4):
		   {
			LibraryBranches();
			admin0();
			break;
		   }
		
		case(5): 
		   {
			Borrower();
			admin0();
			break;
		   }
		case(6):
		   {
			BookLoan();
			admin0();
			break;
		   }
		default:
		    {
		    	obj.mainMenu();
		    }
		}
	
	  }





	public void book() throws SQLException 
	{
		// TODO Auto-generated method stub
		
		System.out.println( "\n 1)Add \n 2)Update \n 3)Delete \n 4)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			bookAdd();
			break;
		   }
		case(2):
		   {
			bookUpdate();
			break;
		   }
			
		case(3): 
		   {
			bookDelete();
			break;
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }
	}
	
	private void bookAdd() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of books available");
		String bookAddQuery1 = "select * from tbl_book"; 
		pstmt = connect3.prepareStatement(bookAddQuery1);
		ResultSet bookAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(bookAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ bookAdd1Rs.getInt("bookId"));
			System.out.println("Title-- "+ bookAdd1Rs.getString("title"));
			System.out.println("Publisher Id-- "+ bookAdd1Rs.getInt("pubId"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
	//	System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose a new book with a new book id and publisher id in order");
	System.out.println("enter new book Id");
	Scanner sc = new Scanner(System.in);
	int newbookid =sc.nextInt();
	if(newbookid < bId)
	   {
		System.out.println("book Id already exists");
		admin0();
	   }
	if(newbookid == bId )
	{	
	System.out.println("enter title");
	Scanner sc1 = new Scanner(System.in);
	String newbooktitle = sc1.nextLine();
	System.out.println("\n 1)choose between available publishers"
			+ "\n 2)add new publisher");
	int pubidcheck =sc.nextInt();
	if(pubidcheck == 2)
	{
		PublisherAdd();
	}
	if(pubidcheck == 1)
	{
	System.out.println("enter pubId");
	int newpubid =sc.nextInt();
	System.out.println("\n 1)choose between available authors"
			+ "\n 2)add new author");
	int authidcheck =sc.nextInt();
	if(authidcheck == 2)
	{
		authorAdd();
	}
	if(authidcheck == 1)
	{
	
	String bookAddQuery2 = "insert into tbl_book (bookId,title,pubId) values (?,?,?)";
	pstmt = connect3.prepareStatement(bookAddQuery2);

	pstmt.setInt(1, newbookid);
	pstmt.setString(2, newbooktitle);
	pstmt.setInt(3, newpubid);

	pstmt.executeUpdate();
	System.out.println("book added successfully");
		}}}
	
	System.out.println("bad entry");
	admin0();
	
	}

	
	private void bookUpdate() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("list of books available");
		String bookUpdateQuery1 = "select * from tbl_book"; 
		pstmt = connect3.prepareStatement(bookUpdateQuery1);
		ResultSet bookUpd1Rs = pstmt.executeQuery();
		bId = 1;
		while(bookUpd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ bookUpd1Rs.getInt("bookId"));
			System.out.println("Title-- "+ bookUpd1Rs.getString("title"));
			System.out.println("Publisher Id-- "+ bookUpd1Rs.getInt("pubId"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose an existing book with a book id and publisher id");
	System.out.println("enter selected book Id");
	Scanner sc2 = new Scanner(System.in);
	int updbookid =sc2.nextInt();
	if(updbookid >= bId)
	   {
		System.out.println("book Id does not exists");
		admin0();
	   }
	System.out.println("enter updated title");
	Scanner sc3 = new Scanner(System.in);
	String updbooktitle = sc3.nextLine();
	System.out.println("\n 1)choose between available publishers"
			+ "\n 2)add new publisher");
	int pubidcheck =sc2.nextInt();
	if(pubidcheck == 2)
	{
		PublisherAdd();
	}
	if(pubidcheck == 1)
	{
//	System.out.println("enter pubId to update");
//	int updpubid =sc.nextInt();
	String bookUpdateQuery2 = "update tbl_book set title = ? where bookId = ?";
	pstmt = connect3.prepareStatement(bookUpdateQuery2);
	pstmt.setString(1,updbooktitle);  //index is based on the number of ?
	pstmt.setInt(2, updbookid);
	pstmt.executeUpdate();
	System.out.println("Updated Successfully");
	}
	}
	
	private void bookDelete() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("list of books available");
		String bookDeleteQuery1 = "select * from tbl_book"; 
		pstmt = connect3.prepareStatement(bookDeleteQuery1);
		ResultSet bookDel1Rs = pstmt.executeQuery();
		bId = 1;
		while(bookDel1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ bookDel1Rs.getInt("bookId"));
			System.out.println("Title-- "+ bookDel1Rs.getString("title"));
			System.out.println("Publisher Id-- "+ bookDel1Rs.getInt("pubId"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose an existing book with a book id");
	System.out.println("enter selected book Id you want to delete");
	Scanner sc = new Scanner(System.in);
	int delbookid =sc.nextInt();
	if(delbookid >= bId)
	   {
		System.out.println("book Id does not exists");
		admin0();
	   }
	String bookDelQuery2 = "delete from tbl_book where bookId = ?";
	pstmt = connect3.prepareStatement(bookDelQuery2);

	pstmt.setInt(1, delbookid);
	pstmt.executeUpdate();
	System.out.println("book deleted successfully");
		
	}

	
	private void author() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println( "\n 1)Add \n 2)Update \n 3)Delete \n 4)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			authorAdd();
			break;
		   }
		case(2):
		   {
			authorUpdate();
			break;
		   }
			
		case(3): 
		   {
			authorDelete();
			break;
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }	
		
	}
	
	private void authorAdd() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of authors");
		String AuthorAddQuery1 = "select * from tbl_author"; 
		pstmt = connect3.prepareStatement(AuthorAddQuery1);
		ResultSet AuthorAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(AuthorAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ AuthorAdd1Rs.getInt("authorId"));
			System.out.println("Title-- "+ AuthorAdd1Rs.getString("authorName"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
	//	System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose a new author with a new author id in order");
	System.out.println("enter new author Id");	
	Scanner sc = new Scanner(System.in);
	int newauthorid =sc.nextInt();
	if(newauthorid < bId)
	   {
		System.out.println("author already exists");
		admin0();
	   }
	if(newauthorid == bId )
	{	
	System.out.println("enter authorName");
	Scanner sc1 = new Scanner(System.in);
	String newauthorname = sc1.nextLine();
	String authorAddQuery2 = "insert into tbl_author (authorId,authorName) values (?,?)";
	pstmt = connect3.prepareStatement(authorAddQuery2);

	pstmt.setInt(1, newauthorid);
	pstmt.setString(2, newauthorname);
	
	pstmt.executeUpdate();
	System.out.println("author added successfully");
	}
	System.out.println("bad entry");
	admin0();
	}
	
	private void authorUpdate() throws SQLException 
	{
		// TODO Auto-generated method stub
		
		System.out.println("list of authors");
		String AuthorUpdQuery1 = "select * from tbl_author"; 
		pstmt = connect3.prepareStatement(AuthorUpdQuery1);
		ResultSet AuthorAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(AuthorAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ AuthorAdd1Rs.getInt("authorId"));
			System.out.println("Title-- "+ AuthorAdd1Rs.getString("authorName"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose an existing author with a author id");
	System.out.println("enter selected author Id");
	Scanner sc = new Scanner(System.in);
	int updauthorid =sc.nextInt();
	if(updauthorid >= bId)
	   {
		System.out.println("author does not exists");
		admin0();
	   }
	System.out.println("enter updated author name");
	Scanner sc1 = new Scanner(System.in);
	String updauthorname = sc1.nextLine();
	String authorUpdateQuery2 = "update tbl_author set authorName = ? where authorId = ?";
	pstmt = connect3.prepareStatement(authorUpdateQuery2);
	pstmt.setString(1,updauthorname);  //index is based on the number of ?
	pstmt.setInt(2, updauthorid);
	pstmt.executeUpdate();
	System.out.println("Updated Successfully");
		
	}

	private void authorDelete() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of authors");
		String AuthorDelQuery1 = "select * from tbl_author"; 
		pstmt = connect3.prepareStatement(AuthorDelQuery1);
		ResultSet AuthorAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(AuthorAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Book Id-- "+ AuthorAdd1Rs.getInt("authorId"));
			System.out.println("Title-- "+ AuthorAdd1Rs.getString("authorName"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		System.out.println(bId+")Quit to cancel operation");
		System.out.println("choose an existing author with an author id");
		System.out.println("enter selected author Id you want to delete");
		Scanner sc = new Scanner(System.in);
		int delauthorid =sc.nextInt();
		if(delauthorid >= bId)
		   {
			System.out.println("author does not exists");
			admin0();
		   }
		String authorDelQuery2 = "delete from tbl_author where authorId = ?";
		pstmt = connect3.prepareStatement(authorDelQuery2);
		pstmt.setInt(1, delauthorid);
		pstmt.executeUpdate();
		System.out.println("author deleted successfully");	
	}

	private void Publisher() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println( "\n 1)Add \n 2)Update \n 3)Delete \n 4)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			PublisherAdd();
			break;
		   }
		case(2):
		   {
			PublisherUpdate();
			break;
		   }
			
		case(3): 
		   {
			PublisherDelete();
			break;
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }
		
		
	}
	
	private void PublisherAdd() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("list of publishers");
		String publisherAddQuery1 = "select * from tbl_publisher"; 
		pstmt = connect3.prepareStatement(publisherAddQuery1);
		ResultSet PublisherAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(PublisherAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("Publisher Id-- "+ PublisherAdd1Rs.getInt("publisherId"));
			System.out.println("Publisher Name-- "+ PublisherAdd1Rs.getString("publisherName"));
			System.out.println("Publisher Address-- "+ PublisherAdd1Rs.getString("publisherAddress"));
			System.out.println("Publisher Phone-- "+ PublisherAdd1Rs.getString("publisherPhone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
	//	System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose a new publisher with a new publisher id ");
	System.out.println("enter new publisher Id");	
	Scanner sc = new Scanner(System.in);
	int newpublisherid =sc.nextInt();
	if(newpublisherid < bId)
	   {
		System.out.println("publisher already exists");
		admin0();
	   }
	if(newpublisherid == bId)
	{	
	System.out.println("enter publisherName");
	Scanner sc1 = new Scanner(System.in);
	String newpublishername = sc1.nextLine();
	System.out.println("enter publisherAddress");
	Scanner sc2 = new Scanner(System.in);
	String newpublisheraddr = sc2.nextLine();
	System.out.println("enter publisherPhone");
	Scanner sc3 = new Scanner(System.in);
	String newpublisherphone = sc3.nextLine();
	String publisherAddQuery2 = "insert into tbl_publisher (publisherId,publisherName,publisherAddress,publisherPhone) values (?,?,?,?)";
	pstmt = connect3.prepareStatement(publisherAddQuery2);

	pstmt.setInt(1, newpublisherid);
	pstmt.setString(2, newpublishername);
	pstmt.setString(3, newpublisheraddr);
	pstmt.setString(4, newpublisherphone);
	
	pstmt.executeUpdate();
	System.out.println("publisher added successfully");
	}
	System.out.println("bad entry");
	admin0();
	}



	private void PublisherUpdate() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of publishers");
		String publisherUpdQuery1 = "select * from tbl_publisher"; 
		pstmt = connect3.prepareStatement(publisherUpdQuery1);
		ResultSet PublisherAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(PublisherAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			//System.out.println("Publisher Id-- "+ PublisherAdd1Rs.getInt("publisherId"));
			System.out.println("Publisher Name-- "+ PublisherAdd1Rs.getString("publisherName"));
			System.out.println("Publisher Address-- "+ PublisherAdd1Rs.getString("publisherAddress"));
			System.out.println("Publisher Phone-- "+ PublisherAdd1Rs.getString("publisherPhone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }  
		System.out.println(bId+")Quit to cancel operation");
		System.out.println("choose an existing publisher with a valid publisher id ");
		System.out.println("enter publisher Id");	
		Scanner sc = new Scanner(System.in);
		int updpublisherid =sc.nextInt();
		if(updpublisherid >= bId)
		   {
			System.out.println("publisher does not exists");
			admin0();
		   }
		System.out.println("enter publisherName");
		Scanner sc1 = new Scanner(System.in);
		String updpublishername = sc1.nextLine();
		System.out.println("enter publisherAddress");
		Scanner sc2 = new Scanner(System.in);
		String updpublisheraddr = sc2.nextLine();
		System.out.println("enter publisherPhone");
		Scanner sc3 = new Scanner(System.in);
		String updpublisherphone = sc3.nextLine();
		//String publisherUpdateQuery2 = "update tbl_publisher set publisherName = ? set publisherAddress = ? set publisherPhone = ? where publisherId = ?";
		String pubUpdQuery2 = "update tbl_publisher set publisherName = ? where publisherId = ?";
		String pubUpdQuery3 = "update tbl_publisher set publisherAddress = ? where publisherId = ?";
		String pubUpdQuery4 = "update tbl_publisher set publisherPhone = ? where publisherId = ?";
		
		pstmt = connect3.prepareStatement(pubUpdQuery2);
		pstmt.setString(1,updpublishername);  //index is based on the number of ?
		pstmt.setInt(2, updpublisherid);
		pstmt.executeUpdate();
		
		pstmt = connect3.prepareStatement(pubUpdQuery3);
		pstmt.setString(1, updpublisheraddr);
		pstmt.setInt(2, updpublisherid);
		pstmt.executeUpdate();
		
		pstmt = connect3.prepareStatement(pubUpdQuery4);
		pstmt.setString(1, updpublisherphone);
		pstmt.setInt(2, updpublisherid);
		pstmt.executeUpdate();

	    System.out.println("Successfully Updated");
			
	}

	private void PublisherDelete() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of publishers");
		String publisherDelQuery1 = "select * from tbl_publisher"; 
		pstmt = connect3.prepareStatement(publisherDelQuery1);
		ResultSet PublisherDel1Rs = pstmt.executeQuery();
		bId = 1;
		while(PublisherDel1Rs.next())
		   {
			System.out.print(bId+"**");
			//System.out.println("Publisher Id-- "+ PublisherDel1Rs.getInt("publisherId"));
			System.out.println("Publisher Name-- "+ PublisherDel1Rs.getString("publisherName"));
			System.out.println("Publisher Address-- "+ PublisherDel1Rs.getString("publisherAddress"));
			System.out.println("Publisher Phone-- "+ PublisherDel1Rs.getString("publisherPhone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }  
		System.out.println(bId+")Quit to cancel operation");
		System.out.println("choose an existing publisher with a valid publisher id ");
		System.out.println("enter new publisher Id");	
		Scanner sc = new Scanner(System.in);
		int delpubid =sc.nextInt();
		if(delpubid >= bId)
		   {
			System.out.println("publisher does not exists");
			admin0();
		   }
		String publisherDelQuery2 = "delete from tbl_publisher where publisherId = ?";
		pstmt = connect3.prepareStatement(publisherDelQuery2);

		pstmt.setInt(1, delpubid);
		pstmt.executeUpdate();
		System.out.println("publisher deleted successfully");	
	}

	private void LibraryBranches() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println( "\n 1)Add \n 2)Update \n 3)Delete \n 4)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			LibraryBranchesAdd();
			break;
		   }
		case(2):
		   {
			LibraryBranchesUpdate();
			break;
		   }
			
		case(3): 
		   {
			LibraryBranchesDelete();
			break;
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }
	}
	
	private void LibraryBranchesAdd() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of library branches");
		pstmt = connect3.prepareStatement("select * from tbl_library_branch");
		ResultSet libraryAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(libraryAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("branch Id-- "+ libraryAdd1Rs.getInt("branchId"));
			System.out.println("branch Name-- "+ libraryAdd1Rs.getString("branchName"));
			System.out.println("branch Address-- "+ libraryAdd1Rs.getString("branchAddress"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
	//	System.out.println(bId+")Quit to cancel operation");
	System.out.println("choose a new library with a new library branch id ");
	System.out.println("enter new branch Id");	
	Scanner sc = new Scanner(System.in);
	int newbranchid =sc.nextInt();
	if(newbranchid < bId)
	   {
		System.out.println("branch already exists");
		admin0();
	   }
	if(newbranchid == bId)
	{	
	System.out.println("enter branchName");
	Scanner sc1 = new Scanner(System.in);
	String newbranchname = sc1.nextLine();
	System.out.println("enter branchAddress");
	Scanner sc2 = new Scanner(System.in);
	String newbranchaddr = sc2.nextLine();
	String branchAddQuery2 = "insert into tbl_library_branch (branchId,branchName,branchAddress) values (?,?,?)";
	pstmt = connect3.prepareStatement(branchAddQuery2);

	pstmt.setInt(1, newbranchid);
	pstmt.setString(2, newbranchname);
	pstmt.setString(3, newbranchaddr);
	pstmt.executeUpdate();
	System.out.println("branch added successfully");
	}
	System.out.println("bad entry");
	admin0();
	}


	private void LibraryBranchesUpdate() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("list of library branches");
		String libraryUpdQuery1 = "select * from tbl_library_branch"; 
		pstmt = connect3.prepareStatement(libraryUpdQuery1);
		ResultSet libraryAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(libraryAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("branch Id-- "+ libraryAdd1Rs.getInt("branchId"));
			System.out.println("branch Name-- "+ libraryAdd1Rs.getString("branchName"));
			System.out.println("branch Address-- "+ libraryAdd1Rs.getString("branchAddress"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }  
	System.out.println("choose a library with an existing library branch id ");
	System.out.println("enter branch Id");	
	Scanner sc = new Scanner(System.in);
	int updbranchid =sc.nextInt();
	if(updbranchid >= bId)
	   {
		System.out.println("branch does not exist");
		admin0();
	   }
	System.out.println("enter branchName");
	Scanner sc1 = new Scanner(System.in);
	String updbranchname = sc1.nextLine();
	System.out.println("enter branchAddress");
	Scanner sc2 = new Scanner(System.in);
	String updbranchaddr = sc2.nextLine();
	String libUpdQuery2 = "update tbl_library_branch set branchName = ? where branchId = ?";
	String libUpdQuery3 = "update tbl_library_branch set branchAddress = ? where branchId = ?";
	pstmt = connect3.prepareStatement(libUpdQuery2);
	pstmt.setString(1,updbranchname);  //index is based on the number of ?
	pstmt.setInt(2, updbranchid);
	pstmt.executeUpdate();
	
	pstmt = connect3.prepareStatement(libUpdQuery3);
	pstmt.setString(1, updbranchaddr);
	pstmt.setInt(2, updbranchid);
	pstmt.executeUpdate();
	
	
    System.out.println("Successfully Updated");
	
	}
	
	private void LibraryBranchesDelete() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of library branches");
		String libraryDelQuery1 = "select * from tbl_library_branch"; 
		pstmt = connect3.prepareStatement(libraryDelQuery1);
		ResultSet libraryAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(libraryAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("branch Id-- "+ libraryAdd1Rs.getInt("branchId"));
			System.out.println("branch Name-- "+ libraryAdd1Rs.getString("branchName"));
			System.out.println("branch Address-- "+ libraryAdd1Rs.getString("branchAddress"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }  
	System.out.println("choose a library with an existing library branch id ");
	System.out.println("enter branch Id");	
	Scanner sc = new Scanner(System.in);
	int delbranchid =sc.nextInt();
	if(delbranchid >= bId)
	   {
		System.out.println("branch does not exist");
		admin0();
	   }
	String libraryDelQuery2 = "delete from tbl_library_branch where branchId = ?";
	pstmt = connect3.prepareStatement(libraryDelQuery2);

	pstmt.setInt(1, delbranchid);
	pstmt.executeUpdate();
	System.out.println("library deleted successfully");	
	}

	private void Borrower() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println( "\n 1)Add \n 2)Update \n 3)Delete \n 4)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			BorrowerAdd();
			break;
		   }
		case(2):
		   {
			BorrowerUpdate();
			break;
		   }
			
		case(3): 
		   {
			BorrowerDelete();
			break;
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }
	}

	private void BorrowerAdd() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of borrowers");
		String borrowerAddQuery1 = "select * from tbl_borrower"; 
		pstmt = connect3.prepareStatement(borrowerAddQuery1);
		ResultSet BorrowerAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(BorrowerAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("borrower Card No-- "+ BorrowerAdd1Rs.getInt("cardNo"));
			System.out.println("borrower Name-- "+ BorrowerAdd1Rs.getString("name"));
			System.out.println("borrower Address-- "+ BorrowerAdd1Rs.getString("address"));
			System.out.println("borrower Phone-- "+ BorrowerAdd1Rs.getString("phone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   } 
		//System.out.println(bId+")Quit to previous");
	System.out.println("choose a new borrower with a new borrower id ");
	System.out.println("enter new borrower Id");	
	Scanner sc = new Scanner(System.in);
	int newborrowerid =sc.nextInt();
	if(newborrowerid < bId)
	   {
		System.out.println("borrower already exists");
		admin0();
	   }
	if(newborrowerid == bId)
	{	
	System.out.println("enter borrower Name");
	Scanner sc1 = new Scanner(System.in);
	String newborrowername = sc1.nextLine();
	System.out.println("enter borrower Address");
	Scanner sc2 = new Scanner(System.in);
	String newborroweraddr = sc2.nextLine();
	System.out.println("enter borrower Phone");
	Scanner sc3 = new Scanner(System.in);
	String newborrowerphone = sc3.nextLine();
	String borrowerAddQuery2 = "insert into tbl_borrower (cardNo,name,address,phone) values (?,?,?,?)";
	pstmt = connect3.prepareStatement(borrowerAddQuery2);

	pstmt.setInt(1, newborrowerid);
	pstmt.setString(2, newborrowername);
	pstmt.setString(3, newborroweraddr);
	pstmt.setString(4, newborrowerphone);
	
	pstmt.executeUpdate();
	System.out.println("borrower added successfully");
	}
	System.out.println("bad entry");
	admin0();
	}
	

	private void BorrowerUpdate() throws SQLException 
	{
		// TODO Auto-generated method stub
		System.out.println("list of borrowers");
		String borrowerUpdQuery1 = "select * from tbl_borrower"; 
		pstmt = connect3.prepareStatement(borrowerUpdQuery1);
		ResultSet BorrowerAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(BorrowerAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("borrower Card No-- "+ BorrowerAdd1Rs.getInt("cardNo"));
			System.out.println("borrower Name-- "+ BorrowerAdd1Rs.getString("name"));
			System.out.println("borrower Address-- "+ BorrowerAdd1Rs.getString("address"));
			System.out.println("borrower Phone-- "+ BorrowerAdd1Rs.getString("phone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		System.out.println(bId+")Quit to previous");
		System.out.println("choose an existing borrower with a borrower id ");
		System.out.println("enter borrower Id");	
		Scanner sc = new Scanner(System.in);
		int updborrowerid =sc.nextInt();
		if(updborrowerid >= bId)
		   {
			System.out.println("borrower does not exists");
			admin0();
		   }
		System.out.println("enter borrower Name");
		Scanner sc1 = new Scanner(System.in);
		String updborrowername = sc1.nextLine();
		System.out.println("enter borrower Address");
		Scanner sc2 = new Scanner(System.in);
		String updborroweraddr = sc2.nextLine();
		System.out.println("enter borrower Phone");
		Scanner sc3 = new Scanner(System.in);
		String updborrowerphone = sc3.nextLine();
		
		String BorrUpdQuery2 = "update tbl_borrower set name = ? where cardNo = ?";
		String BorrUpdQuery3 = "update tbl_borrower set address = ? where cardNo = ?";
		String BorrUpdQuery4 = "update tbl_borrower set phone = ? where cardNo = ?";
		
		pstmt = connect3.prepareStatement(BorrUpdQuery2);
		pstmt.setString(1,updborrowername);  //index is based on the number of ?
		pstmt.setInt(2, updborrowerid);
		pstmt.executeUpdate();
		
		pstmt = connect3.prepareStatement(BorrUpdQuery3);
		pstmt.setString(1, updborroweraddr);
		pstmt.setInt(2, updborrowerid);
		pstmt.executeUpdate();
		
		pstmt = connect3.prepareStatement(BorrUpdQuery4);
		pstmt.setString(1, updborrowerphone);
		pstmt.setInt(2, updborrowerid);
		pstmt.executeUpdate();
		
		
	    System.out.println("Successfully Updated");
		
	}

	private void BorrowerDelete() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println("list of borrowers");
		String borrowerDelQuery1 = "select * from tbl_borrower"; 
		pstmt = connect3.prepareStatement(borrowerDelQuery1);
		ResultSet BorrowerAdd1Rs = pstmt.executeQuery();
		bId = 1;
		while(BorrowerAdd1Rs.next())
		   {
			System.out.print(bId+"**");
			System.out.println("borrower Card No-- "+ BorrowerAdd1Rs.getInt("cardNo"));
			System.out.println("borrower Name-- "+ BorrowerAdd1Rs.getString("name"));
			System.out.println("borrower Address-- "+ BorrowerAdd1Rs.getString("address"));
			System.out.println("borrower Phone-- "+ BorrowerAdd1Rs.getString("phone"));
			System.out.println("-------------------------");
			bId = bId + 1;	
		   }
		
		System.out.println(bId+")Quit to previous");

		
		System.out.println("choose an existing borrower with a borrower id ");
		System.out.println("enter borrower Id");	
		Scanner sc = new Scanner(System.in);
		int delborrowerid =sc.nextInt();

		if(delborrowerid >= bId)
		   {
			System.out.println("borrower does not exists");
			admin0();
		   }
		String borrowerDelQuery2 = "delete from tbl_borrower where cardNo = ?";
		pstmt = connect3.prepareStatement(borrowerDelQuery2);

		pstmt.setInt(1, delborrowerid);
		pstmt.executeUpdate();
		System.out.println("borrower deleted successfully");	
	}
	


	private void BookLoan() throws SQLException
	{
		// TODO Auto-generated method stub
		System.out.println( "\n 1)Over-ride Due Date for a Book Loan \n 2)Quit to previous ");
		  System.out.println("..................................................");
		  System.out.println("select an option");
			Scanner sc = new Scanner(System.in);
			int admin1 = Integer.parseInt(sc.nextLine());
		switch(admin1)
		{
		case(1): 
		   {
			
			System.out.println("list of loaners");
			String bookLoansQuery1 = "select * from tbl_book_loans"; 
			pstmt = connect3.prepareStatement(bookLoansQuery1);
			ResultSet BookLoans1Rs = pstmt.executeQuery();
			bId = 1;
			while(BookLoans1Rs.next())
			   {
				System.out.print(bId+"**");
				System.out.println("book Id-- "+ BookLoans1Rs.getInt("bookId"));
				System.out.println("library branch Id-- "+ BookLoans1Rs.getInt("branchId"));
				System.out.println("borrower Card No-- "+ BookLoans1Rs.getInt("cardNo"));
				System.out.println("date checked out-- "+ BookLoans1Rs.getString("dateOut"));
				System.out.println("due date-- "+ BookLoans1Rs.getString("dueDate"));
				System.out.println("date returned-- "+ BookLoans1Rs.getString("dateIn"));
				System.out.println("-------------------------");
				bId = bId + 1;	
			   }
			System.out.println(bId+")Quit to previous");
			System.out.println("choose an option");
			int bloans = Integer.parseInt(sc.nextLine());
			if(bloans >= bId)
			   {
				System.out.println("loaner does not exists");
				admin0();
			   }
			System.out.println("enter new due date in yyyy-MM-dd");
			String newdate = sc.next();
			
			Date utilDate;
			try {
				utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(newdate);
			    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
			   } 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			pstmt = connect3.prepareStatement("update tbl_book_loans set dueDate = ? where bookId = ?");
			pstmt.setString(1, newdate);
			pstmt.setInt(2, bloans);
			pstmt.executeUpdate();

			System.out.println("due date updated successfully");	
		   }
		default:
	    {
	    	admin0();
	    }
		
	    }
	}
	
}
