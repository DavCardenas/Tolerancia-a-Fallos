package logic;

import java.util.*;

public class ArrayDequeMessage{

	private ArrayDeque<Message> deque;

	public ArrayDequeMessage(){
		this.deque = new ArrayDeque<Message>();
	}

	public boolean addMessage(Message message){
		return this.deque.add(message);
	}

	public Message getMessage(){
		return this.deque.remove();
	}

	public void clearAll(){
		this.deque.clear();
	}

	public int getSize(){
		return this.deque.size();
	}

	public boolean isEmpty(){
		return this.deque.isEmpty();
	}

}
