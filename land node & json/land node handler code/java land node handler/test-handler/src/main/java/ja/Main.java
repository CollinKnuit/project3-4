package ja;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Main {
	public static void main(String[] args) {
		Socket socket = IO.socket("http://145.24.222.151::8085");
		

		String bankCode = "FLFL";
		

		socket.on("connect", function() {
			System.out.println("connected to server");
		    socket.emit("verbonden", bankCode);
		    socket.emit("withdraw", withdraw);

		    socket.on("withdraw", function(withdrawRequest.getJSONObject("body")){
		    	System.out.println(withdrawBody);
		    })

		    socket.on("response", function(responseBody){
		    	System.out.println("Response ontvangen: ");
		    	System.out.println(responseBody);
		    })


		});
		
		
		
	}
}