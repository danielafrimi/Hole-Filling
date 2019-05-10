import org.opencv.core.Mat;
import java.util.Set;

/**
 * This class responsible of filling the hole in the image
 */
public class HoleFilling
{
    /**
     * This Function fills the hole in the image with relevant values
     * @param connectivity - neighbors connectivity
     * @param weight - weight function
     * @param im - Image to fix (fill the hole with relevant values)
     * @return The specific image after filling the hole
     */
    public Mat fixImage(int connectivity, weightFunction weight, Mat im) throws InvalidPixelConnectivityException, InvalidPixelIntensity {
        Mat image = im.clone();
        SetFinder finder = new SetFinder(connectivity);
        // Finds Boundary and Hole Set
        Set<Pixel> boundarySet = finder.getBoundarySet(im);
        Set<Pixel> holeSet = finder.getHoleSet(im);

        // Fill the hole
        image = fillImage(holeSet, boundarySet, image, weight);
        return image;
    }


    /**
     * This function sets new values for the hole pixels.
     * @param holeSet -  set of hole (missing) pixels coordinates.
     * @param boundarySet - set of boundary pixels coordinates. A boundary pixel is defined as pixel
     *                      that is connected to a hole pixel, but is not in the hole itself. Pixels can be
     *                      either 4- or 8-connected.
     * @param im - image with a hole
     * @return Fixed image - an image without a hole
     */
    public Mat fillImage(Set<Pixel> holeSet, Set<Pixel> boundarySet, Mat im, weightFunction weight) throws InvalidPixelIntensity {
        float numerator ;
        float denominator ;
        double sumWeights;
        float newValue;
        for (Pixel pixelHole : holeSet)
        {
            numerator = 0;
            denominator = 0;
            for (Pixel pixelBound : boundarySet)
            {
                // For each hole pixel - Sum the weight between the boundary pixels and the pixel
                // and multiply it by the intensity of each boundary pixel
                sumWeights = weight.calcWeight(pixelHole, pixelBound);
                numerator += sumWeights * im.get(pixelBound.getCor_x(),pixelBound.getCor_y())[0];
                denominator += sumWeights;
            }
            // Normalize the value
            newValue = numerator/denominator;
            pixelHole.setIntensity(newValue);
            // Set a new value for each hole pixel
            im.put(pixelHole.getCor_x(), pixelHole.getCor_y(), newValue);
        }
        return im;
    }

}
