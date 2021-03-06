package co.com.johan.green.servicio;

import co.com.johan.green.logica.RolLogica;
import co.com.johan.green.dto.RolDTO;
import co.com.johan.green.exception.ApplicationException;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @author Johan Lopez
 * @generated
 */
@Stateless
@Path("/Rol")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolServicio {

    @EJB
    private RolLogica logica;

    /**
     * retorna una lista con los Rol que se encuentran en la base de datos
     *
     * @return retorna una lista de RolDTO
     * @generated
     */
    @GET
    public List<RolDTO> obtenerTodosRols() {
        return logica.obtenerTodos();
    }

    /**
     * @param id identificador del elemento Rol
     * @return Rol del id dado
     * @generated
     */
    @GET
    @Path("/{id}")
    public RolDTO obtenerRol(@PathParam("id") Long id) {
        return logica.obtener(id);
    }

    /**
     * almacena la informacion de Rol
     *
     * @param dto Rol a guardar
     * @return Rol con los cambios realizados por el proceso de guardar
     * @generated
     */
    @POST
    public RolDTO guardarRol(RolDTO dto) {
        List<RolDTO> roles = logica.obtenerPorNombre(dto.getNombre());
        if (!roles.isEmpty()) {
            if (dto.getId() == null || !dto.getId().equals(roles.get(0).getId())) {
                throw new ApplicationException("El rol " + dto.getNombre() + " ya se encuentra parametrizado");
            }
        }
        if (dto.getId() != null) {
            logica.actualizar(dto);
            return dto;
        } else {
            return logica.guardar(dto);
        }
    }

    /**
     * elimina el registro Rol con el identificador dado
     *
     * @param id identificador del Rol
     * @generated
     */
    @DELETE
    @Path("/{id}")
    public void borrarRol(@PathParam("id") Long id) {
        logica.borrar(id);
    }

}
