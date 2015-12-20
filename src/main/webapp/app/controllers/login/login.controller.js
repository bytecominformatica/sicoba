'use strict';

angular.module('sicobaApp')
    .controller('LoginCtrl', function ($scope, $window) {

        $scope.loginGoogle = function(){
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
                oauth2.state
                ,"_self");
        };

        $scope.getGoogleInfo = function(){
            $http.get("https://www.googleapis.com/plus/v1/people/me?access_token=" + $scope.accessToken).success(function(data){
                console.log(data)
            });
        };

    });