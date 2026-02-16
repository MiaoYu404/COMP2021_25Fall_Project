# CLEVIS
PolyU COMP 2411 25 Fall Group Project: A Command Line Vector Graphics Software

## 要求（编辑中）：
- 命令行交互（CLI）
- 用户可以创建并编辑向量图形，包括线段、圆、矩形、正方形。
- 所有的数字都是 `double`，输出时保留两位小数 2 decimal places。
- 导入 COMP2021_PROJECT.xml 到 Intellij IDEA 以检查代码格式要求。

### 文件结构与功能说明

### 项目主要结构

```
Clevis/
├── src/
│   └── clevis/
│       ├── Application.java           # 主程序入口
│       ├── sql/                       # 几何计算相关类
│       │   ├── Geometry.java          # 几何计算核心功能
│       │   ├── Lines.java             # 线段相关计算
│       │   └── Points.java            # 点相关计算
│       ├── system/                    # 系统核心类
│       │   ├── Clevis.java            # 系统主类
│       │   ├── Console.java           # 控制台交互处理
│       │   └── IO.java                # 输入输出处理
│       └── util/                      # 几何形状工具类
│           ├── Circle.java            # 圆形实现
│           ├── Group.java             # 形状组实现
│           ├── Line.java              # 线段实现
│           ├── Point.java             # 点实现
│           ├── Rectangle.java         # 矩形实现
│           ├── Shape.java             # 形状接口
│           └── Square.java            # 正方形实现
└── test/                              # 测试代码目录
    └── clevis/
        └── model/                     # 模型测试
            ├── TestCircle.java        # 圆形测试
            ├── TestLine.java          # 线段测试
            ├── TestPoint.java         # 点测试
            └── TestRectangle.java     # 矩形测试
```

### 核心文件功能说明

#### 1. 主程序入口

| 文件路径 | 主要功能 | 核心函数 | 作用说明 |
|---------|---------|---------|--------|
| `Application.java` | 程序入口点，启动系统并处理基本命令行输入 | `main(String[] args)` | 初始化Clevis和Console对象，处理用户输入命令 |

#### 2. 系统核心类

| 文件路径 | 主要功能 | 核心函数 | 作用说明 |
|---------|---------|---------|--------|
| `system/Clevis.java` | 系统主类，作为程序的核心控制类 | 构造函数 | 初始化系统组件 |
| `system/Console.java` | 控制台交互处理，管理形状和命令执行 | `add(String[] args)` | 根据参数创建并添加不同类型的形状 |
| | | `group(String newName, List<String> names)` | 将多个形状组合成一个组 |
| | | `ungroup(String name)` | 解散指定的组 |
| | | `delete(String name)` | 删除指定的形状或组 |
| | | `move(String name, double dx, double dy)` | 移动指定的形状 |
| | | `shapeAt(double x, double y)` | 返回指定坐标位置的顶层形状 |
| | | `intersects(String s1, String s2)` | 判断两个形状是否相交 |
| `system/IO.java` | 处理输入输出操作 | (待实现) | 处理文件读写和日志记录 |

#### 3. 几何计算类

| 文件路径 | 主要功能 | 核心函数 | 作用说明 |
|---------|---------|---------|--------|
| `sql/Geometry.java` | 提供几何计算的核心功能 | `sign(double a)` | 考虑精度误差的符号判断函数 |
| | | `intersects(Shape s1, Shape s2)` | 判断两个形状是否相交 |
| `sql/Points.java` | 点相关的计算功能 | (待实现) | 提供点的向量运算和其他操作 |
| `sql/Lines.java` | 线段相关的计算功能 | (待实现) | 提供线段的相交、距离等计算 |

#### 4. 几何形状工具类

| 文件路径 | 主要功能 | 核心函数 | 作用说明 |
|---------|---------|---------|--------|
| `util/Shape.java` | 所有形状的接口定义 | `name()` | 返回形状名称 |
| | | `move(double dx, double dy)` | 移动形状 |
| | | `boundingBox()` | 计算形状的边界框 |
| | | `getFather()/setFather()` | 管理形状的父子关系 |
| `util/Point.java` | 点的实现，支持向量运算 | `x()/y()` | 获取坐标值 |
| | | `add(Point p)` | 向量加法 |
| | | `dot(Point p)` | 向量点积 |
| | | `distance(Point p)` | 计算两点距离 |
| | | `coveredBy(Shape s)` | 判断点是否被形状覆盖 |
| `util/Line.java` | 线段实现 | 构造函数 | 创建线段 |
| | | `from()/to()` | 获取线段端点 |
| | | `direction()` | 计算方向向量 |
| | | `move(double dx, double dy)` | 移动线段 |
| `util/Rectangle.java` | 矩形实现 | 构造函数 | 创建矩形 |
| | | `points()` | 获取矩形顶点 |
| | | `width()/height()` | 获取矩形尺寸 |
| | | `lines()` | 获取矩形的四条边 |
| `util/Circle.java` | 圆形实现 | 构造函数 | 创建圆形 |
| | | `center()` | 获取圆心 |
| | | `r()` | 获取半径 |
| `util/Square.java` | 正方形实现 | 构造函数 | 创建正方形 |
| | | `side()` | 获取边长 |
| `util/Group.java` | 形状组实现 | 构造函数 | 创建形状组 |
| | | `shapes()` | 获取组成员形状 |

### 使用示例

#### 创建形状

```java
// 创建一个矩形
Point topLeft = new Point(10, 20);
Rectangle rectangle = new Rectangle("rect1", topLeft, 50, 30);

// 创建一条线段
Point from = new Point(0, 0);
Point to = new Point(100, 100);
Line line = new Line("line1", from, to);

// 创建一个圆
Point center = new Point(50, 50);
Circle circle = new Circle("circle1", center, 25);
```

#### 移动形状

```java
// 移动矩形，向右移动10单位，向上移动5单位
rectangle.move(10, -5);

// 或者使用Point作为向量
Point vector = new Point(10, -5);
console.move("rect1", vector);
```

#### 形状操作

```java
// 计算边界框
Shape boundingBox = rectangle.boundingBox();

// 判断点是否在形状内
Point testPoint = new Point(30, 30);
bool isInside = testPoint.inside(rectangle);

// 判断两个形状是否相交
boolean doIntersect = Geometry.intersects(rectangle, circle);
```

#### 组操作

```java
// 创建一个包含多个形状的组
List<String> members = Arrays.asList("rect1", "line1", "circle1");
console.group("group1", members);

// 解散组
console.ungroup("group1");
```

### 启动
1. (4 points) 在Clevis会话期间执行的所有操作都应记录到两个文件中：
   1. HTML 文件，指令以表格形式记录，表格每行包含两列:
      1. 第一列是操作序号（index）；
      2. 第二列是指令内容。
   2. TXT 文件，命令按执行顺序记录。每行一条指令。
   3. 这两个文件的名称应在启动Clevis工具时作为参数输入
   4. 运行示例：`java hk.edu.polyu.comp.comp2021.clevis.Application -html d:\log.html -txt d:\log.txt`。

### 图形
1. (1.5 points) 你需要创建一个矩形 `rectangle n x y w h`
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


###问题反馈：
