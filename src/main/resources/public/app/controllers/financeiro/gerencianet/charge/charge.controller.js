(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Charge', 'DateDiff',
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

                    $scope.statusList = [
                        {value: 'NEW', label: 'Novo', descricao: 'Cobrança gerada, aguardando definição da forma de pagamento.'},
                        {value: 'WAITING', label: 'Aguardando', descricao: 'Forma de pagamento selecionada, aguardando a confirmação do pagamento.'},
                        {value: 'PAID', label: 'Pago', descricao: 'Pagamento confirmado.'},
                        {value: 'REFUNDED', label: 'Devolvido', descricao: 'Pagamento devolvido pelo lojista ou pelo intermediador Gerencianet.'},
                        {value: 'CONTESTED', label: 'Contestado', descricao: 'Pagamento em processo de contestação.'},
                        {value: 'CANCELED', label: 'Cancelado', descricao: 'Cobrança cancelada pelo vendedor ou pelo pagador.'},
                        {value: 'LINK', label: 'Link', descricao: 'Cobrança associada a um link de pagamento.'}
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
