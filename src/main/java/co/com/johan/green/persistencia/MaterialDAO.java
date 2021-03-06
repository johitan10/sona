package co.com.johan.green.persistencia;

import co.com.johan.green.persistencia.entity.Material;
import java.util.List;
import javax.persistence.*;
import javax.ejb.Stateless;

/**
 * @generated @author Johan Lopez
 */
@Stateless
public class MaterialDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Retorna una lista con los Material que se encuentran en la base de datos.
     *
     * @return lista de Material
     * @generated
     */
    public List<Material> obtenerTodos() {
        return em.createNamedQuery("material.obtenerTodos").getResultList();
    }
    
    public List<Material> obtenerPorDescripcion(String descripcion) {
        return em.createNamedQuery("material.obtenerPorDescripcion")
                .setParameter("descripcion", "%" + descripcion.toUpperCase() + "%")
                .getResultList();
    }

    /**
     * Obtiene Material dado su identificador.
     *
     * @param id identificador del elemento Material
     * @return Material del id dado
     * @generated
     */
    public Material obtener(Long id) {
        return em.find(Material.class, id);
    }

    /**
     * Almacena la informacion de Material
     *
     * @param entidad Material a guardar
     * @return Material con los cambios realizados por el proceso de guardar
     * @generated
     */
    public Material guardar(Material entidad) {
        em.persist(entidad);
        return entidad;
    }

    /**
     * Elimina el registro Material con el identificador dado.
     *
     * @param id identificador del Material
     * @generated
     */
    public void borrar(Long id) {
        em.remove(em.find(Material.class, id));
    }

    /**
     * Actualiza la informacion de Material.
     *
     * @param entidad Material a actualizar
     * @generated
     */
    public void actualizar(Material entidad) {
        em.merge(entidad);
    }

}
