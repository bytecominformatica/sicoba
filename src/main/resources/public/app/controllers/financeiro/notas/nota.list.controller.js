(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NotaListCtrl', ['$scope', '$rootScope', '$http', 'Notas', 'GerencianetAccount', function ($scope, $rootScope, $http, Notas, GerencianetAccount) {
            $scope.findItensByDateOfProvision = _findItensByDateOfProvision;
            $scope.toogle = _toogle;
            $scope.totalSelecionado = _totalSelecionado;
            $scope.gerarArquivoSyncNfe = _gerarArquivoSyncNfe;

            _init();

            function _init() {
                $scope.params = {
                    start: new Date(),
                    end: new Date(),
                    byPaymentDate: true,
                    allSelected: false
                };

                $scope.statusList = [
                    {value: 'NEW', description: 'Novo'},
                    {value: 'WAITING', description: 'Aguardando'},
                    {value: 'PAID', description: 'Pago'},
                    {value: 'PAYMENT MANUAL', description: 'Pagamento Manual'},
                    {value: 'UNPAID', description: 'Não Pago'},
                    {value: 'REFUNDED', description: 'Devolvido'},
                    {value: 'CONTESTED', description: 'Contestado'},
                    {value: 'CANCELED', description: 'Cancelado'},
                    {value: 'LINK', description: 'Link'}
                ];

                $scope.accounts = GerencianetAccount.query();
            }

            function _toogle() {
                console.log($scope.allSelected);
                if ($scope.nfeItemList) {
                    $scope.nfeItemList.forEach(c => {
                        c.selected = $scope.allSelected;
                    })
                }
            }

            function _totalSelecionado() {
                if (!$scope.nfeItemList) return 0;
                return $scope.nfeItemList.filter(c => c.selected).length;
            }

            function _gerarArquivoSyncNfe() {
                let notasSelecionadas = $scope.nfeItemList.filter(c => c.selected);
                $http.post('api/notas/syncnfe/files', notasSelecionadas, {responseType: 'arraybuffer'})
                    .then(function (response) {
                        var file = new Blob([response.data], {type: 'application/zip'});
                        console.log('file', file);
                        var url = window.URL.createObjectURL(file);
                        console.log('url', url);
                        var a = document.createElement('a');
                        a.href = url;
                        a.download = 'syncnfe.zip';
                        a.click();
                        setTimeout(() => window.URL.revokeObjectURL(url), 100);
                    });
                // Notas.downloadSyncnfeFiles(notasSelecionadas, (data) => {
                //         var file = new Blob([data], {type: 'text/plain'});
                //         console.log('file', file);
                //         // console.log('file', file.data);
                //         var url = window.URL.createObjectURL(file);
                //         console.log('url', url);
                //         var a = document.createElement('a');
                //         a.href = url;
                //         a.download = 'master.txt';
                //         a.click();
                //
                //         // let byteArray = res.data;
                //         // byteArray = new Uint8Array(byteArray);
                //         // let file = new Blob([byteArray], { type: 'text/plan' });
                //         // let fileURL = URL.createObjectURL(file);
                //         // window.open(fileURL);
                //         //
                //         // let blob = new Blob([data], {type: "text/plan"});
                //         // console.log('blob2', blob);
                //         // let objectUrl = $window.URL.createObjectURL(blob);
                //         // // let file = headers('Content-Disposition');
                //         //
                //         // window.open(objectUrl);
                //
                //
                //         // let url = window.URL.createObjectURL(blob);
                //         // let a = document.createElement('a');
                //         // a.href = url;
                //         // a.download = 'master.txt';
                //         // a.click();
                //
                //         // For Firefox it is necessary to delay revoking the ObjectURL
                //         setTimeout(() => window.URL.revokeObjectURL(url), 100);
                //     });
            }

            function _findItensByDateOfProvision(params) {
                var params2 = {
                    start: moment(params.start).format('YYYY-MM-DD'),
                    end: moment(params.end).format('YYYY-MM-DD')
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.nfeItemList = Notas.findItensByDateOfProvision(params2);
            }

        }]);
}());
