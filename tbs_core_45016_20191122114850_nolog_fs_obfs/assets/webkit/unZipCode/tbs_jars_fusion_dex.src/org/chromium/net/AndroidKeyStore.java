package org.chromium.net;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net::android")
public class AndroidKeyStore
{
  /* Error */
  private static Object a(PrivateKey paramPrivateKey)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +16 -> 17
    //   4: ldc 18
    //   6: ldc 20
    //   8: iconst_0
    //   9: anewarray 4	java/lang/Object
    //   12: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   15: aconst_null
    //   16: areturn
    //   17: aload_0
    //   18: instanceof 28
    //   21: ifne +16 -> 37
    //   24: ldc 18
    //   26: ldc 30
    //   28: iconst_0
    //   29: anewarray 4	java/lang/Object
    //   32: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: aconst_null
    //   36: areturn
    //   37: ldc 32
    //   39: invokestatic 38	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   42: astore_1
    //   43: aload_1
    //   44: aload_0
    //   45: invokevirtual 42	java/lang/Class:isInstance	(Ljava/lang/Object;)Z
    //   48: ifne +45 -> 93
    //   51: new 44	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   58: astore_1
    //   59: aload_1
    //   60: ldc 47
    //   62: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: aload_1
    //   67: aload_0
    //   68: invokevirtual 55	java/lang/Object:getClass	()Ljava/lang/Class;
    //   71: invokevirtual 59	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   74: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: ldc 18
    //   80: aload_1
    //   81: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   84: iconst_0
    //   85: anewarray 4	java/lang/Object
    //   88: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   91: aconst_null
    //   92: areturn
    //   93: aload_1
    //   94: ldc 64
    //   96: iconst_0
    //   97: anewarray 34	java/lang/Class
    //   100: invokevirtual 68	java/lang/Class:getDeclaredMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   103: astore_1
    //   104: aload_1
    //   105: iconst_1
    //   106: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   109: aload_1
    //   110: aload_0
    //   111: iconst_0
    //   112: anewarray 4	java/lang/Object
    //   115: invokevirtual 78	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   118: astore_0
    //   119: aload_1
    //   120: iconst_0
    //   121: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   124: aload_0
    //   125: ifnonnull +98 -> 223
    //   128: ldc 18
    //   130: ldc 80
    //   132: iconst_0
    //   133: anewarray 4	java/lang/Object
    //   136: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   139: aconst_null
    //   140: areturn
    //   141: astore_0
    //   142: aload_1
    //   143: iconst_0
    //   144: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   147: aload_0
    //   148: athrow
    //   149: astore_0
    //   150: new 44	java/lang/StringBuilder
    //   153: dup
    //   154: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   157: astore_1
    //   158: aload_1
    //   159: ldc 82
    //   161: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload_1
    //   166: aload_0
    //   167: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: ldc 18
    //   173: aload_1
    //   174: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   177: iconst_0
    //   178: anewarray 4	java/lang/Object
    //   181: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   184: aconst_null
    //   185: areturn
    //   186: astore_0
    //   187: new 44	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   194: astore_1
    //   195: aload_1
    //   196: ldc 87
    //   198: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: pop
    //   202: aload_1
    //   203: aload_0
    //   204: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: ldc 18
    //   210: aload_1
    //   211: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: iconst_0
    //   215: anewarray 4	java/lang/Object
    //   218: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   221: aconst_null
    //   222: areturn
    //   223: aload_0
    //   224: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	225	0	paramPrivateKey	PrivateKey
    //   42	169	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   109	119	141	finally
    //   93	109	149	java/lang/Exception
    //   119	124	149	java/lang/Exception
    //   128	139	149	java/lang/Exception
    //   142	149	149	java/lang/Exception
    //   37	43	186	java/lang/Exception
  }
  
  /* Error */
  @CalledByNative
  private static Object getOpenSSLEngineForPrivateKey(PrivateKey paramPrivateKey)
  {
    // Byte code:
    //   0: ldc 91
    //   2: invokestatic 38	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   5: astore_1
    //   6: aload_0
    //   7: invokestatic 93	org/chromium/net/AndroidKeyStore:a	(Ljava/security/PrivateKey;)Ljava/lang/Object;
    //   10: astore_2
    //   11: aload_2
    //   12: ifnonnull +5 -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: aload_2
    //   18: invokevirtual 55	java/lang/Object:getClass	()Ljava/lang/Class;
    //   21: ldc 95
    //   23: iconst_0
    //   24: anewarray 34	java/lang/Class
    //   27: invokevirtual 68	java/lang/Class:getDeclaredMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   30: astore_0
    //   31: aload_0
    //   32: iconst_1
    //   33: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   36: aload_0
    //   37: aload_2
    //   38: iconst_0
    //   39: anewarray 4	java/lang/Object
    //   42: invokevirtual 78	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   45: astore_2
    //   46: aload_0
    //   47: iconst_0
    //   48: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   51: aload_2
    //   52: ifnonnull +14 -> 66
    //   55: ldc 18
    //   57: ldc 97
    //   59: iconst_0
    //   60: anewarray 4	java/lang/Object
    //   63: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   66: aload_1
    //   67: aload_2
    //   68: invokevirtual 42	java/lang/Class:isInstance	(Ljava/lang/Object;)Z
    //   71: ifne +164 -> 235
    //   74: new 44	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   81: astore_0
    //   82: aload_0
    //   83: ldc 99
    //   85: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_0
    //   90: aload_2
    //   91: invokevirtual 55	java/lang/Object:getClass	()Ljava/lang/Class;
    //   94: invokevirtual 59	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   97: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: ldc 18
    //   103: aload_0
    //   104: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   107: iconst_0
    //   108: anewarray 4	java/lang/Object
    //   111: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   114: aconst_null
    //   115: areturn
    //   116: astore_1
    //   117: aload_0
    //   118: iconst_0
    //   119: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   122: aload_1
    //   123: athrow
    //   124: astore_0
    //   125: new 44	java/lang/StringBuilder
    //   128: dup
    //   129: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   132: astore_1
    //   133: aload_1
    //   134: ldc 101
    //   136: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload_1
    //   141: aload_0
    //   142: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: ldc 18
    //   148: aload_1
    //   149: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: iconst_0
    //   153: anewarray 4	java/lang/Object
    //   156: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   159: aconst_null
    //   160: areturn
    //   161: astore_0
    //   162: new 44	java/lang/StringBuilder
    //   165: dup
    //   166: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   169: astore_1
    //   170: aload_1
    //   171: ldc 103
    //   173: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: pop
    //   177: aload_1
    //   178: aload_0
    //   179: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: ldc 18
    //   185: aload_1
    //   186: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   189: iconst_0
    //   190: anewarray 4	java/lang/Object
    //   193: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   196: aconst_null
    //   197: areturn
    //   198: astore_0
    //   199: new 44	java/lang/StringBuilder
    //   202: dup
    //   203: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   206: astore_1
    //   207: aload_1
    //   208: ldc 105
    //   210: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   213: pop
    //   214: aload_1
    //   215: aload_0
    //   216: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: ldc 18
    //   222: aload_1
    //   223: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   226: iconst_0
    //   227: anewarray 4	java/lang/Object
    //   230: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   233: aconst_null
    //   234: areturn
    //   235: aload_2
    //   236: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	paramPrivateKey	PrivateKey
    //   5	62	1	localClass	Class
    //   116	7	1	localObject1	Object
    //   132	91	1	localStringBuilder	StringBuilder
    //   10	226	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   36	46	116	finally
    //   17	31	124	java/lang/Exception
    //   31	36	161	java/lang/Exception
    //   46	51	161	java/lang/Exception
    //   55	66	161	java/lang/Exception
    //   66	114	161	java/lang/Exception
    //   117	124	161	java/lang/Exception
    //   125	159	161	java/lang/Exception
    //   0	6	198	java/lang/Exception
  }
  
  /* Error */
  @CalledByNative
  private static long getOpenSSLHandleForPrivateKey(PrivateKey paramPrivateKey)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 93	org/chromium/net/AndroidKeyStore:a	(Ljava/security/PrivateKey;)Ljava/lang/Object;
    //   4: astore_3
    //   5: aload_3
    //   6: ifnonnull +5 -> 11
    //   9: lconst_0
    //   10: lreturn
    //   11: aload_3
    //   12: invokevirtual 55	java/lang/Object:getClass	()Ljava/lang/Class;
    //   15: ldc 110
    //   17: iconst_0
    //   18: anewarray 34	java/lang/Class
    //   21: invokevirtual 68	java/lang/Class:getDeclaredMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   24: astore_0
    //   25: aload_0
    //   26: iconst_1
    //   27: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   30: aload_0
    //   31: aload_3
    //   32: iconst_0
    //   33: anewarray 4	java/lang/Object
    //   36: invokevirtual 78	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   39: checkcast 112	java/lang/Number
    //   42: invokevirtual 116	java/lang/Number:longValue	()J
    //   45: lstore_1
    //   46: aload_0
    //   47: iconst_0
    //   48: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   51: lload_1
    //   52: lconst_0
    //   53: lcmp
    //   54: ifne +98 -> 152
    //   57: ldc 18
    //   59: ldc 118
    //   61: iconst_0
    //   62: anewarray 4	java/lang/Object
    //   65: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   68: lload_1
    //   69: lreturn
    //   70: astore_3
    //   71: aload_0
    //   72: iconst_0
    //   73: invokevirtual 74	java/lang/reflect/Method:setAccessible	(Z)V
    //   76: aload_3
    //   77: athrow
    //   78: astore_0
    //   79: new 44	java/lang/StringBuilder
    //   82: dup
    //   83: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   86: astore_3
    //   87: aload_3
    //   88: ldc 120
    //   90: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload_3
    //   95: aload_0
    //   96: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: ldc 18
    //   102: aload_3
    //   103: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   106: iconst_0
    //   107: anewarray 4	java/lang/Object
    //   110: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   113: lconst_0
    //   114: lreturn
    //   115: astore_0
    //   116: new 44	java/lang/StringBuilder
    //   119: dup
    //   120: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   123: astore_3
    //   124: aload_3
    //   125: ldc 82
    //   127: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: aload_3
    //   132: aload_0
    //   133: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: ldc 18
    //   139: aload_3
    //   140: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   143: iconst_0
    //   144: anewarray 4	java/lang/Object
    //   147: invokestatic 26	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   150: lconst_0
    //   151: lreturn
    //   152: lload_1
    //   153: lreturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	paramPrivateKey	PrivateKey
    //   45	108	1	l	long
    //   4	28	3	localObject1	Object
    //   70	7	3	localObject2	Object
    //   86	54	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   30	46	70	finally
    //   11	25	78	java/lang/Exception
    //   25	30	115	java/lang/Exception
    //   46	51	115	java/lang/Exception
    //   57	68	115	java/lang/Exception
    //   71	78	115	java/lang/Exception
    //   79	113	115	java/lang/Exception
  }
  
  @CalledByNative
  private static byte[] signWithPrivateKey(PrivateKey paramPrivateKey, String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      Object localObject = Signature.getInstance(paramString);
      try
      {
        ((Signature)localObject).initSign(paramPrivateKey);
        ((Signature)localObject).update(paramArrayOfByte);
        paramArrayOfByte = ((Signature)localObject).sign();
        return paramArrayOfByte;
      }
      catch (Exception paramArrayOfByte)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Exception while signing message with ");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(" and ");
        ((StringBuilder)localObject).append(paramPrivateKey.getAlgorithm());
        ((StringBuilder)localObject).append(" private key (");
        ((StringBuilder)localObject).append(paramPrivateKey.getClass().getName());
        ((StringBuilder)localObject).append("): ");
        ((StringBuilder)localObject).append(paramArrayOfByte);
        Log.e("AndroidKeyStore", ((StringBuilder)localObject).toString(), new Object[0]);
        return null;
      }
      return null;
    }
    catch (NoSuchAlgorithmException paramPrivateKey)
    {
      paramArrayOfByte = new StringBuilder();
      paramArrayOfByte.append("Signature algorithm ");
      paramArrayOfByte.append(paramString);
      paramArrayOfByte.append(" not supported: ");
      paramArrayOfByte.append(paramPrivateKey);
      Log.e("AndroidKeyStore", paramArrayOfByte.toString(), new Object[0]);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\AndroidKeyStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */