package io.l0neman.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by l0neman on 2020/07/07.
 *
 * @author l0neman
 * @version 1.0
 */
public class MarkdownTocCreator {

  // 目录标记
  private static final char TOC_FLAG = '#';

  // 代码段标记
  private static final String CODE_FLAG = "```";

  // 允许出现在目录中的字符
  // 不是 [数字 + 字母 + 中文 + ASCII 表中筛选出的有效字符]
  private static final String NOT_ALLOW_CHAR_REGEX =
      "[^\\w\\u4e00-\\u9fa5-_ƒ^ŠŒŽšœŸªµºÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ]";

  // 存放相同目录名的索引
  // 按照规则，第一个目录为原始字符串，例如“哈哈”，第二个重复名字开始依次为：“哈哈-1”，“哈哈-2”，依次类推
  private final Map<String, Integer> tocCount = new HashMap<>();

  private String fixSpecialChar(String toc) {
    return toc.toLowerCase().replace(' ', '-').replaceAll(NOT_ALLOW_CHAR_REGEX, "");
  }

  /*
    输出标准 github toc 目录
   */
  private String getToc(String tocLine) {
    int index = tocLine.indexOf("# ");

    // 取得目录内容
    String srcToc = tocLine.substring(index + 2);
    String fixToc = fixSpecialChar(srcToc);

    // 处理目录重复的情况
    Integer count = tocCount.get(fixToc);
    if (count == null) {
      tocCount.put(fixToc, 0);
    } else {
      ++count;
      tocCount.put(fixToc, count);
      fixToc = fixToc + "-" + count;
    }


    String toc;
    // 根据 # 符号数量确定目录深度，最大支持 4 层 #
    switch (index) {
      case 0:
        toc = "-";
        break;
      case 1:
        toc = "  -";
        break;
      case 2:
        toc = "    -";
        break;
      case 3:
        toc = "      -";
        break;
      default:
        toc = null;
        break;
    }

    if (toc != null) {
      toc += String.format(" [%s](#%s)", srcToc, fixToc);
    }

    return toc;
  }

  private boolean insideCode = false;

  private void handle(String file) {
    IoUtils.readToLines(new File(file), tocLine -> {

      if (tocLine.startsWith(CODE_FLAG)) {
        // 进入代码片段中，# 符号将不算做目录
        insideCode = !insideCode;
      }

      if (!insideCode && tocLine.startsWith("#")) {
        String convert = getToc(tocLine);

        if (convert != null) {
          System.out.println(convert);
        }
      }
    });
  }

  public static void main(String[] args) {
    if (args.length == 0 || args[0].equals("") || !args[0].endsWith(".md")) {
      System.out.println("Please specify a markdown file.");
      return;
    }

    new MarkdownTocCreator().handle(args[0]);
  }
}
