$(function () {
    var data4vue = {
        beans:[],
        instrumentBean: [],
        columnSorted:false,//column是否排序
        lastSortedColumn:'',//排序前先判断是否给新的一列字段排序.如果是,则倒序排序. 如果否,则是给同一列第二次排序
        instrumentStart: 0,
        instrumentSize: 10,
        listFundManagerStart: 0,
        listFundManagerSize: 10,
        fundManagerPagination: [],
        singleFile: '',
        uploadInstrumentType: 'Bonds',
    };

    var vue = new Vue({
        el:'#workingAreaDiv',
        data:data4vue,
        mounted:function () {
            this.list();
            this.listInstrument();
        },
        methods:{
            addSingle: function () {
                if (this.singleFile == null) return;
                var url = "/uploadExcel?instrumentType=" + data4vue.uploadInstrumentType;
                var formData = new FormData();
                let vm = this;
                formData.append("excelFile", this.singleFile);
                axios.post(url, formData).then(function (response) {

                    if (response.data == 1) {
                        vue.singleFile = null;
                        alert("上传成功")
                        vm.listInstrument();
                    } else {
                        alert("上传失败")
                    }
                });
            },
            getSingleFile: function (event) {
                this.singleFile = event.target.files[0];
                this.addSingle();
            },
            addInstrumentPageStart: function () {
                this.instrumentStart += 1;
                this.listInstrument();
            },
            list:function () {
                console.log("vue成功加载");
                var url = "/listFundManager?start=" + this.listFundManagerStart + "&size=" + this.listFundManagerSize;
                axios.get(url).then(function (response) {
                    data4vue.beans = response.data.content;
                    data4vue.fundManagerPagination = response.data;
                });
            },

            listInstrument: function () {
                console.log("vue成功加载");
                var url = "/getInstruments?start=" + this.instrumentStart + "&size=" + this.instrumentSize;
                console.log(url);
                axios.get(url).then(function (response) {
                    data4vue.instrumentBean = response.data.content;
                    data4vue.instrumentsPagination = response.data;
                    console.log(response.data)
                });
            },
            changeInstrumentsPage: function (addPageNum) {
                if (addPageNum == -1 && data4vue.instrumentStart > 0) {
                    data4vue.instrumentStart--;
                    this.listInstrument();
                }
                if (addPageNum == 1 && data4vue.instrumentStart < data4vue.instrumentsPagination.totalPages - 1) {
                    data4vue.instrumentStart++;
                    this.listInstrument();
                }
            },
            changeFundManagersPage: function (addPageNum) {
                if (addPageNum == -1 && data4vue.listFundManagerStart > 0) {
                    data4vue.listFundManagerStart--;
                    this.listInstrument();
                }
                if (addPageNum == 1 && data4vue.listFundManagerStart < data4vue.fundManagerPagination.totalPages - 1) {
                    data4vue.listFundManagerStart++;
                    this.listInstrument();
                }
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

            sortInstrumentBy: function (columnName) {
                //给同一列多次排序时,排序方式与上次相反
                if (columnName == vue.lastSortedColumn) {
                    vue.columnSorted = !vue.columnSorted;
                } else {//给新的一列排序,首先按其正序排序
                    vue.columnSorted = false;
                }
                vue.lastSortedColumn = columnName;
                vue.instrumentBean.sort(function (a, b) {
                    if (vue.columnSorted) {
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