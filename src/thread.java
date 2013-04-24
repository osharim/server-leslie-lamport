import java.io.*;
import java.net.*;


public class thread {
 

	public DatagramSocket socket ;
	private static final int DISCOVERY_PORT = 2562;
	public Thread thread;
	int ticket;
    String id;
    
	public thread(){ // constructor
		
		this.ticket = 0;
		this.id = "";
	}
	
	
 public void runThread(){
	 
	 
		try{
			  socket = new DatagramSocket(DISCOVERY_PORT);
		      socket.setBroadcast(true);
		}catch (IOException e) {
			 
			}
		
 
		thread = new Thread(new Runnable(){
			@Override
			public void run(){
	 
				
				 try {
					
					  System.out.println("Monitoring");
					  listen_and_process_ticker(socket); 
				     
		 
				} catch (IOException e) {
					System.out.println("mal !!");
				}
 
			}
 	
		}
				
		);
		
		thread.start();
 	
 }
 
 
	/* SENDING */
	/* ********************************************************************************* */
	
	
	  private void listen_and_process_ticker(DatagramSocket socket) throws IOException {
		    
	
 
          byte[] receiveData = new byte[6024];
         // byte[] sendData = new byte[1024];
          while(true)
             {
        	  System.out.println("Server Leslie Lamport: Listening ...");
        	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        	    socket.receive(receivePacket);
        	    
                 id = new String( receivePacket.getData());
                
              
                System.out.println("Tomando turno el Mobile ID: " + id);
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
 
                ticket =  ticket +1;
                String send_ticket = String.valueOf(ticket);
                
                
                byte[] sendData = send_ticket.getBytes();
                
                System.out.println("Mobile ID: " + id+ " Tiene el Ticket : "+ticket);
                
                Doing_something(); // recurso compartido
                
                DatagramPacket newTicket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                socket.send(newTicket);
                
                System.out.println("Mobile ID: " + id+ " con el Ticket : "+ticket+" Ha dejadoe el recurso..");
                
 
             }
	  
	  
	  }
	
	
 
	  	public  synchronized void Doing_something(){
	  		
	  		System.out.println("Mobile ID: " + id+ " con el Ticket : "+ticket+" esta usando el recurso");
	  		
	  	}
 
 

}
