(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('TituloCtrl', function ($scope, $rootScope, $routeParams, $location, Titulo) {

            $scope.save = _save;
            $scope.remove = _remove;
            $scope.atualizarValores = _atualizarValores;

            _init();

            function _init() {
                if ($routeParams.clienteId) {
                    $scope.clienteId = $routeParams.clienteId;
                    _novaTitulo();
                } else if ($routeParams.id) {
                    _buscarPorId($routeParams.id);
                }

                $scope.modalidades = [
                    {value: 14, descricao: 'Registrado'},
                    {value: 24, descricao: 'Sem Registro'}
                ];
                $scope.statusList = [
                    {value: 'PENDENTE', descricao: 'Pendente'},
                    {value: 'BAIXA_MANUAL', descricao: 'Baixa manual'}
                ];
            }

            function _novaTitulo() {
                $scope.titulo = Titulo.novo({clienteId: $routeParams.clienteId});
            }

            function _buscarPorId(id) {
                Titulo.get({id: id}, function (titulo) {
                    $scope.titulo = titulo;
                    $scope.clienteId = titulo.cliente.id;
                });
            }

            function _atualizarValores(titulo) {
                if (titulo.status === 'BAIXA_MANUAL') {
                    titulo.valorPago = titulo.valor - titulo.desconto;
                    if (!titulo.dataOcorrencia) {
                        titulo.dataOcorrencia = new Date();
                    }
                } else {
                    titulo.valorPago = 0;
                    titulo.dataOcorrencia = null;
                }
            }

            function _save(titulo) {
                Titulo.save(titulo, function (data) {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Criado titulo de número ' + data.id,
                        type: 'alert-success'
                    }];
                    _voltar();
                });
            }

            function _remove(titulo) {
                Titulo.remove({id: titulo.id}, function (data) {
                    _init();
                    $rootScope.messages = [{
                        title: 'Sucesso',
                        body: 'Removido titulo de número ' + data.id,
                        type: 'alert-success'
                    }];
                    _voltar();
                });
            }

            function _voltar() {
                $location.path('/titulos/cliente/' + $scope.clienteId);
            }
        });
}());
