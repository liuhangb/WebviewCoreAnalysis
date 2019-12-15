package org.chromium.media;

import android.media.MediaFormat;
import android.os.Build;
import java.nio.ByteBuffer;

class MediaFormatBuilder
{
  public static MediaFormat a(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
  {
    paramString = MediaFormat.createVideoFormat(paramString, paramInt1, paramInt2);
    paramString.setInteger("bitrate", paramInt3);
    paramString.setInteger("frame-rate", paramInt4);
    paramString.setInteger("i-frame-interval", paramInt5);
    paramString.setInteger("color-format", paramInt6);
    a(paramString, paramBoolean);
    return paramString;
  }
  
  public static MediaFormat a(String paramString, int paramInt1, int paramInt2, byte[][] paramArrayOfByte, HdrMetadata paramHdrMetadata, boolean paramBoolean)
  {
    paramString = MediaFormat.createVideoFormat(paramString, paramInt1, paramInt2);
    if (paramString == null) {
      return null;
    }
    a(paramString, paramArrayOfByte);
    if (paramHdrMetadata != null) {
      paramHdrMetadata.a(paramString);
    }
    a(paramString, paramBoolean);
    return paramString;
  }
  
  public static MediaFormat a(String paramString, int paramInt1, int paramInt2, byte[][] paramArrayOfByte, boolean paramBoolean)
  {
    paramString = MediaFormat.createAudioFormat(paramString, paramInt1, paramInt2);
    a(paramString, paramArrayOfByte);
    if (paramBoolean) {
      paramString.setInteger("is-adts", 1);
    }
    return paramString;
  }
  
  private static void a(MediaFormat paramMediaFormat, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramMediaFormat.setInteger("max-width", paramMediaFormat.getInteger("width"));
      paramMediaFormat.setInteger("max-height", paramMediaFormat.getInteger("height"));
    }
    if (paramMediaFormat.containsKey("max-input-size")) {
      return;
    }
    int i = paramMediaFormat.getInteger("height");
    int j = i;
    if (paramBoolean)
    {
      j = i;
      if (paramMediaFormat.containsKey("max-height")) {
        j = Math.max(i, paramMediaFormat.getInteger("max-height"));
      }
    }
    i = paramMediaFormat.getInteger("width");
    int k = i;
    if (paramBoolean)
    {
      k = i;
      if (paramMediaFormat.containsKey("max-width")) {
        k = Math.max(j, paramMediaFormat.getInteger("max-width"));
      }
    }
    String str = paramMediaFormat.getString("mime");
    i = -1;
    switch (str.hashCode())
    {
    default: 
      break;
    case 1599127257: 
      if (str.equals("video/x-vnd.on2.vp9")) {
        i = 3;
      }
      break;
    case 1599127256: 
      if (str.equals("video/x-vnd.on2.vp8")) {
        i = 1;
      }
      break;
    case 1331836730: 
      if (str.equals("video/avc")) {
        i = 0;
      }
      break;
    case -1662541442: 
      if (str.equals("video/hevc")) {
        i = 2;
      }
      break;
    }
    switch (i)
    {
    default: 
      return;
    case 2: 
    case 3: 
      i = k * j;
      j = 4;
      break;
    case 1: 
      i = k * j;
      j = 2;
      break;
    case 0: 
      if ("BRAVIA 4K 2015".equals(Build.MODEL)) {
        return;
      }
      i = (k + 15) / 16 * ((j + 15) / 16) * 16 * 16;
      j = 2;
    }
    paramMediaFormat.setInteger("max-input-size", i * 3 / (j * 2));
  }
  
  private static void a(MediaFormat paramMediaFormat, byte[][] paramArrayOfByte)
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      if (paramArrayOfByte[i].length != 0)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("csd-");
        localStringBuilder.append(i);
        paramMediaFormat.setByteBuffer(localStringBuilder.toString(), ByteBuffer.wrap(paramArrayOfByte[i]));
      }
      i += 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaFormatBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */