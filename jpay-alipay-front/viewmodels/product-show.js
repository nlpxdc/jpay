var app = new Vue({
    el: '#app',
    data: {},
    methods: {
        handleBuyClick() {
            console.log('buy click');
            this.getOrderPayPage();
        },
        getOrderPayPage() {
            axios.get('/order/getOrderPayPage')
                .then(function (response) {
                    console.log(response);
                    document.write(response.data);
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }
})