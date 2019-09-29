var LineChart = function(ctx){
    this.ctx = ctx || document.querySelector('canvas');
    this.canvasWidth = this.ctx.canvas.width-50;
    this.canvasHeight = this.ctx.canvas.height - 50;
    //网格的大小
    this.gridSize = 100;
    //坐标系的间距
    //this.space = this.gridSize ;
    this.space = 0;
    //this.x0 = this.space;
    this.x0 = 30;                   //设置横坐标起点,从画布的哪个位置开始
    //this.y0 = this.canvasHeight - this.space;
    this.y0 = -400;                 //设置纵坐标起点,从画布的哪个位置开始
    //箭头的大小
    this.arrowSize = 5;
    //点的大小
    this.dottedSize = 1;

};
LineChart.prototype.init = function(center_frq,section){
    this.drawGrid(center_frq,section);
};
LineChart.prototype.mydraw = function(data,section,start_section){
    this.drawDotted(data,section,start_section);
}

LineChart.prototype.drawGrid = function(center_frq,section){
    //设置线条颜色
    this.ctx.strokeStyle="#eee";
    //x方向的线
    var xLineTotal = Math.floor(this.canvasHeight/this.gridSize);//数量
    //alert(xLineTotal);
    for(var i=0;i<=xLineTotal;i++){
        if(i == xLineTotal ){
            //底部线条变色
            this.ctx.strokeStyle="#bebebe";
        }else{
            this.ctx.strokeStyle="#eee";
        }
        this.ctx.beginPath();
        this.ctx.moveTo(this.x0,i*this.gridSize);
        this.ctx.lineTo(this.canvasWidth,i*this.gridSize);
        //写纵坐标
        this.ctx.font="15px Georgia";
        // -90 为显示的纵坐标最小值
        this.ctx.fillText((xLineTotal-i)*10-90,0,i*this.gridSize);
        this.ctx.stroke();
    }
    this.ctx.strokeStyle="#eee";
    //y方向的线
    var yLineTotal = 4 * section + 1;
    var yLineSpace = (this.canvasWidth - this.x0 * 2) / (yLineTotal - 1);
    var frq  = new Number(center_frq);
    for(var i = 0; i<yLineTotal; i++){
        this.ctx.beginPath();
        this.ctx.moveTo(i*yLineSpace + this.x0,0);
        this.ctx.lineTo(i*yLineSpace + this.x0,this.canvasHeight);
        //写横坐标
        this.ctx.font="15px Georgia";
        this.ctx.fillText((i - 2 * section) * 5 + frq,i*yLineSpace + this.x0,this.canvasHeight);
        this.ctx.stroke();
    }
    //表明单位
    this.ctx.font="15px Georgia";
    this.ctx.fillText("dB",5,20);
    this.ctx.fillText("MHz",this.canvasWidth,this.canvasHeight);
    this.ctx.stroke();

    this.ctx.strokeStyle='black';
}

LineChart.prototype.drawDotted = function(data,section,start_section){
    //mulitple : 画布横坐标缩小倍数, section ： 整个画布分成几部份
    var multiple = (this.canvasWidth - this.x0 * 2) / section;
    //左移
    var left_shift = this.x0 + multiple * (start_section - 1);
    //记录上一个点坐标，以便画折线
    var prevcanvasX = data[0].x / data.length * multiple + left_shift;
    var prevcanvasY = this.y0 - data[0].y * 10 ;

    for(var i = 1; i < data.length ; i++){
        //坐标转换
        var canvasX = data[i].x / data.length * multiple + left_shift;
        var canvasY = this.y0  - data[i].y * 10 ;

        //以坐标为中心绘制点（小矩形）
        /*
        this.ctx.beginPath();
        this.ctx.moveTo(canvasX-(this.dottedSize/2),canvasY-(this.dottedSize/2));
        this.ctx.lineTo(canvasX+(this.dottedSize/2),canvasY-(this.dottedSize/2));
        this.ctx.lineTo(canvasX+(this.dottedSize/2),canvasY+(this.dottedSize/2));
        this.ctx.lineTo(canvasX-(this.dottedSize/2),canvasY+(this.dottedSize/2));
        this.ctx.closePath();
        this.ctx.fill();
        */

        //绘制折线，连接每个点
        this.ctx.beginPath();
        this.ctx.moveTo(prevcanvasX,prevcanvasY);
        this.ctx.lineTo(canvasX,canvasY);
        this.ctx.stroke();
        //更新上一个点的坐标信息
        prevcanvasX = canvasX;
        prevcanvasY = canvasY;
    }
}
