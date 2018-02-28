package com.mycompany.app.model;
import java.util.*;

public class Cycle<T>{
    protected List<T> list;
    protected int currentIndex;
    
    public Cycle(List<T> list,int index){
        this.list = new ArrayList<T>(list);
        currentIndex = index;
	    getIndex();
    }
    
    public Cycle(Cycle<T> c){

        this(c.list, c.currentIndex);
    }

    protected int getIndex(){
	if(list.size() <= 0)
		currentIndex = 0;
	else
		currentIndex = currentIndex % list.size();  
        return currentIndex;
    }
    protected int nextIndex(){
        currentIndex++;
        return getIndex();
    }
    
    public List<T> items(){
        List<T> temp = new ArrayList<T>();
	for(int i = currentIndex; i < currentIndex + list.size();i++){
		temp.add(list.get(i%list.size()));
	}
	return temp;
    }
    
    public T removeCurrent(){
	if(list.size() <=0)
		return null;
        T temp = list.remove(currentIndex);
        getIndex();
        return temp;
    }
    
    public T current(){
	    if(list.size()<= 0)
		    return null;
        return list.get(currentIndex);
    }
    
    public T next(){
	if(list.size() <=0)
		return null;
        nextIndex();
        return list.get(currentIndex);
    }
    public int size(){
	    return list.size();
    }
}
