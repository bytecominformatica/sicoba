(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarneCtrl', function ($scope, $rootScope, $routeParams, $location, Titulo, Contrato) {

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
                        dataInicio: novoTitulo.dataVencimento
                    };
                });

                $scope.modalidades = [
                    {value: 14, descricao: 'Registrado'},
                    {value: 24, descricao: 'Sem Registro'}
                ];
            }

            function _save(carne) {
                Titulo.criarCarne(carne, function (titulos) {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Criado carnê com ' + titulos.length + ' titulos(s) de ' + carne.boletoInicio + ' até ' + carne.boletoFim,
                        type: 'alert-success'
                    }];
                    $location.path('/titulos/cliente/' + carne.clienteId);
                });
            }
        });
}());
