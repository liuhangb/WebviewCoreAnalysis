package org.chromium.net;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net::android")
public class NetStringUtil
{
  @CalledByNative
  private static String convertToUnicode(ByteBuffer paramByteBuffer, String paramString)
  {
    try
    {
      paramByteBuffer = Charset.forName(paramString).newDecoder().decode(paramByteBuffer).toString();
      return paramByteBuffer;
    }
    catch (Exception paramByteBuffer)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  private static String convertToUnicodeAndNormalize(ByteBuffer paramByteBuffer, String paramString)
  {
    paramByteBuffer = convertToUnicode(paramByteBuffer, paramString);
    if (paramByteBuffer == null) {
      return null;
    }
    return Normalizer.normalize(paramByteBuffer, Normalizer.Form.NFC);
  }
  
  @CalledByNative
  private static String convertToUnicodeWithSubstitutions(ByteBuffer paramByteBuffer, String paramString)
  {
    try
    {
      paramString = Charset.forName(paramString).newDecoder();
      paramString.onMalformedInput(CodingErrorAction.REPLACE);
      paramString.onUnmappableCharacter(CodingErrorAction.REPLACE);
      paramString.replaceWith("�");
      paramByteBuffer = paramString.decode(paramByteBuffer).toString();
      return paramByteBuffer;
    }
    catch (Exception paramByteBuffer)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  private static String toUpperCase(String paramString)
  {
    try
    {
      paramString = paramString.toUpperCase(Locale.getDefault());
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\NetStringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */