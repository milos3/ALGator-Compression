
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lzw.LzwInputStream;
import lzw.LzwOutputStream;

public class LzwAlgorithm extends CompressionAbsAlgorithm {
	private int buffer_size = 8192;
	
        @Override
	public void compress(String in, String out) {
		try {
	        InputStream is = new FileInputStream(in);
	        OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
	        LzwOutputStream los = new LzwOutputStream(9, 21, os);
	        
			byte[] buffer = new byte[buffer_size];
			int bytesRead;
			while((bytesRead = is.read (buffer)) != -1) {
				los.write(buffer, 0, bytesRead);
			}
			los.finish();
			is.close();
			los.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
        @Override
	public void decompress(String in, String out) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
			LzwInputStream lis = new LzwInputStream(9, 21, is);
			OutputStream os = new FileOutputStream(out);

			byte[] buffer = new byte[buffer_size];
			int bytesRead;
			while((bytesRead = lis.read (buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			lis.close();
			os.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
