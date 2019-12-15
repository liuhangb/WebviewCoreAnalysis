package org.chromium.ui.base;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ApplicationStatus;
import org.chromium.base.ApplicationStatus.ActivityStateListener;
import org.chromium.base.Callback;
import org.chromium.base.ContextUtils;
import org.chromium.ui.UiUtils;

public class ActivityWindowAndroid
  extends WindowAndroid
  implements View.OnLayoutChangeListener, ApplicationStatus.ActivityStateListener
{
  private int jdField_a_of_type_Int;
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final SparseArray<WindowAndroid.PermissionCallback> b;
  
  public ActivityWindowAndroid(Context paramContext)
  {
    this(paramContext, true);
  }
  
  public ActivityWindowAndroid(Context paramContext, boolean paramBoolean)
  {
    super(paramContext);
    paramContext = activityFromContext(paramContext);
    if (paramContext != null)
    {
      this.jdField_a_of_type_AndroidOsHandler = new Handler();
      this.jdField_b_of_type_AndroidUtilSparseArray = new SparseArray();
      if (paramBoolean) {
        ApplicationStatus.registerStateListenerForActivity(this, paramContext);
      }
      setAndroidPermissionDelegate(new ActivityAndroidPermissionDelegate(null));
      return;
    }
    throw new IllegalArgumentException("Context is not and does not wrap an Activity");
  }
  
  private int a()
  {
    int i = this.jdField_a_of_type_Int;
    this.jdField_a_of_type_Int = ((i + 1) % 100);
    return i + 1000;
  }
  
  private String a(String paramString)
  {
    String str1 = paramString;
    if (Build.VERSION.SDK_INT < 26) {}
    try
    {
      PermissionInfo localPermissionInfo = getApplicationContext().getPackageManager().getPermissionInfo(paramString, 128);
      str1 = paramString;
      if (!TextUtils.isEmpty(localPermissionInfo.group)) {
        str1 = localPermissionInfo.group;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        String str2 = paramString;
      }
    }
    paramString = new StringBuilder();
    paramString.append("HasRequestedAndroidPermission::");
    paramString.append(str1);
    return paramString.toString();
  }
  
  private void a(int paramInt, WindowAndroid.IntentCallback paramIntentCallback, Integer paramInteger)
  {
    this.jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, paramIntentCallback);
    HashMap localHashMap = this.jdField_a_of_type_JavaUtilHashMap;
    if (paramInteger == null) {
      paramIntentCallback = null;
    } else {
      paramIntentCallback = this.jdField_a_of_type_AndroidContentContext.getString(paramInteger.intValue());
    }
    localHashMap.put(Integer.valueOf(paramInt), paramIntentCallback);
  }
  
  protected void a()
  {
    Object localObject = (Activity)getActivity().get();
    if (localObject == null) {
      return;
    }
    localObject = ((Activity)localObject).findViewById(16908290);
    this.jdField_b_of_type_Boolean = UiUtils.a((Context)getActivity().get(), (View)localObject);
    ((View)localObject).addOnLayoutChangeListener(this);
  }
  
  protected void a(String paramString) {}
  
  protected void b()
  {
    Activity localActivity = (Activity)getActivity().get();
    if (localActivity == null) {
      return;
    }
    localActivity.findViewById(16908290).removeOnLayoutChangeListener(this);
  }
  
  public void cancelIntent(int paramInt)
  {
    Activity localActivity = (Activity)getActivity().get();
    if (localActivity == null) {
      return;
    }
    localActivity.finishActivity(paramInt);
  }
  
  public WeakReference<Activity> getActivity()
  {
    return new WeakReference(activityFromContext((Context)getContext().get()));
  }
  
  public void onActivityStateChange(Activity paramActivity, int paramInt)
  {
    if (paramInt == 5)
    {
      c();
      return;
    }
    if (paramInt == 2) {
      d();
    }
  }
  
  public void onLayoutChange(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    a(UiUtils.a((Context)getActivity().get(), paramView));
  }
  
  public int showCancelableIntent(PendingIntent paramPendingIntent, WindowAndroid.IntentCallback paramIntentCallback, Integer paramInteger)
  {
    Activity localActivity = (Activity)getActivity().get();
    if (localActivity == null) {
      return -1;
    }
    int i = a();
    try
    {
      localActivity.startIntentSenderForResult(paramPendingIntent.getIntentSender(), i, new Intent(), 0, 0, 0);
      a(i, paramIntentCallback, paramInteger);
      return i;
    }
    catch (IntentSender.SendIntentException paramPendingIntent) {}
    return -1;
  }
  
  public int showCancelableIntent(Intent paramIntent, WindowAndroid.IntentCallback paramIntentCallback, Integer paramInteger)
  {
    Activity localActivity = (Activity)getActivity().get();
    if (localActivity == null) {
      return -1;
    }
    int i = a();
    try
    {
      localActivity.startActivityForResult(paramIntent, i);
      a(i, paramIntentCallback, paramInteger);
      return i;
    }
    catch (ActivityNotFoundException paramIntent) {}
    return -1;
  }
  
  public int showCancelableIntent(Callback<Integer> paramCallback, WindowAndroid.IntentCallback paramIntentCallback, Integer paramInteger)
  {
    if ((Activity)getActivity().get() == null) {
      return -1;
    }
    int i = a();
    paramCallback.onResult(Integer.valueOf(i));
    a(i, paramIntentCallback, paramInteger);
    return i;
  }
  
  private class ActivityAndroidPermissionDelegate
    implements AndroidPermissionDelegate
  {
    private ActivityAndroidPermissionDelegate() {}
    
    private boolean a(String[] paramArrayOfString, WindowAndroid.PermissionCallback paramPermissionCallback)
    {
      if (Build.VERSION.SDK_INT < 23) {
        return false;
      }
      Activity localActivity = (Activity)ActivityWindowAndroid.this.getActivity().get();
      if (localActivity == null) {
        return false;
      }
      int i = ActivityWindowAndroid.a(ActivityWindowAndroid.this);
      ActivityWindowAndroid.a(ActivityWindowAndroid.this).put(i, paramPermissionCallback);
      localActivity.requestPermissions(paramArrayOfString, i);
      return true;
    }
    
    public boolean canRequestPermission(String paramString)
    {
      if (Build.VERSION.SDK_INT < 23) {
        return false;
      }
      Object localObject = (Activity)ActivityWindowAndroid.this.getActivity().get();
      if (localObject == null) {
        return false;
      }
      if (isPermissionRevokedByPolicy(paramString)) {
        return false;
      }
      if (((Activity)localObject).shouldShowRequestPermissionRationale(paramString)) {
        return true;
      }
      localObject = ActivityWindowAndroid.a(ActivityWindowAndroid.this, paramString);
      if (!ContextUtils.getAppSharedPreferences().getBoolean((String)localObject, false)) {
        return true;
      }
      ActivityWindowAndroid.this.a(paramString);
      return false;
    }
    
    public boolean hasPermission(String paramString)
    {
      return ApiCompatibilityUtils.checkPermission(ActivityWindowAndroid.this.a, paramString, Process.myPid(), Process.myUid()) == 0;
    }
    
    public boolean isPermissionRevokedByPolicy(String paramString)
    {
      if (Build.VERSION.SDK_INT < 23) {
        return false;
      }
      Activity localActivity = (Activity)ActivityWindowAndroid.this.getActivity().get();
      if (localActivity == null) {
        return false;
      }
      return localActivity.getPackageManager().isPermissionRevokedByPolicy(paramString, localActivity.getPackageName());
    }
    
    public void requestPermissions(final String[] paramArrayOfString, final WindowAndroid.PermissionCallback paramPermissionCallback)
    {
      if (a(paramArrayOfString, paramPermissionCallback)) {
        return;
      }
      ActivityWindowAndroid.a(ActivityWindowAndroid.this).post(new Runnable()
      {
        public void run()
        {
          int[] arrayOfInt = new int[paramArrayOfString.length];
          int i = 0;
          String[] arrayOfString;
          for (;;)
          {
            arrayOfString = paramArrayOfString;
            if (i >= arrayOfString.length) {
              break;
            }
            int j;
            if (ActivityWindowAndroid.ActivityAndroidPermissionDelegate.this.hasPermission(arrayOfString[i])) {
              j = 0;
            } else {
              j = -1;
            }
            arrayOfInt[i] = j;
            i += 1;
          }
          paramPermissionCallback.onRequestPermissionsResult(arrayOfString, arrayOfInt);
        }
      });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\ActivityWindowAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */