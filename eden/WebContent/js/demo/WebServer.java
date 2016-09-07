import java.net.*;
import java.io.*;

public class WebServer{
	public static void main(String []args) throws Throwable{
		//tcp开发：开发服务器
		//1. ServerSocket
		//2. Socket socket = ss.accept();			
		//3. IO:InputStream   OutputStream
		ServerSocket ss = new ServerSocket(50000);
		Socket socket = ss.accept();
		OutputStream os = socket.getOutputStream();
		//PrintStream ps = new PrintStream(os);
		//ps.println("test...");
		FileInputStream fis = new FileInputStream(new File("hello.html"));
		byte buffer[] = new byte[1000];
		int len = fis.read(buffer);
		os.write(buffer,0,len);
		ss.close();
		socket.close();
		os.close();
	}
}