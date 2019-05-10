/**
 * This class represent an Invalid Pixel intensity Exception
 */
public class InvalidPixelIntensity extends Exception
{

    private static final String INTENSITY_ERR_MSG = "Error - The value of the pixel isnt between [0,1]";
    /**
     * Constructor
     */
    public InvalidPixelIntensity()
    {
        super(INTENSITY_ERR_MSG);
    }
}
