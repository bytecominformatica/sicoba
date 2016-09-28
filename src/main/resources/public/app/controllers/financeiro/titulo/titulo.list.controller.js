(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('TituloListCtrl', function ($scope, $routeParams, Titulo, Cliente, FileSaver, Blob) {

            $scope.getStatusClass = _getStatusClass;
            $scope.gerarBoletos = _gerarBoletos;

            _init();

            function _init() {
                $scope.cliente = Cliente.get({id: $routeParams.clienteId});
                $scope.titulos = Titulo.buscarPorCliente({clienteId: $routeParams.clienteId});
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

            function _isSelected(it) {
                return it.selected;
            }

            function _gerarBoletos() {
                var selecionados = $scope.titulos.filter(_isSelected).map(function (it) {
                    return it.id;
                });
                if (selecionados && selecionados.length > 0) {
                    var filename = $scope.cliente.nome + '.pdf';

                    Titulo.gerarBoletos({titulos: selecionados}, function (data) {
                        FileSaver.saveAs(data.file, filename);
                    });
                }

            }

        });
}());
