package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]) throws IOException{
		int port = 12350;
		
		String newName = "newPic";
		Socket socket;
		ServerSocket serverSocket = new ServerSocket(port);
		
		while(true){
			try{
				socket = serverSocket.accept();
				Thread workThread = new Thread(new PictureHandler(socket, newName));
				workThread.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
