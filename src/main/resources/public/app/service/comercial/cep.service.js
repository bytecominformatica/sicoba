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
