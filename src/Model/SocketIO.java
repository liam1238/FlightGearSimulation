package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SocketIO {

	private Socket socket;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	
	public SocketIO(Socket socket) {
		this.socket = socket;
		try {
			input = new DataInputStream(this.socket.getInputStream());
			out = new DataOutputStream(this.socket.getOutputStream());
		}
		catch(UnknownHostException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
	}

	public SocketIO(String ip, int port) throws IOException {
		this(new Socket(ip, port));
	}
	
	//constructor for server connection. waits until connected to the flight gear
	public SocketIO(int port) throws IOException {
		this((new ServerSocket(port)).accept());
	}
	
	public void writeln(String data) {
		try {
			if (data.charAt(data.length()-1) != '\n') //drop a line if there isn't one
				data += '\n';
			out.write(data.getBytes(StandardCharsets.UTF_8));
		} catch (UnsupportedEncodingException e) {e.printStackTrace(); }
		catch (IOException e) {e.printStackTrace(); }
	}
	
	public String readline() {
		try {
			return input.readLine();
		} catch (IOException e) { e.printStackTrace(); return ""; }
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

}
