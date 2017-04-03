import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 01/04/2017.
 */
public class Image_expansion {
    public static boolean Image_expansion(String path) throws IOException {
        int[] array_Image,eImage;
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        array_Image=new int[image.getHeight()*image.getWidth()];
        array_Image=new int[image.getHeight()*image.getWidth()*4];
        BufferedImage expansion_Image = new BufferedImage(image.getWidth()*2, image.getHeight()*2, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                int newPixel = colorToRGB(255, gray, gray, gray);
                array_Image[j*image.getWidth()+i]=newPixel;
                expansion_Image.setRGB(2*i,j*2,newPixel);
                expansion_Image.setRGB(2*i,j*2+1,newPixel);
                expansion_Image.setRGB(2*i+1,j*2,newPixel);
                expansion_Image.setRGB(2*i+1,j*2+1,newPixel);

            }
        }
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){

            }
        }
        File newFile = new File(System.getProperty("user.dir") + "/pic/expantion.bmp");
        ImageIO.write(expansion_Image, "bmp", newFile);
        return true;
    }

    private static int colorToRGB(int alpha, int red, int green, int bule) {
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

    public static void main(String[] args)throws IOException{
        Image_expansion(System.getProperty("user.dir")+"/pic/24-lena.bmp");

    }

}
