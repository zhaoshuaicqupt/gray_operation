import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 2017/3/18.
 */
public class gray_operation {

    public static int colorToRGB(int alpha, int red, int green, int bule) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += bule;
        return newPixel;
    }

    public void grayImage() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/pic/24-lena.bmp");
        BufferedImage image = ImageIO.read(file);

        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayImage.setRGB(i, j, newPixel);
            }
        }
        File newFile = new File(System.getProperty("user.dir") + "/pic/gray.bmp");
        ImageIO.write(grayImage, "bmp", newFile);
    }

    public static void main(String[] args) throws IOException {
        gray_operation demo = new gray_operation();
        try {
            demo.grayImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        File file=new File(System.getProperty("user.dir")+"/src/lena2.bmp");
//        BufferedImage image= ImageIO.read(file);
//        for(int i=0;i<image.getHeight();i++){
//            for(int j=0;j<image.getWidth();j++){
//                final int color=image.getRGB(i,j);
//                final int r=(color >> 16) & 0xff;
//                System.out.print(r+" ");
//            }
//            System.out.println (" ");
//        }
//
    }

}
