package org.chromium.media;

import android.media.MediaCodecInfo.CodecProfileLevel;
import java.util.ArrayList;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
@MainDex
class CodecProfileLevelList
{
  private final List<CodecProfileLevelAdapter> a = new ArrayList();
  
  private static int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 != 1)
    {
      switch (paramInt1)
      {
      default: 
        throw new UnsupportedCodecProfileException(null);
      case 8: 
        if (paramInt2 != 4096) {
          switch (paramInt2)
          {
          default: 
            throw new UnsupportedCodecProfileException(null);
          case 1: 
            return 16;
          }
        }
        return 17;
      case 7: 
        if (paramInt2 != 4)
        {
          if (paramInt2 != 8)
          {
            if (paramInt2 == 4096) {
              break label154;
            }
            if (paramInt2 != 8192)
            {
              switch (paramInt2)
              {
              default: 
                throw new UnsupportedCodecProfileException(null);
              case 2: 
                return 13;
              }
              return 12;
            }
          }
          return 15;
        }
        label154:
        return 14;
      }
      if (paramInt2 == 1) {
        return 11;
      }
      throw new UnsupportedCodecProfileException(null);
    }
    if (paramInt2 != 4)
    {
      if (paramInt2 != 8)
      {
        if (paramInt2 != 16)
        {
          if (paramInt2 != 32)
          {
            if (paramInt2 != 64)
            {
              switch (paramInt2)
              {
              default: 
                throw new UnsupportedCodecProfileException(null);
              case 2: 
                return 1;
              }
              return 0;
            }
            return 6;
          }
          return 5;
        }
        return 4;
      }
      return 3;
    }
    return 2;
  }
  
  private static int a(String paramString)
  {
    if (paramString.endsWith("vp9")) {
      return 7;
    }
    if (paramString.endsWith("vp8")) {
      return 6;
    }
    if (paramString.endsWith("avc")) {
      return 1;
    }
    if (paramString.endsWith("hevc")) {
      return 8;
    }
    throw new UnsupportedCodecProfileException(null);
  }
  
  private static int b(int paramInt1, int paramInt2)
  {
    if (paramInt1 != 1)
    {
      switch (paramInt1)
      {
      default: 
        throw new UnsupportedCodecProfileException(null);
      case 8: 
        switch (paramInt2)
        {
        default: 
          throw new UnsupportedCodecProfileException(null);
        case 16777216: 
        case 33554432: 
          return 186;
        case 4194304: 
        case 8388608: 
          return 183;
        case 1048576: 
        case 2097152: 
          return 180;
        case 262144: 
        case 524288: 
          return 156;
        case 65536: 
        case 131072: 
          return 153;
        case 16384: 
        case 32768: 
          return 150;
        case 4096: 
        case 8192: 
          return 123;
        case 1024: 
        case 2048: 
          return 120;
        case 256: 
        case 512: 
          return 93;
        case 64: 
        case 128: 
          return 90;
        case 16: 
        case 32: 
          return 63;
        case 4: 
        case 8: 
          return 60;
        }
        return 30;
      case 7: 
        switch (paramInt2)
        {
        default: 
          throw new UnsupportedCodecProfileException(null);
        case 8192: 
          return 62;
        case 4096: 
          return 61;
        case 2048: 
          return 60;
        case 1024: 
          return 52;
        case 512: 
          return 51;
        case 256: 
          return 50;
        case 128: 
          return 41;
        case 64: 
          return 40;
        case 32: 
          return 31;
        case 16: 
          return 30;
        case 8: 
          return 21;
        case 4: 
          return 20;
        case 2: 
          return 11;
        }
        return 10;
      }
      if (paramInt2 != 4)
      {
        if (paramInt2 != 8)
        {
          switch (paramInt2)
          {
          default: 
            throw new UnsupportedCodecProfileException(null);
          case 2: 
            return 1;
          }
          return 0;
        }
        return 3;
      }
      return 2;
    }
    switch (paramInt2)
    {
    default: 
      throw new UnsupportedCodecProfileException(null);
    case 65536: 
      return 52;
    case 32768: 
      return 51;
    case 16384: 
      return 50;
    case 8192: 
      return 42;
    case 4096: 
      return 41;
    case 2048: 
      return 40;
    case 1024: 
      return 32;
    case 512: 
      return 31;
    case 256: 
      return 30;
    case 128: 
      return 22;
    case 64: 
      return 21;
    case 32: 
      return 20;
    case 16: 
      return 13;
    case 8: 
      return 12;
    case 4: 
      return 11;
    }
    return 10;
  }
  
  public boolean a(int paramInt1, int paramInt2, int paramInt3)
  {
    this.a.add(new CodecProfileLevelAdapter(paramInt1, paramInt2, paramInt3));
    return true;
  }
  
  public boolean a(String paramString, MediaCodecInfo.CodecProfileLevel paramCodecProfileLevel)
  {
    try
    {
      int i = a(paramString);
      this.a.add(new CodecProfileLevelAdapter(i, a(i, paramCodecProfileLevel.profile), b(i, paramCodecProfileLevel.level)));
      return true;
    }
    catch (UnsupportedCodecProfileException paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public Object[] a()
  {
    return this.a.toArray();
  }
  
  static class CodecProfileLevelAdapter
  {
    private final int a;
    private final int b;
    private final int c;
    
    public CodecProfileLevelAdapter(int paramInt1, int paramInt2, int paramInt3)
    {
      this.a = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
    }
    
    @CalledByNative("CodecProfileLevelAdapter")
    public int getCodec()
    {
      return this.a;
    }
    
    @CalledByNative("CodecProfileLevelAdapter")
    public int getLevel()
    {
      return this.c;
    }
    
    @CalledByNative("CodecProfileLevelAdapter")
    public int getProfile()
    {
      return this.b;
    }
  }
  
  private static class UnsupportedCodecProfileException
    extends RuntimeException
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\CodecProfileLevelList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */