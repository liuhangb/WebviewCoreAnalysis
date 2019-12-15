package com.tencent.common.http;

import android.text.TextUtils;

public class ContentType
{
  public static final String CHARSET_BINARY = "binary";
  public static final String CHARSET_UTF8 = "utf-8";
  public static final String MIME_BDHD = "application/player-activex";
  public static final String MIME_FLASH = "application/x-shockwave-flash";
  public static final String MIME_MP4 = "video/mp4";
  public static final String MIME_QVOD = "application/qvod-plugin";
  public static final String SUBTYPE_CSS = "css";
  public static final String SUBTYPE_GIF = "gif";
  public static final String SUBTYPE_HTML = "html";
  public static final String SUBTYPE_JAVASCRIPT = "javascript";
  public static final String SUBTYPE_JPEG = "jpeg";
  public static final String SUBTYPE_OCTETSTREAM = "octet-stream";
  public static final String SUBTYPE_PLAIN = "plain";
  public static final String SUBTYPE_PNG = "png";
  public static final String SUBTYPE_SHARPP = "sharpp";
  public static final String TYPE_APPLICATION = "application";
  public static final String TYPE_IMAGE = "image";
  public static final String TYPE_TEXT = "text";
  public String mEncoding;
  public String mType;
  public String mTypeValue;
  
  public ContentType(String paramString)
  {
    a(paramString);
  }
  
  public ContentType(String paramString1, String paramString2, String paramString3)
  {
    this.mType = paramString1;
    this.mTypeValue = paramString2;
    this.mEncoding = paramString3;
  }
  
  private void a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    String str2 = paramString.trim();
    int i = str2.indexOf(';');
    String str1 = null;
    paramString = str2;
    if (i != -1)
    {
      paramString = str2.substring(0, i);
      str1 = str2.substring(i + 1);
    }
    if (paramString != null)
    {
      i = paramString.indexOf('/');
      if (i != -1)
      {
        this.mType = paramString.substring(0, i);
        this.mTypeValue = paramString.substring(i + 1);
      }
      else
      {
        this.mType = paramString;
      }
    }
    if (str1 != null)
    {
      i = str1.indexOf('=');
      if (i != -1) {
        this.mEncoding = str1.substring(i + 1);
      }
    }
  }
  
  public String toString()
  {
    String str = this.mType;
    if (str != null) {
      return str.concat("/").concat(this.mTypeValue);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\ContentType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */