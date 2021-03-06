package logic;


public class GenerateMessageRunnable implements Runnable{

	private ServerNode server;
	private boolean flag; //nos suministrá información sobre si esta generando mensajes
	private Message message;

	public GenerateMessageRunnable(ServerNode server){
		this.server = server;
		this.flag = false;
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public boolean getFlag(){
		return this.flag;
	}

	@Override
	public void run(){
	while(true){
		while(this.flag){
			try{
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			this.message = new Message("Este es el mensaje que se replicara una vuelta entera");
			if(this.server.addMessage(this.message)){
//				System.out.println("Mensaje agregado a la cola desde el generador");
				this.flag = false;
				System.out.println("Mensaje genenrado, no se generan mas mensajes");
			} else {
				System.out.println("Error al agregar a la cola desde el generador");
			}
		}
	}
	}
}
