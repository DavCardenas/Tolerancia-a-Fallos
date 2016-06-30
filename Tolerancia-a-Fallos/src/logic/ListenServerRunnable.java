package logic;

public class ListenServerRunnable implements Runnable{

	private ServerNode server;
	private boolean flag; //mantendrá al hilo ejecutandose
	private boolean sendMessage;
	private Message message;

	public ListenServerRunnable(ServerNode server){
		this.server = server;
		this.flag = false;
		this.sendMessage = false;
	}

	public void sendMessage(Message message){
		this.message = message;
		this.sendMessage = true;
	}
	
	public void setSendMessage(boolean sendMessage){
		this.sendMessage = sendMessage;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public boolean getFlag(){
		return this.flag;
	}

	@Override
	public void run(){
	this.server.accept();
	this.flag = true; //abierto a recibir mensajes
	System.out.println("Se ha establecido una conexión con "+this.server.getIpMySock());
	int cont = 0;
	while(flag){
		if(this.server.readMessage()){
			cont += 1;
			System.out.println("Mensaje "+cont+" leido y guardado en la cola del servidor");
		}
		if(this.sendMessage){
			//try{
//				Thread.sleep(7000);
//			} catch (InterruptedException e){
//				System.out.println(e.getMessage());
//			}
			this.server.sendMessage(this.message);
			this.sendMessage=false;
		}
//		System.out.println("El mensaje "+cont+".1 es: "+message.getMessage1());
//		System.out.println("El mensaje "+cont+".2 es: "+message.getMessage2());
	}
	this.server.closeInputObject();
	this.server.closeClient();
	}
}
