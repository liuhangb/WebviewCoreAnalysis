package com.tencent.tbs.tbsshell.partner.reader;

import android.os.Build.VERSION;
import android.util.Log;
import com.tencent.mtt.external.reader.export.IReaderEventProxy;
import com.tencent.smtt.export.external.DexLoader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;

public class TbsReaderEventProxy
  implements IReaderEventProxy
{
  private static final String TBS_READER_WIZZARD_NAME = "com.tencent.smtt.sdk.ReaderWizard";
  private Object mListener;
  private DexLoader mLoader;
  
  public TbsReaderEventProxy(Object paramObject, DexLoader paramDexLoader)
  {
    this.mListener = paramObject;
    this.mLoader = paramDexLoader;
  }
  
  public static Object invokeInstance(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    try
    {
      Class localClass = paramObject.getClass();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("invokeInstance -- cls:");
      localStringBuilder.append(localClass);
      localStringBuilder.append("; method:");
      localStringBuilder.append(paramString);
      localStringBuilder.toString();
      if (Build.VERSION.SDK_INT > 10) {
        paramString = localClass.getMethod(paramString, paramArrayOfClass);
      } else {
        paramString = localClass.getDeclaredMethod(paramString, paramArrayOfClass);
      }
      paramString.setAccessible(true);
      paramArrayOfClass = paramVarArgs;
      if (paramVarArgs.length == 0) {
        paramArrayOfClass = null;
      }
      paramObject = paramString.invoke(paramObject, paramArrayOfClass);
      return paramObject;
    }
    catch (Throwable paramString)
    {
      if ((paramString.getCause() != null) && (paramString.getCause().toString().contains("AuthenticationFail"))) {
        return new String("AuthenticationFail");
      }
      paramObject = new StringWriter();
      paramString.printStackTrace(new PrintWriter((Writer)paramObject));
      paramString = new StringBuilder();
      paramString.append("invokeInstance -- exceptions:");
      paramString.append(((StringWriter)paramObject).toString());
      Log.e("", paramString.toString());
    }
    return null;
  }
  
  public void destroy()
  {
    this.mListener = null;
    this.mLoader = null;
  }
  
  public void doCallBackEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    Object localObject = this.mListener;
    if (localObject != null)
    {
      Integer localInteger = new Integer(paramInt);
      invokeInstance(localObject, "onCallBackAction", new Class[] { Integer.class, Object.class, Object.class }, new Object[] { localInteger, paramObject1, paramObject2 });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\TbsReaderEventProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */