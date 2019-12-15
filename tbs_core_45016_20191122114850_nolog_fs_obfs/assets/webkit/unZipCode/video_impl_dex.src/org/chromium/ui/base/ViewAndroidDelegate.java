package org.chromium.ui.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.view.PointerIcon;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("ui")
public abstract class ViewAndroidDelegate
{
  private int[] jdField_a_of_type_ArrayOfInt = new int[2];
  
  public static ViewAndroidDelegate createBasicDelegate(ViewGroup paramViewGroup)
  {
    1.a(new ViewAndroidDelegate()
    {
      private ViewGroup a;
      
      private ViewAndroidDelegate a(ViewGroup paramAnonymousViewGroup)
      {
        this.a = paramAnonymousViewGroup;
        return this;
      }
      
      public ViewGroup getContainerView()
      {
        return this.a;
      }
    }, paramViewGroup);
  }
  
  @CalledByNative
  private int getXLocationOfContainerViewInWindow()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return 0;
    }
    localViewGroup.getLocationInWindow(this.jdField_a_of_type_ArrayOfInt);
    return this.jdField_a_of_type_ArrayOfInt[0];
  }
  
  @CalledByNative
  private int getXLocationOnScreen()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return 0;
    }
    localViewGroup.getLocationOnScreen(this.jdField_a_of_type_ArrayOfInt);
    return this.jdField_a_of_type_ArrayOfInt[0];
  }
  
  @CalledByNative
  private int getYLocationOfContainerViewInWindow()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return 0;
    }
    localViewGroup.getLocationInWindow(this.jdField_a_of_type_ArrayOfInt);
    return this.jdField_a_of_type_ArrayOfInt[1];
  }
  
  @CalledByNative
  private int getYLocationOnScreen()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return 0;
    }
    localViewGroup.getLocationOnScreen(this.jdField_a_of_type_ArrayOfInt);
    return this.jdField_a_of_type_ArrayOfInt[1];
  }
  
  @CalledByNative
  private boolean hasFocus()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return false;
    }
    return ViewUtils.a(localViewGroup);
  }
  
  @CalledByNative
  private void requestFocus()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup != null) {
      ViewUtils.a(localViewGroup);
    }
  }
  
  @TargetApi(24)
  @CalledByNative
  private boolean startDragAndDrop(String paramString, Bitmap paramBitmap)
  {
    if (Build.VERSION.SDK_INT <= 23) {
      return false;
    }
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return false;
    }
    ImageView localImageView = new ImageView(localViewGroup.getContext());
    localImageView.setImageBitmap(paramBitmap);
    localImageView.layout(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    return localViewGroup.startDragAndDrop(ClipData.newPlainText(null, paramString), new View.DragShadowBuilder(localImageView), null, 256);
  }
  
  @CalledByNative
  public View acquireView()
  {
    ViewGroup localViewGroup = getContainerView();
    if ((localViewGroup != null) && (localViewGroup.getParent() != null))
    {
      View localView = new View(localViewGroup.getContext());
      localViewGroup.addView(localView);
      return localView;
    }
    return null;
  }
  
  @CalledByNative
  public abstract ViewGroup getContainerView();
  
  @CalledByNative
  public int getSystemWindowInsetBottom()
  {
    return 0;
  }
  
  @CalledByNative
  public void onBackgroundColorChanged(int paramInt) {}
  
  @CalledByNative
  public void onBottomControlsChanged(float paramFloat1, float paramFloat2) {}
  
  @VisibleForTesting
  @CalledByNative
  public void onCursorChanged(int paramInt)
  {
    if (Build.VERSION.SDK_INT < 24) {
      return;
    }
    int i = 1012;
    switch (paramInt)
    {
    default: 
      break;
    case 43: 
      if (!jdField_a_of_type_Boolean) {
        throw new AssertionError("onCursorChangedToCustom must be called instead");
      }
    case 42: 
      i = 1021;
      break;
    case 41: 
      i = 1020;
      break;
    case 40: 
      i = 1019;
      break;
    case 39: 
      i = 1018;
      break;
    case 37: 
      i = 0;
      break;
    case 36: 
      i = 1011;
      break;
    case 34: 
      i = 1004;
      break;
    case 33: 
      i = 1010;
      break;
    case 32: 
      i = 1001;
      break;
    case 31: 
      i = 1006;
      break;
    case 30: 
      i = 1009;
      break;
    case 21: 
    case 22: 
    case 23: 
    case 24: 
    case 25: 
    case 26: 
    case 27: 
    case 28: 
      if (!jdField_a_of_type_Boolean) {
        throw new AssertionError("These pointer icon types are not supported");
      }
    case 20: 
    case 29: 
      i = 1013;
      break;
    case 19: 
      i = 1015;
      break;
    case 18: 
      i = 1014;
      break;
    case 17: 
      i = 1017;
      break;
    case 16: 
      i = 1016;
      break;
    case 15: 
      i = 1014;
      break;
    case 14: 
      i = 1015;
      break;
    case 13: 
      i = 1014;
      break;
    case 12: 
      i = 1016;
      break;
    case 11: 
      i = 1017;
      break;
    case 10: 
      i = 1015;
      break;
    case 9: 
      i = 1017;
      break;
    case 8: 
      i = 1016;
      break;
    case 7: 
      i = 1015;
      break;
    case 6: 
      i = 1014;
      break;
    case 5: 
      i = 1003;
      break;
    case 4: 
      i = 1004;
      break;
    case 3: 
      i = 1008;
      break;
    case 2: 
      i = 1002;
      break;
    case 1: 
      i = 1007;
      break;
    case 0: 
      i = 1000;
      break label471;
      i = 1000;
    }
    label471:
    ViewGroup localViewGroup = getContainerView();
    localViewGroup.setPointerIcon(PointerIcon.getSystemIcon(localViewGroup.getContext(), i));
  }
  
  @VisibleForTesting
  @CalledByNative
  public void onCursorChangedToCustom(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      paramBitmap = PointerIcon.create(paramBitmap, paramInt1, paramInt2);
      getContainerView().setPointerIcon(paramBitmap);
    }
  }
  
  @CalledByNative
  public void onTopControlsChanged(float paramFloat1, float paramFloat2) {}
  
  @CalledByNative
  public void removeView(View paramView)
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return;
    }
    localViewGroup.removeView(paramView);
  }
  
  @CalledByNative
  public void setViewPosition(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2)
  {
    Object localObject = getContainerView();
    if (localObject == null) {
      return;
    }
    int j = Math.round(paramFloat3);
    int k = Math.round(paramFloat4);
    if (ApiCompatibilityUtils.isLayoutRtl((View)localObject)) {
      paramInt1 = ((ViewGroup)localObject).getMeasuredWidth() - Math.round(paramFloat3 + paramFloat1);
    }
    int i = j;
    if (j + paramInt1 > ((ViewGroup)localObject).getWidth()) {
      i = ((ViewGroup)localObject).getWidth() - paramInt1;
    }
    localObject = new FrameLayout.LayoutParams(i, k);
    ApiCompatibilityUtils.setMarginStart((ViewGroup.MarginLayoutParams)localObject, paramInt1);
    ((FrameLayout.LayoutParams)localObject).topMargin = paramInt2;
    paramView.setLayoutParams((ViewGroup.LayoutParams)localObject);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\ViewAndroidDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */