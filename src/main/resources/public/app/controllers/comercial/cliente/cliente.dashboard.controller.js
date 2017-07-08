(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteDashboardCtrl', ['$scope', '$routeParams', 'Cliente', 'Contrato', 'Conexao', 'Charge',
            function ($scope, $routeParams, Cliente, Contrato, Conexao, Charge) {

                $scope.getStatusClass = _getStatusClass;

                _init();

                function _init() {
                    if ($routeParams.id) {
                        Cliente.get({id: $routeParams.id}, function (cliente) {
                            var endereco = _enderecoToString(cliente.endereco);

                            $scope.cliente = cliente;
                            $scope.endereco = endereco;
                        });
                        $scope.contrato = Contrato.buscarPorCliente({clienteId: $routeParams.id});
                        $scope.conexao = Conexao.buscarPorCliente({clienteId: $routeParams.id});
                        $scope.charges = Charge.current({clienteId: $routeParams.id});
                    }
                }

                function _enderecoToString(endereco) {
                    var enderecoString = '';
                    enderecoString += endereco.logradouroNumeroComplemento;
                    enderecoString += ' - ';
                    enderecoString += endereco.bairro.nome;
                    enderecoString += ', ';
                    enderecoString += endereco.bairro.cidade.nome;
                    enderecoString += ' - ';
                    enderecoString += endereco.bairro.cidade.estado.uf;
                    enderecoString += ', ';
                    enderecoString += endereco.cep;
                    return enderecoString;
                }

                function _getStatusClass(charge) {
                    var result;
                    switch (charge.status) {
                        case 'PAID':
                            result = 'label-success';
                            break;
                        case 'CANCELED':
                            if (charge.manualPayment)
                                result = 'label-success';
                            else
                                result = 'label-danger';
                            break;
                        case 'REFUNDED':
                            result = 'label-danger';
                            break;
                        case 'LINK':
                            result = 'label-info';
                            break;
                        case 'WAITING':
                            result = 'label-info';
                            break;
                        default:
                            result = 'label-warning';
                    }

                    return result;
                }

            }]);
}());
