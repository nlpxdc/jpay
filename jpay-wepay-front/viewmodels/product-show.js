var app = new Vue({
    el: '#app',
    data: {
        appId: 'wxba004d8c6d611e32', //正式号
        ticket: localStorage['jsapiTicket'],
        wechatConfig: 'unknown',
        supportWepay: 'unknown',
        signType: 'MD5',
        amount: 1,
        orderAmount: '',
        currentTime: '',
        nonceStr: '',
        prepay_id: '',
        paySign: '',
        openid: localStorage['openid']
    },
    computed: {
        originParams() {
            const originParams =
                'jsapi_ticket=' + this.ticket
                + '&noncestr=' + this.nonceStr
                + '&timestamp=' + this.currentTime
                + '&url=' + location.href;

            return originParams;
        },
        signature() {
            const shaObj = new jsSHA("SHA-1", "TEXT");
            shaObj.update(this.originParams);
            const signature = shaObj.getHash("HEX");
            return signature;
        },
        package() {
            return 'prepay_id=' + this.prepay_id;
        }
    },
    mounted() {
        console.log('view mounted');
        this.currentTime = Date.now();
        this.nonceStr = Math.random().toString(16).substr(2);
    },
    methods: {
        handleCheckPay() {
            console.log('check pay click');
            wx.checkJsApi({
                jsApiList: ['chooseWXPay'],
                success: function (res) {
                    console.log('check pay success', res);
                    app.supportWepay = true;
                },
                fail: function (err) {
                    console.error('check pay error', err);
                    app.supportWepay = false;
                },
                complete: function (res) {
                    console.log('check pay complete', res);
                },
                cancel: function (res) {
                    console.warn('check pay cancel', res);
                },
                trigger: function (res) {
                    console.log('menu click', res);
                }
            });
        },
        handlePay() {
            console.log('pay click');

            if (!this.prepay_id) {
                alert('prepay id 不存在');
                return;
            }

            console.log('paySignJS', this.paySignJS);
            console.log('paySign', this.paySign);

            wx.chooseWXPay({
                timestamp: this.currentTime,
                nonceStr: this.nonceStr,
                package: this.package,
                signType: this.signType,
                paySign: this.paySign,
                success: function (res) {
                    console.log('pay success', res);
                },
                fail: function (err) {
                    console.error('pay error', err);
                },
                complete: function (res) {
                    console.log('pay complete', res);
                },
                cancel: function (res) {
                    console.warn('pay cancel', res);
                },
                trigger: function (res) {
                    console.log('menu click', res);
                }
            });
        },
        handleGetJsTicketTouch() {
            console.log('get js ticket touch');
            localStorage['jsapiTicket'] = '';
            app.ticket = localStorage['jsapiTicket'];
            this.getJsTicket();
        },
        getJsTicket() {
            axios.get('/wechat/getJsTicket')
                .then(function (response) {
                    console.log(response);
                    localStorage['jsapiTicket'] = response.data;
                    app.ticket = localStorage['jsapiTicket'];
                })
                .catch(function (error) {
                    console.error(error);
                });
        },
        handleWechatConfigTouch() {
            console.log('wechat config touch');

            if (!this.ticket) {
                alert('ticket 不存在');
                return;
            }

            wx.config({
                debug: false,
                appId: this.appId,
                timestamp: this.currentTime,
                nonceStr: this.nonceStr,
                signature: this.signature,
                jsApiList: [
                    'checkJsApi',
                    'chooseWXPay'
                ]
            });

            wx.error(function (res) {
                console.error('wx error', res);
                app.wechatConfig = false;
            });

            wx.ready(function () {
                console.log('wx ready');
                if (app.wechatConfig !== false) {
                    app.wechatConfig = true;
                }
            });
        },
        handleGetPrepayTouch() {
            console.log('get prepay touch');
            if (!this.amount) {
                alert('amount 不存在');
                return;
            }
            this.getPrepay();
        },
        getPrepay() {
            axios.post('/order/getJsapiPrepay', null, {
                params: {
                    amount: this.amount,
                    signType: this.signType,
                    timestamp: this.currentTime,
                    nonce: this.nonceStr,
                    openid: this.openid
                }
            })
                .then(function (response) {
                    console.log(response);
                    const prepay = response.data;
                    app.prepay_id = prepay.prepayId;
                    app.paySign = prepay.paySign;
                    app.orderAmount = app.amount;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
})