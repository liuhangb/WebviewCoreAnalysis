package com.tencent.tbs.common.zxing.qrcode.encoder;

final class FormatInformation
{
  private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
  private static final int FORMAT_INFO_MASK_QR = 21522;
  private final byte dataMask;
  private final ErrorCorrectionLevel errorCorrectionLevel;
  
  static
  {
    int[] arrayOfInt1 = { 21522, 0 };
    int[] arrayOfInt2 = { 20773, 1 };
    int[] arrayOfInt3 = { 24188, 2 };
    int[] arrayOfInt4 = { 23371, 3 };
    int[] arrayOfInt5 = { 17913, 4 };
    int[] arrayOfInt6 = { 16590, 5 };
    int[] arrayOfInt7 = { 20375, 6 };
    int[] arrayOfInt8 = { 19104, 7 };
    int[] arrayOfInt9 = { 30660, 8 };
    int[] arrayOfInt10 = { 29427, 9 };
    int[] arrayOfInt11 = { 32170, 10 };
    int[] arrayOfInt12 = { 26159, 12 };
    int[] arrayOfInt13 = { 25368, 13 };
    int[] arrayOfInt14 = { 27713, 14 };
    int[] arrayOfInt15 = { 26998, 15 };
    int[] arrayOfInt16 = { 5769, 16 };
    int[] arrayOfInt17 = { 5054, 17 };
    int[] arrayOfInt18 = { 7399, 18 };
    int[] arrayOfInt19 = { 6608, 19 };
    int[] arrayOfInt20 = { 1890, 20 };
    int[] arrayOfInt21 = { 597, 21 };
    int[] arrayOfInt22 = { 3340, 22 };
    int[] arrayOfInt23 = { 2107, 23 };
    int[] arrayOfInt24 = { 13663, 24 };
    int[] arrayOfInt25 = { 12392, 25 };
    int[] arrayOfInt26 = { 16177, 26 };
    int[] arrayOfInt27 = { 14854, 27 };
    int[] arrayOfInt28 = { 9396, 28 };
    int[] arrayOfInt29 = { 8579, 29 };
    int[] arrayOfInt30 = { 11994, 30 };
    int[] arrayOfInt31 = { 11245, 31 };
    FORMAT_INFO_DECODE_LOOKUP = new int[][] { arrayOfInt1, arrayOfInt2, arrayOfInt3, arrayOfInt4, arrayOfInt5, arrayOfInt6, arrayOfInt7, arrayOfInt8, arrayOfInt9, arrayOfInt10, arrayOfInt11, { 30877, 11 }, arrayOfInt12, arrayOfInt13, arrayOfInt14, arrayOfInt15, arrayOfInt16, arrayOfInt17, arrayOfInt18, arrayOfInt19, arrayOfInt20, arrayOfInt21, arrayOfInt22, arrayOfInt23, arrayOfInt24, arrayOfInt25, arrayOfInt26, arrayOfInt27, arrayOfInt28, arrayOfInt29, arrayOfInt30, arrayOfInt31 };
  }
  
  private FormatInformation(int paramInt)
  {
    this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(paramInt >> 3 & 0x3);
    this.dataMask = ((byte)(paramInt & 0x7));
  }
  
  static int numBitsDiffering(int paramInt1, int paramInt2)
  {
    return Integer.bitCount(paramInt1 ^ paramInt2);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof FormatInformation;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (FormatInformation)paramObject;
    bool1 = bool2;
    if (this.errorCorrectionLevel == ((FormatInformation)paramObject).errorCorrectionLevel)
    {
      bool1 = bool2;
      if (this.dataMask == ((FormatInformation)paramObject).dataMask) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  byte getDataMask()
  {
    return this.dataMask;
  }
  
  ErrorCorrectionLevel getErrorCorrectionLevel()
  {
    return this.errorCorrectionLevel;
  }
  
  public int hashCode()
  {
    return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask & 0xFF;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\qrcode\encoder\FormatInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */