# Project_CLEVIS
PolyU COMP 2411 25 Fall Group Project: A Command Line Vector Graphics Software

## 要求（编辑中）：
- 命令行交互（CLI）
- 用户可以创建并编辑向量图形，包括线段、圆、矩形、正方形。
- 所有的数字都是 `double`，输出时保留两位小数 2 decimal places。
- 导入 COMP2021_PROJECT.xml 到 Intellij IDEA 以检查代码格式要求。

### 启动
1. (4 points) 所有的指令需通过两个文件。
   1. HTML 文件，指令以表格形式记录，每行有两列。
      1. 第一列是指令下标（index）；
      2. 第二列是指令内容。
   2. TXT 文件，存储指令的执行顺序。每行一条指令。
   3. 运行示例：`java hk.edu.polyu.comp.comp2021.clevis.Application -html d:\log.html -txt d:\log.txt`。

### 图形
1. (1.5 points) 你需要创建一个矩形 `n x y w h`
    - 创建一个名称为 n 的矩形，top-left 角的坐标是 $(x, y)$，宽 $w$，高 $h$。
2. (1.5 points) 你需要创建一个线段 `line n x1 y1 x2 y2`
    - 创建一个名称为 n 的线段，两端点分别是 $(x1, y1)$, $(x2, y2)$。
3. (1.5 points) 你需要创建一个圆 `circle n x y r`
    - 创建一个名称为 n 的圆，圆心是 $(x, y)$，半径是 $r$。
4. (1.5 points) 你需要创建一个方形 `square n x y l`
    - 创建一个名称是 n 的正方形，中心是 $(x, y)$，边长为 $l$。

### 工具

1. (2 points) 你需要实现为非空的图形集合创建一个组（group）。 `group n n1 n2`
   - 相当于创建一个名为 "n" 的图形。
   - 成员 n1, n2 等图形都**不能执行**操作 $3$ ~ $8$。
2. (2 points) 你需要实现将一个组解散（ungroup）。 `ungroup n`
3. (2 points) 你需要实现删除（delete）一个图形。 `delete n`
   - 如果要删除的是一个 group，他**所有的成员**也将被删除。
4. (4 points) 你需要实现计算一个图形的最小 boundingBox。 `boundingbox n`
   - 以 "x, y, w, h" 的格式输出。如矩形定义那样。
5. (4 points) 你需要实现移动一个图形。 `move n dx dy`
6. (4 points) 你需要实现找到最顶上的图形。 `shapeAt x y`
   - 图形根据渲染的顺序会有叠加关系。对于一个坐标 $x, y$，请输出在这一点最顶上的图形。
   - 对于一个点 $(x, y)$，如果它在一个图形外，并且距离其外边框的最短距离 $\le 0.05$，则称它被该图形覆盖，可以作为 shapeAt 的输出。
      - 对于一个 group，其外边框为所有成员图形的外边框。
7. (4 points) 你需要判断两个图形是否相交。 `intersect n1 n2`
   - 相交取决于两图形的最小 bounding box 是否有重合部分。
8. (4 points) 它可以列出图形的基本信息。 `list n`
   - 需包含构建该图形所用的所有信息。
9. (2 points) 它可以列出目前已绘制的所有图形。 `listAll`
   - 以图层顺序从最前到最后。
10. (2 points) 用户可以退出 CLEVIS。`quit`

### 附加分
1. (8 points) 支持图形化界面。如果你实现这个效果，你需要为其加入一个文本输入框以供用户输入指令。他还需要支持界面放大缩小以让用户得到更好的视觉体验。
2. (4 points) 支持撤销、重做。`boundingBox`, `intersect`, `list`, `listAll` 和 `quit` 除外
