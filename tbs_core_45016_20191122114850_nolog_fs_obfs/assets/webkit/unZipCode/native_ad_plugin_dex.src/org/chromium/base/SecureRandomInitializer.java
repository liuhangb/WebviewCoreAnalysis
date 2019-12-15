package org.chromium.base;

import android.annotation.SuppressLint;

@SuppressLint({"SecureRandom"})
public class SecureRandomInitializer
{
  /* Error */
  public static void initialize(java.security.SecureRandom paramSecureRandom)
    throws java.io.IOException
  {
    // Byte code:
    //   0: new 20	java/io/FileInputStream
    //   3: dup
    //   4: ldc 22
    //   6: invokespecial 25	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   9: astore_3
    //   10: aconst_null
    //   11: astore_2
    //   12: aload_2
    //   13: astore_1
    //   14: bipush 16
    //   16: newarray <illegal type>
    //   18: astore 4
    //   20: aload_2
    //   21: astore_1
    //   22: aload_3
    //   23: aload 4
    //   25: invokevirtual 29	java/io/FileInputStream:read	([B)I
    //   28: aload 4
    //   30: arraylength
    //   31: if_icmpne +16 -> 47
    //   34: aload_2
    //   35: astore_1
    //   36: aload_0
    //   37: aload 4
    //   39: invokevirtual 35	java/security/SecureRandom:setSeed	([B)V
    //   42: aload_3
    //   43: invokevirtual 38	java/io/FileInputStream:close	()V
    //   46: return
    //   47: aload_2
    //   48: astore_1
    //   49: new 16	java/io/IOException
    //   52: dup
    //   53: ldc 40
    //   55: invokespecial 41	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   58: athrow
    //   59: astore_0
    //   60: goto +8 -> 68
    //   63: astore_0
    //   64: aload_0
    //   65: astore_1
    //   66: aload_0
    //   67: athrow
    //   68: aload_1
    //   69: ifnull +19 -> 88
    //   72: aload_3
    //   73: invokevirtual 38	java/io/FileInputStream:close	()V
    //   76: goto +16 -> 92
    //   79: astore_2
    //   80: aload_1
    //   81: aload_2
    //   82: invokevirtual 45	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   85: goto +7 -> 92
    //   88: aload_3
    //   89: invokevirtual 38	java/io/FileInputStream:close	()V
    //   92: aload_0
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	paramSecureRandom	java.security.SecureRandom
    //   13	68	1	localObject1	Object
    //   11	37	2	localObject2	Object
    //   79	3	2	localThrowable	Throwable
    //   9	80	3	localFileInputStream	java.io.FileInputStream
    //   18	20	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   14	20	59	finally
    //   22	34	59	finally
    //   36	42	59	finally
    //   49	59	59	finally
    //   66	68	59	finally
    //   14	20	63	java/lang/Throwable
    //   22	34	63	java/lang/Throwable
    //   36	42	63	java/lang/Throwable
    //   49	59	63	java/lang/Throwable
    //   72	76	79	java/lang/Throwable
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\SecureRandomInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */