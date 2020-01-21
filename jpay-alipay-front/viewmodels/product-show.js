var app = new Vue({
    el: '#app',
    data: {
        amount: 0.01,
        codeUrl: '',
        qrcode: ''
    },
    mounted() {
        console.log('view mounted');
        this.qrcode = new QRCode(document.getElementById("qrcode"));
    },
    methods: {
        handleBuyClick() {
            console.log('buy click');
            location.href = 'http://192.168.137.1/jpayalipayback/order/getOrderPayPage?amount=' + this.amount;
        },
        handleMobileBuyClick() {
            console.log('mobile buy click');
            location.href = 'http://192.168.137.1/jpayalipayback/order/getOrderPayWap?amount=' + this.amount;
        },
        handleGeneratePayQRCodeClick() {
            console.log('generate pay qrcode click');
            this.getPayQRCode();
        },
        getPayQRCode() {
            axios.get('/f2f/getOrderPayQRCode', {
                params: {
                    amount: this.amount
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.codeUrl = response.data;
                    if (app.codeUrl) {
                        app.qrcode.makeCode(app.codeUrl);
                    }else{
                        // app.qrcode.clear();
                        alert('二维码获取失败');
                    }
                    
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }
})