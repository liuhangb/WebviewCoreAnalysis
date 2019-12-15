package com.tencent.tbs.common.zxing.qrcode.encoder;

final class BlockPair
{
  private final byte[] dataBytes;
  private final byte[] errorCorrectionBytes;
  
  BlockPair(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.dataBytes = paramArrayOfByte1;
    this.errorCorrectionBytes = paramArrayOfByte2;
  }
  
  public byte[] getDataBytes()
  {
    return this.dataBytes;
  }
  
  public byte[] getErrorCorrectionBytes()
  {
    return this.errorCorrectionBytes;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\qrcode\encoder\BlockPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */