/**
 * This class represent a default weight function, which calculate the weight between two pixels
 */
public class defaultWeightFunc extends weightFunction
{

    private float z;
    private float epsilon;

    /**
     * Constructor
     * @param z - power Coefficient of the euclidean distance between two pixels.
     * @param eps - epsilon
     */
    public defaultWeightFunc(float z, float eps) throws weightException {
        if (z <= 0 || eps <= 0)
            throw new weightException();

        this.epsilon = eps;
        this.z = z;
    }

    /**
     * Calculate the weight between two pixels according to this formula (1/ ||u-v||^z + epsilon)
     * @param pixel1 - the first pixel
     * @param pixel2 - the second pixel
     * @return the weight between two pixels
     */
    public double calcWeight(Pixel pixel1, Pixel pixel2)
    {
        double dist = calcDistance(pixel1, pixel2);
        double denominator = Math.pow(dist, this.z) + this.epsilon;
        return (1/(denominator));
    }

    /**
     * Getter for z
     * @return z
     */
    public float getZ() {
        return this.z;
    }

    /**
     *  Getter for epsilon
     * @return epsilon
     */
    public float getEpsilon() {
        return this.epsilon;
    }

    /**
     *  Calculate the euclidean distance between two pixels (||u-v||)
     * @param pixel1 - the first pixel
     * @param pixel2 - the second pixel
     * @return the distance
     */
    private double calcDistance(Pixel pixel1, Pixel pixel2)
    {
        // Substract the cooridnates of the pixels
        double y_coor = Math.abs (pixel1.getCor_y() - pixel2.getCor_y());
        double x_coor = Math.abs (pixel1.getCor_x() - pixel2.getCor_x());
        return Math.sqrt(Math.pow(y_coor,2) + Math.pow(x_coor,2));
    }



}
