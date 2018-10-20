(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Notas', ['$resource', function ($resource) {
            return $resource('api/notas/:id/:action', {id: '@id'},
                {
                    gerar: {
                        method: 'POST',
                        url: 'api/notas/gerar',
                        isArray: true
                    }
                });
        }]);
}());
