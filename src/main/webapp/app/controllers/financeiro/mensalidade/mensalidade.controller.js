(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MensalidadeCtrl', function ($scope, $rootScope, $routeParams, $location, Mensalidade) {

            $scope.save = _save;
            $scope.remove = _remove;
            $scope.atualizarValores = _atualizarValores;

            _init();

            function _init() {
                if ($routeParams.clienteId) {
                    $scope.clienteId = $routeParams.clienteId;
                    _novaMensalidade();
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

            function _novaMensalidade() {
                $scope.mensalidade = Mensalidade.nova({clienteId: $routeParams.clienteId});
            }

            function _buscarPorId(id) {
                Mensalidade.get({id: id}, function (mensalidade) {
                    $scope.mensalidade = mensalidade;
                    $scope.clienteId = mensalidade.cliente.id;
                });
            }

            function _atualizarValores(mensalidade) {
                if (mensalidade.status === 'BAIXA_MANUAL') {
                    mensalidade.valorPago = mensalidade.valor - mensalidade.desconto;
                    if (!mensalidade.dataOcorrencia) {
                        mensalidade.dataOcorrencia = new Date();
                    }
                } else {
                    mensalidade.valorPago = 0;
                    mensalidade.dataOcorrencia = null;
                }
            }

            function _save(mensalidade) {
                Mensalidade.save(mensalidade, function (data) {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Criada mensalidade de número ' + data.id,
                        type: 'alert-success'
                    }];
                    _voltar();
                });
            }

            function _remove(mensalidade) {
                Mensalidade.remove({id: mensalidade.id}, function (data) {
                    _init();
                    $rootScope.messages = [{
                        title: 'Sucesso',
                        body: 'Removida mensalidade de número ' + data.id,
                        type: 'alert-success'
                    }];
                    _voltar();
                });
            }

            function _voltar() {
                $location.path('#/mensalidades/cliente/' + $routeParams.clienteId);
            }
        });
}());
