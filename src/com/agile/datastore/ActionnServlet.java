package com.agile.datastore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public class ActionnServlet extends HttpServlet {
	public static final long LIMIT_MILLIS = 1000 * 25; // provide a little leeway


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CustomerPojo cuspojo=new CustomerPojo();
	
		ObjectifyService.register(CustomerPojo.class);//register the entity
		Objectify ofy = ObjectifyService.begin();
		
		 long startTime = System.currentTimeMillis();

		    Query<CustomerPojo> query = ofy.query(CustomerPojo.class);

		    String cursorStr = request.getParameter("cursor");
		    if (cursorStr != null)
		        query.startCursor(Cursor.fromWebSafeString(cursorStr));

		    QueryResultIterator<CustomerPojo> iterator = query.iterator();
		    while (iterator.hasNext()) {
		        CustomerPojo car = iterator.next();

		        System.out.println(car.getCusid()+ "   "   +car.getCusname()+  "  " +car.getPlace());

		        if (System.currentTimeMillis() - startTime > LIMIT_MILLIS) {
		            Cursor cursor = iterator.getCursor();
		            Queue queue = QueueFactory.getDefaultQueue();
		            queue.add(url("/ActionnServlet").param("cursor", cursor.toWebSafeString()));
		            break;
		        }
		    }
		}


	private Object url(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
		

