import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 2017/3/18.
 */
public class ImageDemo {

    public void grayImage()throws IOException{
        File file=new File(System.getProperty("user.dir")+"/src/24-lena.bmp");
        BufferedImage image=ImageIO.read(file);

        int width=image.getWidth();
        int height=image.getHeight();

        BufferedImage grayImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                int rgb=image.getRGB(i,j);
                grayImage.setRGB(i,j,rgb);
            }
        }
        File newFile=new File("/Users/zhaoshuai/IdeaProjects/gray_operation/src/gray.bmp");
        ImageIO.write(grayImage,"bmp",newFile);
    }
    public static void main(String[] args) throws IOException{
        ImageDemo demo=new ImageDemo();
        demo.grayImage();

    }
}
