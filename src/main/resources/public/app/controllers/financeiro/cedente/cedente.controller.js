(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CedenteCtrl', ['$scope', '$rootScope', '$routeParams', 'Cedente', 'Cep',
            function ($scope, $rootScope, $routeParams, Cedente, Cep) {

            $scope.save = _save;
            $scope.remove = _remove;
            $scope.buscarEnderecoPorCep = _buscarEnderecoPorCep;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.cedente = Cedente.get({id: $routeParams.id});
                }
            }

            function _save(cedente) {
                Cedente.save(cedente, function (data) {
                    $scope.cedente = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Cedente ' + data.nome + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(cedente) {
                Cedente.remove({id: cedente.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Cedente ' + cedente.nome + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.cedente = {};
                });
            }

            function _buscarEnderecoPorCep(cep, form) {
                if (cep) {
                    Cep.get({cep: cep}, function (data) {
                        if (data.erro) {
                            form.cep.$error.notFound = true;
                        } else {
                            form.cep.$error.notFound = false;
                            $scope.cedente.cep = data.cep;
                            $scope.cedente.logradouro = data.logradouro;

                            $scope.cedente.bairro = data.bairro;
                            $scope.cedente.cidade = data.localidade;
                            $scope.cedente.uf = data.uf;
                        }
                    });
                }
            }

        }]);
}());
