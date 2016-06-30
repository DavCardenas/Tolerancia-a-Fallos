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
	private ObjectOutputStream outputObject;
	private int intents;

	public ClientNode(String host, int port){
		this.host = host;
		this.port = port;
		this.ipClientSocket = null;
		this.intents = 0;
		this.connect();
	}

	private void connect(){
		try {
			this.clientSocket = new Socket(this.host, this.port);
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
}
