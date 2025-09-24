// 基础形状类 - 对应Java中的Shape抽象类
class Shape {
    constructor(name) {
        this.name = name;
        this.inGroup = false;
    }
    
    getName() { return this.name; }
    isInGroup() { return this.inGroup; }
    setInGroup(inGroup) { this.inGroup = inGroup; }
    
    // 抽象方法，由子类实现
    move(dx, dy) { throw new Error("未实现move方法"); }
    contains(x, y) { throw new Error("未实现contains方法"); }
    getBoundingBox() { throw new Error("未实现getBoundingBox方法"); }
    toString() { throw new Error("未实现toString方法"); }
    draw(ctx, selected = false) { throw new Error("未实现draw方法"); }
}

// 边界框类 - 对应Java中的BoundingBox类
class BoundingBox {
    constructor(minX, minY, maxX, maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }
    
    getMinX() { return this.minX; }
    getMinY() { return this.minY; }
    getMaxX() { return this.maxX; }
    getMaxY() { return this.maxY; }
    
    intersects(other) {
        return !(this.maxX < other.minX ||
                this.minX > other.maxX ||
                this.maxY < other.minY ||
                this.minY > other.maxY);
    }
    
    toString() {
        return `(${this.minX.toFixed(2)},${this.minY.toFixed(2)}) to (${this.maxX.toFixed(2)},${this.maxY.toFixed(2)})`;
    }
}

// 矩形类 - 对应Java中的Rectangle类
class Rectangle extends Shape {
    constructor(name, x, y, width, height) {
        super(name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    move(dx, dy) {
        this.x += dx;
        this.y += dy;
    }
    
    contains(px, py) {
        return px >= this.x && px <= this.x + this.width && py >= this.y && py <= this.y + this.height;
    }
    
    getBoundingBox() {
        return new BoundingBox(this.x, this.y, this.x + this.width, this.y + this.height);
    }
    
    toString() {
        return `Rectangle '${this.name}': top-left=(${this.x.toFixed(2)},${this.y.toFixed(2)}), width=${this.width.toFixed(2)}, height=${this.height.toFixed(2)}`;
    }
    
    draw(ctx, selected = false) {
        ctx.beginPath();
        ctx.rect(this.x, this.y, this.width, this.height);
        ctx.fillStyle = '#4CAF50';
        ctx.fill();
        ctx.strokeStyle = selected ? '#FF0000' : '#000000';
        ctx.lineWidth = selected ? 3 : 1;
        if (selected) {
            ctx.setLineDash([5, 5]);
        }
        ctx.stroke();
        ctx.setLineDash([]);
        
        // 绘制名称
        ctx.fillStyle = '#000000';
        ctx.font = '12px Arial';
        ctx.fillText(this.name, this.x + 5, this.y + 15);
    }
}

// 线条类 - 对应Java中的Line类
class Line extends Shape {
    constructor(name, x1, y1, x2, y2) {
        super(name);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    move(dx, dy) {
        this.x1 += dx;
        this.y1 += dy;
        this.x2 += dx;
        this.y2 += dy;
    }
    
    contains(px, py) {
        // 计算点到直线的距离
        const A = px - this.x1;
        const B = py - this.y1;
        const C = this.x2 - this.x1;
        const D = this.y2 - this.y1;

        const dot = A * C + B * D;
        const lenSq = C * C + D * D;
        let param = -1;

        if (lenSq !== 0) param = dot / lenSq;

        let xx, yy;

        if (param < 0) {
            xx = this.x1;
            yy = this.y1;
        } else if (param > 1) {
            xx = this.x2;
            yy = this.y2;
        } else {
            xx = this.x1 + param * C;
            yy = this.y1 + param * D;
        }

        const dx = px - xx;
        const dy = py - yy;
        return Math.sqrt(dx * dx + dy * dy) < 5; // 更大的容差，便于选择
    }
    
    getBoundingBox() {
        const minX = Math.min(this.x1, this.x2);
        const minY = Math.min(this.y1, this.y2);
        const maxX = Math.max(this.x1, this.x2);
        const maxY = Math.max(this.y1, this.y2);
        return new BoundingBox(minX, minY, maxX, maxY);
    }
    
    toString() {
        return `Line '${this.name}': from (${this.x1.toFixed(2)},${this.y1.toFixed(2)}) to (${this.x2.toFixed(2)},${this.y2.toFixed(2)})`;
    }
    
    draw(ctx, selected = false) {
        ctx.beginPath();
        ctx.moveTo(this.x1, this.y1);
        ctx.lineTo(this.x2, this.y2);
        ctx.strokeStyle = selected ? '#FF0000' : '#000000';
        ctx.lineWidth = selected ? 3 : 2;
        if (selected) {
            ctx.setLineDash([5, 5]);
        }
        ctx.stroke();
        ctx.setLineDash([]);
        
        // 绘制名称
        const midX = (this.x1 + this.x2) / 2;
        const midY = (this.y1 + this.y2) / 2;
        ctx.fillStyle = '#000000';
        ctx.font = '12px Arial';
        ctx.fillText(this.name, midX + 5, midY - 5);
    }
}

// 圆形类 - 对应Java中的Circle类
class Circle extends Shape {
    constructor(name, x, y, radius) {
        super(name);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    move(dx, dy) {
        this.x += dx;
        this.y += dy;
    }
    
    contains(px, py) {
        const dx = px - this.x;
        const dy = py - this.y;
        return dx * dx + dy * dy <= this.radius * this.radius;
    }
    
    getBoundingBox() {
        return new BoundingBox(this.x - this.radius, this.y - this.radius, 
                              this.x + this.radius, this.y + this.radius);
    }
    
    toString() {
        return `Circle '${this.name}': center=(${this.x.toFixed(2)},${this.y.toFixed(2)}), radius=${this.radius.toFixed(2)}`;
    }
    
    draw(ctx, selected = false) {
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
        ctx.fillStyle = '#2196F3';
        ctx.fill();
        ctx.strokeStyle = selected ? '#FF0000' : '#000000';
        ctx.lineWidth = selected ? 3 : 1;
        if (selected) {
            ctx.setLineDash([5, 5]);
        }
        ctx.stroke();
        ctx.setLineDash([]);
        
        // 绘制名称
        ctx.fillStyle = '#000000';
        ctx.font = '12px Arial';
        ctx.fillText(this.name, this.x + this.radius + 5, this.y + 5);
    }
}

// 正方形类 - 对应Java中的Square类
class Square extends Shape {
    constructor(name, centerX, centerY, length) {
        super(name);
        this.centerX = centerX;
        this.centerY = centerY;
        this.length = length;
    }
    
    move(dx, dy) {
        this.centerX += dx;
        this.centerY += dy;
    }
    
    contains(px, py) {
        const halfLength = this.length / 2;
        return px >= this.centerX - halfLength && px <= this.centerX + halfLength &&
               py >= this.centerY - halfLength && py <= this.centerY + halfLength;
    }
    
    getBoundingBox() {
        const halfLength = this.length / 2;
        return new BoundingBox(this.centerX - halfLength, this.centerY - halfLength,
                              this.centerX + halfLength, this.centerY + halfLength);
    }
    
    toString() {
        return `Square '${this.name}': center=(${this.centerX.toFixed(2)},${this.centerY.toFixed(2)}), length=${this.length.toFixed(2)}`;
    }
    
    draw(ctx, selected = false) {
        const halfLength = this.length / 2;
        ctx.beginPath();
        ctx.rect(this.centerX - halfLength, this.centerY - halfLength, this.length, this.length);
        ctx.fillStyle = '#FF9800';
        ctx.fill();
        ctx.strokeStyle = selected ? '#FF0000' : '#000000';
        ctx.lineWidth = selected ? 3 : 1;
        if (selected) {
            ctx.setLineDash([5, 5]);
        }
        ctx.stroke();
        ctx.setLineDash([]);
        
        // 绘制名称
        ctx.fillStyle = '#000000';
        ctx.font = '12px Arial';
        ctx.fillText(this.name, this.centerX - halfLength + 5, this.centerY - halfLength + 15);
    }
}

// 组类 - 对应Java中的Group类
class Group extends Shape {
    constructor(name, members) {
        super(name);
        this.members = [...members];
    }
    
    getMembers() {
        return [...this.members];
    }
    
    move(dx, dy) {
        for (const member of this.members) {
            member.move(dx, dy);
        }
    }
    
    contains(x, y) {
        for (const member of this.members) {
            if (member.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    getBoundingBox() {
        if (this.members.length === 0) {
            return new BoundingBox(0, 0, 0, 0);
        }

        const firstBox = this.members[0].getBoundingBox();
        let minX = firstBox.getMinX();
        let minY = firstBox.getMinY();
        let maxX = firstBox.getMaxX();
        let maxY = firstBox.getMaxY();

        for (let i = 1; i < this.members.length; i++) {
            const box = this.members[i].getBoundingBox();
            minX = Math.min(minX, box.getMinX());
            minY = Math.min(minY, box.getMinY());
            maxX = Math.max(maxX, box.getMaxX());
            maxY = Math.max(maxY, box.getMaxY());
        }

        return new BoundingBox(minX, minY, maxX, maxY);
    }
    
    toString() {
        const memberNames = this.members.map(member => member.getName()).join(', ');
        return `Group '${this.name}': members=[${memberNames}]`;
    }
    
    draw(ctx, selected = false) {
        // 绘制所有成员
        for (const member of this.members) {
            member.draw(ctx);
        }
        
        // 如果组被选中，绘制边界框
        if (selected) {
            const box = this.getBoundingBox();
            ctx.beginPath();
            ctx.rect(box.getMinX(), box.getMinY(), box.getMaxX() - box.getMinX(), box.getMaxY() - box.getMinY());
            ctx.strokeStyle = '#FF0000';
            ctx.lineWidth = 3;
            ctx.setLineDash([5, 5]);
            ctx.stroke();
            ctx.setLineDash([]);
        }
    }
}

// 图形管理器 - 对应Java中的GraphicsManager类
class GraphicsManager {
    constructor() {
        this.shapes = new Map();
        this.shapeOrder = []; // 维护渲染顺序
    }
    
    createRectangle(name, x, y, width, height) {
        this.validateName(name);
        const rectangle = new Rectangle(name, x, y, width, height);
        this.addShape(rectangle);
    }
    
    createLine(name, x1, y1, x2, y2) {
        this.validateName(name);
        const line = new Line(name, x1, y1, x2, y2);
        this.addShape(line);
    }
    
    createCircle(name, x, y, radius) {
        this.validateName(name);
        if (radius <= 0) {
            throw new Error("半径必须为正数");
        }
        const circle = new Circle(name, x, y, radius);
        this.addShape(circle);
    }
    
    createSquare(name, x, y, length) {
        this.validateName(name);
        if (length <= 0) {
            throw new Error("边长必须为正数");
        }
        const square = new Square(name, x, y, length);
        this.addShape(square);
    }
    
    createGroup(groupName, memberNames) {
        this.validateName(groupName);
        const members = [];

        for (const memberName of memberNames) {
            const shape = this.shapes.get(memberName);
            if (!shape) {
                throw new Error(`未找到形状: ${memberName}`);
            }
            if (shape.isInGroup()) {
                throw new Error(`形状已在组中: ${memberName}`);
            }
            members.push(shape);
            shape.setInGroup(true);
        }

        const group = new Group(groupName, members);
        this.addShape(group);
    }
    
    ungroup(name) {
        const shape = this.getShape(name);
        if (!(shape instanceof Group)) {
            throw new Error(`形状不是组: ${name}`);
        }

        const group = shape;
        const members = group.getMembers();

        // 移除组
        this.shapes.delete(name);
        const index = this.shapeOrder.indexOf(name);
        if (index !== -1) {
            this.shapeOrder.splice(index, 1);
        }

        // 解组成员
        for (const member of members) {
            member.setInGroup(false);
        }
    }
    
    deleteShape(name) {
        const shape = this.getShape(name);

        // 如果删除组，同时解组其成员
        if (shape instanceof Group) {
            const group = shape;
            for (const member of group.getMembers()) {
                member.setInGroup(false);
            }
        }

        this.shapes.delete(name);
        const index = this.shapeOrder.indexOf(name);
        if (index !== -1) {
            this.shapeOrder.splice(index, 1);
        }
    }
    
    getBoundingBox(name) {
        const shape = this.getShape(name);
        return shape.getBoundingBox();
    }
    
    moveShape(name, dx, dy) {
        const shape = this.getShape(name);
        shape.move(dx, dy);
    }
    
    getTopShapeAt(x, y) {
        // 反向遍历（从上到下）
        for (let i = this.shapeOrder.length - 1; i >= 0; i--) {
            const shapeName = this.shapeOrder[i];
            const shape = this.shapes.get(shapeName);
            if (shape.contains(x, y)) {
                return shapeName;
            }
        }
        return null;
    }
    
    doShapesIntersect(name1, name2) {
        const shape1 = this.getShape(name1);
        const shape2 = this.getShape(name2);
        return shape1.getBoundingBox().intersects(shape2.getBoundingBox());
    }
    
    getShapeInfo(name) {
        const shape = this.getShape(name);
        return shape.toString();
    }
    
    getAllShapesInfo() {
        const infoList = [];
        for (const name of this.shapeOrder) {
            infoList.push(this.shapes.get(name).toString());
        }
        return infoList;
    }
    
    // 辅助方法
    addShape(shape) {
        this.shapes.set(shape.getName(), shape);
        this.shapeOrder.push(shape.getName());
    }
    
    getShape(name) {
        const shape = this.shapes.get(name);
        if (!shape) {
            throw new Error(`未找到形状: ${name}`);
        }
        return shape;
    }
    
    validateName(name) {
        if (!name || name.trim() === '') {
            throw new Error("形状名称不能为空");
        }
        if (this.shapes.has(name)) {
            throw new Error(`名称为'${name}'的形状已存在`);
        }
    }
}

// 主应用类
class Application {
    constructor() {
        this.graphicsManager = new GraphicsManager();
        this.canvas = document.getElementById('drawing-canvas');
        this.ctx = this.canvas.getContext('2d');
        this.logContainer = document.getElementById('log-container');
        this.dialogOverlay = document.getElementById('dialog-overlay');
        this.dialog = document.getElementById('dialog');
        this.dialogTitle = document.getElementById('dialog-title');
        this.dialogContent = document.getElementById('dialog-content');
        this.dialogConfirm = document.getElementById('dialog-confirm');
        this.dialogCancel = document.getElementById('dialog-cancel');
        this.canvasInfo = document.getElementById('canvas-info');
        
        this.selectedShape = null;
        this.isDrawing = false;
        this.currentDrawingType = null;
        this.startPoint = null;
        this.tempShape = null;
        
        this.initCanvas();
        this.initEventListeners();
        this.logMessage("CLEVIS 向量图形编辑器已启动");
    }
    
    initCanvas() {
        // 设置canvas尺寸
        this.canvas.width = this.canvas.offsetWidth;
        this.canvas.height = this.canvas.offsetHeight;
        
        // 监听窗口大小变化
        window.addEventListener('resize', () => {
            this.canvas.width = this.canvas.offsetWidth;
            this.canvas.height = this.canvas.offsetHeight;
            this.render();
        });
    }
    
    initEventListeners() {
        // 工具按钮事件监听
        document.getElementById('create-rectangle').addEventListener('click', () => this.startCreateShape('rectangle'));
        document.getElementById('create-line').addEventListener('click', () => this.startCreateShape('line'));
        document.getElementById('create-circle').addEventListener('click', () => this.startCreateShape('circle'));
        document.getElementById('create-square').addEventListener('click', () => this.startCreateShape('square'));
        
        document.getElementById('group-shapes').addEventListener('click', () => this.groupShapes());
        document.getElementById('ungroup-shape').addEventListener('click', () => this.ungroupShape());
        document.getElementById('delete-shape').addEventListener('click', () => this.deleteSelectedShape());
        document.getElementById('move-shape').addEventListener('click', () => this.moveShapeDialog());
        
        document.getElementById('show-boundingbox').addEventListener('click', () => this.showBoundingBox());
        document.getElementById('check-intersect').addEventListener('click', () => this.checkIntersect());
        document.getElementById('list-shape').addEventListener('click', () => this.listShape());
        document.getElementById('list-all').addEventListener('click', () => this.listAllShapes());
        
        // Canvas事件监听
        this.canvas.addEventListener('mousedown', (e) => this.handleMouseDown(e));
        this.canvas.addEventListener('mousemove', (e) => this.handleMouseMove(e));
        this.canvas.addEventListener('mouseup', (e) => this.handleMouseUp(e));
        
        // 对话框事件监听
        this.dialogCancel.addEventListener('click', () => this.closeDialog());
    }
    
    // 渲染画布
    render() {
        // 清空画布
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        
        // 绘制坐标系
        this.drawCoordinateSystem();

        // 绘制所有形状
        for (const name of this.graphicsManager.shapeOrder) {
            const shape = this.graphicsManager.shapes.get(name);
            const selected = this.selectedShape === name;
            shape.draw(this.ctx, selected);
        }
        
        // 绘制临时形状
        if (this.tempShape) {
            this.tempShape.draw(this.ctx);
        }
    }

    drawCoordinateSystem() {
        const ctx = this.ctx;
        const width = this.canvas.width;
        const height = this.canvas.height;
        
        // 设置坐标系原点在画布中心
        const originX = width / 2;
        const originY = height / 2;
        
        // 设置刻度间距
        const gridSize = 50;
        
        // 绘制网格线
        ctx.strokeStyle = '#e0e0e0';
        ctx.lineWidth = 1;
        
        // 垂直网格线
        for (let x = originX % gridSize; x < width; x += gridSize) {
            ctx.beginPath();
            ctx.moveTo(x, 0);
            ctx.lineTo(x, height);
            ctx.stroke();
        }
        
        // 水平网格线
        for (let y = originY % gridSize; y < height; y += gridSize) {
            ctx.beginPath();
            ctx.moveTo(0, y);
            ctx.lineTo(width, y);
            ctx.stroke();
        }
        
        // 绘制坐标轴
        ctx.strokeStyle = '#333';
        ctx.lineWidth = 2;
        
        // X轴
        ctx.beginPath();
        ctx.moveTo(0, originY);
        ctx.lineTo(width, originY);
        ctx.stroke();
        
        // Y轴
        ctx.beginPath();
        ctx.moveTo(originX, 0);
        ctx.lineTo(originX, height);
        ctx.stroke();
        
        // 绘制坐标轴箭头
        // X轴箭头
        ctx.beginPath();
        ctx.moveTo(width - 10, originY - 5);
        ctx.lineTo(width, originY);
        ctx.lineTo(width - 10, originY + 5);
        ctx.fillStyle = '#333';
        ctx.fill();
        
        // Y轴箭头
        ctx.beginPath();
        ctx.moveTo(originX - 5, 10);
        ctx.lineTo(originX, 0);
        ctx.lineTo(originX + 5, 10);
        ctx.fillStyle = '#333';
        ctx.fill();
        
        // 绘制刻度标签
        ctx.fillStyle = '#333';
        ctx.font = '12px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';
        
        // X轴刻度标签
        for (let x = originX + gridSize; x < width - 20; x += gridSize) {
            const value = Math.round((x - originX) / gridSize);
            ctx.fillText(value.toString(), x, originY + 5);
        }
        
        for (let x = originX - gridSize; x > 20; x -= gridSize) {
            const value = Math.round((x - originX) / gridSize);
            ctx.fillText(value.toString(), x, originY + 5);
        }
        
        // Y轴刻度标签
        ctx.textAlign = 'right';
        ctx.textBaseline = 'middle';
        
        for (let y = originY - gridSize; y > 20; y -= gridSize) {
            const value = Math.round((originY - y) / gridSize);
            ctx.fillText(value.toString(), originX - 5, y);
        }
        
        for (let y = originY + gridSize; y < height - 20; y += gridSize) {
            const value = Math.round((originY - y) / gridSize);
            ctx.fillText(value.toString(), originX - 5, y);
        }
        
        // 绘制原点标签
        ctx.textAlign = 'right';
        ctx.textBaseline = 'top';
        ctx.fillText('(0,0)', originX - 5, originY + 5);
        
        // 绘制坐标轴标签
        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';
        ctx.font = '14px Arial';
        ctx.fillText('X', width - 15, originY + 10);
        
        ctx.save();
        ctx.translate(originX - 20, 15);
        ctx.rotate(-Math.PI / 2);
        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';
        ctx.fillText('Y', 0, 0);
        ctx.restore();
    }
    
    // 日志记录
    logMessage(message) {
        const logEntry = document.createElement('div');
        logEntry.className = 'log-entry';
        logEntry.textContent = message;
        this.logContainer.appendChild(logEntry);
        this.logContainer.scrollTop = this.logContainer.scrollHeight;
    }
    
    // 打开对话框
    openDialog(title, content, onConfirm) {
        this.dialogTitle.textContent = title;
        this.dialogContent.innerHTML = content;
        this.dialogOverlay.style.display = 'flex';
        
        // 移除之前的确认事件监听
        const newConfirmButton = this.dialogConfirm.cloneNode(true);
        this.dialogConfirm.parentNode.replaceChild(newConfirmButton, this.dialogConfirm);
        this.dialogConfirm = newConfirmButton;
        
        // 添加新的确认事件监听
        this.dialogConfirm.addEventListener('click', () => {
            if (onConfirm && onConfirm()) {
                this.closeDialog();
            }
        });
    }
    
    // 关闭对话框
    closeDialog() {
        this.dialogOverlay.style.display = 'none';
    }
    
    // 开始创建形状
    startCreateShape(type) {
        this.currentDrawingType = type;
        this.isDrawing = false;
        this.selectedShape = null;
        this.tempShape = null;
        this.canvasInfo.textContent = `点击并拖动创建${this.getShapeTypeName(type)}`;
        this.logMessage(`准备创建${this.getShapeTypeName(type)}`);
    }
    
    // 获取形状类型的中文名称
    getShapeTypeName(type) {
        const names = {
            'rectangle': '矩形',
            'line': '线条',
            'circle': '圆形',
            'square': '正方形'
        };
        return names[type] || type;
    }
    
    // 处理鼠标按下事件
    handleMouseDown(e) {
        const rect = this.canvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        if (this.currentDrawingType) {
            // 开始绘制新形状
            this.isDrawing = true;
            this.startPoint = {x, y};
            
            // 根据类型创建临时形状
            const tempName = `temp_${Date.now()}`;
            switch (this.currentDrawingType) {
                case 'rectangle':
                    this.tempShape = new Rectangle(tempName, x, y, 0, 0);
                    break;
                case 'line':
                    this.tempShape = new Line(tempName, x, y, x, y);
                    break;
                case 'circle':
                    this.tempShape = new Circle(tempName, x, y, 0);
                    break;
                case 'square':
                    this.tempShape = new Square(tempName, x, y, 0);
                    break;
            }
        } else {
            // 选择现有形状
            const shapeName = this.graphicsManager.getTopShapeAt(x, y);
            if (shapeName) {
                this.selectedShape = shapeName;
                this.canvasInfo.textContent = `已选择: ${this.graphicsManager.getShapeInfo(shapeName)}`;
                this.logMessage(`选择了形状: ${shapeName}`);
            } else {
                this.selectedShape = null;
                this.canvasInfo.textContent = '点击画布创建或选择形状';
            }
        }
        this.render();
    }
    
    // 处理鼠标移动事件
    handleMouseMove(e) {
        if (!this.isDrawing || !this.startPoint || !this.tempShape) return;
        
        const rect = this.canvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        const dx = x - this.startPoint.x;
        const dy = y - this.startPoint.y;
        
        // 更新临时形状
        switch (this.currentDrawingType) {
            case 'rectangle':
                const rectX = dx > 0 ? this.startPoint.x : x;
                const rectY = dy > 0 ? this.startPoint.y : y;
                const rectWidth = Math.abs(dx);
                const rectHeight = Math.abs(dy);
                this.tempShape.x = rectX;
                this.tempShape.y = rectY;
                this.tempShape.width = rectWidth;
                this.tempShape.height = rectHeight;
                break;
            case 'line':
                this.tempShape.x2 = x;
                this.tempShape.y2 = y;
                break;
            case 'circle':
                const radius = Math.sqrt(dx * dx + dy * dy);
                this.tempShape.radius = radius;
                break;
            case 'square':
                const size = Math.max(Math.abs(dx), Math.abs(dy));
                this.tempShape.centerX = this.startPoint.x;
                this.tempShape.centerY = this.startPoint.y;
                this.tempShape.length = size;
                break;
        }
        
        this.render();
    }
    
    // 处理鼠标释放事件
    handleMouseUp(e) {
        if (!this.isDrawing || !this.tempShape) return;
        
        // 显示对话框获取形状名称
        const content = `
            <div>
                <label for="shape-name">形状名称:</label>
                <input type="text" id="shape-name" placeholder="请输入形状名称" value="${this.currentDrawingType}_${Date.now().toString().slice(-4)}">
            </div>
        `;
        
        this.openDialog(`创建${this.getShapeTypeName(this.currentDrawingType)}`, content, () => {
            const shapeName = document.getElementById('shape-name').value.trim();
            if (!shapeName) {
                alert('请输入形状名称');
                return false;
            }
            
            try {
                // 根据类型创建实际形状
                switch (this.currentDrawingType) {
                    case 'rectangle':
                        if (this.tempShape.width > 5 && this.tempShape.height > 5) {
                            this.graphicsManager.createRectangle(
                                shapeName,
                                this.tempShape.x,
                                this.tempShape.y,
                                this.tempShape.width,
                                this.tempShape.height
                            );
                            this.logMessage(`创建了矩形: ${shapeName}`);
                        }
                        break;
                    case 'line':
                        const lineLength = Math.sqrt(
                            Math.pow(this.tempShape.x2 - this.tempShape.x1, 2) + 
                            Math.pow(this.tempShape.y2 - this.tempShape.y1, 2)
                        );
                        if (lineLength > 5) {
                            this.graphicsManager.createLine(
                                shapeName,
                                this.tempShape.x1,
                                this.tempShape.y1,
                                this.tempShape.x2,
                                this.tempShape.y2
                            );
                            this.logMessage(`创建了线条: ${shapeName}`);
                        }
                        break;
                    case 'circle':
                        if (this.tempShape.radius > 5) {
                            this.graphicsManager.createCircle(
                                shapeName,
                                this.tempShape.x,
                                this.tempShape.y,
                                this.tempShape.radius
                            );
                            this.logMessage(`创建了圆形: ${shapeName}`);
                        }
                        break;
                    case 'square':
                        if (this.tempShape.length > 5) {
                            this.graphicsManager.createSquare(
                                shapeName,
                                this.tempShape.centerX,
                                this.tempShape.centerY,
                                this.tempShape.length
                            );
                            this.logMessage(`创建了正方形: ${shapeName}`);
                        }
                        break;
                }
                
                this.selectedShape = shapeName;
                this.canvasInfo.textContent = `已选择: ${this.graphicsManager.getShapeInfo(shapeName)}`;
                
                // 重置绘制状态
                this.isDrawing = false;
                this.currentDrawingType = null;
                this.tempShape = null;
                this.startPoint = null;
                
                this.render();
                return true;
            } catch (error) {
                alert(`创建形状失败: ${error.message}`);
                return false;
            }
        });
    }
    
    // 分组形状
    groupShapes() {
        const content = `
            <div>
                <label for="group-name">组名称:</label>
                <input type="text" id="group-name" placeholder="请输入组名称" value="group_${Date.now().toString().slice(-4)}">
            </div>
            <div>
                <label for="member-names">成员名称 (用逗号分隔):</label>
                <input type="text" id="member-names" placeholder="例如: rect1,circle1">
            </div>
        `;
        
        this.openDialog('创建形状组', content, () => {
            const groupName = document.getElementById('group-name').value.trim();
            const memberNamesInput = document.getElementById('member-names').value.trim();
            
            if (!groupName) {
                alert('请输入组名称');
                return false;
            }
            
            if (!memberNamesInput) {
                alert('请输入成员名称');
                return false;
            }
            
            const memberNames = memberNamesInput.split(',').map(name => name.trim());
            
            try {
                this.graphicsManager.createGroup(groupName, memberNames);
                this.logMessage(`创建了组: ${groupName}，成员: ${memberNames.join(', ')}`);
                this.render();
                return true;
            } catch (error) {
                alert(`创建组失败: ${error.message}`);
                return false;
            }
        });
    }
    
    // 解组形状
    ungroupShape() {
        if (!this.selectedShape || !(this.graphicsManager.getShape(this.selectedShape) instanceof Group)) {
            alert('请先选择一个组形状');
            return;
        }
        
        try {
            this.graphicsManager.ungroup(this.selectedShape);
            this.logMessage(`解组了: ${this.selectedShape}`);
            this.selectedShape = null;
            this.canvasInfo.textContent = '点击画布创建或选择形状';
            this.render();
        } catch (error) {
            alert(`解组失败: ${error.message}`);
        }
    }
    
    // 删除选中的形状
    deleteSelectedShape() {
        if (!this.selectedShape) {
            alert('请先选择一个形状');
            return;
        }
        
        if (confirm(`确定要删除形状 "${this.selectedShape}" 吗？`)) {
            try {
                this.graphicsManager.deleteShape(this.selectedShape);
                this.logMessage(`删除了形状: ${this.selectedShape}`);
                this.selectedShape = null;
                this.canvasInfo.textContent = '点击画布创建或选择形状';
                this.render();
            } catch (error) {
                alert(`删除失败: ${error.message}`);
            }
        }
    }
    
    // 移动形状对话框
    moveShapeDialog() {
        if (!this.selectedShape) {
            alert('请先选择一个形状');
            return;
        }
        
        const content = `
            <div>
                <label for="move-dx">X方向移动量:</label>
                <input type="number" id="move-dx" placeholder="例如: 10" value="0">
            </div>
            <div>
                <label for="move-dy">Y方向移动量:</label>
                <input type="number" id="move-dy" placeholder="例如: -5" value="0">
            </div>
        `;
        
        this.openDialog('移动形状', content, () => {
            const dx = parseFloat(document.getElementById('move-dx').value) || 0;
            const dy = parseFloat(document.getElementById('move-dy').value) || 0;
            
            try {
                this.graphicsManager.moveShape(this.selectedShape, dx, dy);
                this.logMessage(`移动了形状: ${this.selectedShape}，偏移量: (${dx}, ${dy})`);
                this.render();
                return true;
            } catch (error) {
                alert(`移动失败: ${error.message}`);
                return false;
            }
        });
    }
    
    // 显示边界框
    showBoundingBox() {
        if (!this.selectedShape) {
            alert('请先选择一个形状');
            return;
        }
        
        try {
            const box = this.graphicsManager.getBoundingBox(this.selectedShape);
            const message = `形状 "${this.selectedShape}" 的边界框: ${box.toString()}`;
            this.logMessage(message);
            alert(message);
        } catch (error) {
            alert(`获取边界框失败: ${error.message}`);
        }
    }
    
    // 检查相交
    checkIntersect() {
        const content = `
            <div>
                <label for="shape1-name">第一个形状名称:</label>
                <input type="text" id="shape1-name" placeholder="例如: rect1" ${this.selectedShape ? `value="${this.selectedShape}"` : ''}>
            </div>
            <div>
                <label for="shape2-name">第二个形状名称:</label>
                <input type="text" id="shape2-name" placeholder="例如: circle1">
            </div>
        `;
        
        this.openDialog('检查形状相交', content, () => {
            const shape1Name = document.getElementById('shape1-name').value.trim();
            const shape2Name = document.getElementById('shape2-name').value.trim();
            
            if (!shape1Name || !shape2Name) {
                alert('请输入两个形状的名称');
                return false;
            }
            
            try {
                const intersects = this.graphicsManager.doShapesIntersect(shape1Name, shape2Name);
                const message = `形状 "${shape1Name}" 和 "${shape2Name}" ${intersects ? '相交' : '不相交'}`;
                this.logMessage(message);
                alert(message);
                return true;
            } catch (error) {
                alert(`检查相交失败: ${error.message}`);
                return false;
            }
        });
    }
    
    // 查看单个形状信息
    listShape() {
        if (!this.selectedShape) {
            const content = `
                <div>
                    <label for="shape-name">形状名称:</label>
                    <input type="text" id="shape-name" placeholder="例如: rect1">
                </div>
            `;
            
            this.openDialog('查看形状信息', content, () => {
                const shapeName = document.getElementById('shape-name').value.trim();
                if (!shapeName) {
                    alert('请输入形状名称');
                    return false;
                }
                
                try {
                    const info = this.graphicsManager.getShapeInfo(shapeName);
                    this.logMessage(info);
                    alert(info);
                    return true;
                } catch (error) {
                    alert(`获取形状信息失败: ${error.message}`);
                    return false;
                }
            });
        } else {
            try {
                const info = this.graphicsManager.getShapeInfo(this.selectedShape);
                this.logMessage(info);
                alert(info);
            } catch (error) {
                alert(`获取形状信息失败: ${error.message}`);
            }
        }
    }
    
    // 查看所有形状
    listAllShapes() {
        const allShapes = this.graphicsManager.getAllShapesInfo();
        if (allShapes.length === 0) {
            this.logMessage('没有可用的形状');
            alert('没有可用的形状');
            return;
        }
        
        const message = '所有形状:\n' + allShapes.join('\n');
        this.logMessage(message);
        alert(message);
    }
}

// 初始化应用
window.addEventListener('DOMContentLoaded', () => {
    const app = new Application();
});