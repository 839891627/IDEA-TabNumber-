# Tab Number Index

轻量级 IntelliJ IDEA 插件，为编辑器标签页添加数字前缀，并提供 1–9 的快捷跳转动作。

## 功能
- 在每个窗口内为标签标题添加序号，随打开/关闭/切换自动刷新。
- 提供 `TabNumberIndex.SwitchTab1` … `SwitchTab9` 动作，可配合 IdeaVim 映射数字跳转。
- 无需配置即可使用，适用于 IntelliJ IDEA Community/Ultimate 2023.3 及以上。

## 使用方法
1. 安装插件（Marketplace 或 “Install Plugin from Disk…”）。
2. 使用内置动作，或在 IdeaVim 中自定义映射，例如：
   ```vim
   nnoremap <leader>1 :action TabNumberIndex.SwitchTab1<CR>
   nnoremap <leader>2 :action TabNumberIndex.SwitchTab2<CR>
   ```

## 开发
- 构建插件包：`./gradlew buildPlugin`（产物位于 `build/distributions`）。
- 在沙盒 IDE 运行：`./gradlew runIde`。
- 代码要求：Java 17；项目服务监听标签变更并刷新标题，`EditorTabTitleProvider` 负责生成带编号的标题。***
