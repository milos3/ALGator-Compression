package nayuki.huffmancoding;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public final class AdaptiveHuffmanCompress {
	
	public static void compress(InputStream in, BitOutputStream out) throws IOException {
		int[] initFreqs = new int[257];
		Arrays.fill(initFreqs, 1);
		
		FrequencyTable freqTable = new FrequencyTable(initFreqs);
		HuffmanEncoder enc = new HuffmanEncoder(out);
		enc.codeTree = freqTable.buildCodeTree();  // We don't need to make a canonical code since we don't transmit the code tree
		int count = 0;
		while (true) {
			int b = in.read();
			if (b == -1)
				break;
			enc.write(b);
			
			freqTable.increment(b);
			count++;
			if (count < 262144 && isPowerOf2(count) || count % 262144 == 0)  // Update code tree
				enc.codeTree = freqTable.buildCodeTree();
			if (count % 262144 == 0)  // Reset frequency table
				freqTable = new FrequencyTable(initFreqs);
		}
		enc.write(256);  // EOF
	}
	
	
	private static boolean isPowerOf2(int x) {
		return x > 0 && (x & -x) == x;
	}
	
}
