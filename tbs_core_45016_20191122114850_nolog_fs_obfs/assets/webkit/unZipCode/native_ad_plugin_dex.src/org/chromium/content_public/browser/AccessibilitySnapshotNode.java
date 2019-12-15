package org.chromium.content_public.browser;

import java.util.ArrayList;

public class AccessibilitySnapshotNode
{
  public float a;
  public int a;
  public String a;
  public ArrayList<AccessibilitySnapshotNode> a;
  public boolean a;
  public int b;
  public String b;
  public boolean b;
  public int c;
  public boolean c;
  public int d;
  public boolean d;
  public int e;
  public boolean e;
  public int f;
  public boolean f;
  public int g;
  public boolean g;
  public int h;
  
  public AccessibilitySnapshotNode(String paramString1, String paramString2)
  {
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.jdField_g_of_type_Boolean = true;
    this.jdField_g_of_type_Int = paramInt1;
    this.h = paramInt2;
  }
  
  public void a(int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.jdField_e_of_type_Int = paramInt1;
    this.jdField_f_of_type_Int = paramInt2;
    this.jdField_a_of_type_Float = paramFloat;
    this.jdField_c_of_type_Boolean = paramBoolean1;
    this.jdField_d_of_type_Boolean = paramBoolean2;
    this.jdField_e_of_type_Boolean = paramBoolean3;
    this.jdField_f_of_type_Boolean = paramBoolean4;
    this.jdField_b_of_type_Boolean = true;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_c_of_type_Int = paramInt3;
    this.jdField_d_of_type_Int = paramInt4;
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void a(AccessibilitySnapshotNode paramAccessibilitySnapshotNode)
  {
    this.jdField_a_of_type_JavaUtilArrayList.add(paramAccessibilitySnapshotNode);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\AccessibilitySnapshotNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */