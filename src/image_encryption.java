import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 2017/3/21.
 */
public class image_encryption {

    public static int[] getRGB(String path) throws IOException{
         int [] array_Image;
        File file= new File(path);
        BufferedImage image=ImageIO.read(file);
        array_Image=new int[image.getHeight()*image.getWidth()];

        for(int i=0;i<image.getWidth();i++){
            for(int j =0;j<image.getHeight();j++){
                final int color=image.getRGB(i,j);
                final int r=(color >> 16) & 0xff;
                array_Image[j*image.getWidth()+i]=r;
//                System.out.println(r);
//                array_Image[(j*image.getWidth()+i)*3+0]=r;

//                System.out.print(r);
//                final int g=(color >> 8) & 0xff;
//                array_Image[(j*image.getWidth()+i)*3+1]=g;
//                System.out.print(""+g);
//                final int b=color &0xff;
//                array_Image[(j*image.getWidth()+i)*3+2]=b;
//                System.out.print(""+b);
//                System.out.println(" ");
            }

        }

         return array_Image;
    }

    public static void main(String [] args) throws IOException {
//        image_encryption ie=new image_encryption();
//        int [] a=ie.getRGB(System.getProperty("user.dir")+"/src/gray-lena.bmp");
//        a.toString();
//        System.out.print(a);

    }

}
