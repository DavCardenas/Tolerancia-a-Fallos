//Clase que controla la conexión del servidor de cada nodo

package logic;

import java.net.*;
import java.io.*;

public class ServerNode {

	private int port;
	private InetAddress ipAddress; //Ip del servidor socket
	private InetAddress ipMySock; //Ip del socket que acepta una petición (deberia ser del cliente)
	private ServerSocket serverSocket;
	private Socket mySock;
	private ObjectInputStream inputObject;
	private Message message;
	private ArrayDequeMessage arrayDequeMessage;

	public ServerNode(int port){
		this.port = port;
		this.ipAddress = null;
		this.ipMySock = null;
		this.serverSocket = null;
		this.mySock = null;
		this.arrayDequeMessage = new ArrayDequeMessage();
		this.create();
	}

	private void create(){
		try {
			this.serverSocket = new ServerSocket(this.port);
			System.out.println("Creado satisfactoriamente el servidor en el puerto: "+this.serverSocket.getLocalPort());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void accept(){
		try{
		System.out.println("Escuchando");
		this.mySock = this.serverSocket.accept();
		this.inputObject = new ObjectInputStream(this.mySock.getInputStream());
		System.out.println("Conexión exitosa");
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
		System.out.println("El mensaje le�do es: "+this.message.getMessageAll());
		return this.arrayDequeMessage.addMessage(this.message);
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
		return false;
	}

	public void closeInputObject(){
		try{
		this.inputObject.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

	public void close(){
		try{
		this.mySock.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

	public int getPort(){
		this.port = this.serverSocket.getLocalPort();
		return this.port;
	}

	public InetAddress getIpAddress(){
		this.ipAddress = this.serverSocket.getInetAddress();
		return this.ipAddress;
	}

	public InetAddress getIpMySock(){
		this.ipMySock = this.mySock.getInetAddress();
		return this.ipMySock;
	}
}
