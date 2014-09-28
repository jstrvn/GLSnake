import java.util.*;

public class Queue<T>{

  LinkedList<T> c = new LinkedList<>();
  public void push(T e){
	c.addFirst(e);
  }
  
  public T get(int n){
    return c.get(n);
  }
  
  
  public T pop(){
	return c.removeLast();
  }

  public int size(){ 
	return c.size(); 
  }
}