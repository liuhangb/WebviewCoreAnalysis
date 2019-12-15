package org.chromium.tencent.net;

import java.util.HashMap;
import java.util.Map;

public class TencentAndroidCertVerifyResult
{
  private static Map<String, Integer> mCertificateLength = new HashMap();
  
  public static int getHostCertificateLength(String paramString)
  {
    try
    {
      if (mCertificateLength.containsKey(paramString))
      {
        int i = ((Integer)mCertificateLength.get(paramString)).intValue();
        return i;
      }
      return 3;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  /* Error */
  public static void setHostCertificateLength(String paramString, int paramInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 16	org/chromium/tencent/net/TencentAndroidCertVerifyResult:mCertificateLength	Ljava/util/Map;
    //   6: aload_0
    //   7: iload_1
    //   8: invokestatic 44	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   11: invokeinterface 48 3 0
    //   16: pop
    //   17: goto +9 -> 26
    //   20: astore_0
    //   21: ldc 2
    //   23: monitorexit
    //   24: aload_0
    //   25: athrow
    //   26: ldc 2
    //   28: monitorexit
    //   29: return
    //   30: astore_0
    //   31: goto -5 -> 26
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	34	0	paramString	String
    //   0	34	1	paramInt	int
    // Exception table:
    //   from	to	target	type
    //   3	17	20	finally
    //   3	17	30	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\net\TencentAndroidCertVerifyResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */