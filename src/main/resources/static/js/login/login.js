$(function () {
    var data4vue = {
        username: '',
        password: '',
        LoginInfo: '',
        //登录还是注册
        isSignUp: false,
    };

    var vue = new Vue({
        el: '#workingAreaDiv',
        data: data4vue,
        mounted: function () {
        },
        methods: {
            loginIn: function () {
                var url = "/fundManagerLogin";
                console.log(url);
                var formData = new FormData();
                formData.append("username", this.username);
                formData.append("password", this.password);
                axios.post(url, formData).then(function (response) {
                    console.log(response);
                    var returnCode = response.data;
                    console.log(returnCode);
                    if (returnCode == 2) {
                        data4vue.LoginInfo = 'You Are Already Login,Please Logout First!';
                    }
                    else if (returnCode == -1) {
                        data4vue.LoginInfo = 'User Not Exist!';
                    } else if (returnCode == 0) {
                        data4vue.LoginInfo = 'Wrong Password!';
                    }
                    else {
                        data4vue.LoginInfo = '';
                        window.location.href = '/index';
                    }
                });
            },
            signUp: function () {
                var url = "/fundManagerSignUp";
                console.log(url);
                var formData = new FormData();
                formData.append("username", this.username);
                formData.append("password", this.password);
                axios.post(url, formData).then(function (response) {
                    console.log(response);
                    var returnCode = response.data;
                    console.log(returnCode);
                    if (returnCode == -1) {
                        data4vue.LoginInfo = 'UserName Already Exist!';
                    } else {
                        data4vue.LoginInfo = 'Success!';
                        window.location.href = '/login';
                    }
                });
            }
        }

    });
})