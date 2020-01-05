var app = new Vue({
    el: '#app',
    data: {
    },
    mounted() {
        console.log('view mounted');
        this.getOrderPayPage();
    },
    methods: {
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
});