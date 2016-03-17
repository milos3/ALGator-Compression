import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.colloquial.arithcode.ppm.AdaptiveUnigramModel;
import com.colloquial.arithcode.ppm.ArithCodeInputStream;
import com.colloquial.arithcode.ppm.ArithCodeOutputStream;
import static com.colloquial.io.Util.copy;

public class ArithmeticAlgorithm extends CompressionAbsAlgorithm {
	private int buffer_size = 8192;
	
        @Override
	public void compress(String in, String out) {
		try {
                        InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
                        OutputStream aos = new ArithCodeOutputStream(os, new AdaptiveUnigramModel());
                    
                        copy(is, aos);
                        
                        os.close();
                        is.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
        @Override
	public void decompress(String in, String out) {
		try {
                        InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
                        InputStream  ais = new ArithCodeInputStream(is, new AdaptiveUnigramModel());
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
                        
                        copy(ais, os);
        
                        os.close();
                        is.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
