package Server;

public interface GlobalVarAndObjectInterface {
	
	
	
	public static int Port = 12350;									//服务器端口
	public static String newName = "newPic";						//新文件存储名称：newName＋date；
	public static final LogProxy logProxy = new LogProxy();			//日志代理
	public static LogInterface doLogProxy = (LogInterface) logProxy.bind(new LogOutput());  //日志代理绑定
	

}
