package com.tencent.mtt.base.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.manifest.annotation.Service;
import com.tencent.common.utils.UrlUtils;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SharpP
{
  public static final String EVENT_DECODE = "4";
  public static final String EVENT_DECODE_ERROR = "3";
  public static final String EVENT_DECODE_OK = "5";
  public static final String EVENT_RECEIVED = "2";
  public static final String EVENT_REQUEST = "1";
  private static ISharpDecorder jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder;
  private static Map<String, Boolean> jdField_a_of_type_JavaUtilMap = new ConcurrentHashMap();
  private static boolean jdField_a_of_type_Boolean = true;
  public static SharpPImageFilter sImageFilter = null;
  
  public static void acc(String... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder("BONSHP00");
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str = paramVarArgs[i];
      localStringBuilder.append("_");
      localStringBuilder.append(str);
      i += 1;
    }
    paramVarArgs = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("acc(");
    localStringBuilder.append(paramVarArgs);
    localStringBuilder.append(")");
    localStringBuilder.toString();
    StatServerHolder.userBehaviorStatistics(paramVarArgs);
  }
  
  public static void disable(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("disable sharpP for url ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    if (!TextUtils.isEmpty(paramString)) {
      jdField_a_of_type_JavaUtilMap.put(paramString, Boolean.TRUE);
    }
  }
  
  public static void enable(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("enable sharpP for url ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    if (!TextUtils.isEmpty(paramString)) {
      jdField_a_of_type_JavaUtilMap.remove(paramString);
    }
  }
  
  public static boolean isDisabled(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return true;
    }
    if ((!UrlUtils.isHttpUrl(paramString)) && (!UrlUtils.isHttpsUrl(paramString))) {
      return true;
    }
    return jdField_a_of_type_JavaUtilMap.containsKey(paramString);
  }
  
  /* Error */
  public static ISharpDecorder queryDecoder()
  {
    // Byte code:
    //   0: getstatic 124	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder	Lcom/tencent/mtt/base/image/SharpP$ISharpDecorder;
    //   3: ifnonnull +110 -> 113
    //   6: getstatic 126	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_Boolean	Z
    //   9: ifeq +104 -> 113
    //   12: ldc 2
    //   14: monitorenter
    //   15: getstatic 124	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder	Lcom/tencent/mtt/base/image/SharpP$ISharpDecorder;
    //   18: ifnonnull +56 -> 74
    //   21: getstatic 126	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_Boolean	Z
    //   24: ifeq +50 -> 74
    //   27: invokestatic 132	com/tencent/common/manifest/AppManifest:getInstance	()Lcom/tencent/common/manifest/AppManifest;
    //   30: ldc 6
    //   32: invokevirtual 136	com/tencent/common/manifest/AppManifest:queryService	(Ljava/lang/Class;)Ljava/lang/Object;
    //   35: checkcast 6	com/tencent/mtt/base/image/SharpP$ISharpDecorder
    //   38: putstatic 124	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder	Lcom/tencent/mtt/base/image/SharpP$ISharpDecorder;
    //   41: goto +33 -> 74
    //   44: astore_0
    //   45: goto +63 -> 108
    //   48: astore_0
    //   49: aload_0
    //   50: invokevirtual 139	java/lang/Throwable:printStackTrace	()V
    //   53: iconst_2
    //   54: anewarray 141	java/lang/String
    //   57: dup
    //   58: iconst_0
    //   59: ldc -113
    //   61: aastore
    //   62: dup
    //   63: iconst_1
    //   64: ldc 17
    //   66: aastore
    //   67: invokestatic 145	com/tencent/mtt/base/image/SharpP:acc	([Ljava/lang/String;)V
    //   70: iconst_0
    //   71: putstatic 126	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_Boolean	Z
    //   74: new 51	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 67	java/lang/StringBuilder:<init>	()V
    //   81: astore_0
    //   82: aload_0
    //   83: ldc -109
    //   85: invokevirtual 62	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_0
    //   90: getstatic 124	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder	Lcom/tencent/mtt/base/image/SharpP$ISharpDecorder;
    //   93: invokevirtual 150	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_0
    //   98: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: pop
    //   102: ldc 2
    //   104: monitorexit
    //   105: goto +8 -> 113
    //   108: ldc 2
    //   110: monitorexit
    //   111: aload_0
    //   112: athrow
    //   113: getstatic 124	com/tencent/mtt/base/image/SharpP:jdField_a_of_type_ComTencentMttBaseImageSharpP$ISharpDecorder	Lcom/tencent/mtt/base/image/SharpP$ISharpDecorder;
    //   116: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   44	1	0	localObject	Object
    //   48	2	0	localThrowable	Throwable
    //   81	31	0	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   15	41	44	finally
    //   49	74	44	finally
    //   74	105	44	finally
    //   108	111	44	finally
    //   15	41	48	java/lang/Throwable
  }
  
  public static void report(String paramString1, String paramString2, String... paramVarArgs)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("event_type", paramString1);
    localHashMap.put("url", paramString2);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(Build.VERSION.SDK_INT);
    localHashMap.put("api", paramString1.toString());
    int i = 0;
    while (i < paramVarArgs.length - 1)
    {
      localHashMap.put(paramVarArgs[i], paramVarArgs[(i + 1)]);
      i += 2;
    }
    paramString1 = new StringBuilder();
    paramString1.append("report(QB_SHARPP) = ");
    paramString1.append(localHashMap);
    paramString1.toString();
    StatServerHolder.statWithBeacon("QB_SHARPP", localHashMap);
  }
  
  @Service
  public static abstract interface ISharpDecorder
  {
    public abstract Bitmap decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BitmapFactory.Options paramOptions);
    
    public abstract boolean enabled();
    
    public abstract boolean support(String paramString);
  }
  
  public static abstract interface SharpPImageFilter
  {
    public abstract Bitmap receiveBitmap(Bitmap paramBitmap, String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\image\SharpP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */