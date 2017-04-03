import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by zhaoshuai on 2017/3/21.
 */
public class MD5_encryption {
    /**
     *
     * @param key key为要加密的密钥
     * @return    MD5 的计算结果为一个128位的长整数
     */
    public static byte[] getMD5(String key){
        byte[] e_key=null;
        try{
            /**
             * MessageDigest 通过其getInstance系列静态函数来进行实例化和初始化。
             * MessageDigest 对象通过使用 update 方法处理数据。任何时候都可以调用 reset 方法重置摘要。
             * 一旦所有需要更新的数据都已经被更新了，应该调用 digest 方法之一完成哈希计算并返回结果。
             */
            MessageDigest md=MessageDigest.getInstance("MD5");//MessageDigest 类用与为应用程序提供信息摘要算法的功能。
            md.update(key.getBytes());
            e_key=md.digest();
        }catch  (Exception e){
            e.printStackTrace();
        }
        return e_key;
    }
    public static void main(String args[]){
        String a="zssnihaomanicetomeetyou";
        System.out.print(a);
        System.out.print(MD5_encryption.getMD5("zss").length+" " +a.getBytes().length);
        System.out.println();
        byte[] str=a.getBytes();
        for(byte tmp:str){
            System.out.print(tmp+" ");

        }
        //97 98 111 -28 111 -28 127

    }
}
