package com.mycompany.app.model;
import java.util.*;

public class Cycle<T>{
    protected List<T> list;
    protected int currentIndex;
    
    public Cycle(List<T> list,int index){
        this.list = new ArrayList<T>(list);
        currentIndex = index;
    }
    
    protected int getIndex(){
        currentIndex = currentIndex % list.size();  
        return currentIndex;
    }
    protected int nextIndex(){
        currentIndex++;
        return getIndex();
    }
    
    public List<T> items(){
        return new ArrayList<T>(list);
    }
    
    public T removeCurrent(){
        T temp = list.remove(currentIndex);
        getIndex();
        return temp;
    }
    
    public T current(){
        return list.get(currentIndex);
    }
    
    public T next(){
        nextIndex();
        return list.get(currentIndex);
    }
    public int size(){
	    return list.size();
    }
}
