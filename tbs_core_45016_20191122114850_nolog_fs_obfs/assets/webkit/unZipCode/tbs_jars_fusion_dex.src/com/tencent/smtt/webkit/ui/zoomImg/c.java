package com.tencent.smtt.webkit.ui.zoomImg;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

public class c
{
  private final GestureDetector jdField_a_of_type_AndroidViewGestureDetector;
  private final ScaleGestureDetector jdField_a_of_type_AndroidViewScaleGestureDetector;
  
  public c(Context paramContext, a parama)
  {
    this.jdField_a_of_type_AndroidViewGestureDetector = new GestureDetector(paramContext, parama);
    this.jdField_a_of_type_AndroidViewGestureDetector.setOnDoubleTapListener(parama);
    this.jdField_a_of_type_AndroidViewScaleGestureDetector = new ScaleGestureDetector(paramContext, parama);
  }
  
  public boolean a(MotionEvent paramMotionEvent)
  {
    boolean bool2 = false;
    try
    {
      boolean bool1 = this.jdField_a_of_type_AndroidViewScaleGestureDetector.onTouchEvent(paramMotionEvent);
      boolean bool3 = bool1;
      bool2 = bool1;
      if (!this.jdField_a_of_type_AndroidViewScaleGestureDetector.isInProgress())
      {
        bool2 = bool1;
        bool3 = this.jdField_a_of_type_AndroidViewGestureDetector.onTouchEvent(paramMotionEvent);
        bool3 = bool1 | bool3;
      }
      return bool3;
    }
    catch (Exception paramMotionEvent) {}
    return bool2;
  }
  
  public static abstract class a
    implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener
  {
    public boolean onDoubleTap(MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onDoubleTapEvent(MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onDown(MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      return false;
    }
    
    public void onLongPress(MotionEvent paramMotionEvent) {}
    
    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      return false;
    }
    
    public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
    {
      return false;
    }
    
    public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector) {}
    
    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      return false;
    }
    
    public void onShowPress(MotionEvent paramMotionEvent) {}
    
    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onSingleTapUp(MotionEvent paramMotionEvent)
    {
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */