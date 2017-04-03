import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 2017/3/25.
 */
public class extract {
    public static byte[] extract(String key,int s) throws IOException{
        File file= new File(System.getProperty("user.dir")+"/pic/D1.bmp");
        BufferedImage image= ImageIO.read(file);
        byte [] message=new byte[23];
        for(int i=0;i<23;i++){message[i]=0;}
        int[] rgb_image=new int [image.getWidth()*image.getHeight()];
        int[] rgb2_image=new int [image.getWidth()*image.getHeight()];
        for(int i=0;i<image.getWidth();i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                rgb_image[j * image.getWidth() + i] = r;
                rgb2_image[j * image.getWidth() + i]=r;

            }
        }
        int i;
        int j;
        double f0=0.0000,f1=0.0000;
        int [] sequence=new int[s*s];
        sequence=embed.pRandom(s,key);
        int count_block=0;
        int count_block_w=image.getWidth()/s;
        for(j=0;j<23;j++){
            for(i=0;i<8;i++){
                //全部翻转
                for(int p=s*(count_block/count_block_w);p<s+s*(count_block/count_block_w)&&p<image.getHeight();++p)
                {
                    for(int q=s*(count_block%count_block_w);q<s+s*(count_block%count_block_w)&&q<image.getWidth();++q)
                    {
                        if(sequence[p%s*s+q%s]==0)
                            rgb_image[p*image.getWidth()+q]=(rgb_image[p*image.getWidth()+q]&0xF8) + ((~rgb_image[p*image.getWidth()+q] )&0x07);
                        else
                            rgb2_image[p*image.getWidth()+q]=(rgb2_image[p*image.getWidth()+q]&0xF8) + ((~rgb2_image[p*image.getWidth()+q] )&0x07);
                    }
                }
                //波动函数计算
                for(int p=s*(count_block/count_block_w)+1;p<s+s*(count_block/count_block_w)-1;++p)
                {
                    for( int q=s*(count_block%count_block_w)+1;q<s+s*(count_block%count_block_w)-1;++q)
                    {

                        f0 =f0+Math.abs(rgb_image[p*image.getWidth()+q]-(double)(rgb_image[(p-1)*image.getWidth()+q]+rgb_image[p*image.getWidth()+q-1]+rgb_image[(p+1)*image.getWidth()+q]+rgb_image[p*image.getWidth()+q+1])/4);
                        f1 =f1+Math.abs(rgb2_image[p*image.getWidth()+q]-(double)(rgb2_image[(p-1)*image.getWidth()+q]+rgb2_image[p*image.getWidth()+q-1]+rgb2_image[(p+1)*image.getWidth()+q]+rgb2_image[p*image.getWidth()+q+1])/4);
                    }
                }
                //确定
                if(f0>f1)//表示输入的信息是1，需要将Imgdata中的S0集合再次翻转,s1集合翻转，恢复
                {

                    for(int p=s*(count_block/count_block_w);p<s+s*(count_block/count_block_w)&&p<image.getHeight();p++)
                    {
                        for(int q=s*(count_block%count_block_w);q<s+s*(count_block%count_block_w)&&q<image.getWidth();q++)
                        {
                            rgb_image[p*image.getWidth()+q]=(rgb_image[p*image.getWidth()+q]& 0xF8) + ((~rgb_image[p*image.getWidth()+q] )& 0x07);
                        }
                    }
                    message[j] +=Math.pow(2,7-(count_block%8));
                }
                f0=f1=0;
                count_block++;
            }
            }
        BufferedImage encryImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel =rgb_image[y*image.getWidth()+x];
                int newPixel = gray_operation.colorToRGB(255, pixel, pixel, pixel);
                encryImage.setRGB(x, y, newPixel);

            }
        }
        File newFile = new File(System.getProperty("user.dir") + "/pic/extract.bmp");
        ImageIO.write(encryImage, "bmp", newFile);
        return message;
        }
        public static void main(String [] args)throws IOException{
        byte[] e_message=extract("123456",32);
        System.out.println(new String(e_message,"UTF-8"));
            for(byte tmp:e_message){
                System.out.print(tmp+" ");
            }


        }

    }

