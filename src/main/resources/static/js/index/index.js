$(function () {
    var data4vue = {
        beans:[],
        gareen:{'name':'盖伦'}
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
                var url = "/getData";
                axios.get(url).then(function (response) {
                    data4vue.beans =response.data;
                });
            },
        }

    });
})