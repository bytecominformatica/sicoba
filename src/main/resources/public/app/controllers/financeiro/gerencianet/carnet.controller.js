(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarnetCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Titulo', 'Carnet',
            function ($scope, $rootScope, $routeParams, $location, Titulo, Carnet) {

                $scope.save = _save;

                _init();

                function _init() {
                    $scope.clienteId = $routeParams.clienteId;

                    Titulo.novo({clienteId: $routeParams.clienteId}, function (novoTitulo) {
                        $scope.cliente = novoTitulo.cliente;
                        $scope.carne = {
                            clienteId: novoTitulo.cliente.id,
                            modalidade: novoTitulo.modalidade,
                            valor: novoTitulo.valor,
                            desconto: novoTitulo.desconto,
                            quantidadeParcela: 12,
                            dataInicio: novoTitulo.dataVencimento
                        };
                    });
                }

                function _save(carne) {
                    Carnet.gerar(carne, function (titulos) {
                        $rootScope.messages = [{
                            title: 'Sucesso:',
                            body: 'Criado carnê com ' + titulos.length + ' titulos(s)',
                            type: 'alert-success'
                        }];
                        $location.path('/titulos/cliente/' + carne.clienteId);
                    });
                }
            }]);
}());
