/**
 * This class represent an Invalid Pixel Connectivity Exception
 */
public class InvalidPixelConnectivityException extends Exception
{
    private static final String ERR_MSG = "Pixel Connectivity Should Be 4 Or 8";

    /**
     * Constructor
     */
    public InvalidPixelConnectivityException()
    {
        super(ERR_MSG);
    }
}
