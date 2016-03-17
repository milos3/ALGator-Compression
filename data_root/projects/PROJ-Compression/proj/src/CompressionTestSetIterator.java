import si.fri.algotest.entities.EParameter;
import si.fri.algotest.entities.EResultDescription;
import si.fri.algotest.entities.ETestSet;
import si.fri.algotest.entities.ParameterType;
import si.fri.algotest.entities.TestCase;
import si.fri.algotest.execute.DefaultTestSetIterator;
import si.fri.algotest.tools.ATTools;
import si.fri.algotest.global.ErrorStatus;


/**
 *
 * @author ...
 */
public class CompressionTestSetIterator extends DefaultTestSetIterator {

    @Override
    public void initIterator() {
        super.initIterator(); //To change body of generated methods, choose Tools | Templates.
    }
   
  
  
    
  @Override
  public TestCase getCurrent() {
    if (currentInputLine == null) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "No valid input!");
      return null;
    }
    
    String [] polja = currentInputLine.split(":");
    if (polja.length != 3) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "Invalid input line!");
      return null;
    }
       
    String fName = polja[1];
    
    String fType = polja[2];
    
    CompressionTestCase tCase = new CompressionTestCase();
    tCase.inFile = testSet.entity_rootdir + "/" + fName;
    
    EParameter testIDPar = EResultDescription.getTestIDParameter("Test-" + Integer.toString(lineNumber));
    tCase.addParameter(testIDPar);   
    
    EParameter parameter1 = new EParameter("FileName",  "The name of the file",  ParameterType.STRING, fName);    
    tCase.addParameter(parameter1);

    EParameter parameter2 = new EParameter("FileType",  "The type of the file",  ParameterType.STRING, fType);   
    tCase.addParameter(parameter2);
    
    return tCase;
  }
}
 