package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client {
	//private static final String IP = null;
	public static void main(String[] args) throws IOException{
		
		String fileDir = "";
		String fileName = "2.png";
	   Socket socket = null; 
	   int length = 0;
	   int sum = 0;//Client的socket
	   DataOutputStream dos = null;
	   FileInputStream fis =null;
	   System.out.println("===========");
	   
	   socket = getConnection("127.0.0.1",12350);//建立连接
	   //System.out.println(socket.getInetAddress().toString());


	   //fileName = args[0];
//	   File file= new File(fileDir+fileName);//获取文件路径
	   File file = new File("2.png");
	   try{
		   try{
			   dos= new DataOutputStream(socket.getOutputStream());
			   fis = new FileInputStream(file);
			   byte[] sendByte = new byte[1024];
			   while((length = fis.read(sendByte, 0, sendByte.length))>0){
				   dos.write(sendByte,0,length);
				   sum += length;
				   dos.flush();  
			   }
			   System.out.println(sum);
		   }finally{
			   if(dos != null){
				   dos.close();
			   }
			   if(fis != null){
				   fis.close();
			   }
			   if(socket != null ){
				   socket.close();
			   }
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	  
	   
	   
	   

		
	}
	public static boolean isRight(String[] args){
		if(args.length<3)
		{
			System.out.println("参数不合法！");
			return false;
		}else
			return true;
	}
	
	public static Socket getConnection(String IP,int port){
//		Client.main(abc);
		System.out.println("尝试连接服务器!");
		Socket socket = null;
		try{
			socket = new Socket(IP,port);
			System.out.println("服务器连接成功!");
			return socket;
			
			
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("连接失败！");
		}
		return socket;
	}
	
}
