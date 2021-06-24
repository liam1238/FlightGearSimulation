package Algorithms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import Algorithms.Commands.DefaultIO;
import Algorithms.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler{
	public int id = 0;
	public Socket s = null;
	
	public class SocketIO implements DefaultIO{
		private Socket socket;
		private DataInputStream input = null;
		private DataOutputStream output = null;
	    
	    public SocketIO(Socket socket) { // establish a connection
	    	this.socket = socket;
	        try {
				input = new DataInputStream(this.socket.getInputStream());
				output = new DataOutputStream(this.socket.getOutputStream());
	        } catch(IOException e) { e.printStackTrace(); }
		}
		
	    @Override
		public void write(String data) {
			try {
				output.write(data.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) { e.printStackTrace();
			}
		}
		
		@Override
		public void write(float data) {
			try {
				output.writeFloat(data);
			} catch (IOException e) { e.printStackTrace();
			}
		}
		
		@Override
		public String readText() {
			try {
				return input.readLine();
			} catch (IOException e) { e.printStackTrace(); return ""; }
	    }
		
		@Override
		public float readVal() {
			try {
				return input.readFloat();
			} catch (IOException e) { e.printStackTrace(); return -1; }
	    }
	}

	@Override
	public void start() {
		DefaultIO dio = new SocketIO(s);
		CLI cli = new CLI(dio,id);
		cli.start();
	}

	@Override
	public void generate(int cid, Socket socket) {
		id = cid;
		s = socket;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Socket getSocket() {
		return s;
	}
}
