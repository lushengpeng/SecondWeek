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
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException{
		
	   String fileDir = "";    		//传输图片目录
	   String fileName = "2.png";	//传输文件的名称
	   String filePath = null;
	   Socket socket = null; 		//Client的socket
	   int length = 0;				//每次传输文件长度，单位字节
	   int sum = 0;					//文件大小
	   DataOutputStream dos = null;	 //写入socket中
	   FileInputStream fis =null;    //读取文件流
	   
	   
	   System.out.println("===========");
	   Scanner scan = new Scanner(System.in);
	   System.out.println("输入文件所在目录：");
	   fileDir = scan.nextLine();
	   System.out.println("输入文件名称：");
	   fileName = scan.nextLine();
	   
	   
	   File file= new File(fileDir+fileName);//获取文件路径,并且进行合法性判断。
	   if(!file.exists())
	   {
		   System.out.println("文件不存在！");
		   return ;
	   }
	   
	   socket = getConnection("172.29.32.195",12350);//建立连接
	   
	   filePath = fileDir+fileName;
	   

	   
	   try{
		   try{
			   dos= new DataOutputStream(socket.getOutputStream()); 
			   fis = new FileInputStream(file);
			   byte[] sendByte = new byte[1024];
			   while((length = fis.read(sendByte, 0, sendByte.length))>0){
				   dos.write(sendByte,0,length);
				   sum += length;
				   dos.flush();  				//立刻输出，不用等到缓冲区满
			   }
			   
			   System.out.println("文件上传成功,文件大小："+sum);
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
	
	
    /**
     *  判断参数是否合法 
     * @param  args 命令后参数
     */
	public static boolean isRight(String[] args){
		if(args.length<3)
		{
			System.out.println("参数不合法！");
			return false;
		}else
			return true;
	}
	
	
	  /**
     *  获取socket连接 
     * @param   IP 服务器地址
     * @param   port 服务端口
     *   返回一个Socket
     */
	public static Socket getConnection(String IP,int port){
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
