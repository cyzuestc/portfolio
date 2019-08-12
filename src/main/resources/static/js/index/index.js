$(function () {
    var data4vue = {
        beans:[],
        columnSorted:false,//column是否排序
        lastSortedColumn:'',//排序前先判断是否给新的一列字段排序.如果是,则倒序排序. 如果否,则是给同一列第二次排序
        type: 'Bonds',
        start:0,
        size:20
    };

    var vue = new Vue({
        el:'#workingAreaDiv',
        data:data4vue,
        mounted:function () {
            this.list();
        },
        methods:{
            list:function () {
                console.log("vue成功加载");
                var url = "/getCurrentPriceByType?type=" + this.type;
                axios.get(url).then(function (response) {
                    data4vue.beans =response.data;
                });
            },
            changeInstrument: function (instrumentType) {
                this.type = instrumentType;
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
            }
        }

    });
})