package com.tencent.tbs.common.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.ViewStub;
import java.lang.reflect.Constructor;
import java.util.HashMap;

final class PluginLayoutInflater
  extends LayoutInflater
  implements LayoutInflaterProxy.InflaterImpl
{
  private static final String TAG = "PluginLayoutInflater";
  private static final String[] sClassPrefixList = { "android.widget.", "android.webkit." };
  static final Class<?>[] sConstructorSignature = { Context.class, AttributeSet.class };
  private final Object[] mConstructorArgs = new Object[2];
  private final HashMap<String, Constructor<? extends View>> mConstructorMap = new HashMap();
  private HashMap<String, Boolean> mFilterMap;
  
  protected PluginLayoutInflater(Context paramContext)
  {
    this(paramContext, new LayoutInflaterProxy(paramContext));
  }
  
  private PluginLayoutInflater(Context paramContext, LayoutInflaterProxy paramLayoutInflaterProxy)
  {
    super(paramLayoutInflaterProxy, paramContext);
    paramLayoutInflaterProxy.setInflaterImpl(this);
  }
  
  protected PluginLayoutInflater(PluginLayoutInflater paramPluginLayoutInflater, Context paramContext)
  {
    super(paramPluginLayoutInflater, paramContext);
  }
  
  private View createViewFromParent(String paramString, AttributeSet paramAttributeSet)
    throws ClassNotFoundException
  {
    if (-1 == paramString.indexOf('.')) {
      return onCreateView(paramString, paramAttributeSet);
    }
    return createView(paramString, null, paramAttributeSet);
  }
  
  @SuppressLint({"InlinedApi", "NewApi"})
  private View createViewInternal(String paramString, AttributeSet paramAttributeSet)
    throws ClassNotFoundException, InflateException
  {
    synchronized (this.mConstructorArgs)
    {
      Object localObject1 = this.mConstructorArgs;
      Object localObject2 = getContext();
      boolean bool2 = false;
      localObject1[0] = localObject2;
      Constructor localConstructor = (Constructor)this.mConstructorMap.get(paramString);
      Object localObject5 = null;
      Object localObject4 = null;
      localObject1 = localObject5;
      try
      {
        LayoutInflater.Filter localFilter = getFilter();
        if (localConstructor == null)
        {
          localObject1 = localObject5;
          localObject4 = getClass().getClassLoader().loadClass(paramString).asSubclass(View.class);
          if ((localFilter != null) && (localObject4 != null))
          {
            localObject1 = localObject4;
            if (!localFilter.onLoadClass((Class)localObject4))
            {
              localObject1 = localObject4;
              failNotAllowed(paramString, paramAttributeSet);
            }
          }
          localObject3 = localConstructor;
          localObject2 = localObject4;
          if (localObject4 != null)
          {
            localObject1 = localObject4;
            localObject3 = ((Class)localObject4).getConstructor(sConstructorSignature);
            localObject1 = localObject4;
            this.mConstructorMap.put(paramString, localObject3);
            localObject2 = localObject4;
          }
        }
        else
        {
          localObject3 = localConstructor;
          localObject2 = localObject4;
          if (localFilter != null)
          {
            localObject1 = localObject5;
            Boolean localBoolean = (Boolean)this.mFilterMap.get(paramString);
            if (localBoolean == null)
            {
              localObject1 = localObject5;
              localObject4 = getClass().getClassLoader().loadClass(paramString).asSubclass(View.class);
              boolean bool1 = bool2;
              if (localObject4 != null)
              {
                bool1 = bool2;
                localObject1 = localObject4;
                if (localFilter.onLoadClass((Class)localObject4)) {
                  bool1 = true;
                }
              }
              localObject1 = localObject4;
              this.mFilterMap.put(paramString, Boolean.valueOf(bool1));
              localObject3 = localConstructor;
              localObject2 = localObject4;
              if (!bool1)
              {
                localObject1 = localObject4;
                failNotAllowed(paramString, paramAttributeSet);
                localObject3 = localConstructor;
                localObject2 = localObject4;
              }
            }
            else
            {
              localObject3 = localConstructor;
              localObject2 = localObject4;
              localObject1 = localObject5;
              if (localBoolean.equals(Boolean.FALSE))
              {
                localObject1 = localObject5;
                failNotAllowed(paramString, paramAttributeSet);
                localObject2 = localObject4;
                localObject3 = localConstructor;
              }
            }
          }
        }
        localObject1 = localObject2;
        localObject4 = this.mConstructorArgs;
        localObject4[1] = paramAttributeSet;
        localObject1 = localObject2;
        localObject3 = (View)((Constructor)localObject3).newInstance((Object[])localObject4);
        localObject1 = localObject2;
        if (Build.VERSION.SDK_INT >= 16)
        {
          localObject1 = localObject2;
          if ((localObject3 instanceof ViewStub))
          {
            localObject1 = localObject2;
            ((ViewStub)localObject3).setLayoutInflater(this);
          }
        }
        return (View)localObject3;
      }
      catch (Exception localException)
      {
        Object localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(paramAttributeSet.getPositionDescription());
        ((StringBuilder)localObject3).append(": Error inflating class ");
        if (localObject1 == null) {
          paramString = "<unknown>";
        } else {
          paramString = ((Class)localObject1).getName();
        }
        ((StringBuilder)localObject3).append(paramString);
        paramString = new InflateException(((StringBuilder)localObject3).toString());
        paramString.initCause(localException);
        throw paramString;
      }
      catch (ClassNotFoundException paramString)
      {
        throw paramString;
      }
      catch (ClassCastException localClassCastException)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramAttributeSet.getPositionDescription());
        localStringBuilder.append(": Class is not a View ");
        localStringBuilder.append(paramString);
        paramString = new InflateException(localStringBuilder.toString());
        paramString.initCause(localClassCastException);
        throw paramString;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramAttributeSet.getPositionDescription());
        localStringBuilder.append(": Error inflating class ");
        localStringBuilder.append(paramString);
        paramString = new InflateException(localStringBuilder.toString());
        paramString.initCause(localNoSuchMethodException);
        throw paramString;
      }
    }
  }
  
  private void failNotAllowed(String paramString, AttributeSet paramAttributeSet)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramAttributeSet.getPositionDescription());
    localStringBuilder.append(": Class not allowed to be inflated ");
    localStringBuilder.append(paramString);
    throw new InflateException(localStringBuilder.toString());
  }
  
  public LayoutInflater cloneInContext(Context paramContext)
  {
    return new PluginLayoutInflater(this, paramContext);
  }
  
  public View createViewImpl(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    throws ClassNotFoundException
  {
    if (-1 != paramString.indexOf('.')) {}
    try
    {
      paramView = createViewInternal(paramString, paramAttributeSet);
    }
    catch (Throwable paramView)
    {
      for (;;)
      {
        try
        {
          paramContext = createViewFromParent(paramString, paramAttributeSet);
          return paramContext;
        }
        catch (Throwable paramString) {}
        paramView = paramView;
      }
    }
    paramView = new StringBuilder();
    paramView.append("fail to create view internal for ");
    paramView.append(paramString);
    paramView.append(" with ");
    paramView.toString();
    paramView = null;
    paramContext = paramView;
    if (paramView == null) {}
    return paramView;
  }
  
  protected View onCreateView(String paramString, AttributeSet paramAttributeSet)
    throws ClassNotFoundException
  {
    String[] arrayOfString = sClassPrefixList;
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = arrayOfString[i];
      try
      {
        localObject = createView(paramString, (String)localObject, paramAttributeSet);
        if (localObject != null) {
          return (View)localObject;
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        for (;;) {}
      }
      i += 1;
    }
    return super.onCreateView(paramString, paramAttributeSet);
  }
  
  public void setFilter(LayoutInflater.Filter paramFilter)
  {
    super.setFilter(paramFilter);
    if (paramFilter != null) {
      this.mFilterMap = new HashMap();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\PluginLayoutInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */