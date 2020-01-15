var app = new Vue({
    el: '#app',
    data: {
        appId: 'wxba004d8c6d611e32', //正式号
        ticket: 'HoagFKDcsGMVCIY2vOjf9uEmwNhGCGgxQ35AwRkUNkRUBxHlF-SjHT3Z6K_tlEAaANxPERL2wXEcH7xEIqK3fg',
        prepay_id: 'wx17141736800711da66ae45bf1456919100',
        signType: 'MD5',
        payKey: '9f87a21142b6af5b144dbb4ac2077c5b' //to hide, calc in backend
    },
    computed: {
        currentTime() {
            return Date.now();
        },
        nonceStr() {
            return Math.random().toString(16).substr(2);
        },
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
        },
        paySign() {
            const payParams =
                'appId=' + this.appId
                + '&nonceStr=' + this.nonceStr
                + '&package=' + this.package
                + '&signType=' + this.signType
                + '&timeStamp=' + this.currentTime
                + '&key=' + this.payKey;

            const md5Str = md5(payParams);
            const md5StrUpper = md5Str.toUpperCase();

            return md5StrUpper;
        }
    },
    mounted() {
        console.log('view mounted');

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
        });

        wx.ready(function () {
            console.log('wx ready');
        });
    },
    methods: {
        handleCheckPay() {
            console.log('check pay click');
            wx.checkJsApi({
                jsApiList: ['chooseWXPay'],
                success: function (res) {
                    console.log('check pay success', res);
                    alert('support pay');
                },
                fail: function (err) {
                    console.error('check pay error', err);
                    alert('not support pay');
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
        }
    }
})