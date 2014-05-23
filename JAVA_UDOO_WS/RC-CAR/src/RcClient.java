import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class RcClient {
	private Socket sock;
	private InputStream in;
	private OutputStream out;
	private int speed=0;
	private int angle=0;

	public RcClient(String ip,int port){
		try {
			sock = new Socket(ip,port);    
			in = sock.getInputStream();  
			out = sock.getOutputStream();   
			out.write(new String("Connection from"+sock.getPort()+"created!!!").getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void send(String income) throws IOException{
		
		String[] in=income.split(":");
		switch (in[0]){
			case "Pressed":
				switch(in[1]){
					case "W":speed=6;break;
					case "S":speed=-6;break;
					case "A":angle=-10;break;
					case "D":angle=10;break;
				}
				break;
			case "Released":
				switch(in[1]){
					case "W":speed=0;break;
					case "S":speed=0;break;
					case "A":angle=0;break;
					case "D":angle=0;break;
				}
				break;
			case "Button":
				switch(in[1]){
					case "Up":break;
					case "Down":break;
					case "Left":break;
					case "Right":break;
				}
				break;
		
		}
		out.write(new String(speed+"+"+angle+".").getBytes());  
	}
	
	
	
	public void end() throws IOException{
		out.write(new String("end").getBytes());
		sock.close();
	}

}