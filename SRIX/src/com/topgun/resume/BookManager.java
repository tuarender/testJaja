package com.topgun.resume;

import java.util.ArrayList;
import java.util.List;
import com.topgun.util.DBManager;

public class BookManager 
{
	public Book get(int idJsk,int idResume,int idBook)
    {
		Book result = null;
    	String SQL=	"SELECT OTHER_BOOK FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_BOOK=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idResume);
    		db.setInt(3, idBook);
    		db.executeQuery();
    		if(db.next())
    		{
    			result = new Book();
    			result.setOtherBook(db.getString("OTHER_BOOK"));
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;
    }
    
	public List<Book> getAll(int idJsk, int idResume)
	{
		List<Book> result = new ArrayList<Book>();
		DBManager db=null;
		String SQL=	"SELECT ID_JSK,ID_RESUME,ID_BOOK,OTHER_BOOK FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?) ORDER BY ID_BOOK";
		try
		{
			db=new DBManager();
			db.createPreparedStatement(SQL);
			db.setInt(1, idJsk);
			db.setInt(2, idResume);
		    db.executeQuery();
		    while(db.next())
		    {
		    	Book book=new Book();
		    	book.setIdJsk(db.getInt("ID_JSK"));
		    	book.setIdResume(db.getInt("ID_RESUME"));
		    	book.setIdBook(db.getInt("ID_BOOK"));
		    	book.setOtherBook(db.getString("OTHER_BOOK"));
		    	result.add(book);
		    }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.close();
		}	
		return result;
	}
	
    public int add(Book book)
    {
    	int result = 0;
    	String SQL= "INSERT INTO INTER_BOOK(ID_JSK,ID_RESUME,ID_BOOK,OTHER_BOOK) VALUES(?,?,?,?) ";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, book.getIdJsk());
    		db.setInt(2, book.getIdResume());
    		db.setInt(3, book.getIdBook());
    		db.setString(4, book.getOtherBook());
    		
    		result=db.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;
    }
    
    public int delete(Book book)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?) AND (ID_BOOK=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, book.getIdJsk());
    		db.setInt(2, book.getIdResume());
    		db.setInt(3, book.getIdBook());
    		result=db.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;
    }
    
    public int deleteAll(int idJsk, int idesume)
    {
    	int result = 0;
    	String SQL=	"DELETE FROM INTER_BOOK WHERE (ID_JSK=?) AND (ID_RESUME=?)";
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setInt(1, idJsk);
    		db.setInt(2, idesume);
    		result=db.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;
    }
    
    public int update(Book book)
    {
    	int result = 0;
    	String SQL=	"UPDATE " +
    				"	INTER_BOOK " +
			    	"SET " +
			    	"	OTHER_BOOK=? " +
			    	"WHERE " +
			    	"	(ID_JSK=?) AND " +
			    	"	(ID_RESUME=?) AND " +
			    	"	(ID_BOOK=?)" ;
    	
    	DBManager db=null;
    	try
    	{
    		db=new DBManager();
    		db.createPreparedStatement(SQL);
    		db.setString(1, book.getOtherBook());
    		db.setInt(2, book.getIdJsk());
    		db.setInt(3, book.getIdResume());
    		db.setInt(4, book.getIdBook());
    		result=db.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		db.close();
    	}
    	return result;	
    }
}
