import java.io.IOException;
import java.net.*;

public class RcClient {
	private DatagramSocket sock;
	private DatagramPacket out;
	private int speed = 0;
	private int angle = 0;
	InetAddress host;
	String welcomeMessage;

	public RcClient(String ip, int port) {
		welcomeMessage = new String("Connection created!!!");
		try {
			host = InetAddress.getByName(ip);
			System.out.println("Host:" + ip + " Port:" + port);
			sock = new DatagramSocket();
			sock.connect(host, port);
			out = new DatagramPacket(welcomeMessage.getBytes(),
					welcomeMessage.length(), host, port);
			sock.send(out);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void send(String income) throws IOException {

		String[] in = income.split(":");
		switch (in[0]) {
		case "Pressed":
			switch (in[1]) {
			case "W":
				speed = 5;
				break;
			case "S":
				speed = -5;
				break;
			case "A":
				angle = -12;
				break;
			case "D":
				angle = 12;
				break;
			}
			break;
		case "Released":
			switch (in[1]) {
			case "W":
				speed = 0;
				break;
			case "S":
				speed = 0;
				break;
			case "A":
				angle = 0;
				break;
			case "D":
				angle = 0;
				break;
			}
			break;
		case "Button":
			switch (in[1]) {
			case "Up":
				break;
			case "Down":
				break;
			case "Left":
				break;
			case "Right":
				break;
			}
			break;

		}
		out.setData(new String(speed + "+" + angle + ".").getBytes());

		
		sock.send(out);

		
	}

	public void reconnect(String ip, int port) throws IOException {
		host = InetAddress.getByName(ip);
		out.setAddress(host);
		out.setPort(port);
		sock.connect(host, port);
		out.setData(welcomeMessage.getBytes());
		sock.send(out);
	}

	public void end() throws IOException {
		out.setData(new String("end").getBytes());
		sock.send(out);
		sock.disconnect();
	}

}
