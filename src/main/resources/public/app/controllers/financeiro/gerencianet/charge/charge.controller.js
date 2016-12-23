(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Charge', 'DateDiff',
            function ($scope, $rootScope, $routeParams, $location, Charge, DateDiff) {

                $scope.create = _create;
                $scope.cancel = _cancel;
                $scope.createBankingBillet = _createBankingBillet;
                $scope.createPaymentLink = _createPaymentLink;
                $scope.atualizarValores = _atualizarValores;

                _init();

                function _init() {
                    if ($routeParams.id) {
                        _findById($routeParams.id);
                    } else if ($location.search().clienteId) {
                        $scope.clienteId = $location.search().clienteId;
                        _newCharge($scope.clienteId);
                    }
                }

                function _newCharge(clienteId) {
                    $scope.charge = Charge.new({clienteId: clienteId});
                }

                function _findById(id) {
                    Charge.get({id: id}, function (charge) {
                        $scope.charge = charge;
                        $scope.clienteId = charge.cliente.id;
                    });
                }

                function _atualizarValores(charge) {
                    if (charge.status === 'BAIXA_MANUAL') {
                        calculaValorAPagar(charge);
                    } else {
                        charge.valorPago = 0;
                        charge.dataOcorrencia = null;
                    }
                }

                function calculaValorAPagar(charge) {
                    if (!charge.dataOcorrencia) {
                        charge.dataOcorrencia = new Date();
                    }

                    var diff = DateDiff.inDays(new Date(charge.dataVencimento), charge.dataOcorrencia);
                    if (diff <= 0) {
                        charge.valorPago = charge.valor - charge.desconto;
                    } else {
                        var juros = charge.valor * 0.05;
                        var mora = charge.valor * 0.006677 * diff;
                        var vp = charge.valor + juros + mora;
                        charge.valorPago = parseFloat(vp.toFixed(2));
                    }
                }

                function _create(charge) {
                    Charge.save(charge, function (data) {
                        $rootScope.messages = [{
                            title: 'Sucesso:',
                            body: 'Criado cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                        $location.path('/charges/' + data.id);
                    });
                }

                function _createBankingBillet(charge) {
                    Charge.bankingBillet({id: charge.id}, function (data) {
                        $scope.charge = data;
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Boleto bancario criado para cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }

                function _createPaymentLink(charge) {
                    Charge.paymentLink({id: charge.id}, function (data) {
                        $scope.charge = data;
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Boleto bancario criado para cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }

                function _cancel(charge) {
                    Charge.cancel({id: charge.id}, function (data) {
                        _init();
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Cancelada cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }

                function _voltar() {
                    $location.path('/charges?cliente=' + $scope.clienteId);
                }
            }]);
}());
