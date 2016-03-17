

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nayuki.huffmancoding.AdaptiveHuffmanCompress;
import nayuki.huffmancoding.AdaptiveHuffmanDecompress;
import nayuki.huffmancoding.BitInputStream;
import nayuki.huffmancoding.BitOutputStream;

public class HuffmanAlgorithm extends CompressionAbsAlgorithm {
	private int buffer_size = 8192;
	
        @Override
	public void compress(String in, String out) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(in), buffer_size);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(out), buffer_size);
			BitOutputStream bos = new BitOutputStream(os);
			
			AdaptiveHuffmanCompress.compress(is, bos);

			is.close();
			bos.close();
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
			
			BitInputStream bis = new BitInputStream(is);
			AdaptiveHuffmanDecompress.decompress(bis, os);
			
			os.close();
			bis.close();
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
