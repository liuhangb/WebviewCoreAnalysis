package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintAttributes.Resolution;
import android.view.ViewGroup;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
@SuppressLint({"NewApi"})
public class AwPdfExporter
{
  private long jdField_a_of_type_Long;
  private ParcelFileDescriptor jdField_a_of_type_AndroidOsParcelFileDescriptor;
  private PrintAttributes jdField_a_of_type_AndroidPrintPrintAttributes;
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private AwPdfExporterCallback jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback;
  
  AwPdfExporter(ViewGroup paramViewGroup)
  {
    setContainerView(paramViewGroup);
  }
  
  private static int a(PrintAttributes paramPrintAttributes)
  {
    int i = paramPrintAttributes.getResolution().getHorizontalDpi();
    paramPrintAttributes.getResolution().getVerticalDpi();
    return i;
  }
  
  @CalledByNative
  private void didExportPdf(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback.pdfWritingDone(paramInt);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback = null;
    this.jdField_a_of_type_AndroidPrintPrintAttributes = null;
    this.jdField_a_of_type_AndroidOsParcelFileDescriptor = null;
  }
  
  @CalledByNative
  private int getBottomMargin()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMinMargins().getBottomMils();
  }
  
  @CalledByNative
  private int getDpi()
  {
    return a(this.jdField_a_of_type_AndroidPrintPrintAttributes);
  }
  
  @CalledByNative
  private int getLeftMargin()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMinMargins().getLeftMils();
  }
  
  @CalledByNative
  private int getPageHeight()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMediaSize().getHeightMils();
  }
  
  @CalledByNative
  private int getPageWidth()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMediaSize().getWidthMils();
  }
  
  @CalledByNative
  private int getRightMargin()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMinMargins().getRightMils();
  }
  
  @CalledByNative
  private int getTopMargin()
  {
    return this.jdField_a_of_type_AndroidPrintPrintAttributes.getMinMargins().getTopMils();
  }
  
  private native void nativeExportToPdf(long paramLong, int paramInt, int[] paramArrayOfInt, CancellationSignal paramCancellationSignal);
  
  @CalledByNative
  private void setNativeAwPdfExporter(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    AwPdfExporterCallback localAwPdfExporterCallback;
    if (paramLong == 0L)
    {
      localAwPdfExporterCallback = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback;
      if (localAwPdfExporterCallback == null) {}
    }
    try
    {
      localAwPdfExporterCallback.pdfWritingDone(0);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback = null;
      return;
    }
    catch (IllegalStateException localIllegalStateException) {}
  }
  
  public void exportToPdf(ParcelFileDescriptor paramParcelFileDescriptor, PrintAttributes paramPrintAttributes, int[] paramArrayOfInt, AwPdfExporterCallback paramAwPdfExporterCallback, CancellationSignal paramCancellationSignal)
  {
    if (paramParcelFileDescriptor != null)
    {
      if (paramAwPdfExporterCallback != null)
      {
        if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback == null)
        {
          if (paramPrintAttributes.getMediaSize() != null)
          {
            if (paramPrintAttributes.getResolution() != null)
            {
              if (paramPrintAttributes.getMinMargins() != null)
              {
                long l = this.jdField_a_of_type_Long;
                if (l == 0L)
                {
                  paramAwPdfExporterCallback.pdfWritingDone(0);
                  return;
                }
                this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter$AwPdfExporterCallback = paramAwPdfExporterCallback;
                this.jdField_a_of_type_AndroidPrintPrintAttributes = paramPrintAttributes;
                this.jdField_a_of_type_AndroidOsParcelFileDescriptor = paramParcelFileDescriptor;
                nativeExportToPdf(l, this.jdField_a_of_type_AndroidOsParcelFileDescriptor.getFd(), paramArrayOfInt, paramCancellationSignal);
                return;
              }
              throw new IllegalArgumentException("attributes must specify margins");
            }
            throw new IllegalArgumentException("attributes must specify print resolution");
          }
          throw new IllegalArgumentException("attributes must specify a media size");
        }
        throw new IllegalStateException("printing is already pending");
      }
      throw new IllegalArgumentException("resultCallback cannot be null");
    }
    throw new IllegalArgumentException("fd cannot be null");
  }
  
  public void setContainerView(ViewGroup paramViewGroup)
  {
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
  }
  
  public static abstract interface AwPdfExporterCallback
  {
    public abstract void pdfWritingDone(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwPdfExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */