'use strict';

module.controller('SalariosRecargosCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {

        $scope.$parent.titulo = 'Salarios y Recargos';

        angular.element('*[required]').prev('label').addClass('label-required');
        //listar
        $scope.lista = [];
        $scope.datosFormulario = {};
        $scope.panelEditar = false;
        $scope.panelListar = true;
        $scope.panelResumen = false;
        $scope.cargo = {};
        $scope.valorTotal = 0;
        $scope.Math = window.Math;

        $scope.listar = function () {
            $http.get('./webresources/SalariosRecargos', {})
                    .success(function (data, status, headers, config) {
                        $scope.lista = data;
                    }).error(function (data, status, headers, config) {
                bootbox.alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        $scope.listarCargo = function () {
            $http.get('./webresources/Cargo', {})
                    .success(function (data, status, headers, config) {
                        $scope.listaCargo = data;
                    }).error(function (data, status, headers, config) {
                bootbox.alert('Error al consultar la informaci\xf3n de cargo, por favor intente m\xe1s tarde');
            });
        };
        $scope.listarCargo();


        $scope.listar();
        //guardar
        $scope.nuevo = function () {
            $scope.panelEditar = true;
            $scope.panelListar = false;
            $scope.panelResumen = false;
            $scope.datosFormulario = {};
            $scope.datosFormulario.activo = false;
        };

        $scope.guardar = function () {
            $scope.errores = {};
            var error = false;

            if (error)
                return;
            $http.post('./webresources/SalariosRecargos', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                bootbox.alert("Los datos han sido guardados con Éxito");
                $scope.cancelar();
                $scope.buscarPorCargo();
            }).error(function (data, status, headers, config) {
                bootbox.alert((data && data.mensaje) || 'Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };
        $scope.cancelar = function () {
            $scope.panelEditar = false;
            $scope.panelListar = true;
            $scope.panelResumen = false;
            $scope.datosFormulario = {};
            $scope.buscarPorCargo();
        };

        $scope.resumen = function () {
            $scope.buscarSalarios();
            $scope.panelEditar = false;
            $scope.panelListar = false;
            $scope.panelResumen = true;
        };

        //editar
        $scope.editar = function (data) {
            $scope.panelEditar = true;
            $scope.panelListar = false;
            $scope.panelResumen = false;
            $scope.datosFormulario = data;
        };

        $scope.calculaTotal = function () {
            if ($scope.datosFormulario.cantidad && $scope.datosFormulario.valor) {
                $scope.datosFormulario.total = Math.ceil((parseFloat($scope.datosFormulario.cantidad) * parseFloat($scope.datosFormulario.valor)) / 100);
            }
        };
        //eliminar
        $scope.guardarCargo = function () {
            $http.post('./webresources/Cargo', JSON.stringify($scope.cargo), {}
            ).success(function (data, status, headers, config) {
                $scope.listarCargo();
                $scope.cargo = {};
                angular.element('#modalCargo').modal('hide');
            }).error(function (data, status, headers, config) {
                bootbox.alert((data && data.mensaje) || 'Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        $scope.buscarPorCargo = function () {
            $scope.valorTotal = 0;
            if (!$scope.filtroCargo || $scope.filtroCargo === '') {
                $scope.listar();
            } else {
                $http.get('./webresources/SalariosRecargos/Cargo/' + $scope.filtroCargo, {})
                        .success(function (data, status, headers, config) {
                            $scope.valorTotal = 0;
                            $scope.lista = data;
                            angular.forEach($scope.lista, function (val) {
                                if (val.activo) {
                                    var valor = parseFloat(val.cantidad) * parseFloat(val.valor);
                                    if (valor > 0) {
                                        $scope.valorTotal += valor / 100;
                                    }
                                }
                            });
                        }).error(function (data, status, headers, config) {
                    bootbox.alert('Error al consultar la informaci\xf3n de ciudad, por favor intente m\xe1s tarde');
                });
            }
        };

        $scope.listarUnidades = function () {
            $http.get('./webresources/Unidad', {})
                    .success(function (data, status, headers, config) {
                        $scope.listaUnidades = data;
                    }).error(function (data, status, headers, config) {
                bootbox.alert((data && data.mensaje) || 'Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };
        $scope.listarUnidades();

        $scope.guardarUnidad = function () {
            $http.post('./webresources/Unidad', JSON.stringify($scope.unidad), {}
            ).success(function (data, status, headers, config) {
                angular.element('#modalUnidad').modal('hide');
                $scope.listarUnidades();
                $scope.unidad = {};
            }).error(function (data, status, headers, config) {
                bootbox.alert((data && data.mensaje) || 'Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        $scope.buscarSalarios = function () {
            $http.get('./webresources/Cargo/Valor', {})
                    .success(function (data, status, headers, config) {
                        $scope.listaSalarios = data;
                    }).error(function (data, status, headers, config) {
                bootbox.alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

    }]);
