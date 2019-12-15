package org.chromium.base;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeAnimator;
import android.animation.TimeAnimator.TimeListener;
import org.chromium.base.annotations.MainDex;

@MainDex
public class AnimationFrameTimeHistogram
{
  private final String jdField_a_of_type_JavaLangString;
  private final Recorder jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder = new Recorder(null);
  
  public AnimationFrameTimeHistogram(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public static Animator.AnimatorListener getAnimatorRecorder(String paramString)
  {
    new AnimatorListenerAdapter()
    {
      private final AnimationFrameTimeHistogram jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram = new AnimationFrameTimeHistogram(this.jdField_a_of_type_JavaLangString);
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram.endRecording();
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram.endRecording();
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram.startRecording();
      }
    };
  }
  
  private native void nativeSaveHistogram(String paramString, long[] paramArrayOfLong, int paramInt);
  
  public void endRecording()
  {
    if (Recorder.a(this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder)) {
      nativeSaveHistogram(this.jdField_a_of_type_JavaLangString, Recorder.a(this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder), Recorder.a(this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder));
    }
    Recorder.b(this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder);
  }
  
  public void startRecording()
  {
    Recorder.a(this.jdField_a_of_type_OrgChromiumBaseAnimationFrameTimeHistogram$Recorder);
  }
  
  private static class Recorder
    implements TimeAnimator.TimeListener
  {
    private int jdField_a_of_type_Int;
    private final TimeAnimator jdField_a_of_type_AndroidAnimationTimeAnimator = new TimeAnimator();
    private long[] jdField_a_of_type_ArrayOfLong;
    
    private Recorder()
    {
      this.jdField_a_of_type_AndroidAnimationTimeAnimator.setTimeListener(this);
    }
    
    private int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    private void a()
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidAnimationTimeAnimator.isRunning())) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_Int = 0;
      this.jdField_a_of_type_ArrayOfLong = new long['ɘ'];
      this.jdField_a_of_type_AndroidAnimationTimeAnimator.start();
    }
    
    private boolean a()
    {
      boolean bool = this.jdField_a_of_type_AndroidAnimationTimeAnimator.isStarted();
      this.jdField_a_of_type_AndroidAnimationTimeAnimator.end();
      return bool;
    }
    
    private long[] a()
    {
      return this.jdField_a_of_type_ArrayOfLong;
    }
    
    private void b()
    {
      this.jdField_a_of_type_ArrayOfLong = null;
    }
    
    public void onTimeUpdate(TimeAnimator paramTimeAnimator, long paramLong1, long paramLong2)
    {
      int i = this.jdField_a_of_type_Int;
      paramTimeAnimator = this.jdField_a_of_type_ArrayOfLong;
      if (i == paramTimeAnimator.length)
      {
        this.jdField_a_of_type_AndroidAnimationTimeAnimator.end();
        b();
        return;
      }
      if (paramLong2 > 0L)
      {
        this.jdField_a_of_type_Int = (i + 1);
        paramTimeAnimator[i] = paramLong2;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\AnimationFrameTimeHistogram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */