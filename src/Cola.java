import java.util.*;
import java.io.*;

public class Cola<T> implements Serializable{
	private static final long serialVersionUID = 42396321225377L;
   
	LinkedList<T> list = new LinkedList<T>();
	
	public T dequeue(){
	  if (list.size() > 0 )
		return list.removeFirst();
	  else
	    return null;
	
	}
	public T getLast(){
	  return list.get(list.size()-1);
	}
	
	public int size(){
	  return list.size();
	}
	
	public T get(int n){
	  return list.get(n);
	}
	
	public void enqueue(T e){
	   list.addLast(e);
	}

	public String toString(){
	   StringBuilder s = new StringBuilder();
	   for( Object o : list ){
	     s.append(o + "\n");
	   }
	   return s.toString();
	}
}