(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Carnet', ['$resource', function ($resource) {
            return $resource('api/carnets/:id/:action/:actionId', {id: '@id'},
                {
                    new: {
                        method: 'GET',
                        url: 'api/carnets/new'
                    },
                    cancel: {
                        method: 'PUT',
                        params: {action: "cancel"}
                    },
                    updateParcelExpireAt: {
                        method: 'PUT',
                        params: {id: '@carnet.id', action: "parcels", actionId: '@parcel'}
                    },
                    refreshUrlsNotification: {
                        method: 'PUT',
                        params: {id: "all", action: "metadata"}
                    }
                });
        }]);
}());
