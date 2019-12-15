package com.tencent.common.utils.bitmap;

public class QImageParams
{
  public int mCropMode = BitmapUtils.CROP_MODE_NORMAL;
  public int mHeight;
  public boolean mNeedAlign = false;
  public boolean mNeedCrop = true;
  public int mWidth;
  
  public QImageParams(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    this.mNeedCrop = paramBoolean;
    this.mCropMode = paramInt3;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (QImageParams)paramObject;
    boolean bool1 = bool2;
    if (((QImageParams)paramObject).mHeight == this.mHeight)
    {
      bool1 = bool2;
      if (((QImageParams)paramObject).mWidth == this.mWidth)
      {
        bool1 = bool2;
        if (((QImageParams)paramObject).mNeedCrop == this.mNeedCrop)
        {
          bool1 = bool2;
          if (this.mCropMode == ((QImageParams)paramObject).mCropMode) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\bitmap\QImageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */