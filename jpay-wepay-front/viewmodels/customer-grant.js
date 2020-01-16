var app = new Vue({
    el: '#app',
    data: {
    },
    methods:{
        handleCustomerGrantTouch(){
            console.log('customer grant touch');
            location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxba004d8c6d611e32&redirect_uri=http%3A%2F%2Fcjf.qianmu.fun%2Fjiedai%2Fauth-code.html&response_type=code&scope=snsapi_base#wechat_redirect';
        }
    }
})