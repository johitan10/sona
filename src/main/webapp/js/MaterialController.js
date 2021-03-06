'use strict';

/* Controllers */
//var module = angular.module('adminmaterial.controllers', []);

module.controller('MaterialCtrl', ['$scope', '$filter', '$http', 'NgTableParams', function ($scope, $filter, $http, ngTableParams) {
        //listar

        $scope.$parent.titulo = 'Materiales';

        $scope.lista = {};
        $scope.datosFormulario = {};
        $scope.panelEditar = false;
        $scope.nombre = '';
        $scope.listar = function () {
            if ($scope.nombre === '') {
                $http.get('./webresources/material', {})
                        .success(function (data, status, headers, config) {
                            $scope.lista = data;
                            $scope.tableParams = new ngTableParams({}, {dataset: $scope.lista});
                        }).error(function (data, status, headers, config) {
                    bootbox.alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
                });
            } else {
                $http.get('./webresources/material/descripcion/' + $scope.nombre, {})
                        .success(function (data, status, headers, config) {
                            $scope.lista = data;
                            $scope.tableParams = new ngTableParams({}, {dataset: $scope.lista});
                        }).error(function (data, status, headers, config) {
                    bootbox.alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
                });
            }

        };
        $scope.listar();
        //guardar
        $scope.nuevo = function () {
            $scope.panelEditar = true;
            $scope.datosFormulario = {};
        };

        $scope.guardar = function () {
            $scope.errores = {};
            var error = false;

            if (error)
                return;
            $http.post('./webresources/material', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                bootbox.alert("Los datos han sido guardados con Éxito");
                $scope.panelEditar = false;
                $scope.listar();
            }).error(function (data, status, headers, config) {
                bootbox.alert((data && data.mensaje) || 'Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };
        $scope.cancelar = function () {
            $scope.panelEditar = false;
            $scope.datosFormulario = {};
        };

        //editar
        $scope.editar = function (data) {
            $scope.listar();
            $scope.panelEditar = true;
            $scope.datosFormulario = data;
        };
        //eliminar
        $scope.eliminar = function (data) {
            if (confirm('�Desea elminar este registro?')) {
                $http.delete('./webresources/material/' + data.id, {})
                        .success(function (data, status, headers, config) {
                            $scope.listar();
                        }).error(function (data, status, headers, config) {
                    bootbox.alert('Error al eliminar la informaci\xf3n de material, por favor intente m\xe1s tarde');
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
    }]);
