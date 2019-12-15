package com.tencent.tbs.common.zxing.qrcode;

import com.tencent.tbs.common.zxing.BarcodeFormat;
import com.tencent.tbs.common.zxing.EncodeHintType;
import com.tencent.tbs.common.zxing.Writer;
import com.tencent.tbs.common.zxing.WriterException;
import com.tencent.tbs.common.zxing.common.BitMatrix;
import com.tencent.tbs.common.zxing.qrcode.encoder.ByteMatrix;
import com.tencent.tbs.common.zxing.qrcode.encoder.Encoder;
import com.tencent.tbs.common.zxing.qrcode.encoder.ErrorCorrectionLevel;
import com.tencent.tbs.common.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter
  implements Writer
{
  private static final int QUIET_ZONE_SIZE = 4;
  
  private static BitMatrix renderResult(QRCode paramQRCode, int paramInt1, int paramInt2, int paramInt3)
  {
    paramQRCode = paramQRCode.getMatrix();
    if (paramQRCode != null)
    {
      int k = paramQRCode.getWidth();
      int m = paramQRCode.getHeight();
      paramInt3 *= 2;
      int i = k + paramInt3;
      int j = paramInt3 + m;
      paramInt3 = Math.max(paramInt1, i);
      paramInt2 = Math.max(paramInt2, j);
      int n = Math.min(paramInt3 / i, paramInt2 / j);
      j = (paramInt3 - k * n) / 2;
      paramInt1 = (paramInt2 - m * n) / 2;
      BitMatrix localBitMatrix = new BitMatrix(paramInt3, paramInt2);
      paramInt2 = 0;
      while (paramInt2 < m)
      {
        paramInt3 = j;
        i = 0;
        while (i < k)
        {
          if (paramQRCode.get(i, paramInt2) == 1) {
            localBitMatrix.setRegion(paramInt3, paramInt1, n, n);
          }
          i += 1;
          paramInt3 += n;
        }
        paramInt2 += 1;
        paramInt1 += n;
      }
      return localBitMatrix;
    }
    throw new IllegalStateException();
  }
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    if (!paramString.isEmpty())
    {
      if (paramBarcodeFormat == BarcodeFormat.QR_CODE)
      {
        if ((paramInt1 >= 0) && (paramInt2 >= 0))
        {
          paramBarcodeFormat = ErrorCorrectionLevel.L;
          int j = 4;
          int i = j;
          BarcodeFormat localBarcodeFormat = paramBarcodeFormat;
          if (paramMap != null)
          {
            if (paramMap.containsKey(EncodeHintType.ERROR_CORRECTION)) {
              paramBarcodeFormat = ErrorCorrectionLevel.valueOf(paramMap.get(EncodeHintType.ERROR_CORRECTION).toString());
            }
            i = j;
            localBarcodeFormat = paramBarcodeFormat;
            if (paramMap.containsKey(EncodeHintType.MARGIN))
            {
              i = Integer.parseInt(paramMap.get(EncodeHintType.MARGIN).toString());
              localBarcodeFormat = paramBarcodeFormat;
            }
          }
          return renderResult(Encoder.encode(paramString, localBarcodeFormat, paramMap), paramInt1, paramInt2, i);
        }
        paramString = new StringBuilder();
        paramString.append("Requested dimensions are too small: ");
        paramString.append(paramInt1);
        paramString.append('x');
        paramString.append(paramInt2);
        throw new IllegalArgumentException(paramString.toString());
      }
      paramString = new StringBuilder();
      paramString.append("Can only encode QR_CODE, but got ");
      paramString.append(paramBarcodeFormat);
      throw new IllegalArgumentException(paramString.toString());
    }
    throw new IllegalArgumentException("Found empty contents");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\qrcode\QRCodeWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */