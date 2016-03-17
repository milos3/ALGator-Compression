import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import si.fri.algotest.entities.EParameter;
import si.fri.algotest.entities.ParameterSet;
import si.fri.algotest.entities.ParameterType;
import si.fri.algotest.entities.TestCase;
import si.fri.algotest.execute.AbsAlgorithm;
import si.fri.algotest.global.ErrorStatus;

/**
 *
 * @author ...
 */
public abstract class CompressionAbsAlgorithm extends AbsAlgorithm {

    //private int buffer_size = 8192;

    String tmpFile = "tmp.zip";
    String tmp2File = "tmp.unzip";

    CompressionTestCase compTestCase;

    @Override
    public ErrorStatus init(TestCase test) {
        if (test instanceof CompressionTestCase) {
            compTestCase = (CompressionTestCase) test;
            return ErrorStatus.STATUS_OK;
        } else {
            return ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR_CANT_PERFORM_TEST, "Invalid test:" + test);
        }
    }

    @Override
    public void run() {
        compress(compTestCase.inFile, tmpFile);
	timer.next();
        decompress(tmpFile, tmp2File);
    }

    @Override
    public ParameterSet done() {

        ParameterSet result = new ParameterSet(compTestCase.getParameters());

        boolean compOK = compare(compTestCase.inFile, tmp2File);
        double compRate = compressionRate(compTestCase.inFile, tmpFile);

        // TODO: set the output parameters
        EParameter passPar = new EParameter("CompOK", "Was compression/decompression OK",
                ParameterType.STRING, compOK ? "OK" : "NOK");
        result.addParameter(passPar, true);

        EParameter crPar = new EParameter("CompRate", "Compression rate",
                ParameterType.DOUBLE, compRate);
        result.addParameter(crPar, true);

        long size = fileSize(compTestCase.inFile);
        EParameter sizePar = new EParameter("FS", "The size of file",
                ParameterType.INT, size);
        result.addParameter(sizePar, true);

        /*File f = new File(tmpFile);
        f.delete();
        File f2 = new File(tmp2File);
        f2.delete();*/

        return result;
    }

    public long fileSize(String in) {
        File file1 = new File(in);

        return file1.length();
    }

    public double compressionRate(String in, String out) {
        File file1 = new File(in);
        File file2 = new File(out);

        return (file2.length() * 100.0) / file1.length();
    }

    public boolean compare(String in, String out) {

        File fin = new File(in);
        File fout = new File(out);
        boolean equal = false;
        
        try {
            equal = FileUtils.contentEquals(fin, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /*
        if (fin.length() != fout.length()) {
            return false;
        }

        try {

            BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(in), buffer_size);
            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(out), buffer_size);

            int read;
            while ((read = bis1.read()) != -1) {
                if (read != bis2.read()) {
                    bis1.close();
                    bis2.close();

                    return false;
                }
            }

            bis1.close();
            bis2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return equal;
    }

    // TODO: define parameters for the execute method
    protected abstract void compress(String in, String out);

    protected abstract void decompress(String in, String out);
}

/*
 * A version of this class that is given to the end-user
 
 public abstract class CompAbsAlgorithm {
 // TODO: copy-paste the method signiture from above
 protected abstract void compress(String in, String out);
 protected abstract void decompress(String in, String out);
 }

 * 
 */
