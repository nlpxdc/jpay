var app = new Vue({
    el: '#app',
    data: {
        orderId: '',
        amount: ''
    },
    methods: {
        handleApplyRefundClick() {
            console.log('apply refund click');
            this.doOrderApplyRefund();
        },
        doOrderApplyRefund() {
            axios.post('/order/applyRefund', {
                orderId: this.orderId,
                amount: this.amount
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }
});