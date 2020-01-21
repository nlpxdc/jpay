var app = new Vue({
    el: '#app',
    data: {
        productId: 'multipleProductIds',
        amount: 1,
        orderAmount: '',
        prepay_id: '',
        codeUrl: '',
        qrcode: ''
    },
    mounted(){
        console.log('view mounted');
        this.qrcode = new QRCode(document.getElementById("qrcode"), this.codeUrl);
    },
    methods: {
        handleGetNativeTwoPrepayTouch() {
            console.log('get native two prepay touch');
            if (!this.amount) {
                alert('amount 不存在');
                return;
            }
            this.getNativeTwoPrepay();
        },
        getNativeTwoPrepay() {
            axios.post('/order/getNativePrepay', null, {
                params: {
                    amount: this.amount,
                    productId: this.productId
                }
            })
                .then(function (response) {
                    console.log(response);
                    const prepay = response.data;
                    app.prepay_id = prepay.prepay_id;
                    app.codeUrl = prepay.code_url;
                    app.orderAmount = app.amount;
                    app.qrcode.clear();
                    app.qrcode.makeCode(app.codeUrl);
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }
})