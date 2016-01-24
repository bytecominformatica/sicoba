(function () {
    'use strict';

    angular.module('sicobaApp', [
        'ngRoute',
        'ngResource',
        'ngAnimate',
        'ngFileUpload',
        'ui.bootstrap',
        'ngMask',
        'angular-confirm',
        'angular-loading-bar'
    ]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/'});
    }]);
}());

/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('MyLoggingInterceptor', function ($rootScope, $q) {
            return {
                request: function (config) {
                    $rootScope.messages = [];
                    return config;
                },
                responseError: function (rejection) {
                    console.log('Error in response ', rejection);
                    // if (rejection.status === 403) {
                    //                Show a login dialog
                    // }

                    if (rejection.data && rejection.data.errors) {
                        rejection.data.errors.forEach(function (it) {
                            $rootScope.messages.push({
                                title: 'Error:' + it.field,
                                body: it.defaultMessage,
                                type: 'alert-danger'
                            });
                        });
                    } else {
                        var message = rejection.message || rejection.data.message;
                        $rootScope.messages.push({title: 'Error:', body: rejection.data.message, type: 'alert-danger'});
                    }

                    return $q.reject(rejection);
                }
            };
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('MyLoggingInterceptor');
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('HomeCtrl', function ($scope, Titulo, Cliente, Contrato) {

            $scope.order = _order;

            _buscarTitulosVencido();
            _buscarClientesSemTitulo();
            _buscarClientesInativo();
            _buscarContratosNovos();


            function _buscarTitulosVencido() {
                $scope.titulosVencidos = Titulo.vencidos();
            }

            function _buscarClientesSemTitulo() {
                $scope.clientesSemTitulo = Cliente.semTitulo();
            }

            function _buscarClientesInativo() {
                $scope.clientesInativo = Cliente.query({status: 'INATIVO'});
            }

            function _buscarContratosNovos() {
                $scope.contratosNovos = Contrato.novos();
            }

            function _order(predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LoginCtrl', function ($scope, $window) {

            $scope.loginGoogle = function () {
                var oauth2 = {
                    url: "https://accounts.google.com/o/oauth2/auth",
                    client_id: "275948024425-ml60j1mqu63t237ifm2d6p617l3gimt3.apps.googleusercontent.com",
                    response_type: "token",
                    redirect_uri: "http://localhost:8000",
                    scope: "profile email",
                    state: "initial"
                };

                $window.sessionStorage.loginType = "google";

                $window.open(oauth2.url + "?client_id=" +
                    oauth2.client_id + "&response_type=" +
                    oauth2.response_type + "&redirect_uri=" +
                    oauth2.redirect_uri + "&scope=" +
                    oauth2.scope + "&state=" +
                    oauth2.state, "_self");
            };

            $scope.getGoogleInfo = function () {
                $http.get("https://www.googleapis.com/plus/v1/people/me?access_token=" + $scope.accessToken).success(function (data) {
                    console.log(data);
                });
            };

        });
}());

(function () {
    'use strict';

angular.module('sicobaApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/views/home/index.html',
            controller: 'HomeCtrl'
        });
    }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/login', {
                templateUrl: 'app/views/login/index.html',
                controller: 'LoginCtrl'
            });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NavbarCtrl', function ($scope, $http, $location) {

            $scope.getPath = _getPath;

            _loadMenu();

            function _loadMenu() {
                $http({method: 'GET', url: 'app/template/menu.json'}).success(function (data) {
                    $scope.menus = data;
                });
            }

            function _getPath() {
                return '#' + $location.path();
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .filter('sumOfValue', function () {
            return function (data, key) {
                if (angular.isUndefined(data) && angular.isUndefined(key))
                    return 0;
                var sum = 0;

                angular.forEach(data, function (v, k) {
                    sum = sum + parseInt(v[key]);
                });
                return sum;
            };
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ConexaoCtrl', function ($scope, $rootScope, $routeParams, Cliente, Conexao, Contrato, Mikrotik) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                $scope.clienteId = $routeParams.clienteId;
                _carregarConexao();
                $scope.contrato = Contrato.buscarPorCliente({clienteId: $routeParams.clienteId});
                $scope.mikrotiks = Mikrotik.query();

            }

            function _carregarConexao() {
                Conexao.buscarPorCliente({clienteId: $routeParams.clienteId}, function (conexao) {
                    if (conexao.id) {
                        $scope.conexao = conexao;
                    } else {
                        _criarNovaConexao();
                    }
                });
            }

            function _criarNovaConexao() {
                Cliente.get({id: $routeParams.clienteId}, function (cliente) {
                    $scope.conexao = {
                        cliente: cliente
                    };


                    var indexOfSpace = cliente.nome.indexOf(' ');
                    if (indexOfSpace > 0) {
                        $scope.conexao.nome = $scope.conexao.senha = cliente.nome.splice(0, indexOfSpace) + cliente.id;
                    } else {
                        $scope.conexao.nome = $scope.conexao.senha = cliente.nome + cliente.id;
                    }

                    Conexao.buscarIpLivre({}, function (data) {
                        $scope.conexao.ip = data.ip;
                    });

                });
            }

            function _save(conexao) {
                Conexao.save(conexao, function (data) {
                    $scope.conexao = data;
                    _sucesso();
                });
            }

            function _remove(conexao) {
                Conexao.remove({id: conexao.id}, function (data) {
                    _init();
                    _sucesso();
                });
            }

            function _sucesso(){
                $rootScope.messages = [{title: 'Sucesso', type: 'alert-success'}];
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ContratoCtrl', function ($scope, $rootScope, $routeParams, Cliente, Contrato, Plano, Equipamento) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                $scope.clienteId = $routeParams.clienteId;
                $scope.hoje = new Date();
                $scope.planos = Plano.query();
                $scope.equipamentosInstalacao = Equipamento.disponiveisParaInstalacao();
                $scope.equipamentosWifi = Equipamento.disponiveisParaWifi();
                _carregarContrato();
            }

            function _carregarContrato() {
                Contrato.buscarPorCliente({clienteId: $routeParams.clienteId}, function (contrato) {
                    if (contrato.id) {
                        $scope.contrato = contrato;
                        if (contrato.equipamento) {
                            $scope.equipamentosInstalacao.push(contrato.equipamento);
                        }
                        if (contrato.equipamentoWifi) {
                            $scope.equipamentosWifi.push(contrato.equipamentoWifi);
                        }
                    } else {
                        $scope.contrato = {
                            cliente: Cliente.get({id: $routeParams.clienteId})
                        };
                    }
                });
            }

            function _save(contrato) {
                Contrato.save(contrato, function (data) {
                    $scope.contrato = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Contrato de número ' + data.id + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(contrato) {
                Contrato.remove({id: contrato.id}, function (data) {
                    _init();
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Contrato de número ' + contrato.id + ' foi removido.',
                        type: 'alert-success'
                    }];
                });
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('RetornoCtrl', function ($scope, $rootScope, Upload) {

            $scope.enviar = _enviar;

            function _enviar(files) {
                files.forEach(function (file) {
                    Upload.upload({
                        url: 'api/retornos/upload',
                        data: {file: file}
                    }).then(function (resp) {
                        file.retorno = resp.data;
                    }, function (resp) {
                        console.log("TESTESTE");
                        console.log(resp);
                        file.error = resp.data.message;
                    }, function (evt) {
                        file.progress = parseInt(100.0 * evt.loaded / evt.total);
                    });
                });
            }
        });
}());

/**
 * Created by clairton on 29/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .directive('sicobaAlert', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/alert/alert.html'
            };
        });

}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/cliente', {
                    templateUrl: 'app/views/comercial/cliente/cliente.html',
                    controller: 'ClienteCtrl'
                })
                .when('/cliente/:id', {
                    templateUrl: 'app/views/comercial/cliente/cliente.html',
                    controller: 'ClienteCtrl'
                })
                .when('/clientes', {
                    templateUrl: 'app/views/comercial/cliente/cliente.list.html',
                    controller: 'ClienteListCtrl'
                });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/conexao/cliente/:clienteId', {
                    templateUrl: 'app/views/comercial/conexao.html',
                    controller: 'ConexaoCtrl'
                });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/contrato/cliente/:clienteId', {
                    templateUrl: 'app/views/comercial/contrato.html',
                    controller: 'ContratoCtrl'
                });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/plano', {
                    templateUrl: 'app/views/comercial/plano/plano.html',
                    controller: 'PlanoCtrl'
                })
                .when('/plano/:id', {
                    templateUrl: 'app/views/comercial/plano/plano.html',
                    controller: 'PlanoCtrl'
                })
                .when('/planos', {
                    templateUrl: 'app/views/comercial/plano/plano.list.html',
                    controller: 'PlanoListCtrl'
                });
        }]);
}());

/**
 * Created by clairton on 13/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/equipamentos', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.list.html',
                    controller: 'EquipamentoListCtrl'
                })
                .when('/equipamento', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.html',
                    controller: 'EquipamentoCtrl'
                })
                .when('/equipamento/:id', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.html',
                    controller: 'EquipamentoCtrl'
                });
        }]);
}());

/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/retorno', {
                    templateUrl: 'app/views/financeiro/retorno.html',
                    controller: 'RetornoCtrl'
                });
        }]);
}());

/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/titulos/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.list.html',
                    controller: 'TituloListCtrl'
                })
                .when('/titulo/:id', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.html',
                    controller: 'TituloCtrl'
                })
                .when('/titulo/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.html',
                    controller: 'TituloCtrl'
                })
                .when('/titulo/cliente/:clienteId/carne', {
                    templateUrl: 'app/views/financeiro/titulo/carne.html',
                    controller: 'CarneCtrl'
                })
                .when('/titulos/relatorio', {
                    templateUrl: 'app/views/financeiro/titulo/relatorio.html',
                    controller: 'RelatorioTituloCtrl'
                });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/mikrotik', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.html',
                    controller: 'MikrotikCtrl'
                })
                .when('/mikrotik/:id', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.html',
                    controller: 'MikrotikCtrl'
                })
                .when('/mikrotiks', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.list.html',
                    controller: 'MikrotikListCtrl'
                });
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Cep', ['$resource', function ($resource) {
            return $resource('http://viacep.com.br/ws/:cep/json', {cep: '@cep'},
                {});
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Cliente', ['$resource', function ($resource) {
            return $resource('api/clientes/:id', {id: '@id'},
                {
                    semTitulo: {
                        method: 'GET',
                        url: 'api/clientes/sem_titulo',
                        isArray: true
                    },
                    ultimosAlterados: {
                        method: 'GET',
                        url: 'api/clientes/ultimos_alterados',
                        isArray: true
                    }
                });
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Conexao', ['$resource', function ($resource) {
            return $resource('api/conexoes/:id', {id: '@id'},
                {
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/conexoes/cliente/:clienteId',
                        params: {id: '@id'}
                    },
                    buscarPorIp: {
                        method: 'GET',
                        url: 'api/conexoes/ip/:ip',
                        params: {ip: '@ip'}
                    },
                    buscarIpLivre: {
                        method: 'GET',
                        url: 'api/conexoes/ip/livre'
                    }
                });
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Contrato', ['$resource', function ($resource) {
            return $resource('api/contratos/:id', {id: '@id'},
                {
                    novos: {
                        method: 'GET',
                        url: 'api/contratos/novos',
                        isArray: true
                    },
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/contratos/cliente/:clienteId',
                        params: {clienteId: '@clienteId'}
                    }
                });
        }]);
}());

/**
 * Created by clairton on 01/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Plano', ['$resource', function ($resource) {
            return $resource('api/planos/:id', {id: '@id'},
                {});
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Equipamento', ['$resource', function ($resource) {
            return $resource('api/equipamentos/:id', {id: '@id'},
                {
                    disponiveisParaInstalacao: {
                        method: 'GET',
                        url: 'api/equipamentos/instalacao/disponiveis',
                        isArray: true
                    },
                    disponiveisParaWifi: {
                        method: 'GET',
                        url: 'api/equipamentos/wifi/disponiveis',
                        isArray: true
                    }
                });
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Titulo', ['$resource', function ($resource) {
            return $resource('api/titulos/:id', {id: '@id'},
                {
                    'vencidos': {
                        method: 'GET',
                        url: 'api/titulos/vencidos',
                        isArray: true
                    },
                    'novo': {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId/nova',
                        params: {clienteId: '@clienteId'}
                    },
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId',
                        params: {clienteId: '@clienteId'},
                        isArray: true
                    },
                    criarCarne: {
                        method: 'POST',
                        url: 'api/titulos/carne',
                        isArray: true
                    },
                    buscarPorDataOcorrencia: {
                        method: 'GET',
                        url: 'api/titulos/ocorrencia',
                        isArray: true
                    },
                    buscarPorDataVencimento: {
                        method: 'GET',
                        url: 'api/titulos/vencimento',
                        isArray: true
                    }
                });
        }]);
}());

/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Mikrotik', ['$resource', function ($resource) {
            return $resource('api/mikrotiks/:id', {id: '@id'},
                {});
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteCtrl', function ($scope, $rootScope, $routeParams, Cliente, Cep, Contrato) {

            $scope.save = _save;
            $scope.buscarEnderecoPorCep = _buscarEnderecoPorCep;

            _init();

            function _init() {
                $scope.cliente = {status: 'ATIVO'};
                $scope.hoje = new Date();

                if ($routeParams.id) {
                    $scope.cliente = Cliente.get({id: $routeParams.id});
                    $scope.contrato = Contrato.buscarPorCliente({clienteId: $routeParams.id});
                }

            }

            function _save(cliente) {
                Cliente.save(cliente, function (data) {
                    $scope.cliente = data;
                    $rootScope.messages = [{title: 'Sucesso:', body: 'Cliente ' + data.nome + ' foi salvo.', type: 'alert-success'}];
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

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteListCtrl', function ($scope, Cliente, Conexao) {
            $scope.buscarPorNome = _buscarPorNome;
            $scope.buscarPorIp = _buscarPorIp;

            _init();

            function _init() {
                Cliente.ultimosAlterados(function (data) {
                    $scope.clientes = data;
                    _buscarConexoes($scope.clientes);
                });
            }

            function _buscarPorNome(nome) {
                $scope.clientes = Cliente.query({nome: nome});
            }

            function _buscarPorIp(ip) {
                Conexao.buscarPorIp({ip: ip}, function (conexao) {
                    if (conexao) {
                        conexao.cliente.conexao = conexao;
                        $scope.clientes = [conexao.cliente];
                    } else {
                        $scope.clientes = [];
                    }
                });
            }

            function _buscarConexoes(clientes) {
                clientes.forEach(function (cliente) {
                    cliente.conexao = Conexao.buscarPorCliente({clienteId: cliente.id});
                });
            }

        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('PlanoCtrl', function ($scope, $rootScope, $routeParams, Plano) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.plano = Plano.get({id: $routeParams.id});
                }
            }

            function _save(plano) {
                Plano.save(plano, function (data) {
                    $scope.plano = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Plano ' + data.nome + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(plano) {
                Plano.remove({id: plano.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Plano ' + plano.nome + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.plano = {};
                });
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('PlanoListCtrl', function ($scope, Plano) {

            $scope.planos = Plano.query();

        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('EquipamentoCtrl', function ($scope, $rootScope, $routeParams, Equipamento) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.equipamento = Equipamento.get({id: $routeParams.id});
                } else {
                    $scope.equipamento = {tipo: "INSTALACAO", status: "OK"};
                }

                $scope.tipos = [
                    {value: "INSTALACAO", descricao: 'Instalação'},
                    {value: "WIFI", descricao: 'Wifi'}
                ];

                $scope.statusList = [
                    {value: "OK", descricao: 'OK'},
                    {value: "DEFEITO", descricao: 'Defeito'}
                ];
            }

            function _save(equipamento) {
                Equipamento.save(equipamento, function (data) {
                    $scope.equipamento = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Equipamento ' + data.marca + ' ' + data.modelo + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(equipamento) {
                Equipamento.remove({id: equipamento.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Equipamento ' + equipamento.marca + ' ' + equipamento.modelo + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.equipamento = {tipo: "INSTALACAO", status: "OK"};
                });
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('EquipamentoListCtrl', function ($scope, Equipamento) {
            $scope.equipamentos = Equipamento.query();
        });
}());

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

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('RelatorioTituloCtrl', function ($scope, Titulo) {

            $scope.getStatusClass = _getStatusClass;
            $scope.consultarPorDataOcorrencia = _consultarPorDataOcorrencia;
            $scope.consultarPorDataVencimento = _consultarPorDataVencimento;

            _init();

            function _init() {
                $scope.params = {
                    inicio: new Date(),
                    fim: new Date(),
                    porDataOcorrencia: true
                };

                $scope.statusList = [
                    {value: 'PAGO_NO_BOLETO', descricao: 'Pago no boleto'},
                    {value: 'PENDENTE', descricao: 'Pendente'},
                    {value: 'BAIXA_MANUAL', descricao: 'Baixa manual'}
                ];
            }

            function _consultarPorDataOcorrencia(params) {
                $scope.titulos = Titulo.buscarPorDataOcorrencia({
                    inicio: _format(params.inicio),
                    fim: _format(params.fim),
                    status: params.status
                });
            }

            function _consultarPorDataVencimento(params) {
                $scope.titulos = Titulo.buscarPorDataVencimento({
                    inicio: _format(params.inicio),
                    fim: _format(params.fim),
                    status: params.status
                });
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

            function _format(date) {
                if (date) {
                    var year = 1900 + date.getYear();
                    var month = date.getMonth() + 1;
                    var day = date.getDate();
                    month = month < 10 ? '0' + month : month;
                    day = day < 10 ? '0' + day : day;
                    return year + '-' + month + '-' + day;
                }
            }

        });
}());

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

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('TituloListCtrl', function ($scope, $routeParams, Titulo, Cliente) {

            $scope.getStatusClass = _getStatusClass;

            _init();

            function _init() {
                $scope.cliente = Cliente.get({id: $routeParams.clienteId});
                $scope.titulos = Titulo.buscarPorCliente({clienteId: $routeParams.clienteId});
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MikrotikCtrl', function ($scope, $rootScope, $routeParams, Mikrotik) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.mikrotik = Mikrotik.get({id: $routeParams.id});
                } else {
                    $scope.mikrotik = {port: 8728};
                }
            }

            function _save(mikrotik) {
                Mikrotik.save(mikrotik, function (data) {
                    $scope.mikrotik = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Mikrotik ' + data.name + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(mikrotik) {
                Mikrotik.remove({id: mikrotik.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Mikrotik ' + mikrotik.name + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.mikrotik = {port: 8728};
                });
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MikrotikListCtrl', function ($scope, Mikrotik) {

            $scope.mikrotiks = Mikrotik.query();

        });
}());

//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC5qcyIsImNvbXBvbmVudGVzL2xvZ2dpbmcuaW50ZXJjZXB0b3IuanMiLCJjb250cm9sbGVycy9ob21lLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9sb2dpbi5jb250cm9sbGVyLmpzIiwicm91dGVzL2hvbWUuanMiLCJyb3V0ZXMvbG9naW4uanMiLCJ0ZW1wbGF0ZS9uYXZiYXIuY29udHJvbGxlci5qcyIsImNvbXBvbmVudGVzL2ZpbHRlcnMvc3VtLm9mLnZhbHVlLmZpbHRlci5qcyIsImNvbnRyb2xsZXJzL2NvbWVyY2lhbC9jb25leGFvLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9jb21lcmNpYWwvY29udHJhdG8uY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2ZpbmFuY2Vpcm8vcmV0b3Juby5jb250cm9sbGVyLmpzIiwiZGlyZWN0aXZlcy9hbGVydC9hbGVydC5kaXJlY3RpdmUuanMiLCJyb3V0ZXMvY29tZXJjaWFsL2NsaWVudGUuanMiLCJyb3V0ZXMvY29tZXJjaWFsL2NvbmV4YW8uanMiLCJyb3V0ZXMvY29tZXJjaWFsL2NvbnRyYXRvLmpzIiwicm91dGVzL2NvbWVyY2lhbC9wbGFuby5qcyIsInJvdXRlcy9lc3RvcXVlL2VxdWlwYW1lbnRvLmpzIiwicm91dGVzL2ZpbmFuY2Vpcm8vcmV0b3Juby5qcyIsInJvdXRlcy9maW5hbmNlaXJvL3RpdHVsby5qcyIsInJvdXRlcy9wcm92ZWRvci9taWtyb3Rpay5qcyIsInNlcnZpY2UvY29tZXJjaWFsL2NlcC5zZXJ2aWNlLmpzIiwic2VydmljZS9jb21lcmNpYWwvY2xpZW50ZS5zZXJ2aWNlLmpzIiwic2VydmljZS9jb21lcmNpYWwvY29uZXhhby5zZXJ2aWNlLmpzIiwic2VydmljZS9jb21lcmNpYWwvY29udHJhdG8uc2VydmljZS5qcyIsInNlcnZpY2UvY29tZXJjaWFsL3BsYW5vLnNlcnZpY2UuanMiLCJzZXJ2aWNlL2VzdG9xdWUvZXF1aXBhbWVudG8uc2VydmljZS5qcyIsInNlcnZpY2UvZmluYW5jZWlyby90aXR1bG8uc2VydmljZS5qcyIsInNlcnZpY2UvcHJvdmVkb3IvbWlrcm90aWsuc2VydmljZS5qcyIsImNvbnRyb2xsZXJzL2NvbWVyY2lhbC9jbGllbnRlL2NsaWVudGUuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2NvbWVyY2lhbC9jbGllbnRlL2NsaWVudGUubGlzdC5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvY29tZXJjaWFsL3BsYW5vL3BsYW5vLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9jb21lcmNpYWwvcGxhbm8vcGxhbm8ubGlzdC5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZXN0b3F1ZS9lcXVpcGFtZW50by9lcXVpcGFtZW50by5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZXN0b3F1ZS9lcXVpcGFtZW50by9lcXVpcGFtZW50by5saXN0LmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9maW5hbmNlaXJvL3RpdHVsby9jYXJuZS5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZmluYW5jZWlyby90aXR1bG8vcmVsYXRvcmlvLnRpdHVsby5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZmluYW5jZWlyby90aXR1bG8vdGl0dWxvLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9maW5hbmNlaXJvL3RpdHVsby90aXR1bG8ubGlzdC5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvcHJvdmVkb3IvbWlrcm90aWsvbWlrcm90aWsuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL3Byb3ZlZG9yL21pa3JvdGlrL21pa3JvdGlrLmxpc3QuY29udHJvbGxlci5qcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNqQkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3hDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3BDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDbENBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1hBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1hBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDckJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2pCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3JFQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDN0RBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQzFCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3BCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1pBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDWkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDcEJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUMvQkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDcEJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDWkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDdkJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDM0JBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1pBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUM1Q0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNaQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ25EQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDeENBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUN4Q0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNwREE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDUkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDekNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQzNEQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2pGQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3JCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQzFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBIiwiZmlsZSI6ImFwcC5qcyIsInNvdXJjZXNDb250ZW50IjpbIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcsIFtcbiAgICAgICAgJ25nUm91dGUnLFxuICAgICAgICAnbmdSZXNvdXJjZScsXG4gICAgICAgICduZ0FuaW1hdGUnLFxuICAgICAgICAnbmdGaWxlVXBsb2FkJyxcbiAgICAgICAgJ3VpLmJvb3RzdHJhcCcsXG4gICAgICAgICduZ01hc2snLFxuICAgICAgICAnYW5ndWxhci1jb25maXJtJyxcbiAgICAgICAgJ2FuZ3VsYXItbG9hZGluZy1iYXInXG4gICAgXSkuXG4gICAgY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgJHJvdXRlUHJvdmlkZXIub3RoZXJ3aXNlKHtyZWRpcmVjdFRvOiAnLyd9KTtcbiAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDA2LzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnTXlMb2dnaW5nSW50ZXJjZXB0b3InLCBmdW5jdGlvbiAoJHJvb3RTY29wZSwgJHEpIHtcbiAgICAgICAgICAgIHJldHVybiB7XG4gICAgICAgICAgICAgICAgcmVxdWVzdDogZnVuY3Rpb24gKGNvbmZpZykge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW107XG4gICAgICAgICAgICAgICAgICAgIHJldHVybiBjb25maWc7XG4gICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICByZXNwb25zZUVycm9yOiBmdW5jdGlvbiAocmVqZWN0aW9uKSB7XG4gICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKCdFcnJvciBpbiByZXNwb25zZSAnLCByZWplY3Rpb24pO1xuICAgICAgICAgICAgICAgICAgICAvLyBpZiAocmVqZWN0aW9uLnN0YXR1cyA9PT0gNDAzKSB7XG4gICAgICAgICAgICAgICAgICAgIC8vICAgICAgICAgICAgICAgIFNob3cgYSBsb2dpbiBkaWFsb2dcbiAgICAgICAgICAgICAgICAgICAgLy8gfVxuXG4gICAgICAgICAgICAgICAgICAgIGlmIChyZWplY3Rpb24uZGF0YSAmJiByZWplY3Rpb24uZGF0YS5lcnJvcnMpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIHJlamVjdGlvbi5kYXRhLmVycm9ycy5mb3JFYWNoKGZ1bmN0aW9uIChpdCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMucHVzaCh7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnRXJyb3I6JyArIGl0LmZpZWxkLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBib2R5OiBpdC5kZWZhdWx0TWVzc2FnZSxcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LWRhbmdlcidcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgdmFyIG1lc3NhZ2UgPSByZWplY3Rpb24ubWVzc2FnZSB8fCByZWplY3Rpb24uZGF0YS5tZXNzYWdlO1xuICAgICAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcy5wdXNoKHt0aXRsZTogJ0Vycm9yOicsIGJvZHk6IHJlamVjdGlvbi5kYXRhLm1lc3NhZ2UsIHR5cGU6ICdhbGVydC1kYW5nZXInfSk7XG4gICAgICAgICAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgICAgICAgICByZXR1cm4gJHEucmVqZWN0KHJlamVjdGlvbik7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfTtcbiAgICAgICAgfSlcbiAgICAgICAgLmNvbmZpZyhmdW5jdGlvbiAoJGh0dHBQcm92aWRlcikge1xuICAgICAgICAgICAgJGh0dHBQcm92aWRlci5pbnRlcmNlcHRvcnMucHVzaCgnTXlMb2dnaW5nSW50ZXJjZXB0b3InKTtcbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0hvbWVDdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgVGl0dWxvLCBDbGllbnRlLCBDb250cmF0bykge1xuXG4gICAgICAgICAgICAkc2NvcGUub3JkZXIgPSBfb3JkZXI7XG5cbiAgICAgICAgICAgIF9idXNjYXJUaXR1bG9zVmVuY2lkbygpO1xuICAgICAgICAgICAgX2J1c2NhckNsaWVudGVzU2VtVGl0dWxvKCk7XG4gICAgICAgICAgICBfYnVzY2FyQ2xpZW50ZXNJbmF0aXZvKCk7XG4gICAgICAgICAgICBfYnVzY2FyQ29udHJhdG9zTm92b3MoKTtcblxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYnVzY2FyVGl0dWxvc1ZlbmNpZG8oKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsb3NWZW5jaWRvcyA9IFRpdHVsby52ZW5jaWRvcygpO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYnVzY2FyQ2xpZW50ZXNTZW1UaXR1bG8oKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVzU2VtVGl0dWxvID0gQ2xpZW50ZS5zZW1UaXR1bG8oKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckNsaWVudGVzSW5hdGl2bygpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZXNJbmF0aXZvID0gQ2xpZW50ZS5xdWVyeSh7c3RhdHVzOiAnSU5BVElWTyd9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckNvbnRyYXRvc05vdm9zKCkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jb250cmF0b3NOb3ZvcyA9IENvbnRyYXRvLm5vdm9zKCk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9vcmRlcihwcmVkaWNhdGUpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUucmV2ZXJzZSA9ICgkc2NvcGUucHJlZGljYXRlID09PSBwcmVkaWNhdGUpID8gISRzY29wZS5yZXZlcnNlIDogZmFsc2U7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnByZWRpY2F0ZSA9IHByZWRpY2F0ZTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0xvZ2luQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICR3aW5kb3cpIHtcblxuICAgICAgICAgICAgJHNjb3BlLmxvZ2luR29vZ2xlID0gZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgIHZhciBvYXV0aDIgPSB7XG4gICAgICAgICAgICAgICAgICAgIHVybDogXCJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aFwiLFxuICAgICAgICAgICAgICAgICAgICBjbGllbnRfaWQ6IFwiMjc1OTQ4MDI0NDI1LW1sNjBqMW1xdTYzdDIzN2lmbTJkNnA2MTdsM2dpbXQzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tXCIsXG4gICAgICAgICAgICAgICAgICAgIHJlc3BvbnNlX3R5cGU6IFwidG9rZW5cIixcbiAgICAgICAgICAgICAgICAgICAgcmVkaXJlY3RfdXJpOiBcImh0dHA6Ly9sb2NhbGhvc3Q6ODAwMFwiLFxuICAgICAgICAgICAgICAgICAgICBzY29wZTogXCJwcm9maWxlIGVtYWlsXCIsXG4gICAgICAgICAgICAgICAgICAgIHN0YXRlOiBcImluaXRpYWxcIlxuICAgICAgICAgICAgICAgIH07XG5cbiAgICAgICAgICAgICAgICAkd2luZG93LnNlc3Npb25TdG9yYWdlLmxvZ2luVHlwZSA9IFwiZ29vZ2xlXCI7XG5cbiAgICAgICAgICAgICAgICAkd2luZG93Lm9wZW4ob2F1dGgyLnVybCArIFwiP2NsaWVudF9pZD1cIiArXG4gICAgICAgICAgICAgICAgICAgIG9hdXRoMi5jbGllbnRfaWQgKyBcIiZyZXNwb25zZV90eXBlPVwiICtcbiAgICAgICAgICAgICAgICAgICAgb2F1dGgyLnJlc3BvbnNlX3R5cGUgKyBcIiZyZWRpcmVjdF91cmk9XCIgK1xuICAgICAgICAgICAgICAgICAgICBvYXV0aDIucmVkaXJlY3RfdXJpICsgXCImc2NvcGU9XCIgK1xuICAgICAgICAgICAgICAgICAgICBvYXV0aDIuc2NvcGUgKyBcIiZzdGF0ZT1cIiArXG4gICAgICAgICAgICAgICAgICAgIG9hdXRoMi5zdGF0ZSwgXCJfc2VsZlwiKTtcbiAgICAgICAgICAgIH07XG5cbiAgICAgICAgICAgICRzY29wZS5nZXRHb29nbGVJbmZvID0gZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgICRodHRwLmdldChcImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL3BsdXMvdjEvcGVvcGxlL21lP2FjY2Vzc190b2tlbj1cIiArICRzY29wZS5hY2Nlc3NUb2tlbikuc3VjY2VzcyhmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZyhkYXRhKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH07XG5cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbmFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAkcm91dGVQcm92aWRlci53aGVuKCcvJywge1xuICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvaG9tZS9pbmRleC5odG1sJyxcbiAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdIb21lQ3RybCdcbiAgICAgICAgfSk7XG4gICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXIud2hlbignL2xvZ2luJywge1xuICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2xvZ2luL2luZGV4Lmh0bWwnLFxuICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdMb2dpbkN0cmwnXG4gICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdOYXZiYXJDdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJGh0dHAsICRsb2NhdGlvbikge1xuXG4gICAgICAgICAgICAkc2NvcGUuZ2V0UGF0aCA9IF9nZXRQYXRoO1xuXG4gICAgICAgICAgICBfbG9hZE1lbnUoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2xvYWRNZW51KCkge1xuICAgICAgICAgICAgICAgICRodHRwKHttZXRob2Q6ICdHRVQnLCB1cmw6ICdhcHAvdGVtcGxhdGUvbWVudS5qc29uJ30pLnN1Y2Nlc3MoZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLm1lbnVzID0gZGF0YTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2dldFBhdGgoKSB7XG4gICAgICAgICAgICAgICAgcmV0dXJuICcjJyArICRsb2NhdGlvbi5wYXRoKCk7XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5maWx0ZXIoJ3N1bU9mVmFsdWUnLCBmdW5jdGlvbiAoKSB7XG4gICAgICAgICAgICByZXR1cm4gZnVuY3Rpb24gKGRhdGEsIGtleSkge1xuICAgICAgICAgICAgICAgIGlmIChhbmd1bGFyLmlzVW5kZWZpbmVkKGRhdGEpICYmIGFuZ3VsYXIuaXNVbmRlZmluZWQoa2V5KSlcbiAgICAgICAgICAgICAgICAgICAgcmV0dXJuIDA7XG4gICAgICAgICAgICAgICAgdmFyIHN1bSA9IDA7XG5cbiAgICAgICAgICAgICAgICBhbmd1bGFyLmZvckVhY2goZGF0YSwgZnVuY3Rpb24gKHYsIGspIHtcbiAgICAgICAgICAgICAgICAgICAgc3VtID0gc3VtICsgcGFyc2VJbnQodltrZXldKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgICAgICByZXR1cm4gc3VtO1xuICAgICAgICAgICAgfTtcbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0NvbmV4YW9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgJHJvdXRlUGFyYW1zLCBDbGllbnRlLCBDb25leGFvLCBDb250cmF0bywgTWlrcm90aWspIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcbiAgICAgICAgICAgICAgICBfY2FycmVnYXJDb25leGFvKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmNvbnRyYXRvID0gQ29udHJhdG8uYnVzY2FyUG9yQ2xpZW50ZSh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLm1pa3JvdGlrcyA9IE1pa3JvdGlrLnF1ZXJ5KCk7XG5cbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2NhcnJlZ2FyQ29uZXhhbygpIHtcbiAgICAgICAgICAgICAgICBDb25leGFvLmJ1c2NhclBvckNsaWVudGUoe2NsaWVudGVJZDogJHJvdXRlUGFyYW1zLmNsaWVudGVJZH0sIGZ1bmN0aW9uIChjb25leGFvKSB7XG4gICAgICAgICAgICAgICAgICAgIGlmIChjb25leGFvLmlkKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhbyA9IGNvbmV4YW87XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBfY3JpYXJOb3ZhQ29uZXhhbygpO1xuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9jcmlhck5vdmFDb25leGFvKCkge1xuICAgICAgICAgICAgICAgIENsaWVudGUuZ2V0KHtpZDogJHJvdXRlUGFyYW1zLmNsaWVudGVJZH0sIGZ1bmN0aW9uIChjbGllbnRlKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5jb25leGFvID0ge1xuICAgICAgICAgICAgICAgICAgICAgICAgY2xpZW50ZTogY2xpZW50ZVxuICAgICAgICAgICAgICAgICAgICB9O1xuXG5cbiAgICAgICAgICAgICAgICAgICAgdmFyIGluZGV4T2ZTcGFjZSA9IGNsaWVudGUubm9tZS5pbmRleE9mKCcgJyk7XG4gICAgICAgICAgICAgICAgICAgIGlmIChpbmRleE9mU3BhY2UgPiAwKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhby5ub21lID0gJHNjb3BlLmNvbmV4YW8uc2VuaGEgPSBjbGllbnRlLm5vbWUuc3BsaWNlKDAsIGluZGV4T2ZTcGFjZSkgKyBjbGllbnRlLmlkO1xuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNvbmV4YW8ubm9tZSA9ICRzY29wZS5jb25leGFvLnNlbmhhID0gY2xpZW50ZS5ub21lICsgY2xpZW50ZS5pZDtcbiAgICAgICAgICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICAgICAgICAgIENvbmV4YW8uYnVzY2FySXBMaXZyZSh7fSwgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jb25leGFvLmlwID0gZGF0YS5pcDtcbiAgICAgICAgICAgICAgICAgICAgfSk7XG5cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUoY29uZXhhbykge1xuICAgICAgICAgICAgICAgIENvbmV4YW8uc2F2ZShjb25leGFvLCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhbyA9IGRhdGE7XG4gICAgICAgICAgICAgICAgICAgIF9zdWNlc3NvKCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9yZW1vdmUoY29uZXhhbykge1xuICAgICAgICAgICAgICAgIENvbmV4YW8ucmVtb3ZlKHtpZDogY29uZXhhby5pZH0sIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgIF9pbml0KCk7XG4gICAgICAgICAgICAgICAgICAgIF9zdWNlc3NvKCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zdWNlc3NvKCl7XG4gICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7dGl0bGU6ICdTdWNlc3NvJywgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnfV07XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdDb250cmF0b0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsIENsaWVudGUsIENvbnRyYXRvLCBQbGFubywgRXF1aXBhbWVudG8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcbiAgICAgICAgICAgICAgICAkc2NvcGUuaG9qZSA9IG5ldyBEYXRlKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnBsYW5vcyA9IFBsYW5vLnF1ZXJ5KCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvc0luc3RhbGFjYW8gPSBFcXVpcGFtZW50by5kaXNwb25pdmVpc1BhcmFJbnN0YWxhY2FvKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvc1dpZmkgPSBFcXVpcGFtZW50by5kaXNwb25pdmVpc1BhcmFXaWZpKCk7XG4gICAgICAgICAgICAgICAgX2NhcnJlZ2FyQ29udHJhdG8oKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2NhcnJlZ2FyQ29udHJhdG8oKSB7XG4gICAgICAgICAgICAgICAgQ29udHJhdG8uYnVzY2FyUG9yQ2xpZW50ZSh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSwgZnVuY3Rpb24gKGNvbnRyYXRvKSB7XG4gICAgICAgICAgICAgICAgICAgIGlmIChjb250cmF0by5pZCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNvbnRyYXRvID0gY29udHJhdG87XG4gICAgICAgICAgICAgICAgICAgICAgICBpZiAoY29udHJhdG8uZXF1aXBhbWVudG8pIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG9zSW5zdGFsYWNhby5wdXNoKGNvbnRyYXRvLmVxdWlwYW1lbnRvKTtcbiAgICAgICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICAgICAgICAgIGlmIChjb250cmF0by5lcXVpcGFtZW50b1dpZmkpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG9zV2lmaS5wdXNoKGNvbnRyYXRvLmVxdWlwYW1lbnRvV2lmaSk7XG4gICAgICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29udHJhdG8gPSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgY2xpZW50ZTogQ2xpZW50ZS5nZXQoe2lkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSlcbiAgICAgICAgICAgICAgICAgICAgICAgIH07XG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUoY29udHJhdG8pIHtcbiAgICAgICAgICAgICAgICBDb250cmF0by5zYXZlKGNvbnRyYXRvLCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29udHJhdG8gPSBkYXRhO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0NvbnRyYXRvIGRlIG7Dum1lcm8gJyArIGRhdGEuaWQgKyAnIGZvaSBzYWx2by4nLFxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnXG4gICAgICAgICAgICAgICAgICAgIH1dO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfcmVtb3ZlKGNvbnRyYXRvKSB7XG4gICAgICAgICAgICAgICAgQ29udHJhdG8ucmVtb3ZlKHtpZDogY29udHJhdG8uaWR9LCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICBfaW5pdCgpO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0NvbnRyYXRvIGRlIG7Dum1lcm8gJyArIGNvbnRyYXRvLmlkICsgJyBmb2kgcmVtb3ZpZG8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ1JldG9ybm9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgVXBsb2FkKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5lbnZpYXIgPSBfZW52aWFyO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfZW52aWFyKGZpbGVzKSB7XG4gICAgICAgICAgICAgICAgZmlsZXMuZm9yRWFjaChmdW5jdGlvbiAoZmlsZSkge1xuICAgICAgICAgICAgICAgICAgICBVcGxvYWQudXBsb2FkKHtcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9yZXRvcm5vcy91cGxvYWQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgZGF0YToge2ZpbGU6IGZpbGV9XG4gICAgICAgICAgICAgICAgICAgIH0pLnRoZW4oZnVuY3Rpb24gKHJlc3ApIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIGZpbGUucmV0b3JubyA9IHJlc3AuZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgfSwgZnVuY3Rpb24gKHJlc3ApIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKFwiVEVTVEVTVEVcIik7XG4gICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZyhyZXNwKTtcbiAgICAgICAgICAgICAgICAgICAgICAgIGZpbGUuZXJyb3IgPSByZXNwLmRhdGEubWVzc2FnZTtcbiAgICAgICAgICAgICAgICAgICAgfSwgZnVuY3Rpb24gKGV2dCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgZmlsZS5wcm9ncmVzcyA9IHBhcnNlSW50KDEwMC4wICogZXZ0LmxvYWRlZCAvIGV2dC50b3RhbCk7XG4gICAgICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMjkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5kaXJlY3RpdmUoJ3NpY29iYUFsZXJ0JywgZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgcmV0dXJuIHtcbiAgICAgICAgICAgICAgICByZXN0cmljdDogJ0UnLFxuICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL2RpcmVjdGl2ZXMvYWxlcnQvYWxlcnQuaHRtbCdcbiAgICAgICAgICAgIH07XG4gICAgICAgIH0pO1xuXG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvY2xpZW50ZScsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL2NsaWVudGUvY2xpZW50ZS5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ0NsaWVudGVDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9jbGllbnRlLzppZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL2NsaWVudGUvY2xpZW50ZS5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ0NsaWVudGVDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9jbGllbnRlcycsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL2NsaWVudGUvY2xpZW50ZS5saXN0Lmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ2xpZW50ZUxpc3RDdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvY29uZXhhby9jbGllbnRlLzpjbGllbnRlSWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2NvbWVyY2lhbC9jb25leGFvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ29uZXhhb0N0cmwnXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy9jb250cmF0by9jbGllbnRlLzpjbGllbnRlSWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2NvbWVyY2lhbC9jb250cmF0by5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ0NvbnRyYXRvQ3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXJcbiAgICAgICAgICAgICAgICAud2hlbignL3BsYW5vJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9jb21lcmNpYWwvcGxhbm8vcGxhbm8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdQbGFub0N0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3BsYW5vLzppZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL3BsYW5vL3BsYW5vLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnUGxhbm9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9wbGFub3MnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2NvbWVyY2lhbC9wbGFuby9wbGFuby5saXN0Lmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnUGxhbm9MaXN0Q3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxMy8wMS8xNi5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvZXF1aXBhbWVudG9zJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9lc3RvcXVlL2VxdWlwYW1lbnRvL2VxdWlwYW1lbnRvLmxpc3QuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdFcXVpcGFtZW50b0xpc3RDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9lcXVpcGFtZW50bycsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZXN0b3F1ZS9lcXVpcGFtZW50by9lcXVpcGFtZW50by5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ0VxdWlwYW1lbnRvQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvZXF1aXBhbWVudG8vOmlkJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9lc3RvcXVlL2VxdWlwYW1lbnRvL2VxdWlwYW1lbnRvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnRXF1aXBhbWVudG9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDA5LzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy9yZXRvcm5vJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9maW5hbmNlaXJvL3JldG9ybm8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdSZXRvcm5vQ3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAwOS8wMS8xNi5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvdGl0dWxvcy9jbGllbnRlLzpjbGllbnRlSWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2ZpbmFuY2Vpcm8vdGl0dWxvL3RpdHVsby5saXN0Lmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnVGl0dWxvTGlzdEN0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3RpdHVsby86aWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2ZpbmFuY2Vpcm8vdGl0dWxvL3RpdHVsby5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ1RpdHVsb0N0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3RpdHVsby9jbGllbnRlLzpjbGllbnRlSWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2ZpbmFuY2Vpcm8vdGl0dWxvL3RpdHVsby5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ1RpdHVsb0N0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3RpdHVsby9jbGllbnRlLzpjbGllbnRlSWQvY2FybmUnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2ZpbmFuY2Vpcm8vdGl0dWxvL2Nhcm5lLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ2FybmVDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy90aXR1bG9zL3JlbGF0b3JpbycsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZmluYW5jZWlyby90aXR1bG8vcmVsYXRvcmlvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnUmVsYXRvcmlvVGl0dWxvQ3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXJcbiAgICAgICAgICAgICAgICAud2hlbignL21pa3JvdGlrJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9wcm92ZWRvci9taWtyb3Rpay9taWtyb3Rpay5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ01pa3JvdGlrQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvbWlrcm90aWsvOmlkJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9wcm92ZWRvci9taWtyb3Rpay9taWtyb3Rpay5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ01pa3JvdGlrQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvbWlrcm90aWtzJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9wcm92ZWRvci9taWtyb3Rpay9taWtyb3Rpay5saXN0Lmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnTWlrcm90aWtMaXN0Q3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxOS8xMi8xNS5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ0NlcCcsIFsnJHJlc291cmNlJywgZnVuY3Rpb24gKCRyZXNvdXJjZSkge1xuICAgICAgICAgICAgcmV0dXJuICRyZXNvdXJjZSgnaHR0cDovL3ZpYWNlcC5jb20uYnIvd3MvOmNlcC9qc29uJywge2NlcDogJ0BjZXAnfSxcbiAgICAgICAgICAgICAgICB7fSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMTkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5mYWN0b3J5KCdDbGllbnRlJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvY2xpZW50ZXMvOmlkJywge2lkOiAnQGlkJ30sXG4gICAgICAgICAgICAgICAge1xuICAgICAgICAgICAgICAgICAgICBzZW1UaXR1bG86IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY2xpZW50ZXMvc2VtX3RpdHVsbycsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIHVsdGltb3NBbHRlcmFkb3M6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY2xpZW50ZXMvdWx0aW1vc19hbHRlcmFkb3MnLFxuICAgICAgICAgICAgICAgICAgICAgICAgaXNBcnJheTogdHJ1ZVxuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMTkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5mYWN0b3J5KCdDb25leGFvJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvY29uZXhvZXMvOmlkJywge2lkOiAnQGlkJ30sXG4gICAgICAgICAgICAgICAge1xuICAgICAgICAgICAgICAgICAgICBidXNjYXJQb3JDbGllbnRlOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL2NvbmV4b2VzL2NsaWVudGUvOmNsaWVudGVJZCcsXG4gICAgICAgICAgICAgICAgICAgICAgICBwYXJhbXM6IHtpZDogJ0BpZCd9XG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhclBvcklwOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL2NvbmV4b2VzL2lwLzppcCcsXG4gICAgICAgICAgICAgICAgICAgICAgICBwYXJhbXM6IHtpcDogJ0BpcCd9XG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhcklwTGl2cmU6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY29uZXhvZXMvaXAvbGl2cmUnXG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxOS8xMi8xNS5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ0NvbnRyYXRvJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvY29udHJhdG9zLzppZCcsIHtpZDogJ0BpZCd9LFxuICAgICAgICAgICAgICAgIHtcbiAgICAgICAgICAgICAgICAgICAgbm92b3M6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY29udHJhdG9zL25vdm9zJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgYnVzY2FyUG9yQ2xpZW50ZToge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9jb250cmF0b3MvY2xpZW50ZS86Y2xpZW50ZUlkJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHBhcmFtczoge2NsaWVudGVJZDogJ0BjbGllbnRlSWQnfVxuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMDEvMDEvMTYuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5mYWN0b3J5KCdQbGFubycsIFsnJHJlc291cmNlJywgZnVuY3Rpb24gKCRyZXNvdXJjZSkge1xuICAgICAgICAgICAgcmV0dXJuICRyZXNvdXJjZSgnYXBpL3BsYW5vcy86aWQnLCB7aWQ6ICdAaWQnfSxcbiAgICAgICAgICAgICAgICB7fSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMTkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5mYWN0b3J5KCdFcXVpcGFtZW50bycsIFsnJHJlc291cmNlJywgZnVuY3Rpb24gKCRyZXNvdXJjZSkge1xuICAgICAgICAgICAgcmV0dXJuICRyZXNvdXJjZSgnYXBpL2VxdWlwYW1lbnRvcy86aWQnLCB7aWQ6ICdAaWQnfSxcbiAgICAgICAgICAgICAgICB7XG4gICAgICAgICAgICAgICAgICAgIGRpc3Bvbml2ZWlzUGFyYUluc3RhbGFjYW86IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvZXF1aXBhbWVudG9zL2luc3RhbGFjYW8vZGlzcG9uaXZlaXMnLFxuICAgICAgICAgICAgICAgICAgICAgICAgaXNBcnJheTogdHJ1ZVxuICAgICAgICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgICAgICAgICBkaXNwb25pdmVpc1BhcmFXaWZpOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL2VxdWlwYW1lbnRvcy93aWZpL2Rpc3Bvbml2ZWlzJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDE5LzEyLzE1LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnVGl0dWxvJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvdGl0dWxvcy86aWQnLCB7aWQ6ICdAaWQnfSxcbiAgICAgICAgICAgICAgICB7XG4gICAgICAgICAgICAgICAgICAgICd2ZW5jaWRvcyc6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvdGl0dWxvcy92ZW5jaWRvcycsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgICdub3ZvJzoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS90aXR1bG9zL2NsaWVudGUvOmNsaWVudGVJZC9ub3ZhJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHBhcmFtczoge2NsaWVudGVJZDogJ0BjbGllbnRlSWQnfVxuICAgICAgICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgICAgICAgICBidXNjYXJQb3JDbGllbnRlOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL3RpdHVsb3MvY2xpZW50ZS86Y2xpZW50ZUlkJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHBhcmFtczoge2NsaWVudGVJZDogJ0BjbGllbnRlSWQnfSxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgY3JpYXJDYXJuZToge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnUE9TVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvdGl0dWxvcy9jYXJuZScsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhclBvckRhdGFPY29ycmVuY2lhOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL3RpdHVsb3Mvb2NvcnJlbmNpYScsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhclBvckRhdGFWZW5jaW1lbnRvOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL3RpdHVsb3MvdmVuY2ltZW50bycsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAwOS8wMS8xNi5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ01pa3JvdGlrJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvbWlrcm90aWtzLzppZCcsIHtpZDogJ0BpZCd9LFxuICAgICAgICAgICAgICAgIHt9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdDbGllbnRlQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb290U2NvcGUsICRyb3V0ZVBhcmFtcywgQ2xpZW50ZSwgQ2VwLCBDb250cmF0bykge1xuXG4gICAgICAgICAgICAkc2NvcGUuc2F2ZSA9IF9zYXZlO1xuICAgICAgICAgICAgJHNjb3BlLmJ1c2NhckVuZGVyZWNvUG9yQ2VwID0gX2J1c2NhckVuZGVyZWNvUG9yQ2VwO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZSA9IHtzdGF0dXM6ICdBVElWTyd9O1xuICAgICAgICAgICAgICAgICRzY29wZS5ob2plID0gbmV3IERhdGUoKTtcblxuICAgICAgICAgICAgICAgIGlmICgkcm91dGVQYXJhbXMuaWQpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGUgPSBDbGllbnRlLmdldCh7aWQ6ICRyb3V0ZVBhcmFtcy5pZH0pO1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29udHJhdG8gPSBDb250cmF0by5idXNjYXJQb3JDbGllbnRlKHtjbGllbnRlSWQ6ICRyb3V0ZVBhcmFtcy5pZH0pO1xuICAgICAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfc2F2ZShjbGllbnRlKSB7XG4gICAgICAgICAgICAgICAgQ2xpZW50ZS5zYXZlKGNsaWVudGUsIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlID0gZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7dGl0bGU6ICdTdWNlc3NvOicsIGJvZHk6ICdDbGllbnRlICcgKyBkYXRhLm5vbWUgKyAnIGZvaSBzYWx2by4nLCB0eXBlOiAnYWxlcnQtc3VjY2Vzcyd9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckVuZGVyZWNvUG9yQ2VwKGNlcCwgZm9ybSkge1xuICAgICAgICAgICAgICAgIGlmIChjZXApIHtcbiAgICAgICAgICAgICAgICAgICAgQ2VwLmdldCh7Y2VwOiBjZXB9LCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAgICAgaWYgKGRhdGEuZXJybykge1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGZvcm0uY2VwLiRlcnJvci5ub3RGb3VuZCA9IHRydWU7XG4gICAgICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlLmVuZGVyZWNvLmNlcCA9IGRhdGEuY2VwO1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlLmVuZGVyZWNvLmxvZ3JhZG91cm8gPSBkYXRhLmxvZ3JhZG91cm87XG5cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZS5lbmRlcmVjby5iYWlycm8gPSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIFwibm9tZVwiOiBkYXRhLmJhaXJybyxcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXCJjaWRhZGVcIjoge1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXCJub21lXCI6IGRhdGEubG9jYWxpZGFkZSxcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIFwiZXN0YWRvXCI6IHtcInVmXCI6IGRhdGEudWZ9XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB9O1xuICAgICAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdDbGllbnRlTGlzdEN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCBDbGllbnRlLCBDb25leGFvKSB7XG4gICAgICAgICAgICAkc2NvcGUuYnVzY2FyUG9yTm9tZSA9IF9idXNjYXJQb3JOb21lO1xuICAgICAgICAgICAgJHNjb3BlLmJ1c2NhclBvcklwID0gX2J1c2NhclBvcklwO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICBDbGllbnRlLnVsdGltb3NBbHRlcmFkb3MoZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVzID0gZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgX2J1c2NhckNvbmV4b2VzKCRzY29wZS5jbGllbnRlcyk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9idXNjYXJQb3JOb21lKG5vbWUpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZXMgPSBDbGllbnRlLnF1ZXJ5KHtub21lOiBub21lfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9idXNjYXJQb3JJcChpcCkge1xuICAgICAgICAgICAgICAgIENvbmV4YW8uYnVzY2FyUG9ySXAoe2lwOiBpcH0sIGZ1bmN0aW9uIChjb25leGFvKSB7XG4gICAgICAgICAgICAgICAgICAgIGlmIChjb25leGFvKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBjb25leGFvLmNsaWVudGUuY29uZXhhbyA9IGNvbmV4YW87XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZXMgPSBbY29uZXhhby5jbGllbnRlXTtcbiAgICAgICAgICAgICAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlcyA9IFtdO1xuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9idXNjYXJDb25leG9lcyhjbGllbnRlcykge1xuICAgICAgICAgICAgICAgIGNsaWVudGVzLmZvckVhY2goZnVuY3Rpb24gKGNsaWVudGUpIHtcbiAgICAgICAgICAgICAgICAgICAgY2xpZW50ZS5jb25leGFvID0gQ29uZXhhby5idXNjYXJQb3JDbGllbnRlKHtjbGllbnRlSWQ6IGNsaWVudGUuaWR9KTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignUGxhbm9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgJHJvdXRlUGFyYW1zLCBQbGFubykge1xuXG4gICAgICAgICAgICAkc2NvcGUuc2F2ZSA9IF9zYXZlO1xuICAgICAgICAgICAgJHNjb3BlLnJlbW92ZSA9IF9yZW1vdmU7XG5cbiAgICAgICAgICAgIF9pbml0KCk7XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9pbml0KCkge1xuICAgICAgICAgICAgICAgIGlmICgkcm91dGVQYXJhbXMuaWQpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLnBsYW5vID0gUGxhbm8uZ2V0KHtpZDogJHJvdXRlUGFyYW1zLmlkfSk7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfc2F2ZShwbGFubykge1xuICAgICAgICAgICAgICAgIFBsYW5vLnNhdmUocGxhbm8sIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5wbGFubyA9IGRhdGE7XG4gICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMgPSBbe1xuICAgICAgICAgICAgICAgICAgICAgICAgdGl0bGU6ICdTdWNlc3NvOicsXG4gICAgICAgICAgICAgICAgICAgICAgICBib2R5OiAnUGxhbm8gJyArIGRhdGEubm9tZSArICcgZm9pIHNhbHZvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9yZW1vdmUocGxhbm8pIHtcbiAgICAgICAgICAgICAgICBQbGFuby5yZW1vdmUoe2lkOiBwbGFuby5pZH0sIGZ1bmN0aW9uICgpIHtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdQbGFubyAnICsgcGxhbm8ubm9tZSArICcgZm9pIHJlbW92aWRvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5wbGFubyA9IHt9O1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignUGxhbm9MaXN0Q3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsIFBsYW5vKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5wbGFub3MgPSBQbGFuby5xdWVyeSgpO1xuXG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdFcXVpcGFtZW50b0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsIEVxdWlwYW1lbnRvKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5zYXZlID0gX3NhdmU7XG4gICAgICAgICAgICAkc2NvcGUucmVtb3ZlID0gX3JlbW92ZTtcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgaWYgKCRyb3V0ZVBhcmFtcy5pZCkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG8gPSBFcXVpcGFtZW50by5nZXQoe2lkOiAkcm91dGVQYXJhbXMuaWR9KTtcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG8gPSB7dGlwbzogXCJJTlNUQUxBQ0FPXCIsIHN0YXR1czogXCJPS1wifTtcbiAgICAgICAgICAgICAgICB9XG5cbiAgICAgICAgICAgICAgICAkc2NvcGUudGlwb3MgPSBbXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogXCJJTlNUQUxBQ0FPXCIsIGRlc2NyaWNhbzogJ0luc3RhbGHDp8Ojbyd9LFxuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6IFwiV0lGSVwiLCBkZXNjcmljYW86ICdXaWZpJ31cbiAgICAgICAgICAgICAgICBdO1xuXG4gICAgICAgICAgICAgICAgJHNjb3BlLnN0YXR1c0xpc3QgPSBbXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogXCJPS1wiLCBkZXNjcmljYW86ICdPSyd9LFxuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6IFwiREVGRUlUT1wiLCBkZXNjcmljYW86ICdEZWZlaXRvJ31cbiAgICAgICAgICAgICAgICBdO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfc2F2ZShlcXVpcGFtZW50bykge1xuICAgICAgICAgICAgICAgIEVxdWlwYW1lbnRvLnNhdmUoZXF1aXBhbWVudG8sIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5lcXVpcGFtZW50byA9IGRhdGE7XG4gICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMgPSBbe1xuICAgICAgICAgICAgICAgICAgICAgICAgdGl0bGU6ICdTdWNlc3NvOicsXG4gICAgICAgICAgICAgICAgICAgICAgICBib2R5OiAnRXF1aXBhbWVudG8gJyArIGRhdGEubWFyY2EgKyAnICcgKyBkYXRhLm1vZGVsbyArICcgZm9pIHNhbHZvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9yZW1vdmUoZXF1aXBhbWVudG8pIHtcbiAgICAgICAgICAgICAgICBFcXVpcGFtZW50by5yZW1vdmUoe2lkOiBlcXVpcGFtZW50by5pZH0sIGZ1bmN0aW9uICgpIHtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdFcXVpcGFtZW50byAnICsgZXF1aXBhbWVudG8ubWFyY2EgKyAnICcgKyBlcXVpcGFtZW50by5tb2RlbG8gKyAnIGZvaSByZW1vdmlkby4nLFxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnXG4gICAgICAgICAgICAgICAgICAgIH1dO1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG8gPSB7dGlwbzogXCJJTlNUQUxBQ0FPXCIsIHN0YXR1czogXCJPS1wifTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0VxdWlwYW1lbnRvTGlzdEN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCBFcXVpcGFtZW50bykge1xuICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvcyA9IEVxdWlwYW1lbnRvLnF1ZXJ5KCk7XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdDYXJuZUN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsICRsb2NhdGlvbiwgVGl0dWxvLCBDb250cmF0bykge1xuXG4gICAgICAgICAgICAkc2NvcGUuc2F2ZSA9IF9zYXZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcblxuICAgICAgICAgICAgICAgIFRpdHVsby5ub3ZvKHtjbGllbnRlSWQ6ICRyb3V0ZVBhcmFtcy5jbGllbnRlSWR9LCBmdW5jdGlvbiAobm92b1RpdHVsbykge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZSA9IG5vdm9UaXR1bG8uY2xpZW50ZTtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNhcm5lID0ge1xuICAgICAgICAgICAgICAgICAgICAgICAgY2xpZW50ZUlkOiBub3ZvVGl0dWxvLmNsaWVudGUuaWQsXG4gICAgICAgICAgICAgICAgICAgICAgICBtb2RhbGlkYWRlOiBub3ZvVGl0dWxvLm1vZGFsaWRhZGUsXG4gICAgICAgICAgICAgICAgICAgICAgICB2YWxvcjogbm92b1RpdHVsby52YWxvcixcbiAgICAgICAgICAgICAgICAgICAgICAgIGRhdGFJbmljaW86IG5vdm9UaXR1bG8uZGF0YVZlbmNpbWVudG9cbiAgICAgICAgICAgICAgICAgICAgfTtcbiAgICAgICAgICAgICAgICB9KTtcblxuICAgICAgICAgICAgICAgICRzY29wZS5tb2RhbGlkYWRlcyA9IFtcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiAxNCwgZGVzY3JpY2FvOiAnUmVnaXN0cmFkbyd9LFxuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6IDI0LCBkZXNjcmljYW86ICdTZW0gUmVnaXN0cm8nfVxuICAgICAgICAgICAgICAgIF07XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKGNhcm5lKSB7XG4gICAgICAgICAgICAgICAgVGl0dWxvLmNyaWFyQ2FybmUoY2FybmUsIGZ1bmN0aW9uICh0aXR1bG9zKSB7XG4gICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMgPSBbe1xuICAgICAgICAgICAgICAgICAgICAgICAgdGl0bGU6ICdTdWNlc3NvOicsXG4gICAgICAgICAgICAgICAgICAgICAgICBib2R5OiAnQ3JpYWRvIGNhcm7DqiBjb20gJyArIHRpdHVsb3MubGVuZ3RoICsgJyB0aXR1bG9zKHMpIGRlICcgKyBjYXJuZS5ib2xldG9JbmljaW8gKyAnIGF0w6kgJyArIGNhcm5lLmJvbGV0b0ZpbSxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICAgICAgJGxvY2F0aW9uLnBhdGgoJy90aXR1bG9zL2NsaWVudGUvJyArIGNhcm5lLmNsaWVudGVJZCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdSZWxhdG9yaW9UaXR1bG9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgVGl0dWxvKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5nZXRTdGF0dXNDbGFzcyA9IF9nZXRTdGF0dXNDbGFzcztcbiAgICAgICAgICAgICRzY29wZS5jb25zdWx0YXJQb3JEYXRhT2NvcnJlbmNpYSA9IF9jb25zdWx0YXJQb3JEYXRhT2NvcnJlbmNpYTtcbiAgICAgICAgICAgICRzY29wZS5jb25zdWx0YXJQb3JEYXRhVmVuY2ltZW50byA9IF9jb25zdWx0YXJQb3JEYXRhVmVuY2ltZW50bztcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnBhcmFtcyA9IHtcbiAgICAgICAgICAgICAgICAgICAgaW5pY2lvOiBuZXcgRGF0ZSgpLFxuICAgICAgICAgICAgICAgICAgICBmaW06IG5ldyBEYXRlKCksXG4gICAgICAgICAgICAgICAgICAgIHBvckRhdGFPY29ycmVuY2lhOiB0cnVlXG4gICAgICAgICAgICAgICAgfTtcblxuICAgICAgICAgICAgICAgICRzY29wZS5zdGF0dXNMaXN0ID0gW1xuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6ICdQQUdPX05PX0JPTEVUTycsIGRlc2NyaWNhbzogJ1BhZ28gbm8gYm9sZXRvJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogJ1BFTkRFTlRFJywgZGVzY3JpY2FvOiAnUGVuZGVudGUnfSxcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiAnQkFJWEFfTUFOVUFMJywgZGVzY3JpY2FvOiAnQmFpeGEgbWFudWFsJ31cbiAgICAgICAgICAgICAgICBdO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfY29uc3VsdGFyUG9yRGF0YU9jb3JyZW5jaWEocGFyYW1zKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsb3MgPSBUaXR1bG8uYnVzY2FyUG9yRGF0YU9jb3JyZW5jaWEoe1xuICAgICAgICAgICAgICAgICAgICBpbmljaW86IF9mb3JtYXQocGFyYW1zLmluaWNpbyksXG4gICAgICAgICAgICAgICAgICAgIGZpbTogX2Zvcm1hdChwYXJhbXMuZmltKSxcbiAgICAgICAgICAgICAgICAgICAgc3RhdHVzOiBwYXJhbXMuc3RhdHVzXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9jb25zdWx0YXJQb3JEYXRhVmVuY2ltZW50byhwYXJhbXMpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUudGl0dWxvcyA9IFRpdHVsby5idXNjYXJQb3JEYXRhVmVuY2ltZW50byh7XG4gICAgICAgICAgICAgICAgICAgIGluaWNpbzogX2Zvcm1hdChwYXJhbXMuaW5pY2lvKSxcbiAgICAgICAgICAgICAgICAgICAgZmltOiBfZm9ybWF0KHBhcmFtcy5maW0pLFxuICAgICAgICAgICAgICAgICAgICBzdGF0dXM6IHBhcmFtcy5zdGF0dXNcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2dldFN0YXR1c0NsYXNzKHN0YXR1cykge1xuICAgICAgICAgICAgICAgIHJldHVybiBzdGF0dXMgPT09ICdQRU5ERU5URScgPyAnbGFiZWwtd2FybmluZycgOiAnbGFiZWwtc3VjY2Vzcyc7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9mb3JtYXQoZGF0ZSkge1xuICAgICAgICAgICAgICAgIGlmIChkYXRlKSB7XG4gICAgICAgICAgICAgICAgICAgIHZhciB5ZWFyID0gMTkwMCArIGRhdGUuZ2V0WWVhcigpO1xuICAgICAgICAgICAgICAgICAgICB2YXIgbW9udGggPSBkYXRlLmdldE1vbnRoKCkgKyAxO1xuICAgICAgICAgICAgICAgICAgICB2YXIgZGF5ID0gZGF0ZS5nZXREYXRlKCk7XG4gICAgICAgICAgICAgICAgICAgIG1vbnRoID0gbW9udGggPCAxMCA/ICcwJyArIG1vbnRoIDogbW9udGg7XG4gICAgICAgICAgICAgICAgICAgIGRheSA9IGRheSA8IDEwID8gJzAnICsgZGF5IDogZGF5O1xuICAgICAgICAgICAgICAgICAgICByZXR1cm4geWVhciArICctJyArIG1vbnRoICsgJy0nICsgZGF5O1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH1cblxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignVGl0dWxvQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb290U2NvcGUsICRyb3V0ZVBhcmFtcywgJGxvY2F0aW9uLCBUaXR1bG8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuICAgICAgICAgICAgJHNjb3BlLmF0dWFsaXphclZhbG9yZXMgPSBfYXR1YWxpemFyVmFsb3JlcztcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgaWYgKCRyb3V0ZVBhcmFtcy5jbGllbnRlSWQpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVJZCA9ICRyb3V0ZVBhcmFtcy5jbGllbnRlSWQ7XG4gICAgICAgICAgICAgICAgICAgIF9ub3ZhVGl0dWxvKCk7XG4gICAgICAgICAgICAgICAgfSBlbHNlIGlmICgkcm91dGVQYXJhbXMuaWQpIHtcbiAgICAgICAgICAgICAgICAgICAgX2J1c2NhclBvcklkKCRyb3V0ZVBhcmFtcy5pZCk7XG4gICAgICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICAgICAgJHNjb3BlLm1vZGFsaWRhZGVzID0gW1xuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6IDE0LCBkZXNjcmljYW86ICdSZWdpc3RyYWRvJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogMjQsIGRlc2NyaWNhbzogJ1NlbSBSZWdpc3Rybyd9XG4gICAgICAgICAgICAgICAgXTtcbiAgICAgICAgICAgICAgICAkc2NvcGUuc3RhdHVzTGlzdCA9IFtcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiAnUEVOREVOVEUnLCBkZXNjcmljYW86ICdQZW5kZW50ZSd9LFxuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6ICdCQUlYQV9NQU5VQUwnLCBkZXNjcmljYW86ICdCYWl4YSBtYW51YWwnfVxuICAgICAgICAgICAgICAgIF07XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9ub3ZhVGl0dWxvKCkge1xuICAgICAgICAgICAgICAgICRzY29wZS50aXR1bG8gPSBUaXR1bG8ubm92byh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9idXNjYXJQb3JJZChpZCkge1xuICAgICAgICAgICAgICAgIFRpdHVsby5nZXQoe2lkOiBpZH0sIGZ1bmN0aW9uICh0aXR1bG8pIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsbyA9IHRpdHVsbztcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVJZCA9IHRpdHVsby5jbGllbnRlLmlkO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYXR1YWxpemFyVmFsb3Jlcyh0aXR1bG8pIHtcbiAgICAgICAgICAgICAgICBpZiAodGl0dWxvLnN0YXR1cyA9PT0gJ0JBSVhBX01BTlVBTCcpIHtcbiAgICAgICAgICAgICAgICAgICAgdGl0dWxvLnZhbG9yUGFnbyA9IHRpdHVsby52YWxvciAtIHRpdHVsby5kZXNjb250bztcbiAgICAgICAgICAgICAgICAgICAgaWYgKCF0aXR1bG8uZGF0YU9jb3JyZW5jaWEpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdHVsby5kYXRhT2NvcnJlbmNpYSA9IG5ldyBEYXRlKCk7XG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICB0aXR1bG8udmFsb3JQYWdvID0gMDtcbiAgICAgICAgICAgICAgICAgICAgdGl0dWxvLmRhdGFPY29ycmVuY2lhID0gbnVsbDtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKHRpdHVsbykge1xuICAgICAgICAgICAgICAgIFRpdHVsby5zYXZlKHRpdHVsbywgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdDcmlhZG8gdGl0dWxvIGRlIG7Dum1lcm8gJyArIGRhdGEuaWQsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgICAgIF92b2x0YXIoKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3JlbW92ZSh0aXR1bG8pIHtcbiAgICAgICAgICAgICAgICBUaXR1bG8ucmVtb3ZlKHtpZDogdGl0dWxvLmlkfSwgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgX2luaXQoKTtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc28nLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ1JlbW92aWRvIHRpdHVsbyBkZSBuw7ptZXJvICcgKyBkYXRhLmlkLFxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnXG4gICAgICAgICAgICAgICAgICAgIH1dO1xuICAgICAgICAgICAgICAgICAgICBfdm9sdGFyKCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF92b2x0YXIoKSB7XG4gICAgICAgICAgICAgICAgJGxvY2F0aW9uLnBhdGgoJy90aXR1bG9zL2NsaWVudGUvJyArICRzY29wZS5jbGllbnRlSWQpO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignVGl0dWxvTGlzdEN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm91dGVQYXJhbXMsIFRpdHVsbywgQ2xpZW50ZSkge1xuXG4gICAgICAgICAgICAkc2NvcGUuZ2V0U3RhdHVzQ2xhc3MgPSBfZ2V0U3RhdHVzQ2xhc3M7XG5cbiAgICAgICAgICAgIF9pbml0KCk7XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9pbml0KCkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlID0gQ2xpZW50ZS5nZXQoe2lkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsb3MgPSBUaXR1bG8uYnVzY2FyUG9yQ2xpZW50ZSh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9nZXRTdGF0dXNDbGFzcyhzdGF0dXMpIHtcbiAgICAgICAgICAgICAgICByZXR1cm4gc3RhdHVzID09PSAnUEVOREVOVEUnID8gJ2xhYmVsLXdhcm5pbmcnIDogJ2xhYmVsLXN1Y2Nlc3MnO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdNaWtyb3Rpa0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsIE1pa3JvdGlrKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5zYXZlID0gX3NhdmU7XG4gICAgICAgICAgICAkc2NvcGUucmVtb3ZlID0gX3JlbW92ZTtcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgaWYgKCRyb3V0ZVBhcmFtcy5pZCkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUubWlrcm90aWsgPSBNaWtyb3Rpay5nZXQoe2lkOiAkcm91dGVQYXJhbXMuaWR9KTtcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUubWlrcm90aWsgPSB7cG9ydDogODcyOH07XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfc2F2ZShtaWtyb3Rpaykge1xuICAgICAgICAgICAgICAgIE1pa3JvdGlrLnNhdmUobWlrcm90aWssIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5taWtyb3RpayA9IGRhdGE7XG4gICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMgPSBbe1xuICAgICAgICAgICAgICAgICAgICAgICAgdGl0bGU6ICdTdWNlc3NvOicsXG4gICAgICAgICAgICAgICAgICAgICAgICBib2R5OiAnTWlrcm90aWsgJyArIGRhdGEubmFtZSArICcgZm9pIHNhbHZvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9yZW1vdmUobWlrcm90aWspIHtcbiAgICAgICAgICAgICAgICBNaWtyb3Rpay5yZW1vdmUoe2lkOiBtaWtyb3Rpay5pZH0sIGZ1bmN0aW9uICgpIHtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdNaWtyb3RpayAnICsgbWlrcm90aWsubmFtZSArICcgZm9pIHJlbW92aWRvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5taWtyb3RpayA9IHtwb3J0OiA4NzI4fTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ01pa3JvdGlrTGlzdEN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCBNaWtyb3Rpaykge1xuXG4gICAgICAgICAgICAkc2NvcGUubWlrcm90aWtzID0gTWlrcm90aWsucXVlcnkoKTtcblxuICAgICAgICB9KTtcbn0oKSk7XG4iXSwic291cmNlUm9vdCI6Ii9zb3VyY2UvIn0=
