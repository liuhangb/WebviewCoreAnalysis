package com.tencent.common.utils;

public class TbsMode
{
  public static final String PR_DEFAULT = "TRD";
  public static final String PR_QB = "QB";
  public static final String PR_QQ = "QQ";
  public static final String PR_QZ = "QZ";
  public static final String PR_WX = "WX";
  public static int TBSMODE_QB_STATIC_QBTBTS = 20;
  public static int TBSMODE_TBS_HOST_NOSHARE_TBS = 2;
  public static int TBSMODE_TBS_HOST_SHARE_TBS = 1;
  public static int TBSMODE_TBS_THIRD = 10;
  public static final String TBS_QQ_PACKAGE_NAME = "com.tencent.mobileqq";
  public static final String TBS_QZONE_PACKAGE_NAME = "com.qzone";
  public static final String TBS_WX_PACKAGE_NAME = "com.tencent.mm";
  private static int jdField_a_of_type_Int = TBSMODE_TBS_HOST_SHARE_TBS;
  private static String jdField_a_of_type_JavaLangString = "TRD";
  private static boolean jdField_a_of_type_Boolean = false;
  private static String b = "com.tencent.mtt";
  
  public static String PR()
  {
    return jdField_a_of_type_JavaLangString;
  }
  
  public static String QB_PKGNAME()
  {
    return b;
  }
  
  public static boolean TBSISQB()
  {
    return jdField_a_of_type_Boolean;
  }
  
  public static int TBS_MODE()
  {
    return jdField_a_of_type_Int;
  }
  
  public static void initForTbsMode(int paramInt, String paramString)
  {
    jdField_a_of_type_Int = paramInt;
    jdField_a_of_type_JavaLangString = paramString;
  }
  
  public static boolean thirdTbsMode()
  {
    return jdField_a_of_type_Int == TBSMODE_TBS_THIRD;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\TbsMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */