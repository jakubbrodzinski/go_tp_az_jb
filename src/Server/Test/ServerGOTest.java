package Server.Test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakub on 12/4/16.
 */
public class ServerGOTest {
	private ExecutorService service;
	private Future<String>[] task;
	@org.junit.Before
	public void setUp() throws Exception {
		service= Executors.newFixedThreadPool(5);
		task=new Future[5];
		task[0]=service.submit(new ClientDummy("CREATE-9"));
		task[1]=service.submit(new ClientDummy("JOIN-0"));
		task[2]=service.submit(new ClientDummy("JOIN-0"));
		task[3]=service.submit(new ClientDummy("CREATE-0"));
		task[4]=service.submit(new ClientDummy("JOIN-1"));
	}

	@Test
	public void main() throws Exception {
		assertEquals("BLACK-0", task[0].get());
		assertEquals("WHITE-0", task[1].get());
		//	assertEquals("FULL", task[2].get());
		//	assertEquals("BLACK-1", task[3].get());
		//	assertEquals("WHITE-1", task[4].get());
	}
}


class ClientDummy implements Callable<String>{
	String message;
	public ClientDummy(String s) {
		message = s;
	}
	public String call(){
		try{
			int PORT = 8901;
			Socket socket=new Socket("localhost",PORT);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
			out.println(message);
			String outputString=in.readLine();
			socket.close();
			return outputString;
		}catch(IOException e){
			e.printStackTrace();
		}
		return "WeirdBehaviour";
	}
}