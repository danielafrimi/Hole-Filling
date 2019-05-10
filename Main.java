import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;


public class Main
{
    //Loading the Core library
     static  {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    private static final int IMAGE_PATH_INDEX = 0;
    private static final int Z_INDEX = 1;
    private static final int EPSILON_INDEX = 2;
    private static final int CONNECTIVITY_INDEX = 3;
    private static final int MAX_INTENSITY = 255;
    private static final int HOLE_VALUE = -1;

    public static void main(String[] args)  {
        try
        {
            // Checks if the numbers of arg are valid - Supposed to be 4 arguments:
            // input image file, z, ε and connectivity type
            if(args.length != 4)
                throw new InputException();

            //Reading the Image from the file
            Mat image = Imgcodecs.imread(args[IMAGE_PATH_INDEX]);
            Mat imGrayScale = new Mat();

            // Converting the image to gray scale as float and negative values
            Imgproc.cvtColor(image, imGrayScale, Imgproc.COLOR_RGB2GRAY);
            imGrayScale.convertTo(imGrayScale, CvType.CV_32F);

            // Normalize the image  - make sure that the intensities of the pixels are in range [0,1]
            Core.divide(imGrayScale, new Scalar((float)MAX_INTENSITY), imGrayScale);

            // Creating a hole in the image - For checks
//            imGrayScale = createHole(imGrayScale, 110, 140, 80, 100);

            // Gets Z and EPSILON for the Weight Function
            float z = Float.parseFloat(args[Z_INDEX]);
            float epsilon = Float.parseFloat(args[EPSILON_INDEX]);

            // Gets neighbors connectivity
            int connectivity = Integer.parseInt(args[CONNECTIVITY_INDEX]);

            // Process of filling the hole
            HoleFilling fixImage = new HoleFilling();
            weightFunction weight = new defaultWeightFunc(z,epsilon);
            Mat imageAfterFix = fixImage.fixImage(connectivity, weight, imGrayScale);
            saveImages(imageAfterFix, imGrayScale);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    /***
     * Makes a hole in the image (i.e puts a value of -1 in the specific rectangle)
     * @param img - Image
     * @param leftTop - left top coordinate rectangle
     * @param rightTop - right top coordinate rectangle
     * @param leftBot - left bottom coordinate rectangle
     * @param rightBot - right bottom coordinate rectangle
     * @return The specific image with a hole in it
     */
    private static Mat createHole(Mat img, int leftTop, int rightTop, int leftBot, int rightBot)  {
        for (int col = leftTop ; col <= rightTop; col++){
            for (int row = leftBot ; row <= rightBot; row++){
                img.put(row,col,HOLE_VALUE);
            }
          }
        return img;
    }

    /***
     * Save the images and cancel normalization of the images
      * @param imageAfterFix - image after filling the hole
     * @param imGrayScale - original image (in gray scale)
     */
    private static void saveImages(Mat imageAfterFix , Mat imGrayScale)
    {
        // Cancel Normalization
        Core.multiply(imageAfterFix, new Scalar(MAX_INTENSITY), imageAfterFix);
        Core.multiply(imGrayScale, new Scalar(MAX_INTENSITY), imGrayScale);

        // Save the images
        Imgcodecs.imwrite("output" + ".png", imageAfterFix);
        Imgcodecs.imwrite("input" + ".png", imGrayScale);
    }

    /***
     * Display image to screen
     * @param image - Image to display
     */
    public static void displayImage(Mat image) {
        HighGui.namedWindow("image", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("image", image);
        HighGui.waitKey();
    }
}
