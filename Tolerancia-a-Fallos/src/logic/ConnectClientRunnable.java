package logic;



public class ConnectClientRunnable implements Runnable{

	private ClientNode client;
	private boolean flag; //bandera de conexión
	private boolean sendMessage;
	private Message message;

	public ConnectClientRunnable(ClientNode client){
		this.client = client;
		this.flag = false;
		this.sendMessage = false;
	}

	public void sendMessage(Message message){
		this.message = message;
		this.sendMessage = true;
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public void setSendMessage(boolean sendMessage){
		this.sendMessage = sendMessage;
	}
	public boolean getFlag(){
		return this.flag;
	}

	@Override
	public void run(){
	this.client.connect();
	this.flag = true;
	int cont = 0;
	while(flag){
		if(this.sendMessage){
			//try{
//				Thread.sleep(7000);
//			} catch (InterruptedException e){
//				System.out.println(e.getMessage());
//			}
			this.client.sendMessage(this.message);
			this.sendMessage=false;
		}
		if(this.client.readMessage()){
			cont += 1;
			System.out.println("Mensaje "+cont+" leido y guardado en la cola del client");
		}
	}
	this.client.closeOutputObject();
	this.client.close();
	}
}
