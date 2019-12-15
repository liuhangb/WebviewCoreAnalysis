package com.tencent.tbs.common.zxing.datamatrix.encoder;

final class X12Encoder
  extends C40Encoder
{
  public void encode(EncoderContext paramEncoderContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    while (paramEncoderContext.hasMoreCharacters())
    {
      char c = paramEncoderContext.getCurrentChar();
      paramEncoderContext.pos += 1;
      encodeChar(c, localStringBuilder);
      if (localStringBuilder.length() % 3 == 0)
      {
        writeNextTriplet(paramEncoderContext, localStringBuilder);
        if (HighLevelEncoder.lookAheadTest(paramEncoderContext.getMessage(), paramEncoderContext.pos, getEncodingMode()) != getEncodingMode()) {
          paramEncoderContext.signalEncoderChange(0);
        }
      }
    }
    handleEOD(paramEncoderContext, localStringBuilder);
  }
  
  int encodeChar(char paramChar, StringBuilder paramStringBuilder)
  {
    if (paramChar != '\r')
    {
      if (paramChar != ' ')
      {
        if (paramChar != '*')
        {
          if (paramChar != '>')
          {
            if ((paramChar >= '0') && (paramChar <= '9'))
            {
              paramStringBuilder.append((char)(paramChar - '0' + 4));
              return 1;
            }
            if ((paramChar >= 'A') && (paramChar <= 'Z'))
            {
              paramStringBuilder.append((char)(paramChar - 'A' + 14));
              return 1;
            }
            HighLevelEncoder.illegalCharacter(paramChar);
            return 1;
          }
          paramStringBuilder.append('\002');
          return 1;
        }
        paramStringBuilder.append('\001');
        return 1;
      }
      paramStringBuilder.append('\003');
      return 1;
    }
    paramStringBuilder.append('\000');
    return 1;
  }
  
  public int getEncodingMode()
  {
    return 3;
  }
  
  void handleEOD(EncoderContext paramEncoderContext, StringBuilder paramStringBuilder)
  {
    paramEncoderContext.updateSymbolInfo();
    int i = paramEncoderContext.getSymbolInfo().getDataCapacity() - paramEncoderContext.getCodewordCount();
    int j = paramStringBuilder.length();
    paramEncoderContext.pos -= j;
    if ((paramEncoderContext.getRemainingCharacters() > 1) || (i > 1) || (paramEncoderContext.getRemainingCharacters() != i)) {
      paramEncoderContext.writeCodeword('þ');
    }
    if (paramEncoderContext.getNewEncoding() < 0) {
      paramEncoderContext.signalEncoderChange(0);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\datamatrix\encoder\X12Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */