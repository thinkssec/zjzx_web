<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>huanying</title>
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
    <li ><a href="${ctx}/gdzc/index " class="gicon-check">&nbsp;资产分布图</a></li>
    <li class="active"><a href=""><span class="gicon-cargo">&nbsp;组织架构图</span></a></li>
</ul>
<div id="main" style="width: 95%;height:1200px"></div>
<sys:message content="${message}"/>

<script>
    $(function () {
        //$('#main').height(($(window).height() +1000) + 'px');
        var myChart = echarts.init(document.getElementById('main'));
        var uploadedDataURL = "${ctx}/gdzc/gdzcTreeMapListT";
        myChart.showLoading();
        $.get(uploadedDataURL, function (data) {
            data=eval('(' + data + ')');
            myChart.hideLoading();
            /*echarts.util.each(data.children, function (datum, index) {
                index % 2 === 0 && (datum.collapsed = true);
            });*/

            myChart.setOption(option = {
                tooltip: {
                    trigger: 'item',
                    triggerOn: 'mousemove'
                },
                series: [
                    {
                        type: 'tree',
                        data: [data],
                        top: '1%',
                        left: '15%',
                        bottom: '1%',
                        right: '20%',
                        symbolSize: 7,
                        //orient: 'vertical',
                        label: {
                            normal: {
                                position: 'left',
                                verticalAlign: 'middle',
                                align: 'right',
                                fontSize: 10
                            }
                        },
                        leaves: {
                            label: {
                                normal: {
                                    position: 'right',
                                    verticalAlign: 'middle',
                                    align: 'left'
                                }
                            }
                        },
                        expandAndCollapse: true,
                        animationDuration: 550,
                        animationDurationUpdate: 750
                    }
                ]
            });
        });
    });


</script>
</body>

</html>