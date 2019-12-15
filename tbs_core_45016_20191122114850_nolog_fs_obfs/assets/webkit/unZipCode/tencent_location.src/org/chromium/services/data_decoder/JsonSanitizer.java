package org.chromium.services.data_decoder;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.MalformedJsonException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.chromium.base.StreamUtil;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("data_decoder")
public class JsonSanitizer
{
  public static String a(String paramString)
    throws IOException, IllegalStateException
  {
    JsonReader localJsonReader = new JsonReader(new StringReader(paramString));
    Object localObject1 = new StringWriter(paramString.length());
    paramString = new JsonWriter((Writer)localObject1);
    StackChecker localStackChecker = new StackChecker(null);
    int i = 0;
    for (;;)
    {
      if (i == 0) {}
      label124:
      boolean bool;
      try
      {
        localObject3 = localJsonReader.peek();
        switch (1.a[localObject3.ordinal()])
        {
        case 9: 
          bool = a;
        }
      }
      finally
      {
        Object localObject3;
        StreamUtil.closeQuietly(localJsonReader);
        StreamUtil.closeQuietly(paramString);
      }
      localJsonReader.nextNull();
      paramString.nullValue();
      continue;
      paramString.value(localJsonReader.nextBoolean());
      continue;
      localObject3 = localJsonReader.nextString();
      try
      {
        paramString.value(Long.parseLong((String)localObject3));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;) {}
      }
      paramString.value(Double.parseDouble((String)localObject3));
      continue;
      paramString.value(b(localJsonReader.nextString()));
      continue;
      paramString.name(b(localJsonReader.nextName()));
      continue;
      localStackChecker.b();
      localJsonReader.endObject();
      paramString.endObject();
      continue;
      localStackChecker.a();
      localJsonReader.beginObject();
      paramString.beginObject();
      continue;
      localStackChecker.b();
      localJsonReader.endArray();
      paramString.endArray();
      continue;
      localStackChecker.a();
      localJsonReader.beginArray();
      paramString.beginArray();
      continue;
      while (!bool)
      {
        throw new AssertionError(localObject3);
        localObject1 = ((StringWriter)localObject1).toString();
        StreamUtil.closeQuietly(localJsonReader);
        StreamUtil.closeQuietly(paramString);
        return (String)localObject1;
        break label124;
        i = 1;
        break;
      }
    }
  }
  
  private static boolean a(int paramInt)
  {
    return (paramInt < 55296) || ((paramInt >= 57344) && (paramInt < 64976)) || ((paramInt > 65007) && (paramInt <= 1114111) && ((paramInt & 0xFFFE) != 65534));
  }
  
  private static boolean a(String paramString)
  {
    int m = paramString.length();
    int j;
    for (int i = 0; i < m; i = j + 1)
    {
      char c1 = paramString.charAt(i);
      if (Character.isLowSurrogate(c1)) {
        return false;
      }
      j = i;
      char c3 = c1;
      int k;
      if (Character.isHighSurrogate(c1))
      {
        j = i + 1;
        if (j >= m) {
          return false;
        }
        char c2 = paramString.charAt(j);
        if (!Character.isLowSurrogate(c2)) {
          return false;
        }
        k = Character.toCodePoint(c1, c2);
      }
      if (!a(k)) {
        return false;
      }
    }
    return true;
  }
  
  private static String b(String paramString)
    throws MalformedJsonException
  {
    if (a(paramString)) {
      return paramString;
    }
    throw new MalformedJsonException("Invalid escape sequence");
  }
  
  private static native void nativeOnError(long paramLong, String paramString);
  
  private static native void nativeOnSuccess(long paramLong, String paramString);
  
  @CalledByNative
  public static void sanitize(long paramLong, String paramString)
  {
    try
    {
      paramString = a(paramString);
      nativeOnSuccess(paramLong, paramString);
      return;
    }
    catch (IllegalStateException paramString) {}catch (IOException paramString) {}
    nativeOnError(paramLong, paramString.getMessage());
  }
  
  private static class StackChecker
  {
    private int a;
    
    public void a()
    {
      int i = this.a + 1;
      this.a = i;
      if (i < 200) {
        return;
      }
      throw new IllegalStateException("Too much nesting");
    }
    
    public void b()
    {
      this.a -= 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\services\data_decoder\JsonSanitizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */