package org.chromium.android_webview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.VisibleForTesting;
import org.chromium.ui.base.ViewAndroidDelegate;
import org.chromium.ui.display.DisplayAndroid;

public class AwViewAndroidDelegate
  extends ViewAndroidDelegate
{
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private final Map<View, Position> jdField_a_of_type_JavaUtilMap = new LinkedHashMap();
  private final AwContentsClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient;
  private final AwScrollOffsetManager jdField_a_of_type_OrgChromiumAndroid_webviewAwScrollOffsetManager;
  
  @VisibleForTesting
  public AwViewAndroidDelegate(ViewGroup paramViewGroup, AwContentsClient paramAwContentsClient, AwScrollOffsetManager paramAwScrollOffsetManager)
  {
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient = paramAwContentsClient;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwScrollOffsetManager = paramAwScrollOffsetManager;
  }
  
  public View acquireView()
  {
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup == null) {
      return null;
    }
    View local1 = new View(localViewGroup.getContext())
    {
      public boolean requestRectangleOnScreen(Rect paramAnonymousRect, boolean paramAnonymousBoolean)
      {
        return false;
      }
    };
    localViewGroup.addView(local1);
    this.jdField_a_of_type_JavaUtilMap.put(local1, null);
    return local1;
  }
  
  public ViewGroup getContainerView()
  {
    return this.jdField_a_of_type_AndroidViewViewGroup;
  }
  
  public void onBackgroundColorChanged(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient.onBackgroundColorChanged(paramInt);
  }
  
  public void removeView(View paramView)
  {
    this.jdField_a_of_type_JavaUtilMap.remove(paramView);
    ViewGroup localViewGroup = getContainerView();
    if (localViewGroup != null) {
      localViewGroup.removeView(paramView);
    }
  }
  
  public void setViewPosition(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2)
  {
    ViewGroup localViewGroup = getContainerView();
    if (this.jdField_a_of_type_JavaUtilMap.containsKey(paramView))
    {
      if (localViewGroup == null) {
        return;
      }
      this.jdField_a_of_type_JavaUtilMap.put(paramView, new Position(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2));
      if ((localViewGroup instanceof FrameLayout))
      {
        super.setViewPosition(paramView, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2);
        return;
      }
      int i = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwScrollOffsetManager.getScrollX();
      int j = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwScrollOffsetManager.getScrollY();
      paramView.setLayoutParams(new AbsoluteLayout.LayoutParams(Math.round(paramFloat3), Math.round(paramFloat4), paramInt1 + i, paramInt2 + j));
      return;
    }
  }
  
  public void updateCurrentContainerView(ViewGroup paramViewGroup, DisplayAndroid paramDisplayAndroid)
  {
    paramDisplayAndroid = getContainerView();
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
    Iterator localIterator = this.jdField_a_of_type_JavaUtilMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Map.Entry)localIterator.next();
      View localView = (View)((Map.Entry)localObject).getKey();
      localObject = (Position)((Map.Entry)localObject).getValue();
      if (paramDisplayAndroid != null) {
        paramDisplayAndroid.removeView(localView);
      }
      paramViewGroup.addView(localView);
      if (localObject != null) {
        setViewPosition(localView, ((Position)localObject).mX, ((Position)localObject).mY, ((Position)localObject).mWidth, ((Position)localObject).mHeight, ((Position)localObject).mLeftMargin, ((Position)localObject).mTopMargin);
      }
    }
  }
  
  @VisibleForTesting
  private static class Position
  {
    public final float mHeight;
    public final int mLeftMargin;
    public final int mTopMargin;
    public final float mWidth;
    public final float mX;
    public final float mY;
    
    public Position(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2)
    {
      this.mX = paramFloat1;
      this.mY = paramFloat2;
      this.mWidth = paramFloat3;
      this.mHeight = paramFloat4;
      this.mLeftMargin = paramInt1;
      this.mTopMargin = paramInt2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwViewAndroidDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */