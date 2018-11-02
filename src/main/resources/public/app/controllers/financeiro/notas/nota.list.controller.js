(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NotaListCtrl', ['$scope', '$rootScope', 'Notas', 'GerencianetAccount', function ($scope, $rootScope, Notas, GerencianetAccount) {
            $scope.findItensByDateOfProvision = _findItensByDateOfProvision;
            $scope.toogle = _toogle;
            $scope.totalSelecionado = _totalSelecionado;
            $scope.gerarArquivoSyncNfe = _gerarArquivoSyncNfe;

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
                console.log($scope.allSelected);
                if ($scope.notas) {
                    $scope.notas.forEach(c => {
                        c.selected = $scope.allSelected;
                    })
                }
            }

            function _totalSelecionado() {
                if (!$scope.notas) return 0;
                return $scope.notas.filter(c => c.selected).length;
            }

            function _gerarArquivoSyncNfe() {
                let notasSelecionadas = $scope.notas.filter(c => c.selected);
                console.log('gerando notas....2', notasSelecionadas);
                Notas.gerar(notasSelecionadas, notasGeradas => {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: `${notasGeradas.leading} nota(s) geradas com sucesso`,
                        type: 'alert-success'
                    }];
                });
            }

            function _findItensByDateOfProvision(params) {
                var params2 = {
                    start: moment(params.start).format('YYYY-MM-DD'),
                    end: moment(params.end).format('YYYY-MM-DD')
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.notas = Notas.findItensByDateOfProvision(params2);
            }

        }]);
}());
