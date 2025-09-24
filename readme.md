# CLEVIS 向量图形编辑器
## 项目简介
CLEVIS 是一个基于 Java 开发的命令行向量图形编辑器，支持创建、编辑和管理多种向量图形元素，如矩形、线段、圆和正方形。该编辑器提供了丰富的图形操作功能，并支持将所有命令记录到日志文件中。

## 功能特点
- 创建多种基本图形：矩形、线段、圆和正方形
- 支持图形的组合（group）和解散（ungroup）操作
- 提供图形移动、删除、边界框计算等编辑功能
- 支持图层概念，可查找指定坐标点上的最顶层图形
- 能够检测两个图形是否相交
- 提供图形信息查询功能
- 将所有命令记录到 HTML 和 TXT 日志文件中
- 所有数值输出保留两位小数
## 系统要求
- Java Development Kit (JDK) 8 或更高版本
- 支持标准 Java 命令行环境
## 项目结构
```
groupproject/
├── clevis/
│   ── Application.java         # 主应用类
│   ── GraphicsManager.java     # 图形管理器
│   ── model/
│   │   ├── Shape.java          # 图形抽象基类
│   │   ├── Rectangle.java       # 矩形类
│   │   ├── Line.java            # 线段类
│   │   ├── Circle.java          # 圆类
│   │   ├── Square.java          # 正方形类
│   │   └── Group.java           # 图形组类
│   └── util/
│       └── BoundingBox.java      # 边界框工具类
```
## 编译指南
1. 
   确保已安装 JDK24 或更高版本
2. 
   打开命令行终端
3. 
   导航到项目根目录
4. 
   执行以下编译命令：
```
javac -d bin *.java model\*.java util\*.java
```
## 运行指南
使用以下命令启动 CLEVIS 编辑器：

```
java -cp bin Application -html <html_log_path> -txt <txt_log_path>
or
java -cp bin Application
```
参数说明 ：
### 如果不指定 -html 和 -txt 参数，程序将在log文件夹中生成默认的 log.html 和 log.txt 文件。
- -html <html_log_path> : 指定 HTML 日志文件路径，用于以表格形式记录所有命令
- -txt <txt_log_path> : 指定 TXT 日志文件路径，用于按顺序记录所有命令
示例 ：

```
java -cp bin Application -html ./log.html -txt ./log.txt
```
## 命令参考
### 一、图形创建命令 
1. 创建矩形
命令格式 ：

```
n <name> <x> <y> <width> <height>
```
参数说明 ：

- <name> : 矩形的唯一名称
- <x> , <y> : 矩形左上角顶点的坐标
- <width> : 矩形的宽度
- <height> : 矩形的高度
示例 ：

```
n rect1 10.0 20.0 50.0 30.0
``` 
2. 创建线段
命令格式 ：

```
line <name> <x1> <y1> <x2> <y2>
```
参数说明 ：

- <name> : 线段的唯一名称
- <x1> , <y1> : 线段起点坐标
- <x2> , <y2> : 线段终点坐标
示例 ：

```
line line1 0.0 0.0 100.0 100.0
``` 
3. 创建圆
命令格式 ：

```
circle <name> <x> <y> <radius>
```
参数说明 ：

- <name> : 圆的唯一名称
- <x> , <y> : 圆心坐标
- <radius> : 圆的半径（必须为正数）
示例 ：

```
circle circle1 50.0 50.0 25.0
``` 
4. 创建正方形    
命令格式 ：

```
square <name> <x> <y> <length>
```
参数说明 ：

- <name> : 正方形的唯一名称
- <x> , <y> : 正方形中心点坐标
- <length> : 正方形的边长（必须为正数）
示例 ：

```
square square1 50.0 50.0 40.0
```
### 二、工具命令 

1. 创建图形组
命令格式 ：

```
group <group_name> <member1> 
<member2> ...
```
参数说明 ：

- <group_name> : 组的唯一名称
- <member1> , <member2> , ...: 要包含在组中的图形名称（这些图形不能已经在其他组中）
示例 ：

``` 
group myGroup rect1 circle1 line1
``` 
2. 解散图形组
命令格式 ：

```
ungroup <group_name>
```
参数说明 ：

- <group_name> : 要解散的组名称
示例 ：

```
ungroup myGroup
``` 
3. 删除图形
命令格式 ：

```
delete <name>
```
参数说明 ：

- <name> : 要删除的图形或组的名称
示例 ：

```
delete rect1
``` 
4. 计算边界框
命令格式 ：

```
boundingbox <name>
```
参数说明 ：

- <name> : 图形或组的名称
示例 ：

```
boundingbox circle1
``` 
5. 移动图形
命令格式 ：

```
move <name> <dx> <dy>
```
参数说明 ：

- <name> : 要移动的图形或组的名称
- <dx> : x 方向的移动距离
- <dy> : y 方向的移动距离
示例 ：

```
move rect1 10.5 -5.2
``` 
6. 查找最顶层图形
命令格式 ：

```
shapeAt <x> <y>
```
参数说明 ：

- <x> , <y> : 查询点的坐标
示例 ：

```
shapeAt 30.0 40.0
``` 
7. 判断图形相交
命令格式 ：

```
intersect <name1> <name2>
```
参数说明 ：

- <name1> , <name2> : 要判断的两个图形或组的名称
示例 ：

```
intersect rect1 circle1
``` 
8. 列出单个图形信息
命令格式 ：

```
list <name>
```
参数说明 ：

- <name> : 要查询的图形或组的名称
示例 ：

```
list rect1
``` 
9. 列出所有图形
命令格式 ：

```
listAll
```
### 三、退出命令
命令格式 ：

```
quit
```

## 数据格式说明
- 所有坐标和尺寸数值均为浮点数（double类型）
- 所有数值输出时保留两位小数
- 图形名称必须唯一，且不能为空
- 命令日志会同时记录到指定的 HTML 和 TXT 文件中
  - HTML 文件以表格形式记录，包含命令索引和命令内容
  - TXT 文件按顺序记录每条命令
## 错误处理
CLEVIS 编辑器会对以下情况进行错误处理并显示相应提示：

- 无效的命令格式
- 图形名称重复或不存在
- 无效的数值格式
- 半径或边长为非正数
- 尝试操作已经在组中的图形
## 示例工作流
以下是一个简单的 CLEVIS 使用示例：

```
# 创建几个基本图形
n rect1 10.0 10.0 40.0 30.0
circle circle1 50.0 50.0 20.0
line line1 0.0 0.0 100.0 100.0

# 查看所有图形
listAll

# 创建一个组
group myGroup rect1 circle1

# 移动组
move myGroup 5.0 5.0

# 检查边界框
boundingbox myGroup

# 检查两个图形是否相交
intersect myGroup line1

# 解散组
ungroup myGroup

# 删除图形
delete rect1

# 退出程序
quit
```
## 注意事项
1. 
   所有命令中的参数需要按照指定顺序输入
2. 
   图形名称区分大小写
3. 
   确保日志文件路径具有写入权限
4. 
   导入 COMP2021_PROJECT.xml 到 IntelliJ IDEA 可以检查代码格式要求
## 开发说明
该项目使用标准 Java 开发，遵循面向对象设计原则：

- 使用抽象类 Shape 定义所有图形的共同接口
- 通过继承实现不同类型的具体图形类
- 使用组合模式实现图形组功能
- 采用管理器模式统一管理所有图形操作


# 图形化
直接用浏览器打开frontend，html，即可查看图形化界面


# 下一步任务
- 优化界面和操作
- 将时间戳的log文件转换为历史记录，用户可以查看和回放之前的操作
- 将frontend中的操作保存进log文件夹