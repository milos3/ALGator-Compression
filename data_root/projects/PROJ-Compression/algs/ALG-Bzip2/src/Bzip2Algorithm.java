

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.itadaki.bzip2.BZip2InputStream;

import org.itadaki.bzip2.BZip2OutputStream;

public class Bzip2Algorithm extends CompressionAbsAlgorithm {
	private int buffer_size = 8192;
	
        @Override
	public void compress(String in, String out) {
		try {
			InputStream is = new FileInputStream(in);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
			BZip2OutputStream bos = new BZip2OutputStream(os);
			
			compressDecompress(is, bos);
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
        @Override
	public void decompress(String in, String out) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
			BZip2InputStream bis = new BZip2InputStream(is, false);
			OutputStream os = new FileOutputStream(out);
			
			compressDecompress(bis, os);
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void compressDecompress(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[buffer_size];
		int bytesRead;
		while((bytesRead = is.read (buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		is.close();
		os.close();
	}
}
