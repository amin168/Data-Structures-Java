package com.amin.model;

public class Key {
	protected int value;

	public Key(int value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return value / 10; //value 相等，hashCode相等
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != getClass()) return false;
		return ((Key) obj).value == value; //value 相等的话，就代表这两个对象相等
	}
	
	@Override
	public String toString() {
		return "v(" + value + ")";
	}
}
