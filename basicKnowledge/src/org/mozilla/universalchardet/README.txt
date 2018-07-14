======================================================================
摘自：http://www.open-open.com/lib/view/open1373879020591.html
Mozilla在很多年前就做了一个非常优秀的编码检测工具，叫chardet(java版jchardet )，后来有发布了算法更加优秀的universalchardet，用于Firefox的自动编码识别。另外Apache内容抽取项目Tika的发布包tika-app-1.*.jar(自1.2及以后版本)其中打包了juniversalchardet。
注意：如果试图识别几个字节的短文本编码，可能会出现了识别错误，这应该是算法实现本身的缺陷，但识别稍大一点文本编码，正确率则非常高，尤其较chardet要高的多。


======================================================================
摘自：https://github.com/albfernandez/juniversalchardet/releases


juniversalchardet


1. What is it?

juniversalchardet is a Java port of "universalchardet",
that is the encoding detector library of Mozilla.

The original code of universalchardet is available at
http://lxr.mozilla.org/seamonkey/source/extensions/universalchardet/

Techniques used by universalchardet are described at
http://www.mozilla.org/projects/intl/UniversalCharsetDetection.html


2. Encodings that can be detected

- Chinese
  - ISO-2022-CN
  - BIG-5
  - EUC-TW
  - GB18030
  - HZ-GB-2312

- Cyrillic
  - ISO-8859-5
  - KOI8-R
  - WINDOWS-1251
  - MACCYRILLIC
  - IBM866
  - IBM855

- Greek
  - ISO-8859-7
  - WINDOWS-1253

- Hebrew
  - ISO-8859-8
  - WINDOWS-1255

- Japanese
  - ISO-2022-JP
  - Shift_JIS
  - EUC-JP

- Korean
  - ISO-2022-KR
  - EUC-KR

- Unicode
  - UTF-8
  - UTF-16BE / UTF-16LE
  - UTF-32BE / UTF-32LE / X-ISO-10646-UCS-4-3412 / X-ISO-10646-UCS-4-2143

- Others
  - WINDOWS-1252

All supported encodings are listed in org.mozilla.universalchardet.Constants.


3. How to use it

(1) Construct an instance of org.mozilla.universalchardet.UniversalDetector.
(2) Feed some data (typically some thousand bytes) to the detector
    using UniversalDetector.handleData().
(3) Notify the detector of the end of data by using
    UniversalDetector.dataEnd().
(4) Get the detected encoding name by using
    UniversalDetector.getDetectedCharset().
(5) Don't forget to call UniversalDetector.reset() before you reuse
    the detector instance for another guess.


------------ Sample Code ------------
import org.mozilla.universalchardet.UniversalDetector;

public class TestDetector
{
  public static void main(String[] args)
  {
    byte[] buf = new byte[4096];
    java.io.FileInputStream fis = new java.io.FileInputStream("test.txt");

    // (1)
    UniversalDetector detector = new UniversalDetector(null);

    // (2)
    int nread;
    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
      detector.handleData(buf, 0, nread);
    }
    // (3)
    detector.dataEnd();

    // (4)
    String encoding = detector.getDetectedCharset();
    if (encoding != null) {
      System.out.println("Detected encoding = " + encoding);
    } else {
      System.out.println("No encoding detected.");
    }

    // (5)
    detector.reset();
  }
}


4. Related Woks

- jchardet  http://jchardet.sourceforge.net/

jchardet is another Java port of the Mozilla's encoding dectection library.
The main difference between jchardet and juniversalchardet is modules
they are based on. jchardet is based on the "chardet" module that has
long existed. juniversalchardet is based on the "universalchardet" module
that is new and generally provides better accuracy on detection results.


5. License

The library is subject to the Mozilla Public License Version 1.1.
Alternatively, the library may be used under the terms of either
the GNU General Public License Version 2 or later, or the GNU
Lesser General Public License 2.1 or later.
