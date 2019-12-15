package org.chromium.content.browser.selection;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.ActionMode;
import android.view.ActionMode.Callback2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.base.PackageUtils;
import org.chromium.base.ThreadUtils;

public final class LGEmailActionModeWorkaround
{
  public static final int LGEmailWorkaroundMaxVersion = 67502100;
  
  private static Object a(Object paramObject, String paramString)
    throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException
  {
    paramString = paramObject.getClass().getDeclaredField(paramString);
    paramString.setAccessible(true);
    return paramString.get(paramObject);
  }
  
  @TargetApi(23)
  private static void a(ActionMode paramActionMode)
  {
    try
    {
      a(paramActionMode, "mCallback", new ActionMode.Callback2()
      {
        public boolean onActionItemClicked(ActionMode paramAnonymousActionMode, MenuItem paramAnonymousMenuItem)
        {
          return this.a.onActionItemClicked(paramAnonymousActionMode, paramAnonymousMenuItem);
        }
        
        public boolean onCreateActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return this.a.onCreateActionMode(paramAnonymousActionMode, paramAnonymousMenu);
        }
        
        public void onDestroyActionMode(final ActionMode paramAnonymousActionMode)
        {
          ThreadUtils.postOnUiThread(new Runnable()
          {
            public void run()
            {
              LGEmailActionModeWorkaround.1.this.a.onDestroyActionMode(paramAnonymousActionMode);
            }
          });
        }
        
        public boolean onPrepareActionMode(ActionMode paramAnonymousActionMode, Menu paramAnonymousMenu)
        {
          return this.a.onPrepareActionMode(paramAnonymousActionMode, paramAnonymousMenu);
        }
      });
      Object localObject = a(paramActionMode, "mFloatingToolbar");
      paramActionMode = a(localObject, "mPopup");
      final ViewGroup localViewGroup = (ViewGroup)a(paramActionMode, "mContentContainer");
      PopupWindow localPopupWindow = (PopupWindow)a(paramActionMode, "mPopupWindow");
      localObject = localObject.getClass().getDeclaredMethod("createExitAnimation", new Class[] { View.class, Integer.TYPE, Animator.AnimatorListener.class });
      ((Method)localObject).setAccessible(true);
      a(paramActionMode, "mDismissAnimation", ((Method)localObject).invoke(null, new Object[] { localViewGroup, Integer.valueOf(150), new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          ThreadUtils.postOnUiThread(new Runnable()
          {
            public void run()
            {
              LGEmailActionModeWorkaround.2.this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
              LGEmailActionModeWorkaround.2.this.jdField_a_of_type_AndroidViewViewGroup.removeAllViews();
            }
          });
        }
      } }));
      return;
    }
    catch (NoSuchFieldException|IllegalAccessException|IllegalArgumentException|NoSuchMethodException|InvocationTargetException|Exception paramActionMode) {}
  }
  
  private static void a(Object paramObject1, String paramString, Object paramObject2)
    throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException
  {
    paramString = paramObject1.getClass().getDeclaredField(paramString);
    paramString.setAccessible(true);
    paramString.set(paramObject1, paramObject2);
  }
  
  private static boolean a(Context paramContext)
  {
    String str = paramContext.getPackageName();
    int i = PackageUtils.getPackageVersion(paramContext, str);
    if (i == -1) {
      return false;
    }
    int j = paramContext.getApplicationInfo().targetSdkVersion;
    if (j >= 23)
    {
      if (j > 24) {
        return false;
      }
      if (!"com.lge.email".equals(str)) {
        return false;
      }
      return i <= 67502100;
    }
    return false;
  }
  
  public static void runIfNecessary(Context paramContext, ActionMode paramActionMode)
  {
    if (a(paramContext)) {
      a(paramActionMode);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\LGEmailActionModeWorkaround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */