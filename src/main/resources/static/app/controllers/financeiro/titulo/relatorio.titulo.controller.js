(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('RelatorioTituloCtrl', function ($scope, Titulo) {

            $scope.getStatusClass = _getStatusClass;
            $scope.consultarPorDataOcorrencia = _consultarPorDataOcorrencia;
            $scope.consultarPorDataVencimento = _consultarPorDataVencimento;

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

            function _consultarPorDataOcorrencia(params) {
                $scope.titulos = Titulo.buscarPorDataOcorrencia({
                    inicio: _format(params.inicio),
                    fim: _format(params.fim),
                    status: params.status
                });
            }

            function _consultarPorDataVencimento(params) {
                $scope.titulos = Titulo.buscarPorDataVencimento({
                    inicio: _format(params.inicio),
                    fim: _format(params.fim),
                    status: params.status
                });
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

            function _format(date) {
                if (date) {
                    var year = 1900 + date.getYear();
                    var month = date.getMonth() + 1;
                    var day = date.getDate();
                    month = month < 10 ? '0' + month : month;
                    day = day < 10 ? '0' + day : day;
                    return year + '-' + month + '-' + day;
                }
            }

        });
}());
