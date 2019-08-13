$(function () {
    var data4vue = {
        beans:[],
        columnSorted:false,//column是否排序
        lastSortedColumn:'',//排序前先判断是否给新的一列字段排序.如果是,则倒序排序. 如果否,则是给同一列第二次排序
        type: 'Bonds',
        start:0,
        size:10,
        selected:[],
        selectAll:false,
        totalPrice:0
    };

    var vue = new Vue({
        el:'#workingAreaDiv',
        data:data4vue,
        mounted:function () {
            this.list();
        },
        methods:{
            select: function() {
                this.totalPrice = 0;
                console.log(this.selected.length);
                for(var i = 0; i < this.selected.length; i++){
                    this.totalPrice += this.selected[i].price;
                }
                console.log(this.totalPrice);
            },
            sortUp: function(bean) {
                console.log(bean);
                console.log(this.beans);
                var index = this.beans.indexOf(bean);
                console.log(index);
                if(this.beans.length > 1 && index !== 0) {
                    var temp = this.beans[index - 1];
                    this.beans[index - 1] = this.beans[index];
                    this.beans[index] = temp;
                }
            },
            sortDown: function() {

            },
            add2Portfolio: function() {
              if(this.selected.length < 0) {
                  return;
              } else {

              }
            },
            addInstrumentPageStart: function () {
                this.start += 1;
                this.list();
            },
            decreaseInstrumentPageStart: function() {
                this.start -= 1;
                this.list();
            },
            list:function () {
                console.log("vue成功加载");
                var url = "/getCurrentPriceByType?type=" + this.type + "&start=" + this.start + "&size=" + this.size;
                axios.get(url).then(function (response) {
                    data4vue.beans =response.data.content;
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
            }
        }

    });
})