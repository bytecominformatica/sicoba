(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('TituloCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Titulo', 'DateDiff',
            function ($scope, $rootScope, $routeParams, $location, Titulo, DateDiff) {

                $scope.save = _save;
                $scope.remove = _remove;
                $scope.atualizarValores = _atualizarValores;

                _init();

                function _init() {
                    if ($routeParams.clienteId) {
                        $scope.clienteId = $routeParams.clienteId;
                        _novoTitulo();
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

                function _novoTitulo() {
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
                        calculaValorAPagar(titulo);
                    } else {
                        titulo.valorPago = 0;
                        titulo.dataOcorrencia = null;
                    }
                }

                function calculaValorAPagar(titulo) {
                    if (!titulo.dataOcorrencia) {
                        titulo.dataOcorrencia = new Date();
                    }

                    var diff = DateDiff.inDays(new Date(titulo.dataVencimento), titulo.dataOcorrencia);
                    if (diff <= 0) {
                        titulo.valorPago = titulo.valor - titulo.desconto;
                    } else {
                        var juros = titulo.valor * 0.05;
                        var mora = titulo.valor * 0.006677 * diff;
                        var vp = titulo.valor + juros + mora;
                        titulo.valorPago = parseFloat(vp.toFixed(2));
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
            }]);
}());
