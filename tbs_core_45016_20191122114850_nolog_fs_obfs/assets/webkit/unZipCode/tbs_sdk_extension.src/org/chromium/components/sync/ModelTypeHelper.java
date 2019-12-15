package org.chromium.components.sync;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("syncer")
public class ModelTypeHelper
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static final int[] jdField_a_of_type_ArrayOfInt = { 37 };
  
  private static native String nativeModelTypeToNotificationType(int paramInt);
  
  public static abstract interface TestDelegate
  {
    public abstract String toNotificationType(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\sync\ModelTypeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */