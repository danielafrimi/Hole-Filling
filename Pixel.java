public class Pixel
{
    private int cor_x;
    private int cor_y;
    private float intensity;

    /**
     * Constructor
     * @param x - row
     * @param y - col
     */
    public Pixel(int x, int y, float intensity) throws InvalidPixelIntensity {
        this.cor_x = x;
        this.cor_y = y;
        if((intensity < 0 || intensity > 1) && intensity != -1)
            throw new InvalidPixelIntensity();
        this.intensity = intensity;
    }

    /**
     * Getter for column coordinate
     * @return col
     */
    public int getCor_y() {
        return this.cor_y;
    }

    /**
     * Getter for row coordinate
     * @return row
     */
    public int getCor_x() {
        return this.cor_x;
    }

    /**
     * Getter for intensity
     * @return intensity (value) of the pixel
     */
    public float getIntensity() {
        return this.intensity;
    }

    /**
     * Setter for intensity
     * @param intensity - value of the pixel
     */
    public void setIntensity(float intensity) throws InvalidPixelIntensity {
        if(intensity < 0 | intensity > 1)
            throw new InvalidPixelIntensity();
        this.intensity = intensity;
    }
}
