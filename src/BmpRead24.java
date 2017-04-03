import java.awt.*;
import java.io.*;


/**
 * Created by zhaoshuai on 2017/3/20.
 */
public class BmpRead24 extends javax.swing.JFrame {
    private static final long serialVersionUId=1L;
    /**
     * 位图的宽
     */
    private static int width;
    /**
     * 位图的高
     */
    private static int height;
    /**
     * 位图数据数据，即一个像素的三个分量的数据数组
     */
    private static int[][]red,green,blue;

    Graphics g;
    public void init(){
        try{
            /**
             * 通过BMP文件来创建文件输入流对象
             */
            File file=new File(System.getProperty("user.dir")+"/src/gray-lena.bmp");
            FileInputStream fin=new FileInputStream(file);
            /**
             * 根据文件输入流对象创建原始数据输入对象
             */
            BufferedInputStream bis =new BufferedInputStream(fin);
            /**
             * 建立两个字节数组来得到文件头和信息头的数据。
             */
            byte[] array1=new byte[14];
            bis.read(array1,0,14);
            byte[] array2=new byte[40];
            bis.read(array2,0,40);
            /**
             * 翻译bmp文件的数据，即将字节数据转化为int 类型
             * 通过翻译得到位图数据的宽和高
             */
            width=changeInt(array2,7);
            height=changeInt(array2,11);
            /**
             * 调用可以将整个位图数据读成byte数组的方法
             */
            getInf(bis);
            fin.close();
            bis.close();
            showUI();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param array  存储字节的字节数组
     * @param start  起始字节
     * @return 返回翻译后的int 数据
     */
    public int changeInt(byte[] array,int start){
        //注意char,byte,short这些数据类型经过运算符后会自动转为 int 数据类型
        // 所以array2[start]&0xff的实际意思就是通过&0xff将字符数据转化为正int数据，然后在进行位运算。
        // 这里需要注意的是<<的优先级别比&高，所以必须加上括号。
        int i =(int)((array[start]&0xff)<<24)
                |((array[start-1]&0xff)<<16)
                |((array[start-2]&0xff)<<8)
                |(array[start-3]&0xff);
        return i;
    }

    /**
     * 得到位图数据的int数组
     * @param bis   数据输入流对象
     */
    public void getInf(BufferedInputStream bis ){
        red=new int[height][width];
        green=new int[height][width];
        blue=new int[height][width];

        int skip_width=0;
        int m=width*3%4;
        if(m!=0){
            skip_width=4-m;
        }
        for(int i=height-1;i>=0;i--){
            for (int j=0;j<width;j++){
                try {
                    blue[i][j]=bis.read();
                    green[i][j]=bis.read();
                    red[i][j]=bis.read();
                    if(j==0){
                        bis.skip(skip_width);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void showUI(){
        this.setTitle("bmp 解析");
        this.setSize(width,height);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    public  static void man(String args[]){
        BmpRead24 bmp=new BmpRead24();

    }

}

