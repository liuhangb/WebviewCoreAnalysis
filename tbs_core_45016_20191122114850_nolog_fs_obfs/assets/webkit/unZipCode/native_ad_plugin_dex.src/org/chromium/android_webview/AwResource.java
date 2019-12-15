package org.chromium.android_webview;

import android.content.res.Resources;
import android.util.SparseArray;
import java.lang.ref.SoftReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.SharedResource;

@JNINamespace("android_webview::AwResource")
public class AwResource
{
  private static int jdField_a_of_type_Int;
  private static Resources jdField_a_of_type_AndroidContentResResources;
  private static SparseArray<SoftReference<String>> jdField_a_of_type_AndroidUtilSparseArray;
  
  @CalledByNative
  private static String[] getConfigKeySystemUuidMapping()
  {
    return new String[] { "x-com.oem.test-keysystem,EDEF8BA9-79D6-4ACE-A3C8-27DCD51D21ED", "com.oem.test-keysystem,EDEF8BA9-79D6-4ACE-A3C8-27DCD51D21ED" };
  }
  
  @CalledByNative
  public static String[] getKeywordsForPrereading()
  {
    return SharedResource.getStringArray("x5_next_page_desc", "array");
  }
  
  public static void setConfigKeySystemUuidMapping(int paramInt)
  {
    jdField_a_of_type_Int = paramInt;
  }
  
  public static void setResources(Resources paramResources)
  {
    jdField_a_of_type_AndroidContentResResources = paramResources;
    jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */