(function () {
    'use strict';

    angular.module('sicobaApp')
        .filter('sumOfValue', function () {
            return function (data, key) {
                if (angular.isUndefined(data) && angular.isUndefined(key))
                    return 0;
                var sum = 0;


                angular.forEach(data, function (v) {
                    sum = sum + Number(v[key]);
                });
                return sum;
            };
        });
}());
