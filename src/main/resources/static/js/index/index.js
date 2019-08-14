
$(function () {
    // import Utils from '../include/util.js';
    var data4vue = {
        beans:[],
        portfolioBeans: [],
        tradingHistoryBeans: [],
        columnSorted:false,//column是否排序
        lastSortedColumn:'',//排序前先判断是否给新的一列字段排序.如果是,则倒序排序. 如果否,则是给同一列第二次排序
        type: 'Bonds',
        start:0,
        size:10,
        portfolioStart: 0,
        portfolioSize: 10,
        tradingHistoryStart: 0,
        tradingHistorySize: 10,
        portfolioPagination: [],
        totalPageNum: 0,
        curPageNum: 0,
        selected:[],
        selectAll:false,
        totalPrice: 0,
        numberOfInstrumentToBuy: 0,
        numberOfInstrumentToSell: 0,
        rawData: [
            ['2019/1/24', 2320.26, 2320.26, 2287.3, 2362.94],
        ],
        instrumentId: 102,
    };

    var vue = new Vue({
        el:'#workingAreaDiv',
        data:data4vue,
        mounted:function () {
            this.list();
            this.listPortfolio();
            this.listTradingHistory();
            this.getChartData();
        },
        methods:{
            listTradingHistory: function () {
                var url = "/tradingHistoryList?start=" + this.tradingHistoryStart + "&size=" + this.tradingHistorySize;
                axios.get(url).then(function (response) {
                    data4vue.tradingHistoryBeans = response.data.content;
                });
            },
            sellPortfolio: function (instrumentId) {
                var url = "/sellPortfolio?instrumentId=" + instrumentId + "&volume=" + this.numberOfInstrumentToSell;
                axios.get(url).then(function (response) {
                    var returnCode = response.data;
                    if (returnCode == -2) {
                        alert("you don't have enough instruments to sell!")
                    } else if (returnCode == -1) {
                        alert("you don't have this instrument to sell!");
                    } else if (returnCode == 1) {
                        alert("sold!");
                        vue.listPortfolio();
                        vue.listTradingHistory();
                    }
                });
            },
            add2Portfolio: function (instrumentId, price) {
                var url = "/addPortfolio?instrumentId=" + instrumentId + "&volume=" + this.numberOfInstrumentToBuy + "&price=" + price;
                if (this.numberOfInstrumentToBuy <= 0) return;
                let vm = this
                axios.get(url).then(function (response) {
                    console.log("add2Portfolio: " + response.data);
                    var returnCode = response.data;
                    if (returnCode == -1) {
                        window.location.href = "/login";
                    } else if (returnCode == 0) {
                        alert("balance is not enough, you can't afford it!");
                    } else if (returnCode == 1) {
                        alert("购买成功");
                        vue.listPortfolio();
                        vue.listTradingHistory();
                        vm.$emit('updatePrice')
                    }
                });
            },
            addInstrumentPageStart: function () {
                this.start += 1;
                this.list();
            },
            decreaseInstrumentPageStart: function() {
                if (this.start > 0) {
                    this.start -= 1;
                    this.list();
                }
            },
            list:function () {
                var url = "/getCurrentPriceByType?type=" + this.type + "&start=" + this.start + "&size=" + this.size;
                axios.get(url).then(function (response) {
                    data4vue.beans =response.data.content;
                    data4vue.pagination = response.data;
                });
            },

            listPortfolio: function () {
                var url = "/getPortfolio?start=" + this.portfolioStart + "&size=" + this.portfolioSize;
                axios.get(url).then(function (response) {
                    //获取到hold数据
                    data4vue.portfolioBeans = response.data.content;
                    //获取到hold分页数据
                    data4vue.portfolioPagination = response.data;
                });
            },
            changeInstrument: function (instrumentType) {
                this.type = instrumentType;
                this.start = 0;
                this.list();
            },
            //sort data by price/high/low/open/close etc.
            sortBy:function(columnName){
                //给同一列多次排序时,排序方式与上次相反
                if(columnName == vue.lastSortedColumn){
                    vue.columnSorted =!vue.columnSorted;
                }else {//给新的一列排序,首先按其正序排序
                    vue.columnSorted = false;
                }
                vue.lastSortedColumn = columnName;
                    vue.beans.sort(function (a,b) {
                        if(vue.columnSorted){
                            //倒序
                            return a[columnName] < b[columnName];
                        }
                    else {
                            //正序
                            return a[columnName] > b[columnName];
                        }
                    });
            },
            changeInstrumentChart: function (value) {
                data4vue.instrumentId = value;
                this.getChartData();
            },
            getChartData: function () {
                var url = "/listHistoryPricesById?instrumentId=" + this.instrumentId;
                var dataArr = new Array();
                let vm = this;
                axios.get(url).then(function (response) {
                    var json = response.data;
                    for (var i = 0, l = json.length; i < l; i++) {
                        dataArr[i] = new Array();
                        dataArr[i][0] = json[i]['date'];
                        dataArr[i][1] = json[i]['open'];
                        dataArr[i][2] = json[i]['close'];
                        dataArr[i][3] = json[i]['high'];
                        dataArr[i][4] = json[i]['low'];
                    }
                    data4vue.rawData = dataArr;
                    console.log(data4vue.rawData)
                    vm.drawData();
                });
            },
            drawData: function () {
                console.log("vue start");
                // 1. 配置和数据
                var upColor = '#ec0000';
                var upBorderColor = '#8A0000';
                var downColor = '#00da3c';
                var downBorderColor = '#008F28';


                // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
                var data0 = splitData(data4vue.rawData);

                function splitData(rawData) {
                    var categoryData = [];
                    var values = []
                    for (var i = 0; i < rawData.length; i++) {
                        categoryData.push(rawData[i].splice(0, 1)[0]);
                        values.push(rawData[i])
                    }
                    return {
                        categoryData: categoryData,
                        values: values
                    };
                }

                function calculateMA(dayCount) {
                    var result = [];
                    for (var i = 0, len = data0.values.length; i < len; i++) {
                        if (i < dayCount) {
                            result.push('-');
                            continue;
                        }
                        var sum = 0;
                        for (var j = 0; j < dayCount; j++) {
                            sum += data0.values[i - j][1];
                        }
                        result.push(sum / dayCount);
                    }
                    return result;
                }

                option = {
                    title: {
                        text: ' Citi Portfolio',
                        left: 0
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross'
                        }
                    },
                    legend: {
                        data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
                    },
                    grid: {
                        left: '10%',
                        right: '10%',
                        bottom: '15%'
                    },
                    xAxis: {
                        type: 'category',
                        data: data0.categoryData,
                        scale: true,
                        boundaryGap: false,
                        axisLine: {onZero: false},
                        splitLine: {show: false},
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax'
                    },
                    yAxis: {
                        scale: true,
                        splitArea: {
                            show: true
                        }
                    },
                    dataZoom: [
                        {
                            type: 'inside',
                            start: 50,
                            end: 100
                        },
                        {
                            show: true,
                            type: 'slider',
                            y: '90%',
                            start: 50,
                            end: 100
                        }
                    ],
                    series: [
                        {
                            name: '日K',
                            type: 'candlestick',
                            data: data0.values,
                            itemStyle: {
                                normal: {
                                    color: upColor,
                                    color0: downColor,
                                    borderColor: upBorderColor,
                                    borderColor0: downBorderColor
                                }
                            },
                            markPoint: {
                                label: {
                                    normal: {
                                        formatter: function (param) {
                                            return param != null ? Math.round(param.value) : '';
                                        }
                                    }
                                },
                                data: [
                                    {
                                        name: 'XX标点',
                                        coord: ['2019/5/31', 2300],
                                        value: 2300,
                                        itemStyle: {
                                            normal: {color: 'rgb(41,60,85)'}
                                        }
                                    },
                                    {
                                        name: 'highest value',
                                        type: 'max',
                                        valueDim: 'highest'
                                    },
                                    {
                                        name: 'lowest value',
                                        type: 'min',
                                        valueDim: 'lowest'
                                    },
                                    {
                                        name: 'average value on close',
                                        type: 'average',
                                        valueDim: 'close'
                                    }
                                ],
                                tooltip: {
                                    formatter: function (param) {
                                        return param.name + '<br>' + (param.data.coord || '');
                                    }
                                }
                            },
                            markLine: {
                                symbol: ['none', 'none'],
                                data: [
                                    [
                                        {
                                            name: 'from lowest to highest',
                                            type: 'min',
                                            valueDim: 'lowest',
                                            symbol: 'circle',
                                            symbolSize: 10,
                                            label: {
                                                normal: {show: false},
                                                emphasis: {show: false}
                                            }
                                        },
                                        {
                                            type: 'max',
                                            valueDim: 'highest',
                                            symbol: 'circle',
                                            symbolSize: 10,
                                            label: {
                                                normal: {show: false},
                                                emphasis: {show: false}
                                            }
                                        }
                                    ],
                                    {
                                        name: 'min line on close',
                                        type: 'min',
                                        valueDim: 'close'
                                    },
                                    {
                                        name: 'max line on close',
                                        type: 'max',
                                        valueDim: 'close'
                                    }
                                ]
                            }
                        },
                        {
                            name: 'MA5',
                            type: 'line',
                            data: calculateMA(5),
                            smooth: true,
                            lineStyle: {
                                normal: {opacity: 0.5}
                            }
                        },
                        {
                            name: 'MA10',
                            type: 'line',
                            data: calculateMA(10),
                            smooth: true,
                            lineStyle: {
                                normal: {opacity: 0.5}
                            }
                        },
                        {
                            name: 'MA20',
                            type: 'line',
                            data: calculateMA(20),
                            smooth: true,
                            lineStyle: {
                                normal: {opacity: 0.5}
                            }
                        },
                        {
                            name: 'MA30',
                            type: 'line',
                            data: calculateMA(30),
                            smooth: true,
                            lineStyle: {
                                normal: {opacity: 0.5}
                            }
                        },

                    ]
                };

                // 2. 创建echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 3. 作用
                myChart.setOption(option);
            },
        }

    });
})