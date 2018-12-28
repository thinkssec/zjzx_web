<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>huanying </title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/echart4/echarts.min.js" type="text/javascript"></script>
</head>
<style>
    table th {
        text-align: right;
    }
</style>


<body>

<ul class="nav nav-tabs">
    <li class="active"><a href=""><span class="gicon-cargo">&nbsp;资产分布图</span></a></li>
    <li><a href="${ctx}/gdzc/indexT" class="gicon-check">&nbsp;组织架构图</a></li>
</ul>
<div id="main" style="width: 95%;"></div>
<sys:message content="${message}"/>

<script>
    $(function () {
    $('#main').height(($(window).height()-50) + 'px');
    var myChart = echarts.init(document.getElementById('main'));
    var uploadedDataURL = "${ctx}/gdzc/gdzcTreeMapList";
    myChart.showLoading();

    $.get(uploadedDataURL, function (diskData) {
        //console.log(diskData);
        diskData=eval(diskData);
        myChart.hideLoading();
        function colorMappingChange(value) {
            var levelOption = getLevelOption(value);
            chart.setOption({
                series: [{
                    levels: levelOption
                }]
            });
        }
        var formatUtil = echarts.format;
        function getLevelOption() {
            return [
                {
                    itemStyle: {
                        normal: {
                            borderWidth: 2,
                            gapWidth: 5
                        }
                    }
                },
                {
                    itemStyle: {
                        normal: {
                            gapWidth: 1
                        }
                    }
                },
                {
                    colorSaturation: [0.35, 0.5],
                    itemStyle: {
                        normal: {
                            gapWidth: 1,
                            borderColorSaturation: 0.6
                        }
                    }
                }
            ];
        }

        myChart.setOption(option = {

            title: {
                text: '资产分布图',
                left: 'center'
            },

            tooltip: {
                formatter: function (info) {
                    var value = info.value;
                    var treePathInfo = info.treePathInfo;
                    var treePath = [];
                    for (var i = 1; i < treePathInfo.length; i++) {
                        treePath.push(treePathInfo[i].name);
                    }
                    return [
                        '<div class="tooltip-title">' + formatUtil.encodeHTML(treePath.join('/')) + '</div>',
                        '资产原值: ' + formatUtil.addCommas(value) + ' 元',
                    ].join('');
                }
            },

            series: [
                {
                    name:'川气东送管道分公司',
                    type:'treemap',
                    visibleMin: 500,
                    left: '5%',
                    right: '1%',
                    /*top: '1%',
                    left: '1%',
                    bottom: '1%',
                    right: '1%',*/
                    label: {
                        show: true,
                        formatter: '{b}'
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff'
                        }
                    },
                    levels: getLevelOption(),
                    data: diskData
                }
            ]
        });
    });
    });
</script>
</body>

</html>