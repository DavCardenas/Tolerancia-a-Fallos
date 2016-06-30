//Clase que representa a cada nodo de la red.

package logic;


public class Node implements Runnable{

	private int portListen;//Puerto por el que el nodo esta destinado a escuchar
	private int portConnect; //Puerto por el que nodo se conectará
	private String hostConnect; //IP a la cual se conectará el nodo
	private ListenServerRunnable listenServerRunnable;
	private ConnectClientRunnable connectClientRunnable;
	private GenerateMessageClientRunnable generateMessageClientRunnable;
	private GenerateMessageServerRunnable generateMessageServerRunnable;
	private ServerNode server;
	private ClientNode client;
	private boolean flag;
	private boolean run; //true si debe replciar

	public Node(int portListen, int portConnect, String hostConnect, boolean run){
		this.portListen = portListen;
		this.portConnect = portConnect;
		this.hostConnect = hostConnect;
		this.run = run;
		this.server = new ServerNode(this.portListen);
		this.client = new ClientNode(this.hostConnect, this.portConnect);
		this.listenServer();
		this.connectClient();
		if(this.run){
			//eleccion del sentido
			//this.generateMessageClient();
			this.generateMessageServer();
		}
		this.createMyThread();
	}

	//metodo para hilo de escucha como servidor por parte del nodo
	private void listenServer(){
		this.listenServerRunnable = new ListenServerRunnable(this.server);
		Thread listenServerThread = new Thread(this.listenServerRunnable);
		listenServerThread.start();
	}

	private void connectClient(){
		this.connectClientRunnable = new ConnectClientRunnable(this.client);
		Thread connectClientThread = new Thread(this.connectClientRunnable);
		connectClientThread.start();
	}

	private void generateMessageClient(){
		this.generateMessageClientRunnable = new GenerateMessageClientRunnable(this.server);
		Thread generateMessageClientThread = new Thread(this.generateMessageClientRunnable);
		generateMessageClientThread.start();
	}
	
	private void generateMessageServer(){
		this.generateMessageServerRunnable = new GenerateMessageServerRunnable(this.client);
		Thread generateMessageServerThread = new Thread(this.generateMessageServerRunnable);
		generateMessageServerThread.start();
	}

	public void createMyThread(){
		this.flag = true;
		Thread myThread = new Thread(this);
		myThread.start();
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public void runGenerateMessageClient(){
		this.generateMessageClientRunnable.setFlag(true);
	}

	public void stopGenerateMessageClient(){
		this.generateMessageClientRunnable.setFlag(false);
	}
	
	public void runGenerateMessageServer(){
		this.generateMessageServerRunnable.setFlag(true);
	}

	public void stopGenerateMessageServer(){
		this.generateMessageServerRunnable.setFlag(false);
	}

	private void sendMessageClient(Message message){
		this.connectClientRunnable.sendMessage(message);
	}
	
	private void sendMessageServer(Message message){
		this.listenServerRunnable.sendMessage(message);
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
					//eleccion de quien genera el mensaje
					//this.runGenerateMessageClient();
					this.runGenerateMessageServer();
				int contGenerateServer = 0;
				int contGenerateClient = 0;
				while(this.flag){
					//condicional para saber la cola de quien se esta llenando en este caso la del servidor
					if(! this.server.isEmptyArrayDeque()){
						for(int i=0; i<this.server.getSizeArrayDeque(); i++){
							if(this.run){								
								if(contGenerateServer>0){
									System.out.println("El mensjae ha vuelto a quien lo genero y esta genenrando un nuevo");
									this.runGenerateMessageClient();
								}
								this.sendMessageClient(this.server.getMessageArrayDeque());
								System.out.println("enviando el mensaje genenrado "+contGenerateServer);
								contGenerateServer += 1;
							} else {
								System.out.println("Enviando mensaje desde la replicacion de un nodo");
								this.sendMessageClient(this.server.getMessageArrayDeque());
							}
						}
					}
					//condicion para verificar la cola del client, conociendo si llego un mensaje a esa parte del nodo
					if(! this.client.isEmptyArrayDeque()){
						for (int i = 0; i < this.client.getSizeArrayDeque(); i++) {
							if(this.run){
								if(contGenerateClient>0){
									System.out.println("El mensaje ha vuelto a quien lo genero y esta genenrando uno nuevo");
									this.runGenerateMessageServer();
								}
								this.sendMessageServer(this.client.getMessageArrayDeque());
								System.out.println("enviando el mensaje genenrado "+contGenerateServer);
								contGenerateServer += 1;
							} else {
								System.out.println("Enviando mensaje desde la replicacion de un nodo");
								this.sendMessageServer(this.server.getMessageArrayDeque());
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
