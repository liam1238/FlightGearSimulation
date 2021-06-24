package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public class Socket {

	private java.net.Socket socket;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	
	public Socket(java.net.Socket socket) {
		this.socket = socket;
		try {
			input = new DataInputStream(this.socket.getInputStream());
			out = new DataOutputStream(this.socket.getOutputStream());
		} catch(IOException e) { e.printStackTrace(); }
	}

	public Socket(String ip, int port) throws IOException { this(new java.net.Socket(ip, port)); }
	
	//constructor for server connection. waits until connected to the flight gear
	public Socket(int port) throws IOException {
		this((new ServerSocket(port)).accept());
	}
	
	public void writeLine(String data) {
		try {
			if (data.charAt(data.length()-1) != '\n') //drop one line if there isn't one
				data += '\n';
			out.write(data.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {e.printStackTrace(); }
	}

	public String readline() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return "";
		}
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

}
