$(function () {
    var data4vue = {
        currentFundManager: '',
    };

    var headVue = new Vue({
        el: '#navDiv',
        data: data4vue,
        mounted: function () {
            this.getHeaderInfo();
        },
        methods: {
            getHeaderInfo: function () {
                console.log("getHeaderInfo");
                var url = "/getHeaderInfo";
                axios.get(url).then(function (response) {
                    console.log(response.data == '');
                    data4vue.currentFundManager = response.data;
                });
            },
            fundManagerLogout: function () {
                console.log("fundManagerLogout");
                var url = "/fundManagerLogout";
                axios.get(url).then(function (response) {
                    console.log(response.data == '');
                    if (response.data == 1) {
                        data4vue.currentFundManager = '';
                    }
                });
            }
        }

    });
})

