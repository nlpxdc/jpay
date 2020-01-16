var app = new Vue({
    el: '#app',
    data: {
        authcode: '',
        openid: ''
    },
    methods:{
        handleGetOpenidTouch(){
            console.log('get openid touch');
            if (!this.authcode) {
                alert('auth code 为空');
                return;
            }
            this.getOpenidByAuthcode();
        },
        getOpenidByAuthcode(){
            axios.get('/order/getPrepay', {
                params: {
                    authcode: this.authcode
                }
            })
                .then(function (response) {
                    console.log(response);
                    const userBasicInfo = response.data;
                    app.openid = userBasicInfo.openid;
                    localStorage['access_token'] = userBasicInfo.access_token;
                    localStorage['openid'] = userBasicInfo.openid;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})