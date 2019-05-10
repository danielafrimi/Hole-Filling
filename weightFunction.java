/**
 * Abstract class that represent a Weight Function - weighting function which assigns a non-negative float
 * weight to a pair of two pixel coordinates in the image.
 */
public abstract class weightFunction
{
    /**
     * Calculate the weight between two pixels according to this formula (1/ ||u-v||^z + epsilon)
     * @param pixel1 - the first pixel
     * @param pixel2 - the second pixel
     * @return the weight between two pixels
     */
    public abstract double calcWeight(Pixel pixel1, Pixel pixel2);

}
