var app = new Vue({
    el: '#app',
    data: {},
    methods: {
        handleBuyClick() {
            console.log('buy click');
            location.href = 'http://localhost:8080/order/getOrderPayPage'
        }
    }
})