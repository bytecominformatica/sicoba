(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeReportCtrl', ['$scope', 'Charge', function ($scope, Charge) {
            $scope.findByPaymentDate = _findByPaymentDate;
            $scope.findByExpirationDate = _findByExpirationDate;

            _init();

            function _init() {
                $scope.params = {
                    start: new Date(),
                    end: new Date(),
                    byPaymentDate: true
                };

                $scope.statusList = [
                    {value: 'NEW', description: 'Novo'},
                    {value: 'WAITING', description: 'Aguardando'},
                    {value: 'PAID', description: 'Pago'},
                    {value: 'PAYMENT MANUAL', description: 'Pagamento Manual'},
                    {value: 'UNPAID', description: 'Não Pago'},
                    {value: 'REFUNDED', description: 'Devolvido'},
                    {value: 'CONTESTED', description: 'Contestado'},
                    {value: 'CANCELED', description: 'Cancelado'},
                    {value: 'LINK', description: 'Link'}
                ];
            }

            function _findByPaymentDate(params) {
                var status = params.status === 'PAYMENT MANUAL' ? 'CANCELED' : params.status;
                $scope.charges = Charge.findByPaymentDate({
                    start: params.start.getTime(),
                    end: params.end.getTime(),
                    status: status
                });
            }

            function _findByExpirationDate(params) {
                $scope.charges = Charge.findByExpirationDate({
                    start: params.start.getTime(),
                    end: params.end.getTime(),
                    status: params.status
                });
            }
        }]);
}());
