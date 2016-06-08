package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements GlobalVarAndObjectInterface{
	
	public static void main(String args[]) throws IOException{

		Socket socket;
		ServerSocket serverSocket = new ServerSocket(GlobalVarAndObjectInterface.Port);

		GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("服务启动成功，等待连接进入：");
		
		while(true){
			try{
				socket = serverSocket.accept();
				Thread workThread = new Thread(new PictureHandler(socket, GlobalVarAndObjectInterface.newName));
				workThread.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}


	}
}
