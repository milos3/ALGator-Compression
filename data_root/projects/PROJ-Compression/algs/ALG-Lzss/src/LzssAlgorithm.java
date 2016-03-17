
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

import org.crosswire.common.compress.Lzss;

public class LzssAlgorithm extends CompressionAbsAlgorithm {
	private int buffer_size = 8192;
	
        @Override
	public void compress(String in, String out) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
			
                        Lzss lzss = new Lzss(is);
                        ByteArrayOutputStream bos = lzss.compress();
                        
                        bos.writeTo(os);
                        bos.close();
                        is.close();
                        os.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
        @Override
	public void decompress(String in, String out) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
			
                        Lzss lzss = new Lzss(is);
                        ByteArrayOutputStream bos = lzss.uncompress();
                        
                        bos.writeTo(os);
                        bos.close();
                        is.close();
                        os.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
