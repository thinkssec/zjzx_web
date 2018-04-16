var Nhchart = function (dom) {
    var ect;
    var option;
    this._init_(dom);
};

Nhchart.prototype = {
    constructor: Nhchart,
    _init_: function (dom) {
        ect = echarts.init(dom);
        option = {
            yAxis: [{
                type: 'log',
                position: 'left'
            },
                {
                    type: 'log',
                    position: 'right',
                    axisLine: {
                        lineStyle: {
                            color: '#009900'
                        }
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                }

            ],
            xAxis: {
                type: 'value'
            },
            series: [{
                id: 'displayTools',
                type: 'line',
                symbol: 'none',
                data: [],
                yAxisIndex: 0
            }, {
                id: 'displayTools2',
                type: 'line',
                symbol: 'none',
                data: [],
                yAxisIndex: 0,

            }],
            graphic: []
        };
        ect.setOption(option);
    },
    getEcharts: function () {
        return ect;
    },
    getOption: function () {
        return ect.getOption();
    },
    setOption: function (op, nm, lu, slt) {
        ect.setOption(op, nm, lu, slt);
    },
    addLineHotPoint: function (vid, vp0, vp1) {
        /*var cs=ect.getOption();
         for(var i=0;i<cs.series.length;i++){
         if(vid==cs.series[i].id){
         cs.series[i].markPoint={silent: true,
         animation: false,
         symbol: 'rect',
         symbolSize: 10,
         data: [{coord: vp0}, {coord: vp1}]};
         break;
         }
         }
         console.log(cs);
         ect.setOption(cs);*/
        var o = {
            series: [{
                id: vid,
                type: 'line',
                symbol: 'none',
                markPoint: {
                    silent: true,
                    animation: false,
                    symbol: 'rect',
                    symbolSize: 10,
                    data: [{coord: vp0}, {coord: vp1}]
                }
            }]
        };
        ect.setOption(o);
        //console.log(ect.getOption());
    },
    addFitArea: function (vleft, vright) {
        var o = {
            series: [{
                id: 'displayTools',
                type: 'line',
                symbol: 'none',
                markArea: {
                    silent: true,
                    label: {
                        normal: {
                            position: 'inside',
                            textStyle: {
                                color: '#000',
                                fontFamily: 'sans-serif',
                                fontSize: 40,
                            },
                        }
                    },
                    data: [
                        [{
                            name: '\u0046\u0049\u0054\u0054\u0049\u004e\u0047\n\n\u0041\u0052\u0045\u0041',
                            xAxis: vleft,
                            itemStyle: {
                                normal: {
                                    color: '#fff',
                                    opacity: 0.7
                                }
                            },
                        }, {
                            xAxis: vright,
                        }]
                    ]
                }
            }]
        };
        ect.setOption(o);
    },
    addBrushArea: function (vleft, vright) {
        var o = {
            toolbox: {
                show: false,
                feature: {
                    brush: {
                        type: ['lineX', 'clear']
                    }
                }
            }, brush: {
                xAxisIndex: '0',
                throttleType: 'debounce',
                throttleDelay: 1500,
                brushLink: 'all',
                outOfBrush: {
                    colorAlpha: 0.1
                }
            }
        };
        ect.setOption(o);
        ect.dispatchAction({
            type: 'brush',
            areas: [
                {
                    xAxisIndex: 0,
                    brushType: 'lineX',
                    coordRange: [vleft, vright]
                }
            ]
        });

    },
    addBrushListener: function (o) {
        var f = o.onBrushSelected;
        ect.on('brushselected', renderBrushed);
        function renderBrushed(params) {

            var xA = params.batch[0].areas[0].coordRange[0];
            var xB = params.batch[0].areas[0].coordRange[1];
            //if(xA==${condition.c1}&&xB==${condition.c2}) return;
            //loading('\u6b63\u5728\u62df\u5408\uff0c\u8bf7\u7a0d\u5019\uff01');
            f();
            //$('#c1').val(xA);
            //$('#c2').val(xB);
        }
    },
    addSegLineB: function (vx) {
        var o = {
            series: [{
                id: 'displayTools',
                type: 'line',
                symbol: 'none',
                markLine: {
                    //symbol: ['none', 'none'],
                    animation: false,
                    silent: true,
                    symbol: false,
                    lineStyle: {
                        normal: {
                            type: 'solid',
                            color: 'GREEN',
                            width: 4,
                            fontWeight: 'bold',
                            fontSize: 30
                        }
                    },
                    data: [{
                        name: 'b',
                        xAxis: vx
                    }
                    ]
                },
                markPoint: {
                    label: {
                        normal: {
                            show: true,
                            formatter: function (params) {
                                var a = params.name;
                                return a;
                            },
                            textStyle: {
                                color: '#000',
                                fontFamily: 'sans-serif',
                                fontSize: 12,
                            },
                        }
                    },
                    data: [{
                        name: '\u5206\u5272\u7ebf',
                        xAxis: vx,
                        y: '30%'
                    }]
                }
            }]
        };
        ect.setOption(o);
    },
    addSegLineE: function (vx) {
        var o = {
            series: [{
                id: 'displayTools2',
                type: 'line',
                symbol: 'none',
                markLine: {
                    //symbol: ['none', 'none'],
                    animation: false,
                    silent: true,
                    symbol: false,
                    lineStyle: {
                        normal: {
                            type: 'solid',
                            color: 'red',
                            width: 2,
                            fontWeight: 'bold',
                            fontSize: 30
                        }
                    },
                    data: [
                        {
                            name: '',
                            xAxis: vx
                        }
                    ]
                }

            }]
        };
        ect.setOption(o);
    },
    addPointDragEvent: function (vid, n0) {
        var invpt = [];
        invpt = Nhchart.prototype.getInvpt(n0, vid, 'merge', 'point');
        //console.log(ect.getOption());
        /*var co=ect.getOption();
         co.graphic.push(invpt);
         ect.setOption(co,true);*/
        ect.setOption({
            graphic: invpt
        });
        //console.log(ect.getOption());


    },
    addLineDragEvent: function (vid, n0) {
        var invpt = [];
        invpt = Nhchart.prototype.getInvpt(n0, vid, 'merge', 'line');
        ect.setOption({
            graphic: invpt
        });
    },
    onPointDragging: function (arg, dx) {
        var dataIndex = arg.dataIndex;
        var n0 = arg.n0;
        var series = Nhchart.prototype.getSeriesById(arg.seriesId);
        //平移
        var mkp = [];
        if (dataIndex == -1) {
            var tmp = [dx.offsetX, dx.offsetY];
            var itemD = series.markPoint.data;
            var itemP = [];
            itemP[0] = [itemD[0].coord[0], itemD[0].coord[1]];
            itemP[1] = [itemD[1].coord[0], itemD[1].coord[1]];
            var aaa = [];
            aaa[0] = ect.convertToPixel('grid', itemP[0]);
            aaa[1] = ect.convertToPixel('grid', itemP[1]);
            var a = (aaa[1][1] - aaa[0][1]) / (aaa[1][0] - aaa[0][0]);
            var b = tmp[1] - a * tmp[0];
            var dt = [];
            dt[0] = ect.convertFromPixel('grid', [aaa[0][0], a * aaa[0][0] + b]);
            dt[1] = ect.convertFromPixel('grid', [aaa[1][0], a * aaa[1][0] + b]);
            mkp[0] = {coord: [dt[0][0], dt[0][1]]};
            mkp[1] = {coord: [dt[1][0], dt[1][1]]}
        } else {
            //端点拖拽
            var ttttt = n0[dataIndex].value || n0[dataIndex];
            n0[dataIndex][0] = ttttt[0];
            n0[dataIndex][1] = ect.convertFromPixel('grid', this.position)[1];
            mkp = series.markPoint.data;
            if (dataIndex == 0) {
                mkp[0] = {coord: ttttt};
            } else {
                mkp[1] = {coord: ttttt};
            }

        }
        n0 = [mkp[0].coord, mkp[1].coord];
        ect.setOption({
            series: [{
                id: series.id,
                data: n0,
                markPoint: {
                    silent: true,
                    animation: false,
                    symbol: 'rect',
                    symbolSize: 10,
                    data: mkp
                },
            }]
        });
        var invpt = Nhchart.prototype.getInvpt(n0, arg.seriesId,'replace','all');
        ect.setOption({
            graphic: invpt
        });
    },
    getInvpt: function (n3, seriesId, action, flag) {
        var n30 = n3[0].value || n3[0];
        var n31 = n3[n3.length - 1].value || n3[n3.length - 1];
        var invpt=[];
        if (flag == 'point'||flag == 'all')
            invpt = echarts.util.map(n3, function (item, dataIndex) {
                if (dataIndex != 0 && dataIndex != n3.length - 1) return null;
                var item = item.value || item;
                return {
                    id: seriesId + 'graphic' + dataIndex,
                    type: 'rect',
                    position: ect.convertToPixel('grid', item),
                    shape: {
                        x: -5,
                        y: -5,
                        width: 15,
                        height: 15
                    },
                    invisible: true,
                    draggable: true,
                    ondrag: echarts.util.curry(Nhchart.prototype.onPointDragging, {
                        dataIndex: dataIndex,
                        seriesId: seriesId,
                        n0: n3,
                        flag:'point'
                    }),
                    z: 10000
                };
            });
        if (flag == 'line'||flag == 'all')
            invpt.push({
                type: 'line',
                id: seriesId + 'inv',
                $action: action ? action : 'replace',
                invisible: true,
                draggable: true,
                ondrag: echarts.util.curry(Nhchart.prototype.onPointDragging, {
                    dataIndex: -1,
                    seriesId: seriesId,
                    n0: n3,
                    flag:'line'
                }),
                z: 100,
                shape: {
                    x1: ect.convertToPixel('grid', n30)[0],
                    y1: ect.convertToPixel('grid', n30)[1],
                    x2: ect.convertToPixel('grid', n31)[0],
                    y2: ect.convertToPixel('grid', n31)[1]
                }
            });
        return invpt;
    },
    getSeriesById: function (seriesId) {
        var series;
        $.each(ect.getOption().series, function (i, item) {
            if (item.id == seriesId) {
                series = item;
                return;
            }
        });
        return series;
    },
    getNhData:function(opt){
        var outdata=[];
        var xy="http://";
        function get(){
        }
        //console.log(xy+opt.host+":"+opt.port+"/"+opt.service+"/nh/zs");
        $.ajax({
            url: xy+opt.host+":"+opt.port+"/"+opt.service+"/nh/zs"+"?indata="+opt.indata+"&sqparam="+opt.xs+"&nhfs="+opt.nhfs,
            type: 'get',
            dataType: 'jsonp',
            jsonp: "callback",
            jsonpCallback:"get",
            //data:{'indata':indata},
            success: function(json){
                //console.log("请求路径："+jsonpUrl+"返回结果："+json);
                opt.onSuccess(json);
                //console.log(json);
            },
            error: function(){
                // alert('fail');
            }
        });


        return outdata;
    }
}