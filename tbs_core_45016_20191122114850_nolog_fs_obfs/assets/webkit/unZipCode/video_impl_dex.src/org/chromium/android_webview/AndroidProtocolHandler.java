package org.chromium.android_webview;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AndroidProtocolHandler
{
  private static int a(int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    ContextUtils.getApplicationContext().getResources().getValue(paramInt, localTypedValue, true);
    return localTypedValue.type;
  }
  
  private static int a(String paramString1, String paramString2)
    throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException
  {
    String str = ContextUtils.getApplicationContext().getPackageName();
    Object localObject;
    try
    {
      localObject = a(str, paramString1);
    }
    catch (ClassNotFoundException localClassNotFoundException2)
    {
      localObject = null;
    }
    while (localObject == null)
    {
      str = a(str);
      if (str != null) {}
      try
      {
        Class localClass = a(str, paramString1);
        localObject = localClass;
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        for (;;) {}
      }
      continue;
      throw localClassNotFoundException2;
    }
    return ((Class)localObject).getField(paramString2).getInt(null);
  }
  
  private static Uri a(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Object localObject = Uri.parse(paramString);
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Malformed URL: ");
      ((StringBuilder)localObject).append(paramString);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject).toString());
      return null;
    }
    String str = ((Uri)localObject).getPath();
    if ((str != null) && (str.length() != 0)) {
      return (Uri)localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("URL does not have a path: ");
    ((StringBuilder)localObject).append(paramString);
    Log.e("AndroidProtocolHandler", ((StringBuilder)localObject).toString());
    return null;
  }
  
  private static InputStream a(Uri paramUri)
  {
    if ((!a) && (!paramUri.getScheme().equals("file"))) {
      throw new AssertionError();
    }
    if ((!a) && (paramUri.getPath() == null)) {
      throw new AssertionError();
    }
    if ((!a) && (!paramUri.getPath().startsWith(nativeGetAndroidResourcePath()))) {
      throw new AssertionError();
    }
    Object localObject3 = paramUri.getPathSegments();
    if (((List)localObject3).size() != 3)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Incorrect resource path: ");
      ((StringBuilder)localObject1).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject1).toString());
      return null;
    }
    Object localObject2 = (String)((List)localObject3).get(0);
    Object localObject1 = (String)((List)localObject3).get(1);
    localObject3 = (String)((List)localObject3).get(2);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/");
    localStringBuilder.append((String)localObject2);
    localStringBuilder.append("/");
    if (!localStringBuilder.toString().equals(nativeGetAndroidResourcePath()))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Resource path does not start with ");
      ((StringBuilder)localObject1).append(nativeGetAndroidResourcePath());
      ((StringBuilder)localObject1).append(": ");
      ((StringBuilder)localObject1).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject1).toString());
      return null;
    }
    localObject2 = localObject3.split("\\.")[0];
    try
    {
      int i = a((String)localObject1, (String)localObject2);
      if (a(i) == 3) {
        return ContextUtils.getApplicationContext().getResources().openRawResource(i);
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Asset not of type string: ");
      ((StringBuilder)localObject1).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject1).toString());
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Unable to open resource URL: ");
      ((StringBuilder)localObject2).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject2).toString(), localIllegalAccessException);
      return null;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Unable to open resource URL: ");
      ((StringBuilder)localObject2).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject2).toString(), localNoSuchFieldException);
      return null;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Unable to open resource URL: ");
      ((StringBuilder)localObject2).append(paramUri);
      Log.e("AndroidProtocolHandler", ((StringBuilder)localObject2).toString(), localClassNotFoundException);
    }
    return null;
  }
  
  private static Class<?> a(String paramString1, String paramString2)
    throws ClassNotFoundException
  {
    ClassLoader localClassLoader = ContextUtils.getApplicationContext().getClassLoader();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(".R$");
    localStringBuilder.append(paramString2);
    return localClassLoader.loadClass(localStringBuilder.toString());
  }
  
  private static String a(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    int i = paramString.lastIndexOf('.');
    if (i == -1) {
      return null;
    }
    return paramString.substring(0, i);
  }
  
  private static InputStream b(Uri paramUri)
  {
    if ((!a) && (!paramUri.getScheme().equals("file"))) {
      throw new AssertionError();
    }
    if ((!a) && (paramUri.getPath() == null)) {
      throw new AssertionError();
    }
    if ((!a) && (!paramUri.getPath().startsWith(nativeGetAndroidAssetPath()))) {
      throw new AssertionError();
    }
    Object localObject = paramUri.getPath().replaceFirst(nativeGetAndroidAssetPath(), "");
    try
    {
      localObject = ContextUtils.getApplicationContext().getAssets().open((String)localObject, 2);
      return (InputStream)localObject;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unable to open asset URL: ");
    ((StringBuilder)localObject).append(paramUri);
    Log.e("AndroidProtocolHandler", ((StringBuilder)localObject).toString());
    return null;
  }
  
  private static InputStream c(Uri paramUri)
  {
    if ((!a) && (!paramUri.getScheme().equals("content"))) {
      throw new AssertionError();
    }
    try
    {
      localObject = ContextUtils.getApplicationContext().getContentResolver().openInputStream(paramUri);
      return (InputStream)localObject;
    }
    catch (Exception localException)
    {
      Object localObject;
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unable to open content URL: ");
    ((StringBuilder)localObject).append(paramUri);
    Log.e("AndroidProtocolHandler", ((StringBuilder)localObject).toString());
    return null;
  }
  
  @CalledByNative
  public static String getMimeType(InputStream paramInputStream, String paramString)
  {
    Object localObject = a(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      String str = ((Uri)localObject).getPath();
      if (((Uri)localObject).getScheme().equals("content")) {
        return ContextUtils.getApplicationContext().getContentResolver().getType((Uri)localObject);
      }
      if ((((Uri)localObject).getScheme().equals("file")) && (str.startsWith(nativeGetAndroidAssetPath())))
      {
        localObject = URLConnection.guessContentTypeFromName(str);
        if (localObject != null) {
          return (String)localObject;
        }
      }
    }
    catch (Exception paramInputStream)
    {
      for (;;) {}
    }
    try
    {
      paramInputStream = URLConnection.guessContentTypeFromStream(paramInputStream);
      return paramInputStream;
    }
    catch (IOException paramInputStream) {}
    paramInputStream = new StringBuilder();
    paramInputStream.append("Unable to get mime type");
    paramInputStream.append(paramString);
    Log.e("AndroidProtocolHandler", paramInputStream.toString());
    return null;
    return null;
  }
  
  private static native String nativeGetAndroidAssetPath();
  
  private static native String nativeGetAndroidResourcePath();
  
  @CalledByNative
  public static InputStream open(String paramString)
  {
    Object localObject = a(paramString);
    if (localObject == null) {
      return null;
    }
    try
    {
      String str = ((Uri)localObject).getPath();
      if (((Uri)localObject).getScheme().equals("file"))
      {
        if (str.startsWith(nativeGetAndroidAssetPath())) {
          return b((Uri)localObject);
        }
        if (!str.startsWith(nativeGetAndroidResourcePath())) {
          break label109;
        }
        return a((Uri)localObject);
      }
      if (!((Uri)localObject).getScheme().equals("content")) {
        break label109;
      }
      localObject = c((Uri)localObject);
      return (InputStream)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Error opening inputstream: ");
    ((StringBuilder)localObject).append(paramString);
    Log.e("AndroidProtocolHandler", ((StringBuilder)localObject).toString());
    label109:
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AndroidProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */