import java.util.HashSet;
import java.util.Set;
import org.opencv.core.Mat;

/**
 * The Class represent an object that finds the Boundary & Hole Set (Set of pixels coordinates)
 */
public class SetFinder
{

    private int connectivity;

    /**
     * Constructor
     * @param connectivity - neighbors connectivity
     */
    public SetFinder(int connectivity) throws InvalidPixelConnectivityException {
        if( connectivity != 4 && connectivity != 8)
            throw new InvalidPixelConnectivityException();
        this.connectivity = connectivity;
    }

    /**
     * Getter
     * @return neighbors connectivity
     */
    public int getConnectivity() {
        return this.connectivity;
    }


    /**
     * This function checks if a specific pixels is a boundary one (according the connectivity neighbors)
     * @param im - image
     * @param coor_x - row of a pixel
     * @param coor_y - col of a pixel
     * @return true iff the pixel is Boundary pixels (A boundary pixel is defined as pixel that is
     connected to a hole pixel, but is not in the hole itself)
     */
    private boolean pixelBoundaryChecker(Mat im, int coor_x, int coor_y)
    {
        // Checks if the pixles in the cooridinates (coor_x, coor_y) isnt a hole and the neighbors pixels are inside the hole
        boolean check = im.get(coor_x,coor_y)[0] != -1 && (im.get(coor_x -1, coor_y)[0] == -1 ||
                im.get(coor_x+1, coor_y)[0] == -1 || im.get(coor_x,coor_y + 1)[0] == -1 ||
                im.get(coor_x, coor_y - 1)[0] == -1);
        if(this.connectivity == 4)
        {
            return check;
        }
        // Check for 8 connectivity
        return check && (im.get(coor_x+1, coor_y + 1)[0] == -1 || im.get(coor_x-1, coor_y - 1)[0] == -1
                || im.get(coor_x + 1, coor_y - 1)[0] == -1 || im.get(coor_x - 1, coor_y + 1)[0] == -1);

    }

    /**
     * Get the set of all the boundary pixel coordinates. A boundary pixel is defined as pixel that is
     connected to a hole pixel, but is not in the hole itself. Pixels can be either 4- or 8-connected
     * @param im - image
     * @return set of all the boundary pixel coordinates.
     */
    public Set<Pixel> getBoundarySet(Mat im) throws InvalidPixelIntensity {
        Set<Pixel> boundarySet = new HashSet<>();
        Pixel pixel;
        int cols = im.width();
        int rows = im.height();
        // Iterate all over the image and takes the boundary pixels
        for (int i = 0; i < rows -1; ++i)
        {
            for(int j = 0; j < cols -1; ++j)
            {
                if(pixelBoundaryChecker(im, i,j))
                {
                    pixel = new Pixel(i,j, (float)im.get(i,j)[0]);
                    boundarySet.add(pixel);
                }
            }
        }
        return boundarySet;
    }

    /**
     * Get the set of all the hole (missing) pixel coordinates.
     * @param im - image
     * @return set of all the hole (missing) pixel coordinates
     */
    public Set<Pixel> getHoleSet(Mat im) throws InvalidPixelIntensity {
        Set<Pixel> holeSet = new HashSet<>();
        Pixel pixel;
        int cols = im.width();
        int rows = im.height();
        // Iterate all over the image and takes the Hole pixels (intensity = -1)
        for (int i = 0; i < rows; i++)
        {
            for(int j=0; j < cols; j++)
            {
                // Checks if the value of the pixel is -1 -> indicate on a hole
                if(im.get(i,j)[0] == -1)
                {
                    pixel = new Pixel(i,j,(float)im.get(i,j)[0]);
                    holeSet.add(pixel);
                }
            }
        }
        return holeSet;
    }
}
