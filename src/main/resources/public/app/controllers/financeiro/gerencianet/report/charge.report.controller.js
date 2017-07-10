(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeReportCtrl', ['$scope', 'Charge', 'GerencianetAccount', function ($scope, Charge, GerencianetAccount) {
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

                $scope.accounts = GerencianetAccount.query();
            }

            function _findByPaymentDate(params) {
                var status = params.status === 'PAYMENT MANUAL' ? 'CANCELED' : params.status;
                var params2 = {
                    start: params.start.getTime(),
                    end: params.end.getTime(),
                    status: status
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.charges = Charge.findByPaymentDate(params2);
            }

            function _findByExpirationDate(params) {
                var params2 = {
                    start: params.start.getTime(),
                    end: params.end.getTime(),
                    status: params.status
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.charges = Charge.findByExpirationDate(params2);
            }
        }]);
}());
