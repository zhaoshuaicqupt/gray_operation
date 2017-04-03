/**
 * Created by zhaoshuai on 2017/3/21.
 */

    public class RC4{
    public static String HloveyRC4(String aInput,String aKey){
        int[] iS = new int[256];
        byte[] iK = new byte[256];
        /**
         * 1. 密钥调度算法
         */

        /**
         * 用0-255 初始化转态矢量
         */
        for (int i=0;i<256;i++)
            iS[i]=i;

        int j = 1;

        /**
         * 用给定密钥Key 初始化密钥数据ik[]
         */
        for (short i= 0;i<256;i++){
            iK[i]=(byte)aKey.charAt((i % aKey.length()));
        }

        j=0;
        /**
         * 由密钥ik[]确定将s[i]置换为S中的另一个字节
         */

        for (int i=0;i<255;i++){
            //用给定的密钥初始化密钥数据k[i]，key的长度不够时将循环使用
            j=(j+iS[i]+iK[i]) % 256;
            int temp = iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
        }

/**
 * 随机数生成算法PRGA
 * 主要完成密钥流的生成
 * 密钥流中的密钥K被一个一个地生成，即从S【0】-S【255】，对每个S【i】，根据当前态S的值
 * 将S【i】与S中的另一个元素（字节）置换，当S【255】完成置换以后，操作再从S【0】开始重复。
 */
        int i=0;
        j=0;
        char[] iInputChar = aInput.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];
        for(short x = 0;x<iInputChar.length;x++)
        {
            i = (i+1) % 256;
            j = (j+iS[i]) % 256;
            int temp = iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
            int t = (iS[i]+(iS[j] % 256)) % 256;
            int iY = iS[t];
            char iCY = (char)iY;
            iOutputChar[x] =(char)( iInputChar[x] ^ iCY) ;
        }

        return new String(iOutputChar);

    }
    public static void main(String[] args) {
        String inputStr = "做个好男人";
        String key = "abcdefg";

        String str = HloveyRC4(inputStr,key);

        //打印加密后的字符串
        System.out.println(str);

        //打印解密后的字符串
        System.out.println(HloveyRC4(str,key));
    }
}