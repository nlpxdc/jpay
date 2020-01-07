var app = new Vue({
    el: '#app',
    data: {
        amount: ''
    },
    methods: {
        handleBuyClick() {
            console.log('buy click');
            location.href = 'http://localhost/jpayalipayback/order/getOrderPayPage?amount=' + this.amount;
        }
    }
})