package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.PathUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.SmttServiceClientProxy;

@JNINamespace("content")
class MediaResourceGetter
{
  private static final MediaMetadata jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata = new MediaMetadata(0, 0, 0, false);
  private final MediaMetadataRetriever jdField_a_of_type_AndroidMediaMediaMetadataRetriever = new MediaMetadataRetriever();
  
  @SuppressLint({"SdCardPath"})
  private List<String> a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("/mnt/sdcard/");
    localArrayList.add("/sdcard/");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/data/data/");
    localStringBuilder.append(paramContext.getPackageName());
    localStringBuilder.append("/cache/");
    localArrayList.add(localStringBuilder.toString());
    return localArrayList;
  }
  
  private List<String> a(List<String> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    try
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        localArrayList.add(new File((String)paramList.next()).getCanonicalPath());
      }
      return localArrayList;
    }
    catch (IOException paramList) {}
    return localArrayList;
  }
  
  private MediaMetadata a()
  {
    for (;;)
    {
      try
      {
        localObject = a(9);
        if (localObject == null)
        {
          localObject = jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
          return (MediaMetadata)localObject;
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        Object localObject;
        int k;
        Log.e("cr_MediaResource", "Unable to extract metadata: %s", new Object[] { localRuntimeException });
        return jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
      }
      try
      {
        k = Integer.parseInt((String)localObject);
        if (!"yes".equals(a(17))) {
          break label163;
        }
        localObject = a(18);
        if (localObject == null)
        {
          localObject = jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
          return (MediaMetadata)localObject;
        }
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        continue;
        i = 0;
        j = 0;
        continue;
      }
      try
      {
        i = Integer.parseInt((String)localObject);
        localObject = a(19);
        if (localObject == null)
        {
          localObject = jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
          return (MediaMetadata)localObject;
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        continue;
      }
      try
      {
        j = Integer.parseInt((String)localObject);
      }
      catch (NumberFormatException localNumberFormatException3) {}
    }
    return jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
    return jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
    return new MediaMetadata(k, i, j, true);
    localObject = jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
    return (MediaMetadata)localObject;
  }
  
  private boolean a(String paramString)
  {
    return (paramString != null) && ((paramString.equalsIgnoreCase("localhost")) || (paramString.equalsIgnoreCase("localhost.localdomain")) || (paramString.equalsIgnoreCase("localhost6")) || (paramString.equalsIgnoreCase("localhost6.localdomain6")) || (paramString.toLowerCase(Locale.US).endsWith(".localhost")) || (paramString.equals("127.0.0.1")) || (paramString.equals("[::1]")));
  }
  
  @CalledByNative
  private static MediaMetadata extractMediaMetadata(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    paramString1 = SmttServiceClientProxy.getInstance().getProxyURL(paramString1);
    return new MediaResourceGetter().a(ContextUtils.getApplicationContext(), paramString1, paramString2, paramString3, paramString4);
  }
  
  @CalledByNative
  private static MediaMetadata extractMediaMetadataFromFd(int paramInt, long paramLong1, long paramLong2)
  {
    return new MediaResourceGetter().a(paramInt, paramLong1, paramLong2);
  }
  
  @VisibleForTesting
  File a(String paramString)
  {
    return new File(paramString);
  }
  
  @VisibleForTesting
  Integer a(Context paramContext)
  {
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (paramContext == null) {
      return null;
    }
    paramContext = paramContext.getActiveNetworkInfo();
    if (paramContext == null) {
      return null;
    }
    return Integer.valueOf(paramContext.getType());
  }
  
  @VisibleForTesting
  String a()
  {
    return PathUtils.getExternalStorageDirectory();
  }
  
  @VisibleForTesting
  String a(int paramInt)
  {
    return this.jdField_a_of_type_AndroidMediaMediaMetadataRetriever.extractMetadata(paramInt);
  }
  
  @VisibleForTesting
  MediaMetadata a(int paramInt, long paramLong1, long paramLong2)
  {
    a(paramInt, paramLong1, paramLong2);
    return a();
  }
  
  @VisibleForTesting
  MediaMetadata a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (!a(paramContext, paramString1, paramString2, paramString3, paramString4))
    {
      Log.e("cr_MediaResource", "Unable to configure metadata extractor", new Object[0]);
      return jdField_a_of_type_OrgChromiumContentBrowserMediaResourceGetter$MediaMetadata;
    }
    return a();
  }
  
  /* Error */
  @VisibleForTesting
  void a(int paramInt, long paramLong1, long paramLong2)
  {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic 238	org/chromium/tencent/utils/X5ApiCompatibilityUtils:adoptFd	(I)Landroid/os/ParcelFileDescriptor;
    //   4: astore 7
    //   6: aload_0
    //   7: getfield 29	org/chromium/content/browser/MediaResourceGetter:jdField_a_of_type_AndroidMediaMediaMetadataRetriever	Landroid/media/MediaMetadataRetriever;
    //   10: aload 7
    //   12: invokevirtual 244	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   15: lload_2
    //   16: lload 4
    //   18: invokevirtual 248	android/media/MediaMetadataRetriever:setDataSource	(Ljava/io/FileDescriptor;JJ)V
    //   21: aload 7
    //   23: invokevirtual 251	android/os/ParcelFileDescriptor:close	()V
    //   26: return
    //   27: astore 6
    //   29: ldc 124
    //   31: ldc -3
    //   33: iconst_1
    //   34: anewarray 4	java/lang/Object
    //   37: dup
    //   38: iconst_0
    //   39: aload 6
    //   41: aastore
    //   42: invokestatic 132	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   45: return
    //   46: astore 6
    //   48: aload 7
    //   50: invokevirtual 251	android/os/ParcelFileDescriptor:close	()V
    //   53: goto +21 -> 74
    //   56: astore 7
    //   58: ldc 124
    //   60: ldc -3
    //   62: iconst_1
    //   63: anewarray 4	java/lang/Object
    //   66: dup
    //   67: iconst_0
    //   68: aload 7
    //   70: aastore
    //   71: invokestatic 132	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   74: aload 6
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	MediaResourceGetter
    //   0	77	1	paramInt	int
    //   0	77	2	paramLong1	long
    //   0	77	4	paramLong2	long
    //   27	13	6	localIOException1	IOException
    //   46	29	6	localObject	Object
    //   4	45	7	localParcelFileDescriptor	android.os.ParcelFileDescriptor
    //   56	13	7	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   21	26	27	java/io/IOException
    //   6	21	46	finally
    //   48	53	56	java/io/IOException
  }
  
  @VisibleForTesting
  void a(Context paramContext, Uri paramUri)
  {
    this.jdField_a_of_type_AndroidMediaMediaMetadataRetriever.setDataSource(paramContext, paramUri);
  }
  
  @VisibleForTesting
  void a(String paramString)
  {
    this.jdField_a_of_type_AndroidMediaMediaMetadataRetriever.setDataSource(paramString);
  }
  
  @VisibleForTesting
  void a(String paramString, Map<String, String> paramMap)
  {
    this.jdField_a_of_type_AndroidMediaMediaMetadataRetriever.setDataSource(paramString, paramMap);
  }
  
  @VisibleForTesting
  boolean a(Context paramContext)
  {
    if (paramContext.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
      return false;
    }
    paramContext = a(paramContext);
    if (paramContext == null) {
      return false;
    }
    int i = paramContext.intValue();
    return (i == 1) || (i == 9);
  }
  
  @VisibleForTesting
  boolean a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      URI localURI = URI.create(paramString1);
      String str = localURI.getScheme();
      if ((str != null) && (!str.equals("file")))
      {
        if (str.equals("content")) {
          try
          {
            a(paramContext, Uri.parse(localURI.toString()));
            return true;
          }
          catch (RuntimeException paramContext)
          {
            Log.e("cr_MediaResource", "Error configuring data source: %s", new Object[] { paramContext });
            return false;
          }
        }
        if ((localURI.getPath() != null) && (localURI.getPath().endsWith(".m3u8"))) {
          return false;
        }
        if ((!a(localURI.getHost())) && (!a(paramContext))) {
          return false;
        }
        paramContext = new HashMap();
        if (!TextUtils.isEmpty(paramString2)) {
          paramContext.put("Cookie", paramString2);
        }
        if (!TextUtils.isEmpty(paramString3)) {
          paramContext.put("User-Agent", paramString3);
        }
        if (!TextUtils.isEmpty(paramString4)) {
          paramContext.put("Referer", paramString4);
        }
        try
        {
          a(paramString1, paramContext);
          return true;
        }
        catch (RuntimeException paramContext)
        {
          Log.e("cr_MediaResource", "Error configuring data source: %s", new Object[] { paramContext });
          return false;
        }
      }
      paramString1 = a(localURI.getPath());
      if (!paramString1.exists())
      {
        Log.e("cr_MediaResource", "File does not exist.", new Object[0]);
        return false;
      }
      if (!a(paramString1, paramContext))
      {
        Log.e("cr_MediaResource", "Refusing to read from unsafe file location.", new Object[0]);
        return false;
      }
      try
      {
        a(paramString1.getAbsolutePath());
        return true;
      }
      catch (RuntimeException paramContext)
      {
        Log.e("cr_MediaResource", "Error configuring data source: %s", new Object[] { paramContext });
        return false;
      }
      return false;
    }
    catch (IllegalArgumentException paramContext)
    {
      Log.e("cr_MediaResource", "Cannot parse uri: %s", new Object[] { paramContext });
    }
  }
  
  @VisibleForTesting
  boolean a(File paramFile, Context paramContext)
  {
    try
    {
      paramFile = paramFile.getCanonicalPath();
      paramContext = a(a(paramContext));
      String str = a();
      if (!TextUtils.isEmpty(str)) {
        paramContext.add(str);
      }
      paramContext = paramContext.iterator();
      while (paramContext.hasNext()) {
        if (paramFile.startsWith((String)paramContext.next())) {
          return true;
        }
      }
      return false;
    }
    catch (IOException paramFile) {}
    return false;
  }
  
  @VisibleForTesting
  static class MediaMetadata
  {
    private final int jdField_a_of_type_Int;
    private final boolean jdField_a_of_type_Boolean;
    private final int b;
    private final int c;
    
    MediaMetadata(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (MediaMetadata)paramObject;
      if (this.jdField_a_of_type_Int != ((MediaMetadata)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      if (this.c != ((MediaMetadata)paramObject).c) {
        return false;
      }
      if (this.jdField_a_of_type_Boolean != ((MediaMetadata)paramObject).jdField_a_of_type_Boolean) {
        return false;
      }
      return this.b == ((MediaMetadata)paramObject).b;
    }
    
    @CalledByNative("MediaMetadata")
    int getDurationInMilliseconds()
    {
      return this.jdField_a_of_type_Int;
    }
    
    @CalledByNative("MediaMetadata")
    int getHeight()
    {
      return this.c;
    }
    
    @CalledByNative("MediaMetadata")
    int getWidth()
    {
      return this.b;
    }
    
    public int hashCode()
    {
      int j = this.jdField_a_of_type_Int;
      int k = this.c;
      int i;
      if (this.jdField_a_of_type_Boolean) {
        i = 1231;
      } else {
        i = 1237;
      }
      return (((j + 31) * 31 + k) * 31 + i) * 31 + this.b;
    }
    
    @CalledByNative("MediaMetadata")
    boolean isSuccess()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MediaMetadata[durationInMilliseconds=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(", width=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", height=");
      localStringBuilder.append(this.c);
      localStringBuilder.append(", success=");
      localStringBuilder.append(this.jdField_a_of_type_Boolean);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\MediaResourceGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */