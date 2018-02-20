package com.mycompany.app.model;
import java.util.*;

/*
 * Credit: Jacob Tomaw
 * Source: https://stackoverflow.com/questions/4401850/how-to-create-a-multidimensional-arraylist-in-java
 */

public class TwoDimensionalArrayList<T> extends ArrayList<ArrayList<T>> {
    
    public void addToInnerArray(int index, T element){
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }
        this.get(index).add(element);
    }

    public void addToInnerArray(int index, int index2, T element) {
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }

        ArrayList<T> inner = this.get(index);
        while (index2 >= inner.size()) {
            inner.add(null);
        }

        inner.set(index2, element);
    }
}
