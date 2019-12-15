package com.tencent.smtt.util;

import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;

public class MttPerformTrace
{
  private static double jdField_a_of_type_Double = 0.0D;
  private static Choreographer.FrameCallback jdField_a_of_type_AndroidViewChoreographer$FrameCallback;
  private static Choreographer jdField_a_of_type_AndroidViewChoreographer;
  private static String jdField_a_of_type_JavaLangString;
  private static boolean jdField_a_of_type_Boolean = false;
  private static boolean b = false;
  private static boolean c = false;
  
  private static native void nativeJNIPerformTrackerBegin(String paramString1, String paramString2, String paramString3);
  
  private static native void nativeJNIPerformTrackerEnd(String paramString1, String paramString2, String paramString3);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\MttPerformTrace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */