package com.agile.datastore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;


@SuppressWarnings("serial")
public class StoreServlet extends HttpServlet{

	public static final long LIMIT_MILLIS = 1000 * 25;
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		PrintWriter out=response.getWriter();
		out.println("entering");
		
		int cusid=Integer.parseInt(request.getParameter("cusid"));
		String cusname=request.getParameter("cusname");
		String place=request.getParameter("place");
		
		
		CustomerPojo cuspojo=new CustomerPojo();
		cuspojo.setCusid(cusid);
		cuspojo.setCusname(cusname);
		cuspojo.setPlace(place);
     try
     {
		ObjectifyService.register(CustomerPojo.class);//register the entity
		Objectify ofy = ObjectifyService.begin();
		ofy.put(cuspojo);
	Query<CustomerPojo> car = ofy.query(CustomerPojo.class).limit(5);
	String cursorStr = request.getParameter("cursor");
	if (cursorStr != null)
        car.startCursor(Cursor.fromWebSafeString(cursorStr));
	List<CustomerPojo> result = new ArrayList<CustomerPojo>();
	QueryResultIterator<CustomerPojo> iterator = car.iterator();
	while (iterator.hasNext()) {
        CustomerPojo theme = iterator.next();
        result.add(theme);
	
		
		 Gson gson = new Gson();
	
		 String jsonCartList = gson.toJson(result);
	
		 out.print(jsonCartList);
		 
     }
	Cursor cursor=iterator.getCursor();
	System.out.println(cursor.toWebSafeString());
     }
    
     catch(Exception e)
     {
    	 
     }
		 

	}
}
		 
		 
		
		
