var app = new Vue({
    el: '#app',
    data: {
        amount: ''
    },
    methods: {
        handleBuyClick() {
            console.log('buy click');
            location.href = 'http://192.168.137.1/jpayalipayback/order/getOrderPayPage?amount=' + this.amount;
        },
        handleMobileBuyClick() {
            console.log('mobile buy click');
            location.href = 'http://192.168.137.1/jpayalipayback/order/getOrderPayWap?amount=' + this.amount;
        }
    }
})