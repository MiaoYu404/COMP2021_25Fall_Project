# Project_CLEVIS
PolyU COMP 2411 25 Fall Group Project: A Command Line Vector Graphics Software

## 要求（编辑中）：
- 命令行交互（CLI）
- 用户可以创建并编辑向量图形，包括线段、圆、矩形、正方形。
- 所有的数字都是 `double`，输出时保留两位小数 2 decimal places。
- 导入 COMP2021_PROJECT.xml 到 Intellij IDEA 以检查代码格式要求。

### 启动
1. 所有的指令需通过两个文件。
   1. HTML 文件，指令以表格形式记录，每行有两列。
      1. 第一列是指令下标（index）；
      2. 第二列是指令内容。
   2. TXT 文件，存储指令的执行顺序。每行一条指令。
   3. 运行示例：`java hk.edu.polyu.comp.comp2021.clevis.Application -html d:\log.html -txt d:\log.txt`。

### 图形
1. 你需要创建一个矩形 `n x y w h`
    - 创建一个名称为 n 的矩形，top-left 角的坐标是 $(x, y)$，宽 $w$，高 $h$。
2. 你需要创建一个线段 `line n x1 y1 x2 y2`
    - 创建一个名称为 n 的线段，两端点分别是 $(x1, y1)$, $(x2, y2)$。
3. 你需要创建一个圆 `circle n x y r`
    - 创建一个名称为 n 的圆，圆心是 $(x, y)$，半径是 $r$。
4. 你需要创建一个方形 `square n x y l`
    - 创建一个名称是 n 的正方形，中心是 $(x, y)$，边长为 $l$。

### 工具

1. 你需要实现为非空的图形集合创建一个组（group）。 `group n n1 n2`
   - 相当于创建一个名为 "n" 的图形。
   - 成员 n1, n2 等图形都**不能执行**操作 $3$ ~ $8$。
2. 你需要实现将一个组解散（ungroup）。 `ungroup n`
3. 你需要实现删除（delete）一个图形。 `delete n`
4. 你需要实现计算一个图形的最小 boundingBox。 `boundingbox n`
5. 你需要实现移动一个图形。 `move n dx dy`
6. 你需要实现找到最顶上的图形。 `shapeAt x y`
   - 图形根据渲染的顺序会有叠加关系。对于一个坐标 $x, y$，请输出在这一点最顶上的图形。
7. 你需要判断两个图形是否相交。 `intersect n1 n2`
8. 它可以列出图形的基本信息。 `list n`
9. 它可以列出目前已绘制的所有图形。 `listAll`
10. 用户可以退出 CLEVIS。`quit`
