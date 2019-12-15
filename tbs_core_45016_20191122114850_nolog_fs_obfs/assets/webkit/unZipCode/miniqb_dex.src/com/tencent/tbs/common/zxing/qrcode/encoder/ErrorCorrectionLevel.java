package com.tencent.tbs.common.zxing.qrcode.encoder;

public enum ErrorCorrectionLevel
{
  private static final ErrorCorrectionLevel[] FOR_BITS;
  private final int bits;
  
  static
  {
    H = new ErrorCorrectionLevel("H", 3, 2);
    ErrorCorrectionLevel localErrorCorrectionLevel1 = L;
    ErrorCorrectionLevel localErrorCorrectionLevel2 = M;
    ErrorCorrectionLevel localErrorCorrectionLevel3 = Q;
    ErrorCorrectionLevel localErrorCorrectionLevel4 = H;
    $VALUES = new ErrorCorrectionLevel[] { localErrorCorrectionLevel1, localErrorCorrectionLevel2, localErrorCorrectionLevel3, localErrorCorrectionLevel4 };
    FOR_BITS = new ErrorCorrectionLevel[] { localErrorCorrectionLevel2, localErrorCorrectionLevel1, localErrorCorrectionLevel4, localErrorCorrectionLevel3 };
  }
  
  private ErrorCorrectionLevel(int paramInt)
  {
    this.bits = paramInt;
  }
  
  public static ErrorCorrectionLevel forBits(int paramInt)
  {
    if (paramInt >= 0)
    {
      ErrorCorrectionLevel[] arrayOfErrorCorrectionLevel = FOR_BITS;
      if (paramInt < arrayOfErrorCorrectionLevel.length) {
        return arrayOfErrorCorrectionLevel[paramInt];
      }
    }
    throw new IllegalArgumentException();
  }
  
  public int getBits()
  {
    return this.bits;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\qrcode\encoder\ErrorCorrectionLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */