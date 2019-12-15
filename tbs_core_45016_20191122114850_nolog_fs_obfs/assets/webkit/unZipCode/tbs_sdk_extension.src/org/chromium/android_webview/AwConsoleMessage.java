package org.chromium.android_webview;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AwConsoleMessage
{
  public static final int MESSAGE_LEVEL_DEBUG = 4;
  public static final int MESSAGE_LEVEL_ERROR = 3;
  public static final int MESSAGE_LEVEL_LOG = 1;
  public static final int MESSAGE_LEVEL_TIP = 0;
  public static final int MESSAGE_LEVEL_WARNING = 2;
  private int jdField_a_of_type_Int;
  private String jdField_a_of_type_JavaLangString;
  private int jdField_b_of_type_Int;
  private String jdField_b_of_type_JavaLangString;
  
  public AwConsoleMessage(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.jdField_b_of_type_Int = paramInt1;
    this.jdField_a_of_type_Int = paramInt2;
  }
  
  public int lineNumber()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public String message()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public int messageLevel()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String sourceId()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L, 2L, 3L, 4L})
  public static @interface MessageLevel {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwConsoleMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */