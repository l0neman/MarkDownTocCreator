# MarkDownTocCreator

- [使用方法](#使用方法)
- [实现](#实现)
- [提示](#提示)
- [LICENSE](#license)



GitHub Markdown TOC 生成工具。



专用于生成支持 GitHib 的 README.md 文档目录。

GitHub 所支持的 MarkDown 并不支持使用 `[TOC]` 标签渲染目录，那么可以借此工具生成。



## 使用方法

指定一个后缀为 .md 的文件，目录将被打印出来，复制到  MarkDown 文档最上方即可。

```shell
java -jar MarkDownTocCreator.jar XXX.md
```



## 示例

本人所有仓库的文档都会用这个工具生成目录，例如 [https://github.com/l0neman/program-note](https://github.com/l0neman/program-note) 仓库。



## 实现

1. 依据 MarkDown 语法，出现在 `#` 后面的标题将作为目录出现；
2. 标题 `# 你好` 的目录格式为 `- [你好](#你好)`，小括号内为标题的索引，点击可跳转到标题处；
3. 特殊符号在标题的索引中将被替换为 `""`；空格将被替换为 `"-"`，大写字母将被替换为小写；
4. 如果标题重复，那么第一次出现的标题索引保持不变，后续标题索引依次追加 `-1`，`-2` 依次类推。



## 提示

1. 标题必须符合标准格式，`#` 符号和标题文字本身之间必须有一个空格。

```markdown
# 一级标题
## 二级标题
```



2. 最多支持四层标题，即 `#### 四级标题`，太多层次也没有意义。



## LICENSE

```
Copyright 2020 l0neman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

