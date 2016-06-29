//Clase que representa a cada nodo de la red.

package logic;


public class Node implements Runnable{

	private int portListen;//Puerto por el que el nodo esta destinado a escuchar
	private int portConnect; //Puerto por el que nodo se conectará
	private String hostConnect; //IP a la cual se conectará el nodo
	private Thread listenServerThread;
	private ListenServerRunnable listenServerRunnable;
	private Thread connectClientThread;
	private ConnectClientRunnable connectClientRunnable;
	private Thread generateMessageThread;
	private GenerateMessageRunnable generateMessageRunnable;
	private ServerNode server;
	private Thread myThread;
	private boolean flag;
	private boolean run; //true si debe replciar

	public Node(int portListen, int portConnect, String hostConnect, boolean run){
		this.portListen = portListen;
		this.portConnect = portConnect;
		this.hostConnect = hostConnect;
		this.run = run;
		this.server = new ServerNode(this.portListen);
		this.listenServer();
		this.connectClient();
		if(this.run){
			this.generateMessage();
		}
		this.createMyThread();
	}

	//metodo para hilo de escucha como servidor por parte del nodo
	private void listenServer(){
		this.listenServerRunnable = new ListenServerRunnable(this.server);
		this.listenServerThread = new Thread(this.listenServerRunnable);
		this.listenServerThread.start();
	}

	private void connectClient(){
		this.connectClientRunnable = new ConnectClientRunnable(this.hostConnect, this.portConnect);
		this.connectClientThread = new Thread(this.connectClientRunnable);
		this.connectClientThread.start();
	}

	private void generateMessage(){
		this.generateMessageRunnable = new GenerateMessageRunnable(this.server);
		this.generateMessageThread = new Thread(this.generateMessageRunnable);
		this.generateMessageThread.start();
	}

	public void createMyThread(){
		this.flag = true;
		this.myThread = new Thread(this);
		this.myThread.start();
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public void runGenerateMessage(){
		this.generateMessageRunnable.setFlag(true);
	}

	public void stopGenerateMessage(){
		this.generateMessageRunnable.setFlag(false);
	}

	private void sendMessage(Message message){
		this.connectClientRunnable.sendMessage(message);
	}

	public boolean isRun(){
		return this.run;
	}

	public boolean isListenServer(){
		return this.listenServerRunnable.getFlag();
	}

	public boolean isConnectClient(){
		return this.connectClientRunnable.getFlag();
	}

	@Override
	public void run(){
		while(true){
			if(this.isConnectClient() && this.isListenServer()){
				if(this.run)
					this.runGenerateMessage();
				int contGenerate = 0;
				while(this.flag){
					if(! this.server.isEmptyArrayDeque()){
						for(int i=0; i<this.server.getSizeArrayDeque(); i++){
							if(this.run){								
								if(contGenerate>0){
									System.out.println("El mensjae ha vuelto a quien lo genero y esta genenrando un nuevo");
									this.runGenerateMessage();
								}
								this.sendMessage(this.server.getMessageArrayDeque());
								System.out.println("enviando el mensaje genenrado "+contGenerate);
								contGenerate += 1;
							} else {
								System.out.println("Enviando mensaje desde la replicacion de un nodo");
								this.sendMessage(this.server.getMessageArrayDeque());
							}
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			} else {
				System.out.println("Esperando a conectarme y a que se conceten");
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
