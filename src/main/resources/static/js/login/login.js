$(function () {
    var data4vue = {
        username: '',
        password: ''
    };

    var vue = new Vue({
        el: '#workingAreaDiv',
        data: data4vue,
        mounted: function () {
        },
        methods: {
            loginIn: function () {
                var url = "/fundManagerlogin";
                console.log(url);
                var formData = new FormData();
                formData.append("username", this.username);
                formData.append("password", this.password);
                axios.post(url, formData).then(function (response) {
                    console.log(response);
                    var returnCode = response.data;
                    console.log(returnCode);
                    if (returnCode == -1) alert("登录失败,请重新登陆!");
                    else {
                        window.location.href = '/index';
                    }
                });
            }
        }

    });
})