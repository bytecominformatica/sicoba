(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Charge', 'Carnet',
            'GerencianetAccount', 'DateDiff', 'Contrato',
            function ($scope, $rootScope, $routeParams, $location, Charge, Carnet, GerencianetAccount, DateDiff, Contrato) {
                $scope.create = _create;
                $scope.calculateProportionalValue = _calculateProportionalValue;
                $scope.cancel = _cancel;
                $scope.manualPayment = _manualPayment;
                $scope.createBankingBillet = _createBankingBillet;
                $scope.createPaymentLink = _createPaymentLink;
                $scope.updateExpireAt = _updateExpireAt;
                $scope.updateValues = _updateValues;
                $scope.isCancelable = _isCancelable;
                $scope.isAssociable = _isAssociable;
                $scope.isPaid = _isPaid;
                $scope.isPaidViaIntegration = _isPaidViaIntegration;

                _init();

                function _init() {
                    $scope.accounts = GerencianetAccount.query();
                    $scope.charge = {};
                    if ($routeParams.id) {
                        _findById($routeParams.id);
                    } else if ($location.search().clienteId) {
                        $scope.clienteId = $location.search().clienteId;
                        _newCharge($scope.clienteId);
                    }
                }

                function _newCharge(clienteId) {
                    Charge.new({clienteId: clienteId}, function (charge) {
                        if(charge.expireAt) charge.expireAt = moment(charge.expireAt).toDate();
                        $scope.charge = charge;
                    });
                }

                function _findById(id) {
                    Charge.get({id: id}, function (charge) {
                        if(charge.expireAt) charge.expireAt = moment(charge.expireAt).toDate();
                        $scope.charge = charge;
                        $scope.clienteId = charge.cliente.id;
                    });
                }

                function _updateValues(charge) {
                    if (charge.manualPayment) {
                        _calculatePaidValue(charge);
                    } else {
                        charge.paidValue = 0;
                        charge.paidAt = null;
                    }
                }

                function _calculatePaidValue(charge) {

                    if (!charge.paidAt) {
                        charge.paidAt = new Date();
                    }

                    var diff = DateDiff.inDays(new Date(charge.expireAt), charge.paidAt);
                    if (diff <= 0) {
                        charge.paidValue = charge.value;
                        if (charge.discount) {
                            charge.paidValue -= charge.discount;
                        }
                    } else {
                        var fine = charge.value * 0.05;
                        var interest = charge.value * 0.006677 * diff;
                        var vp = charge.value + fine + interest;
                        charge.paidValue = parseFloat(vp.toFixed(2));
                    }
                }

                function _calculateProportionalValue(charge) {
                    Contrato.buscarPorCliente({clienteId: charge.cliente.id}, function (contrato) {
                        var days = DateDiff.inDays(new Date(contrato.dataInstalacao), new Date(charge.expireAt));
                        var valueProportional = contrato.plano.valor / 30 * days;
                        charge.value = parseFloat(valueProportional.toFixed(2));
                        charge.description = charge.description + ' (' + days + ' dia(s))';
                    });
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
                        $scope.charge = data;
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Cancelada cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }

                function _manualPayment(charge) {
                    Charge.manualPayment(charge, function (data) {
                        $scope.charge = data;
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Baixa manual da cobrança de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }

                function _updateExpireAt(charge) {

                    if (charge.carnet) {
                        Carnet.updateParcelExpireAt(charge, function (data) {
                            $scope.charge = data;
                            $rootScope.messages = [{
                                title: 'Sucesso',
                                body: 'Vencimento atualizado para cobrança de número ' + data.id,
                                type: 'alert-success'
                            }];
                        });
                    } else {
                        Charge.updateExpireAt(charge, function (data) {
                            $scope.charge = data;
                            $rootScope.messages = [{
                                title: 'Sucesso',
                                body: 'Vencimento atualizado para cobrança de número ' + data.id,
                                type: 'alert-success'
                            }];
                        });
                    }
                }

                function _isCancelable(charge) {
                    return charge.id && charge.status !== 'CANCELED' && charge.status !== 'PAID' && !charge.manualPayment;
                }

                function _isAssociable(charge) {
                    return charge.id && charge.status === 'NEW';
                }

                function _isPaid(charge) {
                    return charge && (charge.status === 'PAID' || charge.manualPayment);
                }

                function _isPaidViaIntegration(charge) {
                    return charge && charge.status === 'PAID';
                }

            }]);
}());
