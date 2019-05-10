/**
 * This class represent an weight Exception
 */
public class weightException extends Exception
{


    private static final String ERR_MSG = "Weight function got Invalid parameters\n";


    /**
     * Constructor
     */
    public weightException(){
        super(ERR_MSG );
    }
}
