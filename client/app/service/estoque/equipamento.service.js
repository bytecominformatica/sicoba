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
