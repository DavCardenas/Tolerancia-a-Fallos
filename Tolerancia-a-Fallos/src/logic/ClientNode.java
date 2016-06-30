//Clase que controla el cliente de cada nodo

package logic;

import java.net.*;
import java.io.*;

//import java.net.InetAddress.*;
//import java.net.ServerSocket.*;
//import java.net.Socket.*;
public class ClientNode {

	private int port;
	private String host;
	private Socket clientSocket;
	private InetAddress ipClientSocket;
	private ObjectInputStream inputObject;
	private ObjectOutputStream outputObject;
	private int intents;
	private Message message;
	private ArrayDequeMessage arrayDequeMessage;

	public ClientNode(String host, int port){
		this.host = host;
		this.port = port;
		this.ipClientSocket = null;
		this.intents = 0;
		this.arrayDequeMessage = new ArrayDequeMessage();
	}

	public void connect(){
		try {
			this.clientSocket = new Socket(this.host, this.port);
			this.inputObject = new ObjectInputStream(this.clientSocket.getInputStream());
			this.outputObject = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			this.intents +=1;
			try{
				Thread.sleep(3000);
			} catch (InterruptedException e2){
				System.out.println(e2.getMessage());
			}
			System.out.println("Lo mando de nuevo a conectar, intento: "+this.intents);
			this.connect();
			System.out.println(e.getMessage());
		}
	}

	public void close(){
		try{
		this.clientSocket.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public boolean addMessage(Message message){
		return	this.arrayDequeMessage.addMessage(message);
	}

	public Message getMessageArrayDeque(){
		return this.arrayDequeMessage.getMessage();
	}

	public boolean isEmptyArrayDeque(){
		return this.arrayDequeMessage.isEmpty();
	}

	public int getSizeArrayDeque(){
		return this.arrayDequeMessage.getSize();
	}

	public boolean readMessage(){
		try{
		this.message = (Message) this.inputObject.readObject();
		System.out.println("El mensaje leído es: "+this.message.getMessageAll());
		return this.arrayDequeMessage.addMessage(this.message);
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return false;
	}

	public void sendMessage(Message message){
		try{
		this.outputObject.writeObject(message);
		//this.outputObject.close();
		} catch (IOException e){
			this.connect();
			System.out.println(e.getMessage());
		}
	}

	public void closeOutputObject(){
		try{
		this.outputObject.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public InputStream getInputStream(){
		try {
		return this.clientSocket.getInputStream();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public OutputStream getOutputStream(){
		try{
		return this.clientSocket.getOutputStream();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public InetAddress getIpMySock(){
		this.ipClientSocket = this.clientSocket.getInetAddress();
		return this.ipClientSocket;
	}
	
	public void closeInputObject(){
		try{
		this.inputObject.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}
