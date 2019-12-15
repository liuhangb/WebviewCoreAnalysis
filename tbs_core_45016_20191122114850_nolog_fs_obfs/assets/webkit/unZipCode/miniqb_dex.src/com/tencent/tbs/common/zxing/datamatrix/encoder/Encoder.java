package com.tencent.tbs.common.zxing.datamatrix.encoder;

abstract interface Encoder
{
  public abstract void encode(EncoderContext paramEncoderContext);
  
  public abstract int getEncodingMode();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\datamatrix\encoder\Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */