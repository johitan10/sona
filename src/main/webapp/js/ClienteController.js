'use strict';

module.controller('ClienteCtrl', ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
        //listar
        $scope.$parent.titulo = 'Clientes';
        
        angular.element('*[required]').prev('label').addClass('label-required');
        angular.element('input.datepicker[required]').parent().prev('label').addClass('label-required');

        $scope.lista = [];
        $scope.datosFormulario = {};
        $scope.contactos = [];
        $scope.panelEditar = false;
        $scope.contacto = {};

        $scope.listar = function () {
            $http.get('./webresources/Cliente', {})
                    .success(function (data, status, headers, config) {
                        $scope.lista = data;
                    }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        $scope.guardarContacto = function () {
            if ($scope.datosFormulario.id) {
                $scope.contacto.cliente = $scope.datosFormulario;
                $http.post('./webresources/Contacto', JSON.stringify($scope.contacto), {}
                ).success(function (data, status, headers, config) {
                    $scope.buscarContactos($scope.datosFormulario.id);
                    angular.element('#modalContacto').modal('hide');
                }).error(function (data, status, headers, config) {
                    alert('Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
                });
            } else {
                $scope.contactos.push($scope.contacto);
                $scope.contacto = {};
                angular.element('#modalContacto').modal('hide');
            }
        };

        $scope.listar();
        //guardar
        $scope.nuevo = function () {
            $scope.panelEditar = true;
            $scope.datosFormulario = {};
            $scope.contacto = {};
            $scope.contactos = [];
        };

        $scope.guardar = function () {
            if (!$scope.datosFormulario.id) {
                $scope.datosFormulario.contactos = $scope.contactos;
            }
            $http.post('./webresources/Cliente', JSON.stringify($scope.datosFormulario), {}
            ).success(function (data, status, headers, config) {
                alert("Los datos han sido guardados con Exito");
                $scope.panelEditar = false;
                $scope.listar();
            }).error(function (data, status, headers, config) {
                alert('Error al guardar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };
        $scope.cancelar = function () {
            $scope.panelEditar = false;
            $scope.datosFormulario = {};
            $scope.listar();
        };


        //editar
        $scope.editar = function (data) {
            $scope.panelEditar = true;
            $scope.datosFormulario = data;
            $scope.buscarContactos(data.id);
        };

        $scope.editarContacto = function (data) {
            $scope.contacto = data;
        };
        
        $scope.limpiarNuevoContacto = function() {
            $scope.contacto = {};
        };

        $scope.buscarContactos = function (idCliente) {
            $http.get('./webresources/Contacto/Cliente/' + idCliente, {})
                    .success(function (data, status, headers, config) {
                        $scope.contactos = data;
                    }).error(function (data, status, headers, config) {
                alert('Error al consultar la informaci\xf3n, por favor intente m\xe1s tarde');
            });
        };

        //eliminar
        $scope.eliminar = function (data) {
            if (confirm('\xbfDesea elminar este registro?')) {
                $http.delete('./webresources/Cliente/' + data.id, {})
                        .success(function (data, status, headers, config) {
                            $scope.listar();
                        }).error(function (data, status, headers, config) {
                    alert('Error al eliminar la informaci\xf3n de Cliente, por favor intente m\xe1s tarde');
                });
            }
        };
    }]);