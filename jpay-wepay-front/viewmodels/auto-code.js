var app = new Vue({
    el: '#app',
    data: {
        authcode: '',
        openid: ''
    },
    mounted(){
        console.log('view mounted');

        var url = new URL(location.href);
        console.log(url);
        this.authcode = url.searchParams.get("code");
        if (!this.authcode) {
            alert('auth code 不存在');
            return;
        }
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
            axios.get('/wechat/getUserBasicInfo', {
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