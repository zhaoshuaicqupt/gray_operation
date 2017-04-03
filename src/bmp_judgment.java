import java.io.File;

/**
 * Created by zhaoshuai on 2017/3/18.
 */
public class bmp_judgment {
    /**
     * 判断文件是否为bmp图像
     * @param file
     * @return
     */
    public boolean isBMP(File file){
        /**
         * 获取文件的后缀名
         */
        String lastname=file.getName().substring(file.getName().indexOf(".")+1,file.getName().length());
        if(lastname.toUpperCase().equals("BMP"))
            return true;
        else
            return false;
    }
    public static void main(String[] args){
        File file=new File(System.getProperty("user.dir")+"/src/gray.bmp");
        bmp_judgment demo=new bmp_judgment();
        System.out.print(demo.isBMP(file));
    }
}


