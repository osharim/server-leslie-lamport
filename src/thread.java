import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
 

public class thread {
 

	public DatagramSocket socket ;
	private static final int DISCOVERY_PORT = 2562;
	public Thread thread;
	int ticket;
    String id;
      InetAddress address = null;

      byte[] ip = new byte[0];
    
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
        	  RawIPToString();
        	  
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
	  	
	  	
	  //----------------------------------------------------------------------------------------------	  	
	  //----------------------------------------------------------------------------------------------	
	  //----------------------------------------------------------------------------------------------	
	  		
	  	

	  	
	  	public  String RawIPToString(){
	  	  

	  	        try {
	  	            address = InetAddress.getLocalHost();
	  	            ip = address.getAddress();
	  	        } catch (UnknownHostException e) {
	  	            e.printStackTrace();
	  	        }

	  	        String ipAddress =  getIpAddress(ip);
	  	        System.out.println("IP Address Server = " + ipAddress);

	  	        return ipAddress;
	  	        
	  	        /*
	  	        try {
	  	            address = InetAddress.getByName("google.com");
	  	            ip = address.getAddress();
	  	        } catch (UnknownHostException e) {
	  	            e.printStackTrace();
	  	        }
	  	        ipAddress = getIpAddress(ip);
	  	        System.out.println("IP Address = " + ipAddress);
	  	        */
	  	        
	  	        
	  	    }

	  	    /**
	  	     * Convert raw IP address to string.
	  	     *
	  	     * @param rawBytes raw IP address.
	  	     * @return a string representation of the raw ip address.
	  	     */
	  	    public   String getIpAddress(byte[] rawBytes) {
	  	        int i = 4;
	  	        String ipAddress = "";
	  	        for (byte raw : rawBytes)
	  	        {
	  	            ipAddress += (raw & 0xFF);
	  	            if (--i > 0)
	  	            {
	  	                ipAddress += ".";
	  	            }
	  	        }

	  	        return ipAddress;
	  	    }
	   
	  	
	  	
	  	
//----------------------------------------------------------------------------------------------	  	
//----------------------------------------------------------------------------------------------	
//----------------------------------------------------------------------------------------------	
		
		 

	  	
	  	
	  	
}

 
 
