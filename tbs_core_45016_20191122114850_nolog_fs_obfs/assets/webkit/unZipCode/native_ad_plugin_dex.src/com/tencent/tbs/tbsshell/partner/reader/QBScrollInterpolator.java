package com.tencent.tbs.tbsshell.partner.reader;

public class QBScrollInterpolator
{
  private float mMinStep = 1.5F;
  private float mPhysicsTimeStep = 0.008333334F;
  private float mSpringDampening = 15.0F;
  private float mSpringTightness = 7.0F;
  private float mVelocity = 2500.0F;
  
  private float Spring(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float paramFloat3)
  {
    float f2 = paramInt1 - paramInt2;
    float f1 = f2;
    if (f2 < 0.0F) {
      f1 = -f2;
    }
    return -paramFloat2 * f1 - paramFloat3 * paramFloat1;
  }
  
  public int getStep(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == 0) {
      return 0;
    }
    float f1 = Spring(this.mVelocity, paramInt1, paramInt3, this.mSpringTightness, this.mSpringDampening);
    this.mVelocity += f1 * this.mPhysicsTimeStep;
    float f2 = this.mVelocity / 50.0F;
    float f3 = this.mMinStep;
    f1 = f2;
    if (f2 < f3) {
      f1 = f3;
    }
    return (int)f1;
  }
  
  public void initVelocity(int paramInt)
  {
    this.mVelocity = (Math.abs(paramInt) * 8);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\QBScrollInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */