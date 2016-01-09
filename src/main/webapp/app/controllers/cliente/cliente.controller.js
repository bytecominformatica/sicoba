(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteCtrl', function ($scope, $routeParams, Cliente, Cep) {

            $scope.save = _save;
            $scope.buscarEnderecoPorCep = _buscarEnderecoPorCep;

            _init();

            function _init() {
                $scope.cliente = {status: 'ATIVO'};
                $scope.hoje = new Date();

                if ($routeParams.id) {
                    $scope.cliente = Cliente.get({id: $routeParams.id});
                }

            }

            function _save(cliente) {
                Cliente.save(cliente, function (data) {
                    $scope.cliente = data;
                    $scope.message = {title: 'Sucesso', type: 'alert-success'};
                });
            }

            function _buscarEnderecoPorCep(cep, form) {
                if (cep) {
                    Cep.get({cep: cep}, function (data) {
                        if (data.erro) {
                            form.cep.$error.notFound = true;
                        } else {
                            $scope.cliente.endereco.cep = data.cep;
                            $scope.cliente.endereco.logradouro = data.logradouro;

                            $scope.cliente.endereco.bairro = {
                                "nome": data.bairro,
                                "cidade": {
                                    "nome": data.localidade,
                                    "estado": {"uf": data.uf}
                                }
                            };
                        }
                    });
                }
            }
        });
}());
