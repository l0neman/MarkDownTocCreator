package io.l0neman.util;

import java.io.*;
import java.nio.channels.Channels;

/**
 * Created by l0neman on 2020/07/07.
 *
 * @author l0neman
 */
public class IoUtils {
  public interface LineCallback {

    void onLine(String param1String);
  }

  public static void readToLines(File file, LineCallback callback) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(Channels.newReader((new FileInputStream(file))
          .getChannel(), "utf8"));
      String line;
      while ((line = br.readLine()) != null) {
        callback.onLine(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(br);
    }
  }

  private static void closeQuietly(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException ignore) {
      } catch (RuntimeException e) {
        throw new RuntimeException(e);
      }
    }
  }
}