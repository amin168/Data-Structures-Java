package com.amin;

public class ArrayStack<E> implements Stack<E> {

	private ArrayList<E> array;

    public ArrayStack(int capacity){
        array = new ArrayList<>(capacity);
    }
    
    public ArrayStack(){
        array = new ArrayList<>();
    }
	
	@Override
	public int getSize() {
		return array.size;
	}

	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	@Override
	public void push(E e) {
		array.add(e);
	}

	@Override
	public E pop() {
		return array.remove(array.size() - 1);
	}

	@Override
	public E peek() {
		return array.get(array.size() - 1);
	}
	
	@Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Stack: ");
        res.append('[');
        for(int i = 0 ; i < array.size() ; i ++){
            res.append(array.get(i));
            if(i != array.size() - 1)
                res.append(", ");
        }
        res.append("] top");
        return res.toString();
    }

}
