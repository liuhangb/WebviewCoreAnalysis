package org.chromium.components.signin;

import org.chromium.base.annotations.CalledByNative;

public final class ChildAccountInfoFetcher
{
  private final long jdField_a_of_type_Long;
  private final String jdField_a_of_type_JavaLangString;
  
  private ChildAccountInfoFetcher(long paramLong, String paramString1, String paramString2)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_JavaLangString = paramString1;
  }
  
  @CalledByNative
  private static ChildAccountInfoFetcher create(long paramLong, String paramString1, String paramString2)
  {
    return new ChildAccountInfoFetcher(paramLong, paramString1, paramString2);
  }
  
  @CalledByNative
  private void destroy() {}
  
  @CalledByNative
  private static void initializeForTests() {}
  
  private static native void nativeSetIsChildAccount(long paramLong, String paramString, boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\signin\ChildAccountInfoFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */