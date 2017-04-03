/**
 * Created by zhaoshuai on 2017/3/22.
 */
public class RC4_encryption {
    public static int[] RC4(int[] message,byte[] key ){
        int [] s =new int[256];
        int[] k=new int[256];
        /**
         * 1. 密钥调度算法
         */

        /**
         * 用0-255 初始化转态矢量
         */
        for(int i=0;i<256;i++){
            s[i]=i;
        }
        /**
         * 将生成的byte 类型的密钥数组转为int 类型
         */
        for(int i =0;i<key.length;i++){
            k[i]=key[i]&0xff;
        }
        for(int i=key.length;i<k.length;i++){
            k[i]=k[i%key.length];
        }
        /**
         * 用给定密钥Key 初始化密钥数据ik[]
         */
        int j=0;
        for(int i=0;i<256;i++){
            j=(j+s[i]+k[i])%256;
            int temp=s[i];
            s[i]=s[j];
            s[j]=temp;
        }
        /**
         * 随机数生成算法PRGA
         * 主要完成密钥流的生成
         * 密钥流中的密钥K被一个一个地生成，即从S【0】-S【255】，对每个S【i】，根据当前态S的值
         * 将S【i】与S中的另一个元素（字节）置换，当S【255】完成置换以后，操作再从S【0】开始重复。
         */
        int i =0;
        j=0;
        int[] e_message=new int[message.length];
        for(int x=0;x<message.length;x++){
            i=(i+1)%256;
            j=(j+s[i])%256;
            int temp = s[i];
            s[i]=s[j];
            s[j]=temp;
            int t=(s[i]+s[j])%256;
            int temp2=s[t];
            e_message[x]=message[x]^temp2;
        }
        return e_message;
    }

}
