package org.chromium.ui.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.Callback;
import org.chromium.base.ContextUtils;
import org.chromium.base.ObserverList;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.ui.VSyncMonitor;
import org.chromium.ui.VSyncMonitor.Listener;
import org.chromium.ui.display.DisplayAndroid;
import org.chromium.ui.widget.Toast;

@JNINamespace("ui")
public class WindowAndroid
{
  public static final int START_INTENT_FAILURE = -1;
  private long jdField_a_of_type_Long;
  protected Context a;
  protected SparseArray<IntentCallback> a;
  private View jdField_a_of_type_AndroidViewView;
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private final AccessibilityManager jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager;
  private WeakReference<Context> jdField_a_of_type_JavaLangRefWeakReference;
  protected HashMap<Integer, String> a;
  private HashSet<Animator> jdField_a_of_type_JavaUtilHashSet;
  private ObserverList<KeyboardVisibilityListener> jdField_a_of_type_OrgChromiumBaseObserverList;
  private final VSyncMonitor.Listener jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener;
  private final VSyncMonitor jdField_a_of_type_OrgChromiumUiVSyncMonitor;
  private AndroidPermissionDelegate jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate;
  private TouchExplorationMonitor jdField_a_of_type_OrgChromiumUiBaseWindowAndroid$TouchExplorationMonitor;
  private final DisplayAndroid jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroid;
  private boolean jdField_a_of_type_Boolean;
  private final ObserverList<OnCloseContextMenuListener> b;
  protected boolean b;
  private boolean d;
  private boolean e;
  
  public WindowAndroid(Context paramContext)
  {
    this(paramContext, DisplayAndroid.getNonMultiDisplay(paramContext));
  }
  
  /* Error */
  @android.annotation.SuppressLint({"UseSparseArrays"})
  protected WindowAndroid(Context paramContext, DisplayAndroid paramDisplayAndroid)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 86	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: new 88	java/util/HashSet
    //   8: dup
    //   9: invokespecial 89	java/util/HashSet:<init>	()V
    //   12: putfield 91	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_JavaUtilHashSet	Ljava/util/HashSet;
    //   15: aload_0
    //   16: new 93	org/chromium/base/ObserverList
    //   19: dup
    //   20: invokespecial 94	org/chromium/base/ObserverList:<init>	()V
    //   23: putfield 96	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_OrgChromiumBaseObserverList	Lorg/chromium/base/ObserverList;
    //   26: aload_0
    //   27: new 93	org/chromium/base/ObserverList
    //   30: dup
    //   31: invokespecial 94	org/chromium/base/ObserverList:<init>	()V
    //   34: putfield 98	org/chromium/ui/base/WindowAndroid:jdField_b_of_type_OrgChromiumBaseObserverList	Lorg/chromium/base/ObserverList;
    //   37: aload_0
    //   38: new 6	org/chromium/ui/base/WindowAndroid$1
    //   41: dup
    //   42: aload_0
    //   43: invokespecial 101	org/chromium/ui/base/WindowAndroid$1:<init>	(Lorg/chromium/ui/base/WindowAndroid;)V
    //   46: putfield 103	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener	Lorg/chromium/ui/VSyncMonitor$Listener;
    //   49: aload_0
    //   50: aload_1
    //   51: invokevirtual 109	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   54: putfield 111	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   57: aload_0
    //   58: new 113	java/lang/ref/WeakReference
    //   61: dup
    //   62: aload_1
    //   63: invokespecial 116	java/lang/ref/WeakReference:<init>	(Ljava/lang/Object;)V
    //   66: putfield 118	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_JavaLangRefWeakReference	Ljava/lang/ref/WeakReference;
    //   69: aload_0
    //   70: new 120	android/util/SparseArray
    //   73: dup
    //   74: invokespecial 121	android/util/SparseArray:<init>	()V
    //   77: putfield 123	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_AndroidUtilSparseArray	Landroid/util/SparseArray;
    //   80: aload_0
    //   81: new 125	java/util/HashMap
    //   84: dup
    //   85: invokespecial 126	java/util/HashMap:<init>	()V
    //   88: putfield 128	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_JavaUtilHashMap	Ljava/util/HashMap;
    //   91: invokestatic 134	org/chromium/base/StrictModeContext:allowDiskReads	()Lorg/chromium/base/StrictModeContext;
    //   94: astore 5
    //   96: aconst_null
    //   97: astore 4
    //   99: aload 4
    //   101: astore_3
    //   102: aload_0
    //   103: new 136	org/chromium/ui/VSyncMonitor
    //   106: dup
    //   107: aload_1
    //   108: aload_0
    //   109: getfield 103	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener	Lorg/chromium/ui/VSyncMonitor$Listener;
    //   112: invokespecial 139	org/chromium/ui/VSyncMonitor:<init>	(Landroid/content/Context;Lorg/chromium/ui/VSyncMonitor$Listener;)V
    //   115: putfield 141	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_OrgChromiumUiVSyncMonitor	Lorg/chromium/ui/VSyncMonitor;
    //   118: aload 4
    //   120: astore_3
    //   121: aload_0
    //   122: aload_0
    //   123: getfield 111	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   126: ldc -113
    //   128: invokevirtual 147	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   131: checkcast 149	android/view/accessibility/AccessibilityManager
    //   134: putfield 151	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager	Landroid/view/accessibility/AccessibilityManager;
    //   137: aload 5
    //   139: ifnull +8 -> 147
    //   142: aload 5
    //   144: invokevirtual 154	org/chromium/base/StrictModeContext:close	()V
    //   147: aload_0
    //   148: aload_2
    //   149: putfield 156	org/chromium/ui/base/WindowAndroid:jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroid	Lorg/chromium/ui/display/DisplayAndroid;
    //   152: getstatic 161	android/os/Build$VERSION:SDK_INT	I
    //   155: bipush 26
    //   157: if_icmplt +38 -> 195
    //   160: getstatic 165	android/os/Build$VERSION:RELEASE	Ljava/lang/String;
    //   163: ldc -89
    //   165: invokevirtual 173	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   168: ifne +27 -> 195
    //   171: aload_1
    //   172: invokestatic 177	org/chromium/ui/base/WindowAndroid:activityFromContext	(Landroid/content/Context;)Landroid/app/Activity;
    //   175: ifnull +20 -> 195
    //   178: aload_2
    //   179: aload_1
    //   180: invokevirtual 181	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   183: invokevirtual 187	android/content/res/Resources:getConfiguration	()Landroid/content/res/Configuration;
    //   186: invokevirtual 192	android/content/res/Configuration:isScreenWideColorGamut	()Z
    //   189: invokestatic 198	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   192: invokevirtual 202	org/chromium/ui/display/DisplayAndroid:updateIsDisplayServerWideColorGamut	(Ljava/lang/Boolean;)V
    //   195: return
    //   196: astore_1
    //   197: goto +8 -> 205
    //   200: astore_1
    //   201: aload_1
    //   202: astore_3
    //   203: aload_1
    //   204: athrow
    //   205: aload 5
    //   207: ifnull +29 -> 236
    //   210: aload_3
    //   211: ifnull +20 -> 231
    //   214: aload 5
    //   216: invokevirtual 154	org/chromium/base/StrictModeContext:close	()V
    //   219: goto +17 -> 236
    //   222: astore_2
    //   223: aload_3
    //   224: aload_2
    //   225: invokevirtual 206	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   228: goto +8 -> 236
    //   231: aload 5
    //   233: invokevirtual 154	org/chromium/base/StrictModeContext:close	()V
    //   236: aload_1
    //   237: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	238	0	this	WindowAndroid
    //   0	238	1	paramContext	Context
    //   0	238	2	paramDisplayAndroid	DisplayAndroid
    //   101	123	3	localObject1	Object
    //   97	22	4	localObject2	Object
    //   94	138	5	localStrictModeContext	org.chromium.base.StrictModeContext
    // Exception table:
    //   from	to	target	type
    //   102	118	196	finally
    //   121	137	196	finally
    //   203	205	196	finally
    //   102	118	200	java/lang/Throwable
    //   121	137	200	java/lang/Throwable
    //   214	219	222	java/lang/Throwable
  }
  
  public static Activity activityFromContext(Context paramContext)
  {
    if ((paramContext instanceof Activity)) {
      return (Activity)paramContext;
    }
    if ((paramContext instanceof ContextWrapper)) {
      return activityFromContext(((ContextWrapper)paramContext).getBaseContext());
    }
    return null;
  }
  
  @CalledByNative
  private void clearNativePointer()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  private static long createForTesting()
  {
    return new WindowAndroid(ContextUtils.getApplicationContext()).getNativePointer();
  }
  
  private void e()
  {
    boolean bool;
    if ((!this.jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaUtilHashSet.isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    if (this.jdField_a_of_type_AndroidViewView.willNotDraw() != bool) {
      this.jdField_a_of_type_AndroidViewView.setWillNotDraw(bool);
    }
  }
  
  @CalledByNative
  private long getNativePointer()
  {
    if (this.jdField_a_of_type_Long == 0L)
    {
      this.jdField_a_of_type_Long = nativeInit(this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroid.getDisplayId());
      nativeSetVSyncPaused(this.jdField_a_of_type_Long, this.e);
    }
    return this.jdField_a_of_type_Long;
  }
  
  @CalledByNative
  private IBinder getWindowToken()
  {
    Object localObject = activityFromContext((Context)this.jdField_a_of_type_JavaLangRefWeakReference.get());
    if (localObject == null) {
      return null;
    }
    localObject = ((Activity)localObject).getWindow();
    if (localObject == null) {
      return null;
    }
    localObject = ((Window)localObject).peekDecorView();
    if (localObject == null) {
      return null;
    }
    return ((View)localObject).getWindowToken();
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit(int paramInt);
  
  private native void nativeOnActivityStarted(long paramLong);
  
  private native void nativeOnActivityStopped(long paramLong);
  
  private native void nativeOnVSync(long paramLong1, long paramLong2, long paramLong3);
  
  private native void nativeOnVisibilityChanged(long paramLong, boolean paramBoolean);
  
  private native void nativeSetVSyncPaused(long paramLong, boolean paramBoolean);
  
  @CalledByNative
  private void requestVSyncUpdate()
  {
    if (this.e)
    {
      this.d = true;
      return;
    }
    this.jdField_a_of_type_OrgChromiumUiVSyncMonitor.a();
  }
  
  protected void a() {}
  
  protected void a(boolean paramBoolean)
  {
    if (this.jdField_b_of_type_Boolean == paramBoolean) {
      return;
    }
    this.jdField_b_of_type_Boolean = paramBoolean;
    Iterator localIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
    while (localIterator.hasNext()) {
      ((KeyboardVisibilityListener)localIterator.next()).keyboardVisibilityChanged(paramBoolean);
    }
  }
  
  public void addContextMenuCloseListener(OnCloseContextMenuListener paramOnCloseContextMenuListener)
  {
    this.jdField_b_of_type_OrgChromiumBaseObserverList.addObserver(paramOnCloseContextMenuListener);
  }
  
  public void addKeyboardVisibilityListener(KeyboardVisibilityListener paramKeyboardVisibilityListener)
  {
    if (this.jdField_a_of_type_OrgChromiumBaseObserverList.isEmpty()) {
      a();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramKeyboardVisibilityListener);
  }
  
  protected void b() {}
  
  protected void c()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeOnActivityStopped(l);
  }
  
  @CalledByNative
  public final boolean canRequestPermission(String paramString)
  {
    AndroidPermissionDelegate localAndroidPermissionDelegate = this.jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate;
    if (localAndroidPermissionDelegate != null) {
      return localAndroidPermissionDelegate.canRequestPermission(paramString);
    }
    if (c) {
      return false;
    }
    throw new AssertionError("Failed to determine the request permission state using a WindowAndroid without an Activity");
  }
  
  public boolean canResolveActivity(Intent paramIntent)
  {
    PackageManager localPackageManager = this.jdField_a_of_type_AndroidContentContext.getPackageManager();
    boolean bool = false;
    if (localPackageManager.queryIntentActivities(paramIntent, 0).size() > 0) {
      bool = true;
    }
    return bool;
  }
  
  public void cancelIntent(int paramInt) {}
  
  protected void d()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeOnActivityStarted(l);
  }
  
  public void destroy()
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeDestroy(l);
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      TouchExplorationMonitor localTouchExplorationMonitor = this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid$TouchExplorationMonitor;
      if (localTouchExplorationMonitor != null) {
        localTouchExplorationMonitor.a();
      }
    }
  }
  
  public WeakReference<Activity> getActivity()
  {
    return new WeakReference(null);
  }
  
  public Context getApplicationContext()
  {
    return this.jdField_a_of_type_AndroidContentContext;
  }
  
  public WeakReference<Context> getContext()
  {
    return new WeakReference(this.jdField_a_of_type_JavaLangRefWeakReference.get());
  }
  
  public DisplayAndroid getDisplay()
  {
    return this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroid;
  }
  
  public ViewGroup getKeyboardAccessoryView()
  {
    return this.jdField_a_of_type_AndroidViewViewGroup;
  }
  
  public long getVsyncPeriodInMillis()
  {
    return this.jdField_a_of_type_OrgChromiumUiVSyncMonitor.a() / 1000L;
  }
  
  @CalledByNative
  public final boolean hasPermission(String paramString)
  {
    AndroidPermissionDelegate localAndroidPermissionDelegate = this.jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate;
    if (localAndroidPermissionDelegate != null) {
      return localAndroidPermissionDelegate.hasPermission(paramString);
    }
    return ApiCompatibilityUtils.checkPermission(this.jdField_a_of_type_AndroidContentContext, paramString, Process.myPid(), Process.myUid()) == 0;
  }
  
  public boolean isInsideVSync()
  {
    return this.jdField_a_of_type_OrgChromiumUiVSyncMonitor.a();
  }
  
  public final boolean isPermissionRevokedByPolicy(String paramString)
  {
    AndroidPermissionDelegate localAndroidPermissionDelegate = this.jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate;
    if (localAndroidPermissionDelegate != null) {
      return localAndroidPermissionDelegate.isPermissionRevokedByPolicy(paramString);
    }
    if (c) {
      return false;
    }
    throw new AssertionError("Failed to determine the policy permission state using a WindowAndroid without an Activity");
  }
  
  public void onContextMenuClosed()
  {
    Iterator localIterator = this.jdField_b_of_type_OrgChromiumBaseObserverList.iterator();
    while (localIterator.hasNext()) {
      ((OnCloseContextMenuListener)localIterator.next()).onContextMenuClosed();
    }
  }
  
  public void onVisibilityChanged(boolean paramBoolean)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeOnVisibilityChanged(l, paramBoolean);
  }
  
  public void removeContextMenuCloseListener(OnCloseContextMenuListener paramOnCloseContextMenuListener)
  {
    this.jdField_b_of_type_OrgChromiumBaseObserverList.removeObserver(paramOnCloseContextMenuListener);
  }
  
  public boolean removeIntentCallback(IntentCallback paramIntentCallback)
  {
    int i = this.jdField_a_of_type_AndroidUtilSparseArray.indexOfValue(paramIntentCallback);
    if (i < 0) {
      return false;
    }
    this.jdField_a_of_type_AndroidUtilSparseArray.remove(i);
    this.jdField_a_of_type_JavaUtilHashMap.remove(Integer.valueOf(i));
    return true;
  }
  
  public void removeKeyboardVisibilityListener(KeyboardVisibilityListener paramKeyboardVisibilityListener)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramKeyboardVisibilityListener);
    if (this.jdField_a_of_type_OrgChromiumBaseObserverList.isEmpty()) {
      b();
    }
  }
  
  public final void requestPermissions(String[] paramArrayOfString, PermissionCallback paramPermissionCallback)
  {
    AndroidPermissionDelegate localAndroidPermissionDelegate = this.jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate;
    if (localAndroidPermissionDelegate != null)
    {
      localAndroidPermissionDelegate.requestPermissions(paramArrayOfString, paramPermissionCallback);
      return;
    }
    if (c) {
      return;
    }
    throw new AssertionError("Failed to request permissions using a WindowAndroid without an Activity");
  }
  
  public void restoreInstanceState(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    paramBundle = paramBundle.getSerializable("window_callback_errors");
    if ((paramBundle instanceof HashMap)) {
      this.jdField_a_of_type_JavaUtilHashMap = ((HashMap)paramBundle);
    }
  }
  
  public void saveInstanceState(Bundle paramBundle)
  {
    paramBundle.putSerializable("window_callback_errors", this.jdField_a_of_type_JavaUtilHashMap);
  }
  
  public void sendBroadcast(Intent paramIntent)
  {
    this.jdField_a_of_type_AndroidContentContext.sendBroadcast(paramIntent);
  }
  
  @VisibleForTesting
  public void setAndroidPermissionDelegate(AndroidPermissionDelegate paramAndroidPermissionDelegate)
  {
    this.jdField_a_of_type_OrgChromiumUiBaseAndroidPermissionDelegate = paramAndroidPermissionDelegate;
  }
  
  public void setAnimationPlaceholderView(View paramView)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_Boolean = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isTouchExplorationEnabled();
    e();
    if (Build.VERSION.SDK_INT >= 19) {
      this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid$TouchExplorationMonitor = new TouchExplorationMonitor();
    }
  }
  
  public void setKeyboardAccessoryView(ViewGroup paramViewGroup)
  {
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
  }
  
  public void setVSyncPaused(boolean paramBoolean)
  {
    if (this.e == paramBoolean) {
      return;
    }
    this.e = paramBoolean;
    if ((!this.e) && (this.d)) {
      requestVSyncUpdate();
    }
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSetVSyncPaused(l, paramBoolean);
    }
  }
  
  public int showCancelableIntent(PendingIntent paramPendingIntent, IntentCallback paramIntentCallback, Integer paramInteger)
  {
    return -1;
  }
  
  public int showCancelableIntent(Intent paramIntent, IntentCallback paramIntentCallback, Integer paramInteger)
  {
    return -1;
  }
  
  public int showCancelableIntent(Callback<Integer> paramCallback, IntentCallback paramIntentCallback, Integer paramInteger)
  {
    return -1;
  }
  
  public void showError(int paramInt)
  {
    showError(this.jdField_a_of_type_AndroidContentContext.getString(paramInt));
  }
  
  public void showError(String paramString)
  {
    if (paramString != null) {
      Toast.a(this.jdField_a_of_type_AndroidContentContext, paramString, 0).a();
    }
  }
  
  public boolean showIntent(PendingIntent paramPendingIntent, IntentCallback paramIntentCallback, Integer paramInteger)
  {
    return showCancelableIntent(paramPendingIntent, paramIntentCallback, paramInteger) >= 0;
  }
  
  public boolean showIntent(Intent paramIntent, IntentCallback paramIntentCallback, Integer paramInteger)
  {
    return showCancelableIntent(paramIntent, paramIntentCallback, paramInteger) >= 0;
  }
  
  public void startAnimationOverContent(Animator paramAnimator)
  {
    if (this.jdField_a_of_type_AndroidViewView == null) {
      return;
    }
    if (!paramAnimator.isStarted())
    {
      if (this.jdField_a_of_type_JavaUtilHashSet.add(paramAnimator))
      {
        paramAnimator.start();
        e();
        paramAnimator.addListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            paramAnonymousAnimator.removeListener(this);
            WindowAndroid.a(WindowAndroid.this).remove(paramAnonymousAnimator);
            WindowAndroid.a(WindowAndroid.this);
          }
        });
        return;
      }
      throw new IllegalArgumentException("Already Added.");
    }
    throw new IllegalArgumentException("Already started.");
  }
  
  public static abstract interface IntentCallback
  {
    public abstract void onIntentCompleted(WindowAndroid paramWindowAndroid, int paramInt, Intent paramIntent);
  }
  
  public static abstract interface KeyboardVisibilityListener
  {
    public abstract void keyboardVisibilityChanged(boolean paramBoolean);
  }
  
  public static abstract interface OnCloseContextMenuListener
  {
    public abstract void onContextMenuClosed();
  }
  
  public static abstract interface PermissionCallback
  {
    public abstract void onRequestPermissionsResult(String[] paramArrayOfString, int[] paramArrayOfInt);
  }
  
  @TargetApi(19)
  private class TouchExplorationMonitor
  {
    private AccessibilityManager.TouchExplorationStateChangeListener jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager$TouchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener()
    {
      public void onTouchExplorationStateChanged(boolean paramAnonymousBoolean)
      {
        WindowAndroid.a(WindowAndroid.TouchExplorationMonitor.this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid, WindowAndroid.a(WindowAndroid.TouchExplorationMonitor.this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid).isTouchExplorationEnabled());
        WindowAndroid.a(WindowAndroid.TouchExplorationMonitor.this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid);
      }
    };
    
    TouchExplorationMonitor()
    {
      WindowAndroid.a(WindowAndroid.this).addTouchExplorationStateChangeListener(this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager$TouchExplorationStateChangeListener);
    }
    
    void a()
    {
      WindowAndroid.a(WindowAndroid.this).removeTouchExplorationStateChangeListener(this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager$TouchExplorationStateChangeListener);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\WindowAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */