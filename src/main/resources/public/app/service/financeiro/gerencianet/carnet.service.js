(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Carnet', ['$resource', function ($resource) {
            return $resource('api/carnets/:id/:action', {id: '@id'},
                {
                    new: {
                        method: 'GET',
                        url: 'api/carnets/new'
                    },
                    cancel: {
                        method: 'PUT',
                        params: {action: "cancel"}
                    }
                });
        }]);
}());
