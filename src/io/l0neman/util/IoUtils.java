package io.l0neman.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

public class IoUtils {
  public static void write(File file, String text) {
    FileChannel channel = null;

    try {
      channel = (new FileOutputStream(file)).getChannel();
      byte[] bytes = text.getBytes();
      ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
      buffer.put(bytes).flip();
      channel.write(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(channel);
    }
  }


  public static void write(String file, String text) {
    write(new File(file), text);
  }


  public static String read(File file) {
    FileChannel channel = null;

    try {
      FileInputStream fis = new FileInputStream(file);
      channel = fis.getChannel();
      StringBuilder sb = new StringBuilder();
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

      long size = channel.size();
      while (channel.position() < size) {
        byteBuffer.clear();
        channel.read(byteBuffer);
        byteBuffer.flip();
        sb.append(new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit()));
      }

      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(channel);
    }

    return "";
  }


  public static String read(String file) { return read(new File(file)); }


  public static void copy(File in, File out) {
    FileChannel inChannel = null;
    FileChannel outChannel = null;

    try {
      inChannel = (new FileInputStream(in)).getChannel();
      outChannel = (new FileOutputStream(out)).getChannel();
      inChannel.transferTo(0L, inChannel.size(), outChannel);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(outChannel);
      closeQuietly(inChannel);
    }
  }

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