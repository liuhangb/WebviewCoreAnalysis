package org.chromium.content.browser.selection;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextSelection;
import java.lang.reflect.Method;

@TargetApi(26)
public class SelectionEventProxyImpl
  implements SmartSelectionMetricsLogger.SelectionEventProxy
{
  private static Class<?> jdField_a_of_type_JavaLangClass;
  private static Method jdField_a_of_type_JavaLangReflectMethod;
  private static boolean jdField_a_of_type_Boolean = false;
  private static Method b;
  private static Method c;
  private static Method d;
  private static Method e;
  private static Method f;
  
  public static SelectionEventProxyImpl create()
  {
    if (jdField_a_of_type_Boolean) {
      return null;
    }
    if (jdField_a_of_type_JavaLangClass == null) {}
    try
    {
      jdField_a_of_type_JavaLangClass = Class.forName("android.view.textclassifier.logging.SmartSelectionEventTracker$SelectionEvent");
      jdField_a_of_type_JavaLangReflectMethod = jdField_a_of_type_JavaLangClass.getMethod("selectionStarted", new Class[] { Integer.TYPE });
      b = jdField_a_of_type_JavaLangClass.getMethod("selectionModified", new Class[] { Integer.TYPE, Integer.TYPE });
      c = jdField_a_of_type_JavaLangClass.getMethod("selectionModified", new Class[] { Integer.TYPE, Integer.TYPE, TextClassification.class });
      d = jdField_a_of_type_JavaLangClass.getMethod("selectionModified", new Class[] { Integer.TYPE, Integer.TYPE, TextSelection.class });
      e = jdField_a_of_type_JavaLangClass.getMethod("selectionAction", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
      f = jdField_a_of_type_JavaLangClass.getMethod("selectionAction", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, TextClassification.class });
    }
    catch (ClassNotFoundException|NoSuchMethodException localClassNotFoundException)
    {
      for (;;) {}
    }
    jdField_a_of_type_Boolean = true;
    return null;
    return new SelectionEventProxyImpl();
  }
  
  public Object createSelectionAction(int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      Object localObject = e.invoke(null, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
      return localObject;
    }
    catch (ReflectiveOperationException localReflectiveOperationException) {}
    return null;
  }
  
  public Object createSelectionAction(int paramInt1, int paramInt2, int paramInt3, @NonNull Object paramObject)
  {
    try
    {
      paramObject = f.invoke(null, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), (TextClassification)paramObject });
      return paramObject;
    }
    catch (ReflectiveOperationException paramObject) {}
    return null;
  }
  
  public Object createSelectionModified(int paramInt1, int paramInt2)
  {
    try
    {
      Object localObject = b.invoke(null, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
      return localObject;
    }
    catch (ReflectiveOperationException localReflectiveOperationException) {}
    return null;
  }
  
  public Object createSelectionModifiedClassification(int paramInt1, int paramInt2, @NonNull Object paramObject)
  {
    try
    {
      paramObject = c.invoke(null, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), (TextClassification)paramObject });
      return paramObject;
    }
    catch (ReflectiveOperationException paramObject) {}
    return null;
  }
  
  public Object createSelectionModifiedSelection(int paramInt1, int paramInt2, @NonNull Object paramObject)
  {
    try
    {
      paramObject = d.invoke(null, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), (TextSelection)paramObject });
      return paramObject;
    }
    catch (ReflectiveOperationException paramObject) {}
    return null;
  }
  
  public Object createSelectionStarted(int paramInt)
  {
    try
    {
      Object localObject = jdField_a_of_type_JavaLangReflectMethod.invoke(null, new Object[] { Integer.valueOf(paramInt) });
      return localObject;
    }
    catch (ReflectiveOperationException localReflectiveOperationException) {}
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SelectionEventProxyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */