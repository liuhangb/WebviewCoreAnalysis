package org.chromium.android_webview;

public class AwRenderProcessGoneDetail
{
  private final int jdField_a_of_type_Int;
  private final boolean jdField_a_of_type_Boolean;
  
  public AwRenderProcessGoneDetail(boolean paramBoolean, int paramInt)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public boolean didCrash()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public int rendererPriority()
  {
    return this.jdField_a_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwRenderProcessGoneDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */