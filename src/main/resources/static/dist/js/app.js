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
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/logout', {
                    template: '<div> Bye bye!</div>',
                    controller: function ($scope, $http) {
                        console.log('teste2');
                        $http.post('/logout');
                        console.log('teste');
                    }
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

//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC5qcyIsImNvbXBvbmVudGVzL2xvZ2dpbmcuaW50ZXJjZXB0b3IuanMiLCJjb250cm9sbGVycy9ob21lLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9sb2dpbi5jb250cm9sbGVyLmpzIiwicm91dGVzL2hvbWUuanMiLCJyb3V0ZXMvbG9naW4uanMiLCJyb3V0ZXMvbG9nb3V0LmpzIiwidGVtcGxhdGUvbmF2YmFyLmNvbnRyb2xsZXIuanMiLCJjb21wb25lbnRlcy9maWx0ZXJzL3N1bS5vZi52YWx1ZS5maWx0ZXIuanMiLCJkaXJlY3RpdmVzL2FsZXJ0L2FsZXJ0LmRpcmVjdGl2ZS5qcyIsImNvbnRyb2xsZXJzL2NvbWVyY2lhbC9jb25leGFvLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9jb21lcmNpYWwvY29udHJhdG8uY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2ZpbmFuY2Vpcm8vcmV0b3Juby5jb250cm9sbGVyLmpzIiwicm91dGVzL2NvbWVyY2lhbC9jbGllbnRlLmpzIiwicm91dGVzL2NvbWVyY2lhbC9jb25leGFvLmpzIiwicm91dGVzL2NvbWVyY2lhbC9jb250cmF0by5qcyIsInJvdXRlcy9jb21lcmNpYWwvcGxhbm8uanMiLCJyb3V0ZXMvZXN0b3F1ZS9lcXVpcGFtZW50by5qcyIsInJvdXRlcy9maW5hbmNlaXJvL3JldG9ybm8uanMiLCJyb3V0ZXMvZmluYW5jZWlyby90aXR1bG8uanMiLCJyb3V0ZXMvcHJvdmVkb3IvbWlrcm90aWsuanMiLCJzZXJ2aWNlL2NvbWVyY2lhbC9jZXAuc2VydmljZS5qcyIsInNlcnZpY2UvY29tZXJjaWFsL2NsaWVudGUuc2VydmljZS5qcyIsInNlcnZpY2UvY29tZXJjaWFsL2NvbmV4YW8uc2VydmljZS5qcyIsInNlcnZpY2UvY29tZXJjaWFsL2NvbnRyYXRvLnNlcnZpY2UuanMiLCJzZXJ2aWNlL2NvbWVyY2lhbC9wbGFuby5zZXJ2aWNlLmpzIiwic2VydmljZS9lc3RvcXVlL2VxdWlwYW1lbnRvLnNlcnZpY2UuanMiLCJzZXJ2aWNlL2ZpbmFuY2Vpcm8vdGl0dWxvLnNlcnZpY2UuanMiLCJzZXJ2aWNlL3Byb3ZlZG9yL21pa3JvdGlrLnNlcnZpY2UuanMiLCJjb250cm9sbGVycy9jb21lcmNpYWwvY2xpZW50ZS9jbGllbnRlLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9jb21lcmNpYWwvY2xpZW50ZS9jbGllbnRlLmxpc3QuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2NvbWVyY2lhbC9wbGFuby9wbGFuby5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvY29tZXJjaWFsL3BsYW5vL3BsYW5vLmxpc3QuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2VzdG9xdWUvZXF1aXBhbWVudG8vZXF1aXBhbWVudG8uY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2VzdG9xdWUvZXF1aXBhbWVudG8vZXF1aXBhbWVudG8ubGlzdC5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZmluYW5jZWlyby90aXR1bG8vY2FybmUuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2ZpbmFuY2Vpcm8vdGl0dWxvL3JlbGF0b3Jpby50aXR1bG8uY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL2ZpbmFuY2Vpcm8vdGl0dWxvL3RpdHVsby5jb250cm9sbGVyLmpzIiwiY29udHJvbGxlcnMvZmluYW5jZWlyby90aXR1bG8vdGl0dWxvLmxpc3QuY29udHJvbGxlci5qcyIsImNvbnRyb2xsZXJzL3Byb3ZlZG9yL21pa3JvdGlrL21pa3JvdGlrLmNvbnRyb2xsZXIuanMiLCJjb250cm9sbGVycy9wcm92ZWRvci9taWtyb3Rpay9taWtyb3Rpay5saXN0LmNvbnRyb2xsZXIuanMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDakJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUN4Q0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNwQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2xDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNYQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNYQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDaEJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDckJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2pCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDckVBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUM3REE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDMUJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3BCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1pBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDWkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDcEJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUMvQkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDcEJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDWkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDdkJBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDM0JBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1pBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3ZCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUM1Q0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNaQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ25EQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDeENBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUN4Q0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ1ZBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUNwREE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDUkE7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FDekNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQzNEQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ2pGQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQ3JCQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQzFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBIiwiZmlsZSI6ImFwcC5qcyIsInNvdXJjZXNDb250ZW50IjpbIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcsIFtcbiAgICAgICAgJ25nUm91dGUnLFxuICAgICAgICAnbmdSZXNvdXJjZScsXG4gICAgICAgICduZ0FuaW1hdGUnLFxuICAgICAgICAnbmdGaWxlVXBsb2FkJyxcbiAgICAgICAgJ3VpLmJvb3RzdHJhcCcsXG4gICAgICAgICduZ01hc2snLFxuICAgICAgICAnYW5ndWxhci1jb25maXJtJyxcbiAgICAgICAgJ2FuZ3VsYXItbG9hZGluZy1iYXInXG4gICAgXSkuXG4gICAgY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgJHJvdXRlUHJvdmlkZXIub3RoZXJ3aXNlKHtyZWRpcmVjdFRvOiAnLyd9KTtcbiAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDA2LzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnTXlMb2dnaW5nSW50ZXJjZXB0b3InLCBmdW5jdGlvbiAoJHJvb3RTY29wZSwgJHEpIHtcbiAgICAgICAgICAgIHJldHVybiB7XG4gICAgICAgICAgICAgICAgcmVxdWVzdDogZnVuY3Rpb24gKGNvbmZpZykge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW107XG4gICAgICAgICAgICAgICAgICAgIHJldHVybiBjb25maWc7XG4gICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICByZXNwb25zZUVycm9yOiBmdW5jdGlvbiAocmVqZWN0aW9uKSB7XG4gICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKCdFcnJvciBpbiByZXNwb25zZSAnLCByZWplY3Rpb24pO1xuICAgICAgICAgICAgICAgICAgICAvLyBpZiAocmVqZWN0aW9uLnN0YXR1cyA9PT0gNDAzKSB7XG4gICAgICAgICAgICAgICAgICAgIC8vICAgICAgICAgICAgICAgIFNob3cgYSBsb2dpbiBkaWFsb2dcbiAgICAgICAgICAgICAgICAgICAgLy8gfVxuXG4gICAgICAgICAgICAgICAgICAgIGlmIChyZWplY3Rpb24uZGF0YSAmJiByZWplY3Rpb24uZGF0YS5lcnJvcnMpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIHJlamVjdGlvbi5kYXRhLmVycm9ycy5mb3JFYWNoKGZ1bmN0aW9uIChpdCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgICAgICRyb290U2NvcGUubWVzc2FnZXMucHVzaCh7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnRXJyb3I6JyArIGl0LmZpZWxkLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBib2R5OiBpdC5kZWZhdWx0TWVzc2FnZSxcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LWRhbmdlcidcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgdmFyIG1lc3NhZ2UgPSByZWplY3Rpb24ubWVzc2FnZSB8fCByZWplY3Rpb24uZGF0YS5tZXNzYWdlO1xuICAgICAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcy5wdXNoKHt0aXRsZTogJ0Vycm9yOicsIGJvZHk6IHJlamVjdGlvbi5kYXRhLm1lc3NhZ2UsIHR5cGU6ICdhbGVydC1kYW5nZXInfSk7XG4gICAgICAgICAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgICAgICAgICByZXR1cm4gJHEucmVqZWN0KHJlamVjdGlvbik7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfTtcbiAgICAgICAgfSlcbiAgICAgICAgLmNvbmZpZyhmdW5jdGlvbiAoJGh0dHBQcm92aWRlcikge1xuICAgICAgICAgICAgJGh0dHBQcm92aWRlci5pbnRlcmNlcHRvcnMucHVzaCgnTXlMb2dnaW5nSW50ZXJjZXB0b3InKTtcbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0hvbWVDdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgVGl0dWxvLCBDbGllbnRlLCBDb250cmF0bykge1xuXG4gICAgICAgICAgICAkc2NvcGUub3JkZXIgPSBfb3JkZXI7XG5cbiAgICAgICAgICAgIF9idXNjYXJUaXR1bG9zVmVuY2lkbygpO1xuICAgICAgICAgICAgX2J1c2NhckNsaWVudGVzU2VtVGl0dWxvKCk7XG4gICAgICAgICAgICBfYnVzY2FyQ2xpZW50ZXNJbmF0aXZvKCk7XG4gICAgICAgICAgICBfYnVzY2FyQ29udHJhdG9zTm92b3MoKTtcblxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYnVzY2FyVGl0dWxvc1ZlbmNpZG8oKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsb3NWZW5jaWRvcyA9IFRpdHVsby52ZW5jaWRvcygpO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYnVzY2FyQ2xpZW50ZXNTZW1UaXR1bG8oKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVzU2VtVGl0dWxvID0gQ2xpZW50ZS5zZW1UaXR1bG8oKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckNsaWVudGVzSW5hdGl2bygpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZXNJbmF0aXZvID0gQ2xpZW50ZS5xdWVyeSh7c3RhdHVzOiAnSU5BVElWTyd9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckNvbnRyYXRvc05vdm9zKCkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jb250cmF0b3NOb3ZvcyA9IENvbnRyYXRvLm5vdm9zKCk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9vcmRlcihwcmVkaWNhdGUpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUucmV2ZXJzZSA9ICgkc2NvcGUucHJlZGljYXRlID09PSBwcmVkaWNhdGUpID8gISRzY29wZS5yZXZlcnNlIDogZmFsc2U7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnByZWRpY2F0ZSA9IHByZWRpY2F0ZTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0xvZ2luQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICR3aW5kb3cpIHtcblxuICAgICAgICAgICAgJHNjb3BlLmxvZ2luR29vZ2xlID0gZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgIHZhciBvYXV0aDIgPSB7XG4gICAgICAgICAgICAgICAgICAgIHVybDogXCJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aFwiLFxuICAgICAgICAgICAgICAgICAgICBjbGllbnRfaWQ6IFwiMjc1OTQ4MDI0NDI1LW1sNjBqMW1xdTYzdDIzN2lmbTJkNnA2MTdsM2dpbXQzLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tXCIsXG4gICAgICAgICAgICAgICAgICAgIHJlc3BvbnNlX3R5cGU6IFwidG9rZW5cIixcbiAgICAgICAgICAgICAgICAgICAgcmVkaXJlY3RfdXJpOiBcImh0dHA6Ly9sb2NhbGhvc3Q6ODAwMFwiLFxuICAgICAgICAgICAgICAgICAgICBzY29wZTogXCJwcm9maWxlIGVtYWlsXCIsXG4gICAgICAgICAgICAgICAgICAgIHN0YXRlOiBcImluaXRpYWxcIlxuICAgICAgICAgICAgICAgIH07XG5cbiAgICAgICAgICAgICAgICAkd2luZG93LnNlc3Npb25TdG9yYWdlLmxvZ2luVHlwZSA9IFwiZ29vZ2xlXCI7XG5cbiAgICAgICAgICAgICAgICAkd2luZG93Lm9wZW4ob2F1dGgyLnVybCArIFwiP2NsaWVudF9pZD1cIiArXG4gICAgICAgICAgICAgICAgICAgIG9hdXRoMi5jbGllbnRfaWQgKyBcIiZyZXNwb25zZV90eXBlPVwiICtcbiAgICAgICAgICAgICAgICAgICAgb2F1dGgyLnJlc3BvbnNlX3R5cGUgKyBcIiZyZWRpcmVjdF91cmk9XCIgK1xuICAgICAgICAgICAgICAgICAgICBvYXV0aDIucmVkaXJlY3RfdXJpICsgXCImc2NvcGU9XCIgK1xuICAgICAgICAgICAgICAgICAgICBvYXV0aDIuc2NvcGUgKyBcIiZzdGF0ZT1cIiArXG4gICAgICAgICAgICAgICAgICAgIG9hdXRoMi5zdGF0ZSwgXCJfc2VsZlwiKTtcbiAgICAgICAgICAgIH07XG5cbiAgICAgICAgICAgICRzY29wZS5nZXRHb29nbGVJbmZvID0gZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgICRodHRwLmdldChcImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL3BsdXMvdjEvcGVvcGxlL21lP2FjY2Vzc190b2tlbj1cIiArICRzY29wZS5hY2Nlc3NUb2tlbikuc3VjY2VzcyhmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZyhkYXRhKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH07XG5cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbmFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAkcm91dGVQcm92aWRlci53aGVuKCcvJywge1xuICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvaG9tZS9pbmRleC5odG1sJyxcbiAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdIb21lQ3RybCdcbiAgICAgICAgfSk7XG4gICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXIud2hlbignL2xvZ2luJywge1xuICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2xvZ2luL2luZGV4Lmh0bWwnLFxuICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdMb2dpbkN0cmwnXG4gICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXJcbiAgICAgICAgICAgICAgICAud2hlbignL2xvZ291dCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGU6ICc8ZGl2PiBCeWUgYnllITwvZGl2PicsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6IGZ1bmN0aW9uICgkc2NvcGUsICRodHRwKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZygndGVzdGUyJyk7XG4gICAgICAgICAgICAgICAgICAgICAgICAkaHR0cC5wb3N0KCcvbG9nb3V0Jyk7XG4gICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZygndGVzdGUnKTtcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ05hdmJhckN0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkaHR0cCwgJGxvY2F0aW9uKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5nZXRQYXRoID0gX2dldFBhdGg7XG5cbiAgICAgICAgICAgIF9sb2FkTWVudSgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfbG9hZE1lbnUoKSB7XG4gICAgICAgICAgICAgICAgJGh0dHAoe21ldGhvZDogJ0dFVCcsIHVybDogJ2FwcC90ZW1wbGF0ZS9tZW51Lmpzb24nfSkuc3VjY2VzcyhmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUubWVudXMgPSBkYXRhO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfZ2V0UGF0aCgpIHtcbiAgICAgICAgICAgICAgICByZXR1cm4gJyMnICsgJGxvY2F0aW9uLnBhdGgoKTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZpbHRlcignc3VtT2ZWYWx1ZScsIGZ1bmN0aW9uICgpIHtcbiAgICAgICAgICAgIHJldHVybiBmdW5jdGlvbiAoZGF0YSwga2V5KSB7XG4gICAgICAgICAgICAgICAgaWYgKGFuZ3VsYXIuaXNVbmRlZmluZWQoZGF0YSkgJiYgYW5ndWxhci5pc1VuZGVmaW5lZChrZXkpKVxuICAgICAgICAgICAgICAgICAgICByZXR1cm4gMDtcbiAgICAgICAgICAgICAgICB2YXIgc3VtID0gMDtcblxuICAgICAgICAgICAgICAgIGFuZ3VsYXIuZm9yRWFjaChkYXRhLCBmdW5jdGlvbiAodiwgaykge1xuICAgICAgICAgICAgICAgICAgICBzdW0gPSBzdW0gKyBwYXJzZUludCh2W2tleV0pO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgIHJldHVybiBzdW07XG4gICAgICAgICAgICB9O1xuICAgICAgICB9KTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMjkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5kaXJlY3RpdmUoJ3NpY29iYUFsZXJ0JywgZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgcmV0dXJuIHtcbiAgICAgICAgICAgICAgICByZXN0cmljdDogJ0UnLFxuICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL2RpcmVjdGl2ZXMvYWxlcnQvYWxlcnQuaHRtbCdcbiAgICAgICAgICAgIH07XG4gICAgICAgIH0pO1xuXG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0NvbmV4YW9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgJHJvdXRlUGFyYW1zLCBDbGllbnRlLCBDb25leGFvLCBDb250cmF0bywgTWlrcm90aWspIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcbiAgICAgICAgICAgICAgICBfY2FycmVnYXJDb25leGFvKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmNvbnRyYXRvID0gQ29udHJhdG8uYnVzY2FyUG9yQ2xpZW50ZSh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLm1pa3JvdGlrcyA9IE1pa3JvdGlrLnF1ZXJ5KCk7XG5cbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2NhcnJlZ2FyQ29uZXhhbygpIHtcbiAgICAgICAgICAgICAgICBDb25leGFvLmJ1c2NhclBvckNsaWVudGUoe2NsaWVudGVJZDogJHJvdXRlUGFyYW1zLmNsaWVudGVJZH0sIGZ1bmN0aW9uIChjb25leGFvKSB7XG4gICAgICAgICAgICAgICAgICAgIGlmIChjb25leGFvLmlkKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhbyA9IGNvbmV4YW87XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBfY3JpYXJOb3ZhQ29uZXhhbygpO1xuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9jcmlhck5vdmFDb25leGFvKCkge1xuICAgICAgICAgICAgICAgIENsaWVudGUuZ2V0KHtpZDogJHJvdXRlUGFyYW1zLmNsaWVudGVJZH0sIGZ1bmN0aW9uIChjbGllbnRlKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5jb25leGFvID0ge1xuICAgICAgICAgICAgICAgICAgICAgICAgY2xpZW50ZTogY2xpZW50ZVxuICAgICAgICAgICAgICAgICAgICB9O1xuXG5cbiAgICAgICAgICAgICAgICAgICAgdmFyIGluZGV4T2ZTcGFjZSA9IGNsaWVudGUubm9tZS5pbmRleE9mKCcgJyk7XG4gICAgICAgICAgICAgICAgICAgIGlmIChpbmRleE9mU3BhY2UgPiAwKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhby5ub21lID0gJHNjb3BlLmNvbmV4YW8uc2VuaGEgPSBjbGllbnRlLm5vbWUuc3BsaWNlKDAsIGluZGV4T2ZTcGFjZSkgKyBjbGllbnRlLmlkO1xuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNvbmV4YW8ubm9tZSA9ICRzY29wZS5jb25leGFvLnNlbmhhID0gY2xpZW50ZS5ub21lICsgY2xpZW50ZS5pZDtcbiAgICAgICAgICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICAgICAgICAgIENvbmV4YW8uYnVzY2FySXBMaXZyZSh7fSwgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jb25leGFvLmlwID0gZGF0YS5pcDtcbiAgICAgICAgICAgICAgICAgICAgfSk7XG5cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUoY29uZXhhbykge1xuICAgICAgICAgICAgICAgIENvbmV4YW8uc2F2ZShjb25leGFvLCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29uZXhhbyA9IGRhdGE7XG4gICAgICAgICAgICAgICAgICAgIF9zdWNlc3NvKCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9yZW1vdmUoY29uZXhhbykge1xuICAgICAgICAgICAgICAgIENvbmV4YW8ucmVtb3ZlKHtpZDogY29uZXhhby5pZH0sIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgIF9pbml0KCk7XG4gICAgICAgICAgICAgICAgICAgIF9zdWNlc3NvKCk7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zdWNlc3NvKCl7XG4gICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7dGl0bGU6ICdTdWNlc3NvJywgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnfV07XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdDb250cmF0b0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsIENsaWVudGUsIENvbnRyYXRvLCBQbGFubywgRXF1aXBhbWVudG8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcbiAgICAgICAgICAgICAgICAkc2NvcGUuaG9qZSA9IG5ldyBEYXRlKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnBsYW5vcyA9IFBsYW5vLnF1ZXJ5KCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvc0luc3RhbGFjYW8gPSBFcXVpcGFtZW50by5kaXNwb25pdmVpc1BhcmFJbnN0YWxhY2FvKCk7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvc1dpZmkgPSBFcXVpcGFtZW50by5kaXNwb25pdmVpc1BhcmFXaWZpKCk7XG4gICAgICAgICAgICAgICAgX2NhcnJlZ2FyQ29udHJhdG8oKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2NhcnJlZ2FyQ29udHJhdG8oKSB7XG4gICAgICAgICAgICAgICAgQ29udHJhdG8uYnVzY2FyUG9yQ2xpZW50ZSh7Y2xpZW50ZUlkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSwgZnVuY3Rpb24gKGNvbnRyYXRvKSB7XG4gICAgICAgICAgICAgICAgICAgIGlmIChjb250cmF0by5pZCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNvbnRyYXRvID0gY29udHJhdG87XG4gICAgICAgICAgICAgICAgICAgICAgICBpZiAoY29udHJhdG8uZXF1aXBhbWVudG8pIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG9zSW5zdGFsYWNhby5wdXNoKGNvbnRyYXRvLmVxdWlwYW1lbnRvKTtcbiAgICAgICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICAgICAgICAgIGlmIChjb250cmF0by5lcXVpcGFtZW50b1dpZmkpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG9zV2lmaS5wdXNoKGNvbnRyYXRvLmVxdWlwYW1lbnRvV2lmaSk7XG4gICAgICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29udHJhdG8gPSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgY2xpZW50ZTogQ2xpZW50ZS5nZXQoe2lkOiAkcm91dGVQYXJhbXMuY2xpZW50ZUlkfSlcbiAgICAgICAgICAgICAgICAgICAgICAgIH07XG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUoY29udHJhdG8pIHtcbiAgICAgICAgICAgICAgICBDb250cmF0by5zYXZlKGNvbnRyYXRvLCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY29udHJhdG8gPSBkYXRhO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0NvbnRyYXRvIGRlIG7Dum1lcm8gJyArIGRhdGEuaWQgKyAnIGZvaSBzYWx2by4nLFxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnXG4gICAgICAgICAgICAgICAgICAgIH1dO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfcmVtb3ZlKGNvbnRyYXRvKSB7XG4gICAgICAgICAgICAgICAgQ29udHJhdG8ucmVtb3ZlKHtpZDogY29udHJhdG8uaWR9LCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICBfaW5pdCgpO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0NvbnRyYXRvIGRlIG7Dum1lcm8gJyArIGNvbnRyYXRvLmlkICsgJyBmb2kgcmVtb3ZpZG8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ1JldG9ybm9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgVXBsb2FkKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5lbnZpYXIgPSBfZW52aWFyO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfZW52aWFyKGZpbGVzKSB7XG4gICAgICAgICAgICAgICAgZmlsZXMuZm9yRWFjaChmdW5jdGlvbiAoZmlsZSkge1xuICAgICAgICAgICAgICAgICAgICBVcGxvYWQudXBsb2FkKHtcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9yZXRvcm5vcy91cGxvYWQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgZGF0YToge2ZpbGU6IGZpbGV9XG4gICAgICAgICAgICAgICAgICAgIH0pLnRoZW4oZnVuY3Rpb24gKHJlc3ApIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIGZpbGUucmV0b3JubyA9IHJlc3AuZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgfSwgZnVuY3Rpb24gKHJlc3ApIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKFwiVEVTVEVTVEVcIik7XG4gICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZyhyZXNwKTtcbiAgICAgICAgICAgICAgICAgICAgICAgIGZpbGUuZXJyb3IgPSByZXNwLmRhdGEubWVzc2FnZTtcbiAgICAgICAgICAgICAgICAgICAgfSwgZnVuY3Rpb24gKGV2dCkge1xuICAgICAgICAgICAgICAgICAgICAgICAgZmlsZS5wcm9ncmVzcyA9IHBhcnNlSW50KDEwMC4wICogZXZ0LmxvYWRlZCAvIGV2dC50b3RhbCk7XG4gICAgICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy9jbGllbnRlJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9jb21lcmNpYWwvY2xpZW50ZS9jbGllbnRlLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ2xpZW50ZUN0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL2NsaWVudGUvOmlkJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9jb21lcmNpYWwvY2xpZW50ZS9jbGllbnRlLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ2xpZW50ZUN0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL2NsaWVudGVzJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9jb21lcmNpYWwvY2xpZW50ZS9jbGllbnRlLmxpc3QuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdDbGllbnRlTGlzdEN0cmwnXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy9jb25leGFvL2NsaWVudGUvOmNsaWVudGVJZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL2NvbmV4YW8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdDb25leGFvQ3RybCdcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXJcbiAgICAgICAgICAgICAgICAud2hlbignL2NvbnRyYXRvL2NsaWVudGUvOmNsaWVudGVJZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL2NvbnRyYXRvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnQ29udHJhdG9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvcGxhbm8nLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2NvbWVyY2lhbC9wbGFuby9wbGFuby5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ1BsYW5vQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvcGxhbm8vOmlkJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9jb21lcmNpYWwvcGxhbm8vcGxhbm8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdQbGFub0N0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3BsYW5vcycsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvY29tZXJjaWFsL3BsYW5vL3BsYW5vLmxpc3QuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdQbGFub0xpc3RDdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDEzLzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy9lcXVpcGFtZW50b3MnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2VzdG9xdWUvZXF1aXBhbWVudG8vZXF1aXBhbWVudG8ubGlzdC5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ0VxdWlwYW1lbnRvTGlzdEN0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL2VxdWlwYW1lbnRvJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9lc3RvcXVlL2VxdWlwYW1lbnRvL2VxdWlwYW1lbnRvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnRXF1aXBhbWVudG9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9lcXVpcGFtZW50by86aWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2VzdG9xdWUvZXF1aXBhbWVudG8vZXF1aXBhbWVudG8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdFcXVpcGFtZW50b0N0cmwnXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMDkvMDEvMTYuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb25maWcoWyckcm91dGVQcm92aWRlcicsIGZ1bmN0aW9uICgkcm91dGVQcm92aWRlcikge1xuICAgICAgICAgICAgJHJvdXRlUHJvdmlkZXJcbiAgICAgICAgICAgICAgICAud2hlbignL3JldG9ybm8nLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL2ZpbmFuY2Vpcm8vcmV0b3Juby5odG1sJyxcbiAgICAgICAgICAgICAgICAgICAgY29udHJvbGxlcjogJ1JldG9ybm9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDA5LzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29uZmlnKFsnJHJvdXRlUHJvdmlkZXInLCBmdW5jdGlvbiAoJHJvdXRlUHJvdmlkZXIpIHtcbiAgICAgICAgICAgICRyb3V0ZVByb3ZpZGVyXG4gICAgICAgICAgICAgICAgLndoZW4oJy90aXR1bG9zL2NsaWVudGUvOmNsaWVudGVJZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZmluYW5jZWlyby90aXR1bG8vdGl0dWxvLmxpc3QuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdUaXR1bG9MaXN0Q3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvdGl0dWxvLzppZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZmluYW5jZWlyby90aXR1bG8vdGl0dWxvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnVGl0dWxvQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvdGl0dWxvL2NsaWVudGUvOmNsaWVudGVJZCcsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZmluYW5jZWlyby90aXR1bG8vdGl0dWxvLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnVGl0dWxvQ3RybCdcbiAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgIC53aGVuKCcvdGl0dWxvL2NsaWVudGUvOmNsaWVudGVJZC9jYXJuZScsIHtcbiAgICAgICAgICAgICAgICAgICAgdGVtcGxhdGVVcmw6ICdhcHAvdmlld3MvZmluYW5jZWlyby90aXR1bG8vY2FybmUuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdDYXJuZUN0cmwnXG4gICAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgICAud2hlbignL3RpdHVsb3MvcmVsYXRvcmlvJywge1xuICAgICAgICAgICAgICAgICAgICB0ZW1wbGF0ZVVybDogJ2FwcC92aWV3cy9maW5hbmNlaXJvL3RpdHVsby9yZWxhdG9yaW8uaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdSZWxhdG9yaW9UaXR1bG9DdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbmZpZyhbJyRyb3V0ZVByb3ZpZGVyJywgZnVuY3Rpb24gKCRyb3V0ZVByb3ZpZGVyKSB7XG4gICAgICAgICAgICAkcm91dGVQcm92aWRlclxuICAgICAgICAgICAgICAgIC53aGVuKCcvbWlrcm90aWsnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL3Byb3ZlZG9yL21pa3JvdGlrL21pa3JvdGlrLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnTWlrcm90aWtDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9taWtyb3Rpay86aWQnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL3Byb3ZlZG9yL21pa3JvdGlrL21pa3JvdGlrLmh0bWwnLFxuICAgICAgICAgICAgICAgICAgICBjb250cm9sbGVyOiAnTWlrcm90aWtDdHJsJ1xuICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgLndoZW4oJy9taWtyb3Rpa3MnLCB7XG4gICAgICAgICAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnYXBwL3ZpZXdzL3Byb3ZlZG9yL21pa3JvdGlrL21pa3JvdGlrLmxpc3QuaHRtbCcsXG4gICAgICAgICAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdNaWtyb3Rpa0xpc3RDdHJsJ1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDE5LzEyLzE1LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnQ2VwJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdodHRwOi8vdmlhY2VwLmNvbS5ici93cy86Y2VwL2pzb24nLCB7Y2VwOiAnQGNlcCd9LFxuICAgICAgICAgICAgICAgIHt9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxOS8xMi8xNS5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ0NsaWVudGUnLCBbJyRyZXNvdXJjZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UpIHtcbiAgICAgICAgICAgIHJldHVybiAkcmVzb3VyY2UoJ2FwaS9jbGllbnRlcy86aWQnLCB7aWQ6ICdAaWQnfSxcbiAgICAgICAgICAgICAgICB7XG4gICAgICAgICAgICAgICAgICAgIHNlbVRpdHVsbzoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9jbGllbnRlcy9zZW1fdGl0dWxvJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgdWx0aW1vc0FsdGVyYWRvczoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9jbGllbnRlcy91bHRpbW9zX2FsdGVyYWRvcycsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxOS8xMi8xNS5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ0NvbmV4YW8nLCBbJyRyZXNvdXJjZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UpIHtcbiAgICAgICAgICAgIHJldHVybiAkcmVzb3VyY2UoJ2FwaS9jb25leG9lcy86aWQnLCB7aWQ6ICdAaWQnfSxcbiAgICAgICAgICAgICAgICB7XG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhclBvckNsaWVudGU6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY29uZXhvZXMvY2xpZW50ZS86Y2xpZW50ZUlkJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHBhcmFtczoge2lkOiAnQGlkJ31cbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgYnVzY2FyUG9ySXA6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvY29uZXhvZXMvaXAvOmlwJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHBhcmFtczoge2lwOiAnQGlwJ31cbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgYnVzY2FySXBMaXZyZToge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9jb25leG9lcy9pcC9saXZyZSdcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDE5LzEyLzE1LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnQ29udHJhdG8nLCBbJyRyZXNvdXJjZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UpIHtcbiAgICAgICAgICAgIHJldHVybiAkcmVzb3VyY2UoJ2FwaS9jb250cmF0b3MvOmlkJywge2lkOiAnQGlkJ30sXG4gICAgICAgICAgICAgICAge1xuICAgICAgICAgICAgICAgICAgICBub3Zvczoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9jb250cmF0b3Mvbm92b3MnLFxuICAgICAgICAgICAgICAgICAgICAgICAgaXNBcnJheTogdHJ1ZVxuICAgICAgICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgICAgICAgICBidXNjYXJQb3JDbGllbnRlOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL2NvbnRyYXRvcy9jbGllbnRlLzpjbGllbnRlSWQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgcGFyYW1zOiB7Y2xpZW50ZUlkOiAnQGNsaWVudGVJZCd9XG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAwMS8wMS8xNi5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ1BsYW5vJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvcGxhbm9zLzppZCcsIHtpZDogJ0BpZCd9LFxuICAgICAgICAgICAgICAgIHt9KTtcbiAgICAgICAgfV0pO1xufSgpKTtcbiIsIi8qKlxuICogQ3JlYXRlZCBieSBjbGFpcnRvbiBvbiAxOS8xMi8xNS5cbiAqL1xuKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmZhY3RvcnkoJ0VxdWlwYW1lbnRvJywgWyckcmVzb3VyY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlKSB7XG4gICAgICAgICAgICByZXR1cm4gJHJlc291cmNlKCdhcGkvZXF1aXBhbWVudG9zLzppZCcsIHtpZDogJ0BpZCd9LFxuICAgICAgICAgICAgICAgIHtcbiAgICAgICAgICAgICAgICAgICAgZGlzcG9uaXZlaXNQYXJhSW5zdGFsYWNhbzoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS9lcXVpcGFtZW50b3MvaW5zdGFsYWNhby9kaXNwb25pdmVpcycsXG4gICAgICAgICAgICAgICAgICAgICAgICBpc0FycmF5OiB0cnVlXG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGRpc3Bvbml2ZWlzUGFyYVdpZmk6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvZXF1aXBhbWVudG9zL3dpZmkvZGlzcG9uaXZlaXMnLFxuICAgICAgICAgICAgICAgICAgICAgICAgaXNBcnJheTogdHJ1ZVxuICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgIH1dKTtcbn0oKSk7XG4iLCIvKipcbiAqIENyZWF0ZWQgYnkgY2xhaXJ0b24gb24gMTkvMTIvMTUuXG4gKi9cbihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5mYWN0b3J5KCdUaXR1bG8nLCBbJyRyZXNvdXJjZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UpIHtcbiAgICAgICAgICAgIHJldHVybiAkcmVzb3VyY2UoJ2FwaS90aXR1bG9zLzppZCcsIHtpZDogJ0BpZCd9LFxuICAgICAgICAgICAgICAgIHtcbiAgICAgICAgICAgICAgICAgICAgJ3ZlbmNpZG9zJzoge1xuICAgICAgICAgICAgICAgICAgICAgICAgbWV0aG9kOiAnR0VUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS90aXR1bG9zL3ZlbmNpZG9zJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgJ25vdm8nOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdHRVQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgdXJsOiAnYXBpL3RpdHVsb3MvY2xpZW50ZS86Y2xpZW50ZUlkL25vdmEnLFxuICAgICAgICAgICAgICAgICAgICAgICAgcGFyYW1zOiB7Y2xpZW50ZUlkOiAnQGNsaWVudGVJZCd9XG4gICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgIGJ1c2NhclBvckNsaWVudGU6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvdGl0dWxvcy9jbGllbnRlLzpjbGllbnRlSWQnLFxuICAgICAgICAgICAgICAgICAgICAgICAgcGFyYW1zOiB7Y2xpZW50ZUlkOiAnQGNsaWVudGVJZCd9LFxuICAgICAgICAgICAgICAgICAgICAgICAgaXNBcnJheTogdHJ1ZVxuICAgICAgICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgICAgICAgICBjcmlhckNhcm5lOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6ICdQT1NUJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHVybDogJ2FwaS90aXR1bG9zL2Nhcm5lJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgYnVzY2FyUG9yRGF0YU9jb3JyZW5jaWE6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvdGl0dWxvcy9vY29ycmVuY2lhJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgYnVzY2FyUG9yRGF0YVZlbmNpbWVudG86IHtcbiAgICAgICAgICAgICAgICAgICAgICAgIG1ldGhvZDogJ0dFVCcsXG4gICAgICAgICAgICAgICAgICAgICAgICB1cmw6ICdhcGkvdGl0dWxvcy92ZW5jaW1lbnRvJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGlzQXJyYXk6IHRydWVcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiLyoqXG4gKiBDcmVhdGVkIGJ5IGNsYWlydG9uIG9uIDA5LzAxLzE2LlxuICovXG4oZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuZmFjdG9yeSgnTWlrcm90aWsnLCBbJyRyZXNvdXJjZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UpIHtcbiAgICAgICAgICAgIHJldHVybiAkcmVzb3VyY2UoJ2FwaS9taWtyb3Rpa3MvOmlkJywge2lkOiAnQGlkJ30sXG4gICAgICAgICAgICAgICAge30pO1xuICAgICAgICB9XSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0NsaWVudGVDdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgJHJvdXRlUGFyYW1zLCBDbGllbnRlLCBDZXAsIENvbnRyYXRvKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5zYXZlID0gX3NhdmU7XG4gICAgICAgICAgICAkc2NvcGUuYnVzY2FyRW5kZXJlY29Qb3JDZXAgPSBfYnVzY2FyRW5kZXJlY29Qb3JDZXA7XG5cbiAgICAgICAgICAgIF9pbml0KCk7XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9pbml0KCkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlID0ge3N0YXR1czogJ0FUSVZPJ307XG4gICAgICAgICAgICAgICAgJHNjb3BlLmhvamUgPSBuZXcgRGF0ZSgpO1xuXG4gICAgICAgICAgICAgICAgaWYgKCRyb3V0ZVBhcmFtcy5pZCkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZSA9IENsaWVudGUuZ2V0KHtpZDogJHJvdXRlUGFyYW1zLmlkfSk7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5jb250cmF0byA9IENvbnRyYXRvLmJ1c2NhclBvckNsaWVudGUoe2NsaWVudGVJZDogJHJvdXRlUGFyYW1zLmlkfSk7XG4gICAgICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKGNsaWVudGUpIHtcbiAgICAgICAgICAgICAgICBDbGllbnRlLnNhdmUoY2xpZW50ZSwgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGUgPSBkYXRhO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3t0aXRsZTogJ1N1Y2Vzc286JywgYm9keTogJ0NsaWVudGUgJyArIGRhdGEubm9tZSArICcgZm9pIHNhbHZvLicsIHR5cGU6ICdhbGVydC1zdWNjZXNzJ31dO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfYnVzY2FyRW5kZXJlY29Qb3JDZXAoY2VwLCBmb3JtKSB7XG4gICAgICAgICAgICAgICAgaWYgKGNlcCkge1xuICAgICAgICAgICAgICAgICAgICBDZXAuZ2V0KHtjZXA6IGNlcH0sIGZ1bmN0aW9uIChkYXRhKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBpZiAoZGF0YS5lcnJvKSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgZm9ybS5jZXAuJGVycm9yLm5vdEZvdW5kID0gdHJ1ZTtcbiAgICAgICAgICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGUuZW5kZXJlY28uY2VwID0gZGF0YS5jZXA7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGUuZW5kZXJlY28ubG9ncmFkb3VybyA9IGRhdGEubG9ncmFkb3VybztcblxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlLmVuZGVyZWNvLmJhaXJybyA9IHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXCJub21lXCI6IGRhdGEuYmFpcnJvLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBcImNpZGFkZVwiOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBcIm5vbWVcIjogZGF0YS5sb2NhbGlkYWRlLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXCJlc3RhZG9cIjoge1widWZcIjogZGF0YS51Zn1cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIH07XG4gICAgICAgICAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0NsaWVudGVMaXN0Q3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsIENsaWVudGUsIENvbmV4YW8pIHtcbiAgICAgICAgICAgICRzY29wZS5idXNjYXJQb3JOb21lID0gX2J1c2NhclBvck5vbWU7XG4gICAgICAgICAgICAkc2NvcGUuYnVzY2FyUG9ySXAgPSBfYnVzY2FyUG9ySXA7XG5cbiAgICAgICAgICAgIF9pbml0KCk7XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9pbml0KCkge1xuICAgICAgICAgICAgICAgIENsaWVudGUudWx0aW1vc0FsdGVyYWRvcyhmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZXMgPSBkYXRhO1xuICAgICAgICAgICAgICAgICAgICBfYnVzY2FyQ29uZXhvZXMoJHNjb3BlLmNsaWVudGVzKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhclBvck5vbWUobm9tZSkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlcyA9IENsaWVudGUucXVlcnkoe25vbWU6IG5vbWV9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhclBvcklwKGlwKSB7XG4gICAgICAgICAgICAgICAgQ29uZXhhby5idXNjYXJQb3JJcCh7aXA6IGlwfSwgZnVuY3Rpb24gKGNvbmV4YW8pIHtcbiAgICAgICAgICAgICAgICAgICAgaWYgKGNvbmV4YW8pIHtcbiAgICAgICAgICAgICAgICAgICAgICAgIGNvbmV4YW8uY2xpZW50ZS5jb25leGFvID0gY29uZXhhbztcbiAgICAgICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlcyA9IFtjb25leGFvLmNsaWVudGVdO1xuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGVzID0gW107XG4gICAgICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhckNvbmV4b2VzKGNsaWVudGVzKSB7XG4gICAgICAgICAgICAgICAgY2xpZW50ZXMuZm9yRWFjaChmdW5jdGlvbiAoY2xpZW50ZSkge1xuICAgICAgICAgICAgICAgICAgICBjbGllbnRlLmNvbmV4YW8gPSBDb25leGFvLmJ1c2NhclBvckNsaWVudGUoe2NsaWVudGVJZDogY2xpZW50ZS5pZH0pO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdQbGFub0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCAkcm9vdFNjb3BlLCAkcm91dGVQYXJhbXMsIFBsYW5vKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5zYXZlID0gX3NhdmU7XG4gICAgICAgICAgICAkc2NvcGUucmVtb3ZlID0gX3JlbW92ZTtcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgaWYgKCRyb3V0ZVBhcmFtcy5pZCkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUucGxhbm8gPSBQbGFuby5nZXQoe2lkOiAkcm91dGVQYXJhbXMuaWR9KTtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKHBsYW5vKSB7XG4gICAgICAgICAgICAgICAgUGxhbm8uc2F2ZShwbGFubywgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLnBsYW5vID0gZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdQbGFubyAnICsgZGF0YS5ub21lICsgJyBmb2kgc2Fsdm8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3JlbW92ZShwbGFubykge1xuICAgICAgICAgICAgICAgIFBsYW5vLnJlbW92ZSh7aWQ6IHBsYW5vLmlkfSwgZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ1BsYW5vICcgKyBwbGFuby5ub21lICsgJyBmb2kgcmVtb3ZpZG8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLnBsYW5vID0ge307XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdQbGFub0xpc3RDdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgUGxhbm8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLnBsYW5vcyA9IFBsYW5vLnF1ZXJ5KCk7XG5cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0VxdWlwYW1lbnRvQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb290U2NvcGUsICRyb3V0ZVBhcmFtcywgRXF1aXBhbWVudG8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICBpZiAoJHJvdXRlUGFyYW1zLmlkKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5lcXVpcGFtZW50byA9IEVxdWlwYW1lbnRvLmdldCh7aWQ6ICRyb3V0ZVBhcmFtcy5pZH0pO1xuICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5lcXVpcGFtZW50byA9IHt0aXBvOiBcIklOU1RBTEFDQU9cIiwgc3RhdHVzOiBcIk9LXCJ9O1xuICAgICAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgICAgICRzY29wZS50aXBvcyA9IFtcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiBcIklOU1RBTEFDQU9cIiwgZGVzY3JpY2FvOiAnSW5zdGFsYcOnw6NvJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogXCJXSUZJXCIsIGRlc2NyaWNhbzogJ1dpZmknfVxuICAgICAgICAgICAgICAgIF07XG5cbiAgICAgICAgICAgICAgICAkc2NvcGUuc3RhdHVzTGlzdCA9IFtcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiBcIk9LXCIsIGRlc2NyaWNhbzogJ09LJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogXCJERUZFSVRPXCIsIGRlc2NyaWNhbzogJ0RlZmVpdG8nfVxuICAgICAgICAgICAgICAgIF07XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKGVxdWlwYW1lbnRvKSB7XG4gICAgICAgICAgICAgICAgRXF1aXBhbWVudG8uc2F2ZShlcXVpcGFtZW50bywgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLmVxdWlwYW1lbnRvID0gZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdFcXVpcGFtZW50byAnICsgZGF0YS5tYXJjYSArICcgJyArIGRhdGEubW9kZWxvICsgJyBmb2kgc2Fsdm8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3JlbW92ZShlcXVpcGFtZW50bykge1xuICAgICAgICAgICAgICAgIEVxdWlwYW1lbnRvLnJlbW92ZSh7aWQ6IGVxdWlwYW1lbnRvLmlkfSwgZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0VxdWlwYW1lbnRvICcgKyBlcXVpcGFtZW50by5tYXJjYSArICcgJyArIGVxdWlwYW1lbnRvLm1vZGVsbyArICcgZm9pIHJlbW92aWRvLicsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5lcXVpcGFtZW50byA9IHt0aXBvOiBcIklOU1RBTEFDQU9cIiwgc3RhdHVzOiBcIk9LXCJ9O1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignRXF1aXBhbWVudG9MaXN0Q3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsIEVxdWlwYW1lbnRvKSB7XG4gICAgICAgICAgICAkc2NvcGUuZXF1aXBhbWVudG9zID0gRXF1aXBhbWVudG8ucXVlcnkoKTtcbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ0Nhcm5lQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb290U2NvcGUsICRyb3V0ZVBhcmFtcywgJGxvY2F0aW9uLCBUaXR1bG8sIENvbnRyYXRvKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5zYXZlID0gX3NhdmU7XG5cbiAgICAgICAgICAgIF9pbml0KCk7XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9pbml0KCkge1xuICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlSWQgPSAkcm91dGVQYXJhbXMuY2xpZW50ZUlkO1xuXG4gICAgICAgICAgICAgICAgVGl0dWxvLm5vdm8oe2NsaWVudGVJZDogJHJvdXRlUGFyYW1zLmNsaWVudGVJZH0sIGZ1bmN0aW9uIChub3ZvVGl0dWxvKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5jbGllbnRlID0gbm92b1RpdHVsby5jbGllbnRlO1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2FybmUgPSB7XG4gICAgICAgICAgICAgICAgICAgICAgICBjbGllbnRlSWQ6IG5vdm9UaXR1bG8uY2xpZW50ZS5pZCxcbiAgICAgICAgICAgICAgICAgICAgICAgIG1vZGFsaWRhZGU6IG5vdm9UaXR1bG8ubW9kYWxpZGFkZSxcbiAgICAgICAgICAgICAgICAgICAgICAgIHZhbG9yOiBub3ZvVGl0dWxvLnZhbG9yLFxuICAgICAgICAgICAgICAgICAgICAgICAgZGF0YUluaWNpbzogbm92b1RpdHVsby5kYXRhVmVuY2ltZW50b1xuICAgICAgICAgICAgICAgICAgICB9O1xuICAgICAgICAgICAgICAgIH0pO1xuXG4gICAgICAgICAgICAgICAgJHNjb3BlLm1vZGFsaWRhZGVzID0gW1xuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6IDE0LCBkZXNjcmljYW86ICdSZWdpc3RyYWRvJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogMjQsIGRlc2NyaWNhbzogJ1NlbSBSZWdpc3Rybyd9XG4gICAgICAgICAgICAgICAgXTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUoY2FybmUpIHtcbiAgICAgICAgICAgICAgICBUaXR1bG8uY3JpYXJDYXJuZShjYXJuZSwgZnVuY3Rpb24gKHRpdHVsb3MpIHtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdDcmlhZG8gY2FybsOqIGNvbSAnICsgdGl0dWxvcy5sZW5ndGggKyAnIHRpdHVsb3MocykgZGUgJyArIGNhcm5lLmJvbGV0b0luaWNpbyArICcgYXTDqSAnICsgY2FybmUuYm9sZXRvRmltLFxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZTogJ2FsZXJ0LXN1Y2Nlc3MnXG4gICAgICAgICAgICAgICAgICAgIH1dO1xuICAgICAgICAgICAgICAgICAgICAkbG9jYXRpb24ucGF0aCgnL3RpdHVsb3MvY2xpZW50ZS8nICsgY2FybmUuY2xpZW50ZUlkKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ1JlbGF0b3Jpb1RpdHVsb0N0cmwnLCBmdW5jdGlvbiAoJHNjb3BlLCBUaXR1bG8pIHtcblxuICAgICAgICAgICAgJHNjb3BlLmdldFN0YXR1c0NsYXNzID0gX2dldFN0YXR1c0NsYXNzO1xuICAgICAgICAgICAgJHNjb3BlLmNvbnN1bHRhclBvckRhdGFPY29ycmVuY2lhID0gX2NvbnN1bHRhclBvckRhdGFPY29ycmVuY2lhO1xuICAgICAgICAgICAgJHNjb3BlLmNvbnN1bHRhclBvckRhdGFWZW5jaW1lbnRvID0gX2NvbnN1bHRhclBvckRhdGFWZW5jaW1lbnRvO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUucGFyYW1zID0ge1xuICAgICAgICAgICAgICAgICAgICBpbmljaW86IG5ldyBEYXRlKCksXG4gICAgICAgICAgICAgICAgICAgIGZpbTogbmV3IERhdGUoKSxcbiAgICAgICAgICAgICAgICAgICAgcG9yRGF0YU9jb3JyZW5jaWE6IHRydWVcbiAgICAgICAgICAgICAgICB9O1xuXG4gICAgICAgICAgICAgICAgJHNjb3BlLnN0YXR1c0xpc3QgPSBbXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogJ1BBR09fTk9fQk9MRVRPJywgZGVzY3JpY2FvOiAnUGFnbyBubyBib2xldG8nfSxcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiAnUEVOREVOVEUnLCBkZXNjcmljYW86ICdQZW5kZW50ZSd9LFxuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6ICdCQUlYQV9NQU5VQUwnLCBkZXNjcmljYW86ICdCYWl4YSBtYW51YWwnfVxuICAgICAgICAgICAgICAgIF07XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9jb25zdWx0YXJQb3JEYXRhT2NvcnJlbmNpYShwYXJhbXMpIHtcbiAgICAgICAgICAgICAgICAkc2NvcGUudGl0dWxvcyA9IFRpdHVsby5idXNjYXJQb3JEYXRhT2NvcnJlbmNpYSh7XG4gICAgICAgICAgICAgICAgICAgIGluaWNpbzogX2Zvcm1hdChwYXJhbXMuaW5pY2lvKSxcbiAgICAgICAgICAgICAgICAgICAgZmltOiBfZm9ybWF0KHBhcmFtcy5maW0pLFxuICAgICAgICAgICAgICAgICAgICBzdGF0dXM6IHBhcmFtcy5zdGF0dXNcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2NvbnN1bHRhclBvckRhdGFWZW5jaW1lbnRvKHBhcmFtcykge1xuICAgICAgICAgICAgICAgICRzY29wZS50aXR1bG9zID0gVGl0dWxvLmJ1c2NhclBvckRhdGFWZW5jaW1lbnRvKHtcbiAgICAgICAgICAgICAgICAgICAgaW5pY2lvOiBfZm9ybWF0KHBhcmFtcy5pbmljaW8pLFxuICAgICAgICAgICAgICAgICAgICBmaW06IF9mb3JtYXQocGFyYW1zLmZpbSksXG4gICAgICAgICAgICAgICAgICAgIHN0YXR1czogcGFyYW1zLnN0YXR1c1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfZ2V0U3RhdHVzQ2xhc3Moc3RhdHVzKSB7XG4gICAgICAgICAgICAgICAgcmV0dXJuIHN0YXR1cyA9PT0gJ1BFTkRFTlRFJyA/ICdsYWJlbC13YXJuaW5nJyA6ICdsYWJlbC1zdWNjZXNzJztcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2Zvcm1hdChkYXRlKSB7XG4gICAgICAgICAgICAgICAgaWYgKGRhdGUpIHtcbiAgICAgICAgICAgICAgICAgICAgdmFyIHllYXIgPSAxOTAwICsgZGF0ZS5nZXRZZWFyKCk7XG4gICAgICAgICAgICAgICAgICAgIHZhciBtb250aCA9IGRhdGUuZ2V0TW9udGgoKSArIDE7XG4gICAgICAgICAgICAgICAgICAgIHZhciBkYXkgPSBkYXRlLmdldERhdGUoKTtcbiAgICAgICAgICAgICAgICAgICAgbW9udGggPSBtb250aCA8IDEwID8gJzAnICsgbW9udGggOiBtb250aDtcbiAgICAgICAgICAgICAgICAgICAgZGF5ID0gZGF5IDwgMTAgPyAnMCcgKyBkYXkgOiBkYXk7XG4gICAgICAgICAgICAgICAgICAgIHJldHVybiB5ZWFyICsgJy0nICsgbW9udGggKyAnLScgKyBkYXk7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfVxuXG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdUaXR1bG9DdHJsJywgZnVuY3Rpb24gKCRzY29wZSwgJHJvb3RTY29wZSwgJHJvdXRlUGFyYW1zLCAkbG9jYXRpb24sIFRpdHVsbykge1xuXG4gICAgICAgICAgICAkc2NvcGUuc2F2ZSA9IF9zYXZlO1xuICAgICAgICAgICAgJHNjb3BlLnJlbW92ZSA9IF9yZW1vdmU7XG4gICAgICAgICAgICAkc2NvcGUuYXR1YWxpemFyVmFsb3JlcyA9IF9hdHVhbGl6YXJWYWxvcmVzO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICBpZiAoJHJvdXRlUGFyYW1zLmNsaWVudGVJZCkge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gJHJvdXRlUGFyYW1zLmNsaWVudGVJZDtcbiAgICAgICAgICAgICAgICAgICAgX25vdmFUaXR1bG8oKTtcbiAgICAgICAgICAgICAgICB9IGVsc2UgaWYgKCRyb3V0ZVBhcmFtcy5pZCkge1xuICAgICAgICAgICAgICAgICAgICBfYnVzY2FyUG9ySWQoJHJvdXRlUGFyYW1zLmlkKTtcbiAgICAgICAgICAgICAgICB9XG5cbiAgICAgICAgICAgICAgICAkc2NvcGUubW9kYWxpZGFkZXMgPSBbXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogMTQsIGRlc2NyaWNhbzogJ1JlZ2lzdHJhZG8nfSxcbiAgICAgICAgICAgICAgICAgICAge3ZhbHVlOiAyNCwgZGVzY3JpY2FvOiAnU2VtIFJlZ2lzdHJvJ31cbiAgICAgICAgICAgICAgICBdO1xuICAgICAgICAgICAgICAgICRzY29wZS5zdGF0dXNMaXN0ID0gW1xuICAgICAgICAgICAgICAgICAgICB7dmFsdWU6ICdQRU5ERU5URScsIGRlc2NyaWNhbzogJ1BlbmRlbnRlJ30sXG4gICAgICAgICAgICAgICAgICAgIHt2YWx1ZTogJ0JBSVhBX01BTlVBTCcsIGRlc2NyaWNhbzogJ0JhaXhhIG1hbnVhbCd9XG4gICAgICAgICAgICAgICAgXTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX25vdmFUaXR1bG8oKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLnRpdHVsbyA9IFRpdHVsby5ub3ZvKHtjbGllbnRlSWQ6ICRyb3V0ZVBhcmFtcy5jbGllbnRlSWR9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2J1c2NhclBvcklkKGlkKSB7XG4gICAgICAgICAgICAgICAgVGl0dWxvLmdldCh7aWQ6IGlkfSwgZnVuY3Rpb24gKHRpdHVsbykge1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUudGl0dWxvID0gdGl0dWxvO1xuICAgICAgICAgICAgICAgICAgICAkc2NvcGUuY2xpZW50ZUlkID0gdGl0dWxvLmNsaWVudGUuaWQ7XG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9hdHVhbGl6YXJWYWxvcmVzKHRpdHVsbykge1xuICAgICAgICAgICAgICAgIGlmICh0aXR1bG8uc3RhdHVzID09PSAnQkFJWEFfTUFOVUFMJykge1xuICAgICAgICAgICAgICAgICAgICB0aXR1bG8udmFsb3JQYWdvID0gdGl0dWxvLnZhbG9yIC0gdGl0dWxvLmRlc2NvbnRvO1xuICAgICAgICAgICAgICAgICAgICBpZiAoIXRpdHVsby5kYXRhT2NvcnJlbmNpYSkge1xuICAgICAgICAgICAgICAgICAgICAgICAgdGl0dWxvLmRhdGFPY29ycmVuY2lhID0gbmV3IERhdGUoKTtcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgIHRpdHVsby52YWxvclBhZ28gPSAwO1xuICAgICAgICAgICAgICAgICAgICB0aXR1bG8uZGF0YU9jb3JyZW5jaWEgPSBudWxsO1xuICAgICAgICAgICAgICAgIH1cbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3NhdmUodGl0dWxvKSB7XG4gICAgICAgICAgICAgICAgVGl0dWxvLnNhdmUodGl0dWxvLCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ0NyaWFkbyB0aXR1bG8gZGUgbsO6bWVybyAnICsgZGF0YS5pZCxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICAgICAgX3ZvbHRhcigpO1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuXG4gICAgICAgICAgICBmdW5jdGlvbiBfcmVtb3ZlKHRpdHVsbykge1xuICAgICAgICAgICAgICAgIFRpdHVsby5yZW1vdmUoe2lkOiB0aXR1bG8uaWR9LCBmdW5jdGlvbiAoZGF0YSkge1xuICAgICAgICAgICAgICAgICAgICBfaW5pdCgpO1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbycsXG4gICAgICAgICAgICAgICAgICAgICAgICBib2R5OiAnUmVtb3ZpZG8gdGl0dWxvIGRlIG7Dum1lcm8gJyArIGRhdGEuaWQsXG4gICAgICAgICAgICAgICAgICAgICAgICB0eXBlOiAnYWxlcnQtc3VjY2VzcydcbiAgICAgICAgICAgICAgICAgICAgfV07XG4gICAgICAgICAgICAgICAgICAgIF92b2x0YXIoKTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3ZvbHRhcigpIHtcbiAgICAgICAgICAgICAgICAkbG9jYXRpb24ucGF0aCgnL3RpdHVsb3MvY2xpZW50ZS8nICsgJHNjb3BlLmNsaWVudGVJZCk7XG4gICAgICAgICAgICB9XG4gICAgICAgIH0pO1xufSgpKTtcbiIsIihmdW5jdGlvbiAoKSB7XG4gICAgJ3VzZSBzdHJpY3QnO1xuXG4gICAgYW5ndWxhci5tb2R1bGUoJ3NpY29iYUFwcCcpXG4gICAgICAgIC5jb250cm9sbGVyKCdUaXR1bG9MaXN0Q3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb3V0ZVBhcmFtcywgVGl0dWxvLCBDbGllbnRlKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5nZXRTdGF0dXNDbGFzcyA9IF9nZXRTdGF0dXNDbGFzcztcblxuICAgICAgICAgICAgX2luaXQoKTtcblxuICAgICAgICAgICAgZnVuY3Rpb24gX2luaXQoKSB7XG4gICAgICAgICAgICAgICAgJHNjb3BlLmNsaWVudGUgPSBDbGllbnRlLmdldCh7aWQ6ICRyb3V0ZVBhcmFtcy5jbGllbnRlSWR9KTtcbiAgICAgICAgICAgICAgICAkc2NvcGUudGl0dWxvcyA9IFRpdHVsby5idXNjYXJQb3JDbGllbnRlKHtjbGllbnRlSWQ6ICRyb3V0ZVBhcmFtcy5jbGllbnRlSWR9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX2dldFN0YXR1c0NsYXNzKHN0YXR1cykge1xuICAgICAgICAgICAgICAgIHJldHVybiBzdGF0dXMgPT09ICdQRU5ERU5URScgPyAnbGFiZWwtd2FybmluZycgOiAnbGFiZWwtc3VjY2Vzcyc7XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgfSk7XG59KCkpO1xuIiwiKGZ1bmN0aW9uICgpIHtcbiAgICAndXNlIHN0cmljdCc7XG5cbiAgICBhbmd1bGFyLm1vZHVsZSgnc2ljb2JhQXBwJylcbiAgICAgICAgLmNvbnRyb2xsZXIoJ01pa3JvdGlrQ3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsICRyb290U2NvcGUsICRyb3V0ZVBhcmFtcywgTWlrcm90aWspIHtcblxuICAgICAgICAgICAgJHNjb3BlLnNhdmUgPSBfc2F2ZTtcbiAgICAgICAgICAgICRzY29wZS5yZW1vdmUgPSBfcmVtb3ZlO1xuXG4gICAgICAgICAgICBfaW5pdCgpO1xuXG4gICAgICAgICAgICBmdW5jdGlvbiBfaW5pdCgpIHtcbiAgICAgICAgICAgICAgICBpZiAoJHJvdXRlUGFyYW1zLmlkKSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5taWtyb3RpayA9IE1pa3JvdGlrLmdldCh7aWQ6ICRyb3V0ZVBhcmFtcy5pZH0pO1xuICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgICRzY29wZS5taWtyb3RpayA9IHtwb3J0OiA4NzI4fTtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICB9XG5cbiAgICAgICAgICAgIGZ1bmN0aW9uIF9zYXZlKG1pa3JvdGlrKSB7XG4gICAgICAgICAgICAgICAgTWlrcm90aWsuc2F2ZShtaWtyb3RpaywgZnVuY3Rpb24gKGRhdGEpIHtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLm1pa3JvdGlrID0gZGF0YTtcbiAgICAgICAgICAgICAgICAgICAgJHJvb3RTY29wZS5tZXNzYWdlcyA9IFt7XG4gICAgICAgICAgICAgICAgICAgICAgICB0aXRsZTogJ1N1Y2Vzc286JyxcbiAgICAgICAgICAgICAgICAgICAgICAgIGJvZHk6ICdNaWtyb3RpayAnICsgZGF0YS5uYW1lICsgJyBmb2kgc2Fsdm8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICB9KTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgZnVuY3Rpb24gX3JlbW92ZShtaWtyb3Rpaykge1xuICAgICAgICAgICAgICAgIE1pa3JvdGlrLnJlbW92ZSh7aWQ6IG1pa3JvdGlrLmlkfSwgZnVuY3Rpb24gKCkge1xuICAgICAgICAgICAgICAgICAgICAkcm9vdFNjb3BlLm1lc3NhZ2VzID0gW3tcbiAgICAgICAgICAgICAgICAgICAgICAgIHRpdGxlOiAnU3VjZXNzbzonLFxuICAgICAgICAgICAgICAgICAgICAgICAgYm9keTogJ01pa3JvdGlrICcgKyBtaWtyb3Rpay5uYW1lICsgJyBmb2kgcmVtb3ZpZG8uJyxcbiAgICAgICAgICAgICAgICAgICAgICAgIHR5cGU6ICdhbGVydC1zdWNjZXNzJ1xuICAgICAgICAgICAgICAgICAgICB9XTtcbiAgICAgICAgICAgICAgICAgICAgJHNjb3BlLm1pa3JvdGlrID0ge3BvcnQ6IDg3Mjh9O1xuICAgICAgICAgICAgICAgIH0pO1xuICAgICAgICAgICAgfVxuICAgICAgICB9KTtcbn0oKSk7XG4iLCIoZnVuY3Rpb24gKCkge1xuICAgICd1c2Ugc3RyaWN0JztcblxuICAgIGFuZ3VsYXIubW9kdWxlKCdzaWNvYmFBcHAnKVxuICAgICAgICAuY29udHJvbGxlcignTWlrcm90aWtMaXN0Q3RybCcsIGZ1bmN0aW9uICgkc2NvcGUsIE1pa3JvdGlrKSB7XG5cbiAgICAgICAgICAgICRzY29wZS5taWtyb3Rpa3MgPSBNaWtyb3Rpay5xdWVyeSgpO1xuXG4gICAgICAgIH0pO1xufSgpKTtcbiJdLCJzb3VyY2VSb290IjoiL3NvdXJjZS8ifQ==
