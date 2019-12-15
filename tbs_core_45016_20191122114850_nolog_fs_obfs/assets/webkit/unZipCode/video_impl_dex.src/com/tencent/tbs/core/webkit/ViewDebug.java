package com.tencent.tbs.core.webkit;

import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.view.ViewDebug.IntToString;
import java.io.BufferedWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ViewDebug
{
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
  public static @interface ExportedProperty
  {
    String category() default "";
    
    boolean deepExport() default false;
    
    ViewDebug.FlagToString[] flagMapping() default {};
    
    boolean formatToHexString() default false;
    
    boolean hasAdjacentMapping() default false;
    
    ViewDebug.IntToString[] indexMapping() default {};
    
    ViewDebug.IntToString[] mapping() default {};
    
    String prefix() default "";
    
    boolean resolveId() default false;
  }
  
  public static abstract interface HierarchyHandler
  {
    public abstract void dumpViewHierarchyWithProperties(BufferedWriter paramBufferedWriter, int paramInt);
    
    public abstract View findHierarchyView(String paramString, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\ViewDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */