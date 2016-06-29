package logic;



public class ConnectClientRunnable implements Runnable{

	private ClientNode client;
	private int portConnect;
	private String hostConnect;
	private boolean flag; //bandera de conexión
	private boolean sendMessage;
	private Message message;

	public ConnectClientRunnable(String hostConnect, int portConnect){
		this.hostConnect = hostConnect;
		this.portConnect = portConnect;
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
	this.client = new ClientNode(this.hostConnect, this.portConnect);
	this.flag = true;
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
	}
	this.client.closeOutputObject();
	this.client.close();
	}
}
