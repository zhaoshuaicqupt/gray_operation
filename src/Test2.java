

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by zhaoshuai on 2017/3/23.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        image_encryption ie = new image_encryption();
        int[] a = ie.getRGB(System.getProperty("user.dir") + "/pic/embed.bmp");
        byte[] k = MD5_encryption.getMD5("123456");
        int[] e_message = RC4_encryption.RC4(a, k);
//        for(int i:e_message){System.out.println(i);}
//        System.out.print(e_message.length);
        File file = new File(System.getProperty("user.dir") + "/pic/embed.bmp");
        BufferedImage image = ImageIO.read(file);
        BufferedImage encryImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int pixel =e_message[j*image.getWidth()+i];
                int newPixel = gray_operation.colorToRGB(255, pixel, pixel, pixel);
                encryImage.setRGB(i, j, newPixel);
            }
        }
        File newFile = new File(System.getProperty("user.dir") + "/pic/D1.bmp");
        ImageIO.write(encryImage, "bmp", newFile);

    }
}
