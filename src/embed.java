import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaoshuai on 2017/3/24.
 */
public class embed {
    /**
     *
     * @param path 该参数为文件的地址
     * @param message 需要嵌入图像的信息
     * @param key 信息嵌入加密密钥
     * @param s 分块的大小
     */
    public static boolean embed(String path ,String message,String key,int s)throws IOException{
        // 打开文件,并获取图像的像素数组
        File file =new File(path);
        BufferedImage image =ImageIO.read(file);
        //如果图像不存在，则返回false;
        if(image==null)
            return false;
        int[] rgb_image=new int [image.getWidth()*image.getHeight()];
        for(int i=0;i<image.getWidth();i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                final int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                rgb_image[j * image.getWidth() + i] = r;
            }
        }
        int i=image.getWidth();
        int j=image.getHeight();
        //分块
        if(message.getBytes().length>(i*j/(s*s*8))){
            System.out.print("嵌入的信息太大，请重新输入");
        }
        int[]squence=new int[s*s];
        squence=pRandom(s,key);

        //嵌入信息
        int count_block=0;
        int count_block_w=i/s;// 存储内容为：在一幅图的的横向可以放置多少个S块
        byte tmp;  // 存储内容为嵌入信息的第i 个byte
        byte[] message_byte=message.getBytes(); //注意嵌入信息类型为String，通过 String.getbyte()将String类型转为byte数组
        for(j=0;j<message_byte.length;j++){  //通过byte.length 来取出所嵌入信息的字节数
            for(i=0;i<8;i++){    // 此处意义为一个字节共有8位
                tmp=message_byte[j]; //tmp值存储为message 的第J个字节
                if(((tmp >>(7-i))& 1)==1){  // 判断tmp 的第7-i 位是否为1,如果为1 ，则对对应的块的S1的所有像素的三个最低有效位LSB进行翻转。将信息存储进去。
                    for(int p=s*(count_block/count_block_w);p<s+s*(count_block/count_block_w);p++){//当tmp 的第7-i位，为0，则将此数据通过第1块的所有S0 集合的三个最低有效位进行翻转。
                                    for(int q=s*(count_block % count_block_w);q<s+s*(count_block%count_block_w);q++){ //这个for 是用来将这个块中的所有像素进行三个最低有效位进行翻转。
                                        if(squence[p%s*s+q%s]==1){ //判断是否S0 集合
                                            rgb_image[p*image.getWidth()+q]=(rgb_image[p*image.getWidth()+q]& 0xF8)+((~rgb_image[p*image.getWidth()+q])&0x07);


                            }
                        }
                    }

                }
                else{
                    for(int p=s*(count_block/count_block_w);p<s+s*(count_block/count_block_w);p++){//当tmp 的第7-i位，为0，则将此数据通过第1块的所有S0 集合的三个最低有效位进行翻转。
                        for(int q=s*(count_block%count_block_w);q<s+s*(count_block%count_block_w);q++){ //这个for 是用来将这个块中的所有像素进行三个最低有效位江南西翻转。
                            if(squence[p%s*s+q%s]==0){ //判断是否S0 集合
                                rgb_image[p*image.getWidth()+q]=(rgb_image[p*image.getWidth()+q]& 0xf8)+((~rgb_image[p*image.getWidth()+q])&0x07);

                            }
                        }
                    }

                }
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
        File newFile = new File(System.getProperty("user.dir") + "/pic/embed.bmp");
        ImageIO.write(encryImage, "bmp", newFile);



        return true;
    }

    /**
     *
     * @param s S为分块的边长
     * @param key key 信息嵌入密钥
     * @return 返回随机序列 ，即保存在一个分块S*S中，哪些元素放入S0，哪些元素放入S1
     */
    public static int[] pRandom(int s,String key){
        int [] pRandom=new int[s*s];
        byte []md_key=MD5_encryption.getMD5(key);
//        int [] md_key =new int[key1.length];
//        for(int i =0;i<key1.length;i++){
//            md_key[i]=key1[i]&0xff;
//        }
        int j=0,i=0;
        byte temp;
        for(i=0;i <16 && i*8 <s*s;i++){
            temp= md_key[i];
            for( j=0; j<8 && j*8+i<s*s; j++){
                int constant=(temp >>(7-j))& 0x01;
                if(constant==1){
                    pRandom[i*8+j]=1;
                }else{
                    pRandom[i+j]=0;
                }
            }
        }
        for(i=128;i<s*s;i++){
            pRandom[i]=pRandom[i%128];
        }
        return pRandom;
    }
    public static void main(String args[]) throws IOException{
        embed(System.getProperty("user.dir")+"/pic/encry.bmp","zss","123456",88);
    }
}
