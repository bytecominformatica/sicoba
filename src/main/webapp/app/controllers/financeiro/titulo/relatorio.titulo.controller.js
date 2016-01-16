(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('RelatorioTituloCtrl', function ($scope, $routeParams, Titulo) {

            $scope.getStatusClass = _getStatusClass;

            _init();

            function _init() {
                $scope.params = {
                    inicio: new Date(),
                    fim: new Date(),
                    porDataOcorrencia: true
                };

                $scope.statusList = [
                    {value: 'PAGO_NO_BOLETO', descricao: 'Pago no boleto'},
                    {value: 'PENDENTE', descricao: 'Pendente'},
                    {value: 'BAIXA_MANUAL', descricao: 'Baixa manual'}
                ];
            }

            function _consultar(params) {
                $scope.titulos = Titulo.query(params);
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

        });
}());
