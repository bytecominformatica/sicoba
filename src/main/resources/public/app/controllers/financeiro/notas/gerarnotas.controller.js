(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('GerarNotasCtrl', ['$scope', '$rootScope', 'Charge', 'Notas', 'GerencianetAccount', function ($scope, $rootScope, Charge, Notas, GerencianetAccount) {
            $scope.findByPaymentDate = _findByPaymentDate;
            $scope.findByExpirationDate = _findByExpirationDate;
            $scope.toogle = _toogle;
            $scope.totalSelecionado = _totalSelecionado;
            $scope.gerarNotas = _gerarNotas;

            _init();

            function _init() {
                $scope.params = {
                    start: new Date(),
                    end: new Date(),
                    byPaymentDate: true,
                    allSelected: false
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

            function _toogle() {
                if ($scope.charges) {
                    $scope.charges.forEach(c => {
                        c.selected = $scope.allSelected;
                    })
                }
            }

            function _totalSelecionado() {
                if (!$scope.charges) return 0;
                return $scope.charges.filter(c => c.selected).length;
            }

            function _gerarNotas() {
                let notasSelecionadas = $scope.charges.filter(c => c.selected);
                Notas.gerar(notasSelecionadas, notasGeradas => {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: `${notasGeradas.length} nota(s) geradas com sucesso`,
                        type: 'alert-success'
                    }];
                });
            }

            function _findByPaymentDate(params) {
                var status = params.status === 'PAYMENT MANUAL' ? 'CANCELED' : params.status;
                var params2 = {
                    start: moment(params.start).format('YYYY-MM-DD'),
                    end: moment(params.end).format('YYYY-MM-DD'),
                    status: status
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.charges = Charge.findByPaymentDate(params2);
            }

            function _findByExpirationDate(params) {
                var params2 = {
                    start: moment(params.start).format('YYYY-MM-DD'),
                    end: moment(params.end).format('YYYY-MM-DD'),
                    status: params.status
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.charges = Charge.findByExpirationDate(params2);
            }
        }]);
}());
