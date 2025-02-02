package stv6.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethods;
import org.checkerframework.checker.index.qual.IndexFor;
import org.checkerframework.checker.mustcall.qual.InheritableMustCall;
import org.checkerframework.checker.mustcall.qual.MustCall;
import org.checkerframework.checker.mustcall.qual.Owning;
import stv6.http.request.Request;

@InheritableMustCall({"close"})
public class HttpSocket {
	
	private @Owning final Socket skt;
	private @Owning final BufferedReader input;
	private @Owning final PrintWriter output;

	public HttpSocket(@Owning Socket s) {
		skt = s;
		BufferedReader tempInput = null;
		PrintWriter tempOutput = null;
		try
		{
			tempInput = new BufferedReader (
				new InputStreamReader( skt.getInputStream() ));
			tempOutput = new PrintWriter( skt.getOutputStream(), true );
		} catch (IOException e) {}
		this.input = tempInput;
		this.output = tempOutput;
	}
	
	public HttpSocket(String host, int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}
	
	public void flush() {
		output.flush();
	}
	
	public boolean isLocal() {
		return skt.getInetAddress().isLoopbackAddress();
	}
	
	public Request readRequest() {		
		
		try {
			return Request.readFrom(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public String read(int length) {
		char[] cbuf = new char[ length ];
		int off = 0, read = -1;
		while (length > 0) {
			try {
				read = input.read(cbuf, off, length);
				if (read < 0)
					break; // end of stream; return what we've got
				length -= read;
				off += read;
			} catch (IOException e) {
				// return whatever we've read?
				break;
			}
		}
		
		return new String(cbuf);
	}
	
	public String readLine() throws IOException {
		if (!ready())
			return null;
		
		return input.readLine();
	}
	
	public boolean ready() {
		try {
			return input.ready();
		} catch (IOException e) {
			return false;
		}
	}

	public boolean write(HttpWritable response) {		
		response.writeHeaders( output );
		flush();
		if (response.isText())
			response.writeBody(output);
		else {
			try {
				skt.getOutputStream().write( response.getBytes() );
			} catch (IOException e) {
				System.err.println("Couldn't write bytes");
				return false;
			}
		}
		flush();
		
		return true;
	}
@EnsuresCalledMethods(value = {"this.skt", "this.input", "this.output"} , methods = "close")
public void close() {
		try{
			skt.close();
		}catch (IOException e){}
		finally{
			try{
				input.close();
			}catch (IOException e){}
			finally {
				output.close();
			}
		}

	}
	
	public static String encode(String raw) {
		try {
			return URLEncoder.encode(raw, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return raw;
		}
	}
}
