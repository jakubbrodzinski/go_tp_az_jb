package Server.Main;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jakub on 12/2/16.
 */
public class ServerGOTest {
	@Before
	public void setUp() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int PORT = 8901;
					Socket socket = new Socket("localhost", PORT);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println("CREATE-9");
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});

	}

	@Test
	public void main() throws Exception {
		for(int i=0;i<10;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						int PORT = 8901;
						Socket socket = new Socket("localhost", PORT);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println("JOIN-1");
						System.out.println(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}