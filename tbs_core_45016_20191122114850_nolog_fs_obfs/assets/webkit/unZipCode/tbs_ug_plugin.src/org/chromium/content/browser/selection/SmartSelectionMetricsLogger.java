package org.chromium.content.browser.selection;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.chromium.content_public.browser.SelectionClient.Result;
import org.chromium.content_public.browser.SelectionMetricsLogger;

@TargetApi(26)
public class SmartSelectionMetricsLogger
  implements SelectionMetricsLogger
{
  private static Class<?> jdField_a_of_type_JavaLangClass;
  private static Constructor jdField_a_of_type_JavaLangReflectConstructor;
  private static Method jdField_a_of_type_JavaLangReflectMethod;
  private static boolean jdField_a_of_type_Boolean;
  private static Class<?> b;
  private Context jdField_a_of_type_AndroidContentContext;
  private Object jdField_a_of_type_JavaLangObject;
  private SelectionIndicesConverter jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter;
  private SelectionEventProxy jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy;
  
  private SmartSelectionMetricsLogger(Context paramContext, SelectionEventProxy paramSelectionEventProxy)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy = paramSelectionEventProxy;
  }
  
  public static SmartSelectionMetricsLogger create(Context paramContext)
  {
    if ((Build.VERSION.SDK_INT > 26) && (paramContext != null))
    {
      if (jdField_a_of_type_Boolean) {
        return null;
      }
      if (jdField_a_of_type_JavaLangClass != null) {}
    }
    try
    {
      jdField_a_of_type_JavaLangClass = Class.forName("android.view.textclassifier.logging.SmartSelectionEventTracker");
      b = Class.forName("android.view.textclassifier.logging.SmartSelectionEventTracker$SelectionEvent");
      jdField_a_of_type_JavaLangReflectConstructor = jdField_a_of_type_JavaLangClass.getConstructor(new Class[] { Context.class, Integer.TYPE });
      jdField_a_of_type_JavaLangReflectMethod = jdField_a_of_type_JavaLangClass.getMethod("logEvent", new Class[] { b });
    }
    catch (ClassNotFoundException|NoSuchMethodException paramContext)
    {
      SelectionEventProxyImpl localSelectionEventProxyImpl;
      for (;;) {}
    }
    jdField_a_of_type_Boolean = true;
    return null;
    localSelectionEventProxyImpl = SelectionEventProxyImpl.create();
    if (localSelectionEventProxyImpl == null) {
      return null;
    }
    return new SmartSelectionMetricsLogger(paramContext, localSelectionEventProxyImpl);
    return null;
  }
  
  public static boolean isTerminal(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return false;
    }
    return true;
  }
  
  public Object createTracker(Context paramContext, boolean paramBoolean)
  {
    try
    {
      Constructor localConstructor = jdField_a_of_type_JavaLangReflectConstructor;
      int i = 2;
      if (paramBoolean) {
        i = 4;
      }
      paramContext = localConstructor.newInstance(new Object[] { paramContext, Integer.valueOf(i) });
      return paramContext;
    }
    catch (ReflectiveOperationException paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public void logEvent(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    try
    {
      jdField_a_of_type_JavaLangReflectMethod.invoke(this.jdField_a_of_type_JavaLangObject, new Object[] { b.cast(paramObject) });
      return;
    }
    catch (ClassCastException|ReflectiveOperationException paramObject) {}
  }
  
  public void logSelectionAction(String paramString, int paramInt1, int paramInt2, SelectionClient.Result paramResult)
  {
    if (this.jdField_a_of_type_JavaLangObject == null) {
      return;
    }
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.updateSelectionState(paramString, paramInt1))
    {
      this.jdField_a_of_type_JavaLangObject = null;
      return;
    }
    int i = paramString.length();
    paramString = new int[2];
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.getWordDelta(paramInt1, i + paramInt1, paramString))
    {
      this.jdField_a_of_type_JavaLangObject = null;
      return;
    }
    if ((paramResult != null) && (paramResult.jdField_a_of_type_AndroidViewTextclassifierTextClassification != null)) {
      logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionAction(paramString[0], paramString[1], paramInt2, paramResult.jdField_a_of_type_AndroidViewTextclassifierTextClassification));
    } else {
      logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionAction(paramString[0], paramString[1], paramInt2));
    }
    if (isTerminal(paramInt2)) {
      this.jdField_a_of_type_JavaLangObject = null;
    }
  }
  
  public void logSelectionModified(String paramString, int paramInt, SelectionClient.Result paramResult)
  {
    if (this.jdField_a_of_type_JavaLangObject == null) {
      return;
    }
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.updateSelectionState(paramString, paramInt))
    {
      this.jdField_a_of_type_JavaLangObject = null;
      return;
    }
    int i = paramString.length();
    paramString = new int[2];
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.getWordDelta(paramInt, i + paramInt, paramString))
    {
      this.jdField_a_of_type_JavaLangObject = null;
      return;
    }
    if ((paramResult != null) && (paramResult.jdField_a_of_type_AndroidViewTextclassifierTextSelection != null))
    {
      logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionModifiedSelection(paramString[0], paramString[1], paramResult.jdField_a_of_type_AndroidViewTextclassifierTextSelection));
      return;
    }
    if ((paramResult != null) && (paramResult.jdField_a_of_type_AndroidViewTextclassifierTextClassification != null))
    {
      logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionModifiedClassification(paramString[0], paramString[1], paramResult.jdField_a_of_type_AndroidViewTextclassifierTextClassification));
      return;
    }
    logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionModified(paramString[0], paramString[1]));
  }
  
  public void logSelectionStarted(String paramString, int paramInt, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangObject = createTracker(this.jdField_a_of_type_AndroidContentContext, paramBoolean);
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter = new SelectionIndicesConverter();
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.updateSelectionState(paramString, paramInt);
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSelectionIndicesConverter.setInitialStartOffset(paramInt);
    logEvent(this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger$SelectionEventProxy.createSelectionStarted(0));
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({100L, 101L, 102L, 103L, 104L, 105L, 106L, 107L, 108L, 200L, 201L})
  public static @interface ActionType
  {
    public static final int ABANDON = 107;
    public static final int COPY = 101;
    public static final int CUT = 103;
    public static final int DRAG = 106;
    public static final int OTHER = 108;
    public static final int OVERTYPE = 100;
    public static final int PASTE = 102;
    public static final int RESET = 201;
    public static final int SELECT_ALL = 200;
    public static final int SHARE = 104;
    public static final int SMART_SHARE = 105;
  }
  
  public static abstract interface SelectionEventProxy
  {
    public abstract Object createSelectionAction(int paramInt1, int paramInt2, int paramInt3);
    
    public abstract Object createSelectionAction(int paramInt1, int paramInt2, int paramInt3, Object paramObject);
    
    public abstract Object createSelectionModified(int paramInt1, int paramInt2);
    
    public abstract Object createSelectionModifiedClassification(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract Object createSelectionModifiedSelection(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract Object createSelectionStarted(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SmartSelectionMetricsLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */