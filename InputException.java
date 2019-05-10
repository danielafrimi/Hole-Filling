/**
 * This class represent an Input Exception
 */
public class InputException extends Exception
{

    private static final String INPUT_ERR_MSG = "Error - Invalid input";

    /**
     * Constructor
     */
    public InputException()
    {
        super(INPUT_ERR_MSG);
    }

}
