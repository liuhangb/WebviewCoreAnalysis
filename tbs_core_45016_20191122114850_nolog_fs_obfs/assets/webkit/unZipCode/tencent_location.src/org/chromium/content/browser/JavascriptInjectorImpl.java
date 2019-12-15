package org.chromium.content.browser;

import android.util.Pair;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.JavascriptInjector;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("content")
public class JavascriptInjectorImpl
  extends JavascriptInjector
{
  private long jdField_a_of_type_Long = nativeInit(paramWebContents, this.jdField_a_of_type_JavaUtilSet);
  private final Map<String, Pair<Object, Class>> jdField_a_of_type_JavaUtilMap = new HashMap();
  private final Set<Object> jdField_a_of_type_JavaUtilSet = new HashSet();
  
  public JavascriptInjectorImpl(WebContents paramWebContents) {}
  
  public static JavascriptInjectorImpl fromWebContents(WebContents paramWebContents)
  {
    return (JavascriptInjectorImpl)WebContentsUserData.fromWebContents(paramWebContents, JavascriptInjectorImpl.class, UserDataFactoryLazyHolder.a());
  }
  
  private native void nativeAddInterface(long paramLong, Object paramObject, String paramString, Class paramClass);
  
  private native long nativeInit(WebContents paramWebContents, Object paramObject);
  
  private native void nativeInjectJavascript(long paramLong, String paramString);
  
  private native void nativeRemoveInterface(long paramLong, String paramString);
  
  private native void nativeSetAllowInspection(long paramLong, boolean paramBoolean);
  
  @CalledByNative
  private void onDestroy()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void addPossiblyUnsafeInterface(Object paramObject, String paramString, Class<? extends Annotation> paramClass)
  {
    if ((this.jdField_a_of_type_Long != 0L) && (paramObject != null))
    {
      this.jdField_a_of_type_JavaUtilMap.put(paramString, new Pair(paramObject, paramClass));
      nativeAddInterface(this.jdField_a_of_type_Long, paramObject, paramString, paramClass);
    }
  }
  
  public Map<String, Pair<Object, Class>> getInterfaces()
  {
    return this.jdField_a_of_type_JavaUtilMap;
  }
  
  public void injectJavascript(String paramString)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeInjectJavascript(l, paramString);
    }
  }
  
  public void removeInterface(String paramString)
  {
    this.jdField_a_of_type_JavaUtilMap.remove(paramString);
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeRemoveInterface(l, paramString);
    }
  }
  
  public void setAllowInspection(boolean paramBoolean)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSetAllowInspection(l, paramBoolean);
    }
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<JavascriptInjectorImpl> a = new WebContentsUserData.UserDataFactory()
    {
      public JavascriptInjectorImpl create(WebContents paramAnonymousWebContents)
      {
        return new JavascriptInjectorImpl(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\JavascriptInjectorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */